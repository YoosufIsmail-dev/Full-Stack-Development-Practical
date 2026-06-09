import { useState, useEffect, useRef } from "react";

const CATEGORIES = [
  { label: "Underweight", min: 0,    max: 18.5, color: "#38BDF8", bg: "#0C2233" },
  { label: "Normal",      min: 18.5, max: 25,   color: "#34D399", bg: "#0C2318" },
  { label: "Overweight",  min: 25,   max: 30,   color: "#FBBF24", bg: "#231C09" },
  { label: "Obese",       min: 30,   max: 999,  color: "#F87171", bg: "#230E0E" },
];

function getCategory(bmi) {
  return CATEGORIES.find(c => bmi >= c.min && bmi < c.max) || CATEGORIES[3];
}

function ArcGauge({ bmi, category }) {
  const canvasRef = useRef(null);
  const animRef = useRef(null);
  const currentRef = useRef(0);

  useEffect(() => {
    const canvas = canvasRef.current;
    if (!canvas) return;
    const ctx = canvas.getContext("2d");
    const W = canvas.width;
    const H = canvas.height;
    const cx = W / 2;
    const cy = H * 0.72;
    const R = W * 0.38;

    // Map BMI 10–45 to 0–1
    const targetFill = Math.min(Math.max((bmi - 10) / 35, 0), 1);
    const startAngle = Math.PI;
    const totalAngle = Math.PI;

    cancelAnimationFrame(animRef.current);

    function draw(fill) {
      ctx.clearRect(0, 0, W, H);

      // Track background
      ctx.beginPath();
      ctx.arc(cx, cy, R, startAngle, startAngle + totalAngle);
      ctx.strokeStyle = "#1E293B";
      ctx.lineWidth = 18;
      ctx.lineCap = "round";
      ctx.stroke();

      // Zone ticks
      const zones = [
        { at: (18.5 - 10) / 35, color: "#38BDF8" },
        { at: (25 - 10) / 35, color: "#FBBF24" },
        { at: (30 - 10) / 35, color: "#F87171" },
      ];
      zones.forEach(({ at, color }) => {
        const a = startAngle + at * totalAngle;
        ctx.beginPath();
        ctx.arc(cx, cy, R, a - 0.01, a + 0.01);
        ctx.strokeStyle = color;
        ctx.lineWidth = 22;
        ctx.stroke();
      });

      // Filled arc
      if (fill > 0) {
        ctx.beginPath();
        ctx.arc(cx, cy, R, startAngle, startAngle + fill * totalAngle);
        ctx.strokeStyle = category.color;
        ctx.lineWidth = 18;
        ctx.lineCap = "round";
        ctx.shadowColor = category.color;
        ctx.shadowBlur = 16;
        ctx.stroke();
        ctx.shadowBlur = 0;
      }

      // Needle dot
      const needleAngle = startAngle + fill * totalAngle;
      const nx = cx + R * Math.cos(needleAngle);
      const ny = cy + R * Math.sin(needleAngle);
      ctx.beginPath();
      ctx.arc(nx, ny, 8, 0, Math.PI * 2);
      ctx.fillStyle = "#F8FAFC";
      ctx.shadowColor = category.color;
      ctx.shadowBlur = 12;
      ctx.fill();
      ctx.shadowBlur = 0;

      // Zone labels
      const zoneLabels = [
        { label: "Under", at: 0.12 },
        { label: "Normal", at: 0.38 },
        { label: "Over", at: 0.64 },
        { label: "Obese", at: 0.88 },
      ];
      ctx.font = "bold 9px Inter, sans-serif";
      ctx.textAlign = "center";
      zoneLabels.forEach(({ label, at }) => {
        const a = startAngle + at * totalAngle;
        const lx = cx + (R - 32) * Math.cos(a);
        const ly = cy + (R - 32) * Math.sin(a);
        ctx.fillStyle = "#475569";
        ctx.fillText(label, lx, ly);
      });
    }

    function animate() {
      const diff = targetFill - currentRef.current;
      if (Math.abs(diff) < 0.002) {
        currentRef.current = targetFill;
        draw(targetFill);
        return;
      }
      currentRef.current += diff * 0.1;
      draw(currentRef.current);
      animRef.current = requestAnimationFrame(animate);
    }

    animate();
    return () => cancelAnimationFrame(animRef.current);
  }, [bmi, category]);

  return (
    <canvas
      ref={canvasRef}
      width={260}
      height={160}
      style={{ width: "100%", maxWidth: 260 }}
    />
  );
}

export default function BMICalculator() {
  const [unit, setUnit] = useState("metric");
  const [height, setHeight] = useState("");
  const [weight, setWeight] = useState("");
  const [heightFt, setHeightFt] = useState("");
  const [heightIn, setHeightIn] = useState("");
  const [bmi, setBmi] = useState(null);
  const [category, setCategory] = useState(null);
  const [shake, setShake] = useState(false);

  function calculate() {
    let h, w;
    if (unit === "metric") {
      h = parseFloat(height) / 100;
      w = parseFloat(weight);
    } else {
      const totalIn = (parseFloat(heightFt) || 0) * 12 + (parseFloat(heightIn) || 0);
      h = totalIn * 0.0254;
      w = parseFloat(weight) * 0.453592;
    }
    if (!h || !w || h <= 0 || w <= 0) {
      setShake(true);
      setTimeout(() => setShake(false), 500);
      return;
    }
    const result = w / (h * h);
    setBmi(result);
    setCategory(getCategory(result));
  }

  function reset() {
    setHeight(""); setWeight("");
    setHeightFt(""); setHeightIn("");
    setBmi(null); setCategory(null);
  }

  const styles = {
    page: {
      minHeight: "100vh",
      background: "linear-gradient(135deg, #0F172A 0%, #0F2847 100%)",
      display: "flex",
      alignItems: "center",
      justifyContent: "center",
      fontFamily: "'Inter', sans-serif",
      padding: "24px 16px",
    },
    card: {
      background: "#111827",
      borderRadius: 24,
      padding: "36px 32px",
      width: "100%",
      maxWidth: 400,
      boxShadow: "0 32px 80px rgba(0,0,0,0.6), 0 0 0 1px #1E293B",
      transition: "background 0.5s",
      ...(category ? { background: category.bg } : {}),
    },
    header: {
      marginBottom: 28,
    },
    eyebrow: {
      fontSize: 11,
      fontWeight: 700,
      letterSpacing: "0.15em",
      color: "#06B6D4",
      textTransform: "uppercase",
      marginBottom: 6,
    },
    title: {
      fontSize: 26,
      fontWeight: 800,
      color: "#F8FAFC",
      margin: 0,
      fontFamily: "'Space Grotesk', 'Inter', sans-serif",
      letterSpacing: "-0.5px",
    },
    unitToggle: {
      display: "flex",
      background: "#1E293B",
      borderRadius: 10,
      padding: 3,
      marginBottom: 24,
      gap: 3,
    },
    unitBtn: (active) => ({
      flex: 1,
      padding: "8px 0",
      border: "none",
      borderRadius: 8,
      cursor: "pointer",
      fontWeight: 600,
      fontSize: 13,
      transition: "all 0.2s",
      background: active ? "#06B6D4" : "transparent",
      color: active ? "#0F172A" : "#64748B",
    }),
    label: {
      display: "block",
      fontSize: 12,
      fontWeight: 600,
      color: "#64748B",
      marginBottom: 6,
      letterSpacing: "0.05em",
      textTransform: "uppercase",
    },
    input: {
      width: "100%",
      background: "#1E293B",
      border: "1.5px solid #334155",
      borderRadius: 10,
      padding: "12px 14px",
      color: "#F8FAFC",
      fontSize: 16,
      fontWeight: 600,
      outline: "none",
      boxSizing: "border-box",
      transition: "border-color 0.2s",
    },
    inputGroup: {
      display: "flex",
      gap: 10,
    },
    inputWrapper: {
      marginBottom: 16,
    },
    calcBtn: {
      width: "100%",
      padding: "14px",
      background: "linear-gradient(135deg, #06B6D4, #0284C7)",
      color: "#0F172A",
      fontWeight: 800,
      fontSize: 15,
      border: "none",
      borderRadius: 12,
      cursor: "pointer",
      marginTop: 6,
      letterSpacing: "0.03em",
      boxShadow: "0 4px 24px rgba(6,182,212,0.3)",
      transition: "transform 0.15s, box-shadow 0.15s",
    },
    resultSection: {
      marginTop: 28,
      textAlign: "center",
    },
    bmiValue: {
      fontSize: 52,
      fontWeight: 900,
      letterSpacing: "-2px",
      fontFamily: "'Space Grotesk', 'Inter', sans-serif",
      lineHeight: 1,
    },
    categoryLabel: {
      fontSize: 14,
      fontWeight: 700,
      letterSpacing: "0.1em",
      textTransform: "uppercase",
      marginTop: 4,
      marginBottom: 2,
    },
    interpretation: {
      fontSize: 12,
      color: "#94A3B8",
      marginTop: 4,
    },
    resetBtn: {
      marginTop: 18,
      background: "transparent",
      border: "1.5px solid #334155",
      color: "#64748B",
      padding: "9px 20px",
      borderRadius: 9,
      cursor: "pointer",
      fontSize: 13,
      fontWeight: 600,
    },
    gaugeWrap: {
      display: "flex",
      justifyContent: "center",
      marginBottom: 8,
    },
    shakeAnim: shake ? {
      animation: "shake 0.4s ease",
    } : {},
  };

  const bmiInfo = bmi && [
    { label: "BMI", value: bmi.toFixed(1) },
    { label: "Category", value: category.label },
    { label: "Healthy range", value: "18.5 – 24.9" },
  ];

  return (
    <>
      <style>{`
        @import url('https://fonts.googleapis.com/css2?family=Space+Grotesk:wght@700;800;900&family=Inter:wght@400;600;700;800&display=swap');
        * { box-sizing: border-box; }
        input[type=number]::-webkit-inner-spin-button { -webkit-appearance: none; }
        input:focus { border-color: #06B6D4 !important; }
        @keyframes shake {
          0%,100%{transform:translateX(0)}
          20%{transform:translateX(-6px)}
          40%{transform:translateX(6px)}
          60%{transform:translateX(-4px)}
          80%{transform:translateX(4px)}
        }
        @keyframes fadeUp {
          from{opacity:0;transform:translateY(12px)}
          to{opacity:1;transform:translateY(0)}
        }
        .result-in { animation: fadeUp 0.4s ease forwards; }
        .calc-btn:hover { transform: translateY(-1px); box-shadow: 0 8px 32px rgba(6,182,212,0.45) !important; }
        .calc-btn:active { transform: translateY(0); }
        .reset-btn:hover { border-color: #475569; color: #94A3B8; }
      `}</style>
      <div style={styles.page}>
        <div style={styles.card}>
          <div style={styles.header}>
            <div style={styles.eyebrow}>Health Tool</div>
            <h1 style={styles.title}>BMI Calculator</h1>
          </div>

          {/* Unit toggle */}
          <div style={styles.unitToggle}>
            <button style={styles.unitBtn(unit === "metric")} onClick={() => { setUnit("metric"); reset(); }}>
              Metric (cm/kg)
            </button>
            <button style={styles.unitBtn(unit === "imperial")} onClick={() => { setUnit("imperial"); reset(); }}>
              Imperial (ft/lbs)
            </button>
          </div>

          {/* Inputs */}
          <div style={styles.shakeAnim}>
            {unit === "metric" ? (
              <div style={styles.inputWrapper}>
                <label style={styles.label}>Height (cm)</label>
                <input
                  type="number"
                  placeholder="e.g. 165"
                  value={height}
                  onChange={e => setHeight(e.target.value)}
                  style={styles.input}
                />
              </div>
            ) : (
              <div style={styles.inputWrapper}>
                <label style={styles.label}>Height</label>
                <div style={styles.inputGroup}>
                  <input
                    type="number"
                    placeholder="ft"
                    value={heightFt}
                    onChange={e => setHeightFt(e.target.value)}
                    style={{ ...styles.input, width: "50%" }}
                  />
                  <input
                    type="number"
                    placeholder="in"
                    value={heightIn}
                    onChange={e => setHeightIn(e.target.value)}
                    style={{ ...styles.input, width: "50%" }}
                  />
                </div>
              </div>
            )}

            <div style={styles.inputWrapper}>
              <label style={styles.label}>Weight ({unit === "metric" ? "kg" : "lbs"})</label>
              <input
                type="number"
                placeholder={unit === "metric" ? "e.g. 84" : "e.g. 185"}
                value={weight}
                onChange={e => setWeight(e.target.value)}
                style={styles.input}
                onKeyDown={e => e.key === "Enter" && calculate()}
              />
            </div>
          </div>

          <button className="calc-btn" style={styles.calcBtn} onClick={calculate}>
            Calculate BMI
          </button>

          {/* Result */}
          {bmi && category && (
            <div className="result-in" style={styles.resultSection}>
              <div style={styles.gaugeWrap}>
                <ArcGauge bmi={bmi} category={category} />
              </div>

              <div style={{ ...styles.bmiValue, color: category.color }}>
                {bmi.toFixed(1)}
              </div>
              <div style={{ ...styles.categoryLabel, color: category.color }}>
                {category.label}
              </div>

              {/* Info row */}
              <div style={{
                display: "flex",
                justifyContent: "center",
                gap: 20,
                marginTop: 18,
                padding: "14px 0",
                borderTop: "1px solid #1E293B",
                borderBottom: "1px solid #1E293B",
              }}>
                {bmiInfo.map(({ label, value }) => (
                  <div key={label} style={{ textAlign: "center" }}>
                    <div style={{ fontSize: 11, color: "#64748B", fontWeight: 600, letterSpacing: "0.05em", textTransform: "uppercase", marginBottom: 3 }}>{label}</div>
                    <div style={{ fontSize: 14, color: "#F8FAFC", fontWeight: 700 }}>{value}</div>
                  </div>
                ))}
              </div>

              <p style={styles.interpretation}>
                BMI is a screening tool, not a diagnostic measure.
              </p>

              <button className="reset-btn" style={styles.resetBtn} onClick={reset}>
                Reset
              </button>
            </div>
          )}
        </div>
      </div>
    </>
  );
}
