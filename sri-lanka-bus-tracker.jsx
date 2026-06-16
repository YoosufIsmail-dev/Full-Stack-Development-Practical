import { useState, useEffect, useRef, useCallback } from "react";

// ── Simulated Data ────────────────────────────────────────────────
const SRI_LANKA_CITIES = [
  { name: "Colombo", lat: 6.9271, lng: 79.8612 },
  { name: "Kandy", lat: 7.2906, lng: 80.6337 },
  { name: "Galle", lat: 6.0535, lng: 80.2210 },
  { name: "Jaffna", lat: 9.6615, lng: 80.0255 },
  { name: "Matara", lat: 5.9485, lng: 80.5353 },
  { name: "Kurunegala", lat: 7.4818, lng: 80.3609 },
  { name: "Negombo", lat: 7.2008, lng: 79.8380 },
  { name: "Anuradhapura", lat: 8.3114, lng: 80.4037 },
  { name: "Batticaloa", lat: 7.7102, lng: 81.6924 },
  { name: "Trincomalee", lat: 8.5874, lng: 81.2152 },
  { name: "Nuwara Eliya", lat: 6.9497, lng: 80.7891 },
];

const ROUTES = [
  { id: "138", name: "Colombo → Kandy", from: "Colombo", to: "Kandy", stops: 14, color: "#3B82F6" },
  { id: "120", name: "Colombo → Galle", from: "Colombo", to: "Galle", stops: 12, color: "#10B981" },
  { id: "154", name: "Colombo → Negombo", from: "Colombo", to: "Negombo", stops: 8, color: "#F59E0B" },
  { id: "177", name: "Kandy → Nuwara Eliya", from: "Kandy", to: "Nuwara Eliya", stops: 10, color: "#EF4444" },
  { id: "99", name: "Colombo → Kurunegala", from: "Colombo", to: "Kurunegala", stops: 9, color: "#8B5CF6" },
  { id: "48", name: "Galle → Matara", from: "Galle", to: "Matara", stops: 6, color: "#06B6D4" },
];

const DRIVERS = ["Suresh Perera", "Nimal Fernando", "Kamal Silva", "Rajan Krishnan", "Anura Bandara", "Priya Jayasuriya"];

function generateBuses() {
  return ROUTES.map((route, i) => {
    const fromCity = SRI_LANKA_CITIES.find(c => c.name === route.from);
    const toCity = SRI_LANKA_CITIES.find(c => c.name === route.to);
    const progress = Math.random();
    return {
      id: `BUS-${route.id}-${i + 1}`,
      routeId: route.id,
      routeName: route.name,
      driver: DRIVERS[i],
      lat: fromCity.lat + (toCity.lat - fromCity.lat) * progress,
      lng: fromCity.lng + (toCity.lng - fromCity.lng) * progress,
      speed: Math.floor(40 + Math.random() * 40),
      capacity: Math.floor(60 + Math.random() * 40),
      passengers: Math.floor(20 + Math.random() * 60),
      eta: Math.floor(10 + Math.random() * 50),
      distance: Math.floor(5 + Math.random() * 80),
      status: Math.random() > 0.1 ? "active" : "delayed",
      progress,
      color: route.color,
      from: route.from,
      to: route.to,
    };
  });
}

// ── Tiny SVG Map (Canvas-based Sri Lanka) ────────────────────────
const MAP_W = 600, MAP_H = 520;
// Sri Lanka bounding: lat 5.9–9.9, lng 79.6–81.9
function geoToCanvas(lat, lng) {
  const x = ((lng - 79.5) / (82.0 - 79.5)) * MAP_W;
  const y = MAP_H - ((lat - 5.7) / (10.1 - 5.7)) * MAP_H;
  return { x, y };
}

function BusMapCanvas({ buses, selectedBus, onSelectBus, darkMode }) {
  const canvasRef = useRef(null);
  const animRef = useRef(0);

  const draw = useCallback(() => {
    const canvas = canvasRef.current;
    if (!canvas) return;
    const ctx = canvas.getContext("2d");
    const bg = darkMode ? "#0f172a" : "#e0f2fe";
    const landColor = darkMode ? "#1e3a5f" : "#bfdbfe";
    const gridColor = darkMode ? "#1e293b" : "#93c5fd";
    const textColor = darkMode ? "#94a3b8" : "#1e40af";

    ctx.clearRect(0, 0, MAP_W, MAP_H);

    // Ocean background
    ctx.fillStyle = bg;
    ctx.fillRect(0, 0, MAP_W, MAP_H);

    // Grid lines
    ctx.strokeStyle = gridColor;
    ctx.lineWidth = 0.5;
    ctx.setLineDash([4, 4]);
    for (let i = 0; i < MAP_W; i += 60) {
      ctx.beginPath(); ctx.moveTo(i, 0); ctx.lineTo(i, MAP_H); ctx.stroke();
    }
    for (let j = 0; j < MAP_H; j += 60) {
      ctx.beginPath(); ctx.moveTo(0, j); ctx.lineTo(MAP_W, j); ctx.stroke();
    }
    ctx.setLineDash([]);

    // Sri Lanka simplified shape
    const slShape = [
      [8.3, 79.8], [9.8, 80.1], [9.7, 80.6], [9.3, 80.7],
      [8.6, 81.2], [8.0, 81.8], [7.7, 81.8], [7.2, 81.8],
      [6.8, 81.6], [6.0, 80.6], [5.9, 80.2], [6.0, 79.8],
      [6.8, 79.8], [7.5, 79.7], [8.0, 79.7], [8.3, 79.8],
    ];
    ctx.beginPath();
    slShape.forEach(([lat, lng], i) => {
      const { x, y } = geoToCanvas(lat, lng);
      i === 0 ? ctx.moveTo(x, y) : ctx.lineTo(x, y);
    });
    ctx.closePath();
    ctx.fillStyle = landColor;
    ctx.fill();
    ctx.strokeStyle = darkMode ? "#3b82f6" : "#1d4ed8";
    ctx.lineWidth = 1.5;
    ctx.stroke();

    // Routes
    ROUTES.forEach(route => {
      const fromCity = SRI_LANKA_CITIES.find(c => c.name === route.from);
      const toCity = SRI_LANKA_CITIES.find(c => c.name === route.to);
      if (!fromCity || !toCity) return;
      const p1 = geoToCanvas(fromCity.lat, fromCity.lng);
      const p2 = geoToCanvas(toCity.lat, toCity.lng);
      ctx.beginPath();
      ctx.moveTo(p1.x, p1.y);
      ctx.lineTo(p2.x, p2.y);
      ctx.strokeStyle = route.color + "55";
      ctx.lineWidth = 2;
      ctx.setLineDash([6, 4]);
      ctx.stroke();
      ctx.setLineDash([]);
    });

    // City dots
    SRI_LANKA_CITIES.forEach(city => {
      const { x, y } = geoToCanvas(city.lat, city.lng);
      ctx.beginPath();
      ctx.arc(x, y, 4, 0, Math.PI * 2);
      ctx.fillStyle = darkMode ? "#60a5fa" : "#1d4ed8";
      ctx.fill();
      ctx.fillStyle = textColor;
      ctx.font = "10px Inter, sans-serif";
      ctx.fillText(city.name, x + 6, y + 3);
    });

    // Buses
    buses.forEach(bus => {
      const { x, y } = geoToCanvas(bus.lat, bus.lng);
      const isSelected = selectedBus?.id === bus.id;
      const pulse = Date.now() / 500;

      if (isSelected) {
        ctx.beginPath();
        ctx.arc(x, y, 14 + Math.sin(pulse) * 4, 0, Math.PI * 2);
        ctx.strokeStyle = bus.color + "88";
        ctx.lineWidth = 2;
        ctx.stroke();
      }

      // Bus icon circle
      ctx.beginPath();
      ctx.arc(x, y, isSelected ? 10 : 7, 0, Math.PI * 2);
      ctx.fillStyle = bus.color;
      ctx.fill();
      ctx.strokeStyle = "#fff";
      ctx.lineWidth = 1.5;
      ctx.stroke();

      // Bus label
      ctx.fillStyle = "#fff";
      ctx.font = `bold ${isSelected ? 8 : 6}px Inter, sans-serif`;
      ctx.textAlign = "center";
      ctx.fillText(bus.routeId, x, y + 2.5);
      ctx.textAlign = "left";
    });

    animRef.current = requestAnimationFrame(draw);
  }, [buses, selectedBus, darkMode]);

  useEffect(() => {
    animRef.current = requestAnimationFrame(draw);
    return () => cancelAnimationFrame(animRef.current);
  }, [draw]);

  const handleClick = (e) => {
    const rect = canvasRef.current.getBoundingClientRect();
    const scaleX = MAP_W / rect.width;
    const scaleY = MAP_H / rect.height;
    const mx = (e.clientX - rect.left) * scaleX;
    const my = (e.clientY - rect.top) * scaleY;
    let closest = null, minDist = 20;
    buses.forEach(bus => {
      const { x, y } = geoToCanvas(bus.lat, bus.lng);
      const d = Math.sqrt((x - mx) ** 2 + (y - my) ** 2);
      if (d < minDist) { minDist = d; closest = bus; }
    });
    if (closest) onSelectBus(closest);
  };

  return (
    <canvas
      ref={canvasRef}
      width={MAP_W}
      height={MAP_H}
      onClick={handleClick}
      style={{ width: "100%", height: "100%", cursor: "crosshair", borderRadius: "12px" }}
    />
  );
}

// ── Main App ─────────────────────────────────────────────────────
export default function App() {
  const [darkMode, setDarkMode] = useState(true);
  const [buses, setBuses] = useState(generateBuses);
  const [selectedBus, setSelectedBus] = useState(null);
  const [activeTab, setActiveTab] = useState("map");
  const [lang, setLang] = useState("en");
  const [searchRoute, setSearchRoute] = useState("");
  const [searchFrom, setSearchFrom] = useState("");
  const [searchTo, setSearchTo] = useState("");
  const [favorites, setFavorites] = useState(["138", "120"]);
  const [notifications, setNotifications] = useState([
    { id: 1, msg: "Bus 138 arriving in 5 min at Pettah", time: "2 min ago", read: false },
    { id: 2, msg: "Route 120 is on schedule", time: "8 min ago", read: false },
    { id: 3, msg: "Bus 177 delayed by 10 minutes", time: "15 min ago", read: true },
  ]);
  const [showNotif, setShowNotif] = useState(false);
  const [tick, setTick] = useState(0);
  const [lastUpdate, setLastUpdate] = useState(new Date());

  const T = {
    en: {
      title: "Sri Lanka Smart Bus Tracker", search: "Search Routes", map: "Live Map",
      routes: "Routes", dashboard: "Dashboard", admin: "Admin",
      busInfo: "Bus Information", driver: "Driver", location: "Location",
      eta: "ETA", distance: "Distance", speed: "Speed", capacity: "Capacity",
      passengers: "Passengers", status: "Status", active: "Active", delayed: "Delayed",
      favRoutes: "Favourite Routes", recentSearches: "Recent Searches",
      notifications: "Notifications", darkMode: "Dark Mode", lightMode: "Light Mode",
      selectBus: "Click a bus on the map to see details",
      liveUpdates: "Live updates every 5s", mins: "min",
      kmh: "km/h", km: "km", from: "From", to: "To", find: "Find Bus",
      allBuses: "All Active Buses", emergency: "⚠ Emergency Report",
    },
    si: {
      title: "ශ්‍රී ලංකා බස් ට්‍රැකර්", search: "මාර්ග සොයන්න", map: "සජීවී සිතියම",
      routes: "මාර්ග", dashboard: "ඩැෂ්බෝඩ්", admin: "පරිපාලන",
      busInfo: "බස් තොරතුරු", driver: "රියදුරු", location: "ස්ථානය",
      eta: "පැමිණෙන වේලාව", distance: "දුර", speed: "වේගය", capacity: "ධාරිතාව",
      passengers: "මගීන්", status: "තත්ත්වය", active: "ක්‍රියාකාරී", delayed: "ප්‍රමාදය",
      favRoutes: "ප්‍රිය මාර්ග", recentSearches: "මෑත සෙවීම්",
      notifications: "දැනුම්දීම්", darkMode: "අඳුරු ප්‍රකාරය", lightMode: "ආලෝක ප්‍රකාරය",
      selectBus: "විස්තර දැකීමට සිතියමේ බස් එකක් ක්ලික් කරන්න",
      liveUpdates: "සෑම 5s කට වරක් සජීවී යාවත්කාලීන", mins: "මිනිත්තු",
      kmh: "කි.මී/ගෙ", km: "කි.මී", from: "සිට", to: "දක්වා", find: "බස් සොයන්න",
      allBuses: "සියලු ක්‍රියාකාරී බස්", emergency: "⚠ හදිසි වාර්තාව",
    },
    ta: {
      title: "இலங்கை பஸ் டிராக்கர்", search: "வழிகளை தேடுங்கள்", map: "நேரடி வரைபடம்",
      routes: "வழிகள்", dashboard: "டாஷ்போர்டு", admin: "நிர்வாகி",
      busInfo: "பஸ் தகவல்", driver: "ஓட்டுனர்", location: "இடம்",
      eta: "வருகை நேரம்", distance: "தூரம்", speed: "வேகம்", capacity: "திறன்",
      passengers: "பயணிகள்", status: "நிலை", active: "செயலில்", delayed: "தாமதம்",
      favRoutes: "விருப்பமான வழிகள்", recentSearches: "சமீபத்திய தேடல்கள்",
      notifications: "அறிவிப்புகள்", darkMode: "இருண்ட பயன்முறை", lightMode: "ஒளி பயன்முறை",
      selectBus: "விவரங்களைப் பார்க்க வரைபடத்தில் ஒரு பஸை கிளிக் செய்யுங்கள்",
      liveUpdates: "ஒவ்வொரு 5 வினாடிக்கும் நேரடி புதுப்பிப்புகள்", mins: "நிமிடம்",
      kmh: "கி.மீ/ம", km: "கி.மீ", from: "இருந்து", to: "வரை", find: "பஸ் கண்டறி",
      allBuses: "அனைத்து செயலில் உள்ள பஸ்கள்", emergency: "⚠ அவசர அறிக்கை",
    }
  };
  const t = T[lang];

  // Simulate bus movement every 5s
  useEffect(() => {
    const interval = setInterval(() => {
      setBuses(prev => prev.map(bus => {
        const fromCity = SRI_LANKA_CITIES.find(c => c.name === bus.from);
        const toCity = SRI_LANKA_CITIES.find(c => c.name === bus.to);
        let newProgress = bus.progress + 0.003 + Math.random() * 0.005;
        if (newProgress > 1) newProgress = 0;
        return {
          ...bus,
          progress: newProgress,
          lat: fromCity.lat + (toCity.lat - fromCity.lat) * newProgress,
          lng: fromCity.lng + (toCity.lng - fromCity.lng) * newProgress,
          speed: Math.floor(35 + Math.random() * 50),
          eta: Math.max(1, bus.eta - 1 + Math.floor(Math.random() * 3) - 1),
          passengers: Math.floor(20 + Math.random() * bus.capacity * 0.9),
        };
      }));
      setLastUpdate(new Date());
      setTick(p => p + 1);
      if (selectedBus) {
        setBuses(prev => {
          const updated = prev.find(b => b.id === selectedBus.id);
          if (updated) setSelectedBus(updated);
          return prev;
        });
      }
    }, 5000);
    return () => clearInterval(interval);
  }, [selectedBus]);

  // Sync selected bus with live data
  useEffect(() => {
    if (selectedBus) {
      const live = buses.find(b => b.id === selectedBus.id);
      if (live) setSelectedBus(live);
    }
  }, [buses]);

  const filteredBuses = buses.filter(b => {
    const rMatch = !searchRoute || b.routeId.includes(searchRoute);
    const fMatch = !searchFrom || b.from.toLowerCase().includes(searchFrom.toLowerCase());
    const tMatch = !searchTo || b.to.toLowerCase().includes(searchTo.toLowerCase());
    return rMatch && fMatch && tMatch;
  });

  const unread = notifications.filter(n => !n.read).length;
  const dm = darkMode;

  const styles = {
    app: {
      minHeight: "100vh",
      background: dm ? "#0a0f1e" : "#f0f9ff",
      color: dm ? "#e2e8f0" : "#1e3a5f",
      fontFamily: "'Inter', 'Segoe UI', sans-serif",
      display: "flex",
      flexDirection: "column",
    },
    header: {
      background: dm ? "linear-gradient(90deg,#0d1b3e,#1a3a6b)" : "linear-gradient(90deg,#1d4ed8,#2563eb)",
      padding: "12px 20px",
      display: "flex",
      alignItems: "center",
      justifyContent: "space-between",
      boxShadow: "0 2px 20px rgba(0,0,0,0.4)",
      position: "sticky",
      top: 0,
      zIndex: 100,
    },
    logo: { display: "flex", alignItems: "center", gap: 10 },
    logoIcon: { fontSize: 28 },
    logoText: { fontSize: 16, fontWeight: 700, color: "#fff", lineHeight: 1.2 },
    logoSub: { fontSize: 10, color: "#93c5fd", letterSpacing: 1 },
    headerRight: { display: "flex", alignItems: "center", gap: 10 },
    btn: (active, color) => ({
      padding: "5px 12px",
      borderRadius: 8,
      border: "none",
      cursor: "pointer",
      fontSize: 12,
      fontWeight: 600,
      background: active ? color || "#3b82f6" : "rgba(255,255,255,0.12)",
      color: "#fff",
      transition: "all 0.2s",
    }),
    notifBtn: {
      position: "relative",
      background: "rgba(255,255,255,0.12)",
      border: "none",
      borderRadius: 8,
      padding: "5px 10px",
      cursor: "pointer",
      color: "#fff",
      fontSize: 16,
    },
    badge: {
      position: "absolute", top: -4, right: -4,
      background: "#ef4444", color: "#fff",
      fontSize: 9, fontWeight: 700,
      borderRadius: "50%", width: 16, height: 16,
      display: "flex", alignItems: "center", justifyContent: "center",
    },
    nav: {
      background: dm ? "#0d1b3e" : "#1d4ed8",
      display: "flex",
      borderBottom: dm ? "1px solid #1e3a5f" : "1px solid #1e40af",
    },
    navBtn: (active) => ({
      padding: "10px 16px",
      background: active ? (dm ? "#1e3a5f" : "#2563eb") : "transparent",
      border: "none",
      borderBottom: active ? "2px solid #60a5fa" : "2px solid transparent",
      color: active ? "#fff" : "#93c5fd",
      cursor: "pointer",
      fontSize: 12,
      fontWeight: active ? 700 : 500,
      transition: "all 0.2s",
    }),
    body: { flex: 1, display: "flex", overflow: "hidden", height: "calc(100vh - 90px)" },
    sidebar: {
      width: 280,
      minWidth: 280,
      background: dm ? "#0f1c35" : "#fff",
      borderRight: dm ? "1px solid #1e3a5f" : "1px solid #bfdbfe",
      overflowY: "auto",
      padding: 12,
      display: "flex",
      flexDirection: "column",
      gap: 12,
    },
    card: {
      background: dm ? "#141f38" : "#f0f9ff",
      border: dm ? "1px solid #1e3a5f" : "1px solid #bfdbfe",
      borderRadius: 10,
      padding: 12,
    },
    cardTitle: {
      fontSize: 11,
      fontWeight: 700,
      textTransform: "uppercase",
      letterSpacing: 1,
      color: dm ? "#60a5fa" : "#1d4ed8",
      marginBottom: 10,
    },
    input: {
      width: "100%",
      padding: "7px 10px",
      background: dm ? "#0f1c35" : "#e0f2fe",
      border: dm ? "1px solid #2d4a7a" : "1px solid #93c5fd",
      borderRadius: 7,
      color: dm ? "#e2e8f0" : "#1e3a5f",
      fontSize: 12,
      outline: "none",
      boxSizing: "border-box",
    },
    busListItem: (sel) => ({
      padding: "8px 10px",
      borderRadius: 8,
      cursor: "pointer",
      background: sel ? (dm ? "#1e3a5f" : "#bfdbfe") : "transparent",
      border: sel ? `1px solid #3b82f6` : "1px solid transparent",
      marginBottom: 4,
      transition: "all 0.15s",
    }),
    mapArea: { flex: 1, position: "relative", overflow: "hidden" },
    liveTag: {
      position: "absolute",
      top: 10,
      left: 10,
      background: dm ? "rgba(15,28,53,0.9)" : "rgba(255,255,255,0.92)",
      border: `1px solid ${dm ? "#1e3a5f" : "#93c5fd"}`,
      borderRadius: 8,
      padding: "5px 10px",
      fontSize: 11,
      display: "flex",
      alignItems: "center",
      gap: 6,
      zIndex: 10,
      backdropFilter: "blur(4px)",
    },
    dot: (color) => ({
      width: 8, height: 8, borderRadius: "50%",
      background: color || "#10b981",
      animation: "pulse 1.5s infinite",
    }),
    infoPanelWrap: {
      width: 260,
      minWidth: 260,
      background: dm ? "#0f1c35" : "#fff",
      borderLeft: dm ? "1px solid #1e3a5f" : "1px solid #bfdbfe",
      overflowY: "auto",
      padding: 12,
      display: "flex",
      flexDirection: "column",
      gap: 10,
    },
    infoRow: {
      display: "flex",
      justifyContent: "space-between",
      alignItems: "center",
      padding: "7px 0",
      borderBottom: dm ? "1px solid #1e3a5f" : "1px solid #e0f2fe",
      fontSize: 12,
    },
    infoLabel: { color: dm ? "#64748b" : "#64748b", fontSize: 11 },
    infoVal: { fontWeight: 600, fontSize: 12 },
    statGrid: { display: "grid", gridTemplateColumns: "1fr 1fr", gap: 8 },
    statBox: {
      background: dm ? "#1a2d50" : "#eff6ff",
      borderRadius: 8,
      padding: "10px 8px",
      textAlign: "center",
    },
    statNum: { fontSize: 20, fontWeight: 800, color: "#3b82f6" },
    statLab: { fontSize: 10, color: dm ? "#64748b" : "#6b7280", marginTop: 2 },
    routeChip: (color) => ({
      display: "inline-flex", alignItems: "center", gap: 6,
      padding: "4px 10px", borderRadius: 20,
      background: color + "22", border: `1px solid ${color}44`,
      fontSize: 11, fontWeight: 600, color: color,
      cursor: "pointer", margin: "2px",
    }),
    favBtn: (active) => ({
      padding: "3px 8px",
      borderRadius: 6,
      border: `1px solid ${active ? "#f59e0b" : "#374151"}`,
      background: active ? "#f59e0b22" : "transparent",
      color: active ? "#f59e0b" : "#6b7280",
      fontSize: 11,
      cursor: "pointer",
    }),
    emerBtn: {
      width: "100%",
      padding: "10px",
      background: "linear-gradient(135deg,#dc2626,#991b1b)",
      color: "#fff",
      border: "none",
      borderRadius: 8,
      cursor: "pointer",
      fontWeight: 700,
      fontSize: 12,
      letterSpacing: 0.5,
    },
    notifPanel: {
      position: "absolute",
      top: 48,
      right: 16,
      width: 280,
      background: dm ? "#141f38" : "#fff",
      border: dm ? "1px solid #2d4a7a" : "1px solid #bfdbfe",
      borderRadius: 12,
      boxShadow: "0 10px 40px rgba(0,0,0,0.4)",
      zIndex: 200,
      overflow: "hidden",
    },
    notifItem: (read) => ({
      padding: "10px 14px",
      borderBottom: dm ? "1px solid #1e3a5f" : "1px solid #e0f2fe",
      background: read ? "transparent" : (dm ? "#1a3a6b22" : "#eff6ff"),
      fontSize: 12,
    }),
  };

  const capacityPct = selectedBus ? Math.round((selectedBus.passengers / selectedBus.capacity) * 100) : 0;

  return (
    <div style={styles.app}>
      <style>{`
        @keyframes pulse { 0%,100%{opacity:1} 50%{opacity:0.3} }
        @keyframes spin { from{transform:rotate(0deg)} to{transform:rotate(360deg)} }
        ::-webkit-scrollbar { width: 4px; }
        ::-webkit-scrollbar-track { background: transparent; }
        ::-webkit-scrollbar-thumb { background: #2d4a7a; border-radius: 4px; }
        * { box-sizing: border-box; }
      `}</style>

      {/* Header */}
      <div style={styles.header}>
        <div style={styles.logo}>
          <span style={styles.logoIcon}>🚌</span>
          <div>
            <div style={styles.logoText}>{t.title}</div>
            <div style={styles.logoSub}>SMART TRANSIT NETWORK</div>
          </div>
        </div>
        <div style={styles.headerRight}>
          <button style={styles.btn(lang==="en")} onClick={() => setLang("en")}>EN</button>
          <button style={styles.btn(lang==="si")} onClick={() => setLang("si")}>සිං</button>
          <button style={styles.btn(lang==="ta")} onClick={() => setLang("ta")}>தமி</button>
          <button style={styles.btn(false)} onClick={() => setDarkMode(d => !d)}>
            {dm ? "☀" : "🌙"}
          </button>
          <div style={{ position: "relative" }}>
            <button style={styles.notifBtn} onClick={() => setShowNotif(s => !s)}>🔔</button>
            {unread > 0 && <div style={styles.badge}>{unread}</div>}
            {showNotif && (
              <div style={styles.notifPanel}>
                <div style={{ padding: "10px 14px", fontWeight: 700, fontSize: 13, borderBottom: dm ? "1px solid #1e3a5f" : "1px solid #bfdbfe" }}>
                  {t.notifications}
                </div>
                {notifications.map(n => (
                  <div key={n.id} style={styles.notifItem(n.read)}>
                    <div>{n.msg}</div>
                    <div style={{ color: "#6b7280", fontSize: 10, marginTop: 3 }}>{n.time}</div>
                  </div>
                ))}
                <div style={{ padding: "8px 14px" }}>
                  <button
                    style={{ ...styles.btn(false), width: "100%", textAlign: "center" }}
                    onClick={() => {
                      setNotifications(n => n.map(x => ({ ...x, read: true })));
                      setShowNotif(false);
                    }}
                  >Mark all read</button>
                </div>
              </div>
            )}
          </div>
        </div>
      </div>

      {/* Nav */}
      <div style={styles.nav}>
        {["map", "routes", "dashboard", "admin"].map(tab => (
          <button key={tab} style={styles.navBtn(activeTab === tab)} onClick={() => setActiveTab(tab)}>
            {tab === "map" ? `🗺 ${t.map}` : tab === "routes" ? `🔍 ${t.routes}` : tab === "dashboard" ? `📊 ${t.dashboard}` : `⚙ ${t.admin}`}
          </button>
        ))}
        <div style={{ marginLeft: "auto", display: "flex", alignItems: "center", padding: "0 14px", gap: 6 }}>
          <div style={styles.dot()}></div>
          <span style={{ fontSize: 10, color: "#94a3b8" }}>{t.liveUpdates}</span>
          <span style={{ fontSize: 10, color: "#60a5fa" }}>{lastUpdate.toLocaleTimeString()}</span>
        </div>
      </div>

      {/* MAP TAB */}
      {activeTab === "map" && (
        <div style={styles.body}>
          {/* Sidebar */}
          <div style={styles.sidebar}>
            {/* Search */}
            <div style={styles.card}>
              <div style={styles.cardTitle}>🔍 {t.search}</div>
              <div style={{ display: "flex", flexDirection: "column", gap: 6 }}>
                <input style={styles.input} placeholder={`Route # (138, 120...)`}
                  value={searchRoute} onChange={e => setSearchRoute(e.target.value)} />
                <input style={styles.input} placeholder={`${t.from} (Colombo...)`}
                  value={searchFrom} onChange={e => setSearchFrom(e.target.value)} />
                <input style={styles.input} placeholder={`${t.to} (Kandy...)`}
                  value={searchTo} onChange={e => setSearchTo(e.target.value)} />
                <button style={{ ...styles.btn(true, "#3b82f6"), padding: "7px", width: "100%" }}
                  onClick={() => {}}>🔎 {t.find}</button>
              </div>
            </div>

            {/* Bus List */}
            <div style={styles.card}>
              <div style={styles.cardTitle}>🚌 {t.allBuses} ({filteredBuses.length})</div>
              <div style={{ maxHeight: 280, overflowY: "auto" }}>
                {filteredBuses.map(bus => (
                  <div key={bus.id} style={styles.busListItem(selectedBus?.id === bus.id)}
                    onClick={() => setSelectedBus(bus)}>
                    <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
                      <div style={{ display: "flex", alignItems: "center", gap: 6 }}>
                        <div style={{ width: 8, height: 8, borderRadius: "50%", background: bus.color }} />
                        <span style={{ fontWeight: 700, fontSize: 12 }}>{bus.routeId}</span>
                      </div>
                      <span style={{
                        fontSize: 9, padding: "2px 6px", borderRadius: 4,
                        background: bus.status === "active" ? "#10b98122" : "#ef444422",
                        color: bus.status === "active" ? "#10b981" : "#ef4444",
                        fontWeight: 700,
                      }}>{bus.status === "active" ? t.active : t.delayed}</span>
                    </div>
                    <div style={{ fontSize: 11, color: dm ? "#94a3b8" : "#64748b", marginTop: 3 }}>
                      {bus.from} → {bus.to}
                    </div>
                    <div style={{ fontSize: 10, color: dm ? "#60a5fa" : "#1d4ed8", marginTop: 2 }}>
                      ETA: {bus.eta}{t.mins} • {bus.speed}{t.kmh}
                    </div>
                  </div>
                ))}
              </div>
            </div>

            {/* Routes */}
            <div style={styles.card}>
              <div style={styles.cardTitle}>🗺 Active Routes</div>
              {ROUTES.map(r => (
                <div key={r.id} style={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginBottom: 6 }}>
                  <span style={styles.routeChip(r.color)}>
                    <span>{r.id}</span>
                    <span style={{ fontWeight: 400 }}>{r.from.slice(0, 3)}→{r.to.slice(0, 3)}</span>
                  </span>
                  <button style={styles.favBtn(favorites.includes(r.id))}
                    onClick={() => setFavorites(f => f.includes(r.id) ? f.filter(x => x !== r.id) : [...f, r.id])}>
                    {favorites.includes(r.id) ? "★ Saved" : "☆ Save"}
                  </button>
                </div>
              ))}
            </div>

            <button style={styles.emerBtn}>⚠ {t.emergency}</button>
          </div>

          {/* Map */}
          <div style={styles.mapArea}>
            <BusMapCanvas
              buses={filteredBuses}
              selectedBus={selectedBus}
              onSelectBus={setSelectedBus}
              darkMode={darkMode}
            />
            <div style={styles.liveTag}>
              <div style={styles.dot()}></div>
              <span style={{ color: dm ? "#e2e8f0" : "#1e3a5f", fontWeight: 600 }}>LIVE</span>
              <span style={{ color: dm ? "#64748b" : "#94a3b8", fontSize: 10 }}>
                {filteredBuses.length} buses tracked
              </span>
            </div>
            <div style={{ position: "absolute", bottom: 10, left: 10, display: "flex", gap: 6, flexWrap: "wrap" }}>
              {ROUTES.map(r => (
                <span key={r.id} style={{
                  ...styles.routeChip(r.color),
                  background: r.color + "33",
                  backdropFilter: "blur(4px)",
                  fontSize: 10,
                }}>
                  {r.id} {r.from.slice(0,3)}→{r.to.slice(0,3)}
                </span>
              ))}
            </div>
          </div>

          {/* Info Panel */}
          <div style={styles.infoPanelWrap}>
            <div style={{ ...styles.card, borderColor: selectedBus ? selectedBus.color + "88" : undefined }}>
              <div style={styles.cardTitle}>
                {selectedBus ? `🚌 ${selectedBus.id}` : "ℹ Bus Info"}
              </div>
              {selectedBus ? (
                <>
                  <div style={{ marginBottom: 10 }}>
                    <div style={{ fontSize: 13, fontWeight: 700, color: selectedBus.color }}>
                      Route {selectedBus.routeId}
                    </div>
                    <div style={{ fontSize: 11, color: dm ? "#94a3b8" : "#64748b" }}>{selectedBus.routeName}</div>
                  </div>
                  {[
                    [t.driver, selectedBus.driver, "👤"],
                    [t.location, `${selectedBus.lat.toFixed(4)}, ${selectedBus.lng.toFixed(4)}`, "📍"],
                    [t.speed, `${selectedBus.speed} ${t.kmh}`, "⚡"],
                    [t.eta, `${selectedBus.eta} ${t.mins}`, "⏱"],
                    [t.distance, `${selectedBus.distance} ${t.km}`, "📏"],
                    [t.status, selectedBus.status === "active" ? t.active : t.delayed, "🟢"],
                  ].map(([label, val, icon]) => (
                    <div key={label} style={styles.infoRow}>
                      <span style={styles.infoLabel}>{icon} {label}</span>
                      <span style={styles.infoVal}>{val}</span>
                    </div>
                  ))}
                  <div style={{ marginTop: 10 }}>
                    <div style={{ display: "flex", justifyContent: "space-between", marginBottom: 5 }}>
                      <span style={styles.infoLabel}>👥 {t.capacity}</span>
                      <span style={{ ...styles.infoVal, color: capacityPct > 80 ? "#ef4444" : capacityPct > 50 ? "#f59e0b" : "#10b981" }}>
                        {selectedBus.passengers}/{selectedBus.capacity} ({capacityPct}%)
                      </span>
                    </div>
                    <div style={{ background: dm ? "#1e3a5f" : "#bfdbfe", borderRadius: 4, height: 6 }}>
                      <div style={{
                        height: 6, borderRadius: 4, width: `${capacityPct}%`,
                        background: capacityPct > 80 ? "#ef4444" : capacityPct > 50 ? "#f59e0b" : "#10b981",
                        transition: "width 0.5s",
                      }} />
                    </div>
                  </div>
                  <div style={{ marginTop: 12, display: "flex", flexDirection: "column", gap: 6 }}>
                    <div style={{ background: dm ? "#1a2d50" : "#eff6ff", borderRadius: 8, padding: "8px 10px" }}>
                      <div style={{ fontSize: 10, color: dm ? "#64748b" : "#6b7280" }}>Progress</div>
                      <div style={{ background: dm ? "#0f1c35" : "#bfdbfe", borderRadius: 4, height: 6, marginTop: 4 }}>
                        <div style={{
                          height: 6, borderRadius: 4,
                          width: `${Math.round(selectedBus.progress * 100)}%`,
                          background: selectedBus.color,
                          transition: "width 1s",
                        }} />
                      </div>
                      <div style={{ display: "flex", justifyContent: "space-between", fontSize: 9, marginTop: 3, color: dm ? "#64748b" : "#6b7280" }}>
                        <span>{selectedBus.from}</span>
                        <span>{Math.round(selectedBus.progress * 100)}%</span>
                        <span>{selectedBus.to}</span>
                      </div>
                    </div>
                  </div>
                </>
              ) : (
                <div style={{ color: dm ? "#64748b" : "#94a3b8", fontSize: 12, textAlign: "center", padding: "20px 0" }}>
                  🖱 {t.selectBus}
                </div>
              )}
            </div>

            {/* Live Stats */}
            <div style={styles.card}>
              <div style={styles.cardTitle}>📊 Network Stats</div>
              <div style={styles.statGrid}>
                {[
                  [buses.length, "Active Buses"],
                  [buses.filter(b => b.status === "active").length, "On Time"],
                  [Math.round(buses.reduce((a, b) => a + b.passengers, 0)), "Passengers"],
                  [Math.round(buses.reduce((a, b) => a + b.speed, 0) / buses.length), "Avg km/h"],
                ].map(([num, lab]) => (
                  <div key={lab} style={styles.statBox}>
                    <div style={styles.statNum}>{num}</div>
                    <div style={styles.statLab}>{lab}</div>
                  </div>
                ))}
              </div>
            </div>
          </div>
        </div>
      )}

      {/* ROUTES TAB */}
      {activeTab === "routes" && (
        <div style={{ padding: 20, overflowY: "auto", flex: 1 }}>
          <h2 style={{ marginBottom: 16, fontSize: 18, fontWeight: 800 }}>Sri Lanka Bus Routes</h2>
          <div style={{ display: "grid", gridTemplateColumns: "repeat(auto-fill,minmax(300px,1fr))", gap: 16 }}>
            {ROUTES.map(route => {
              const routeBuses = buses.filter(b => b.routeId === route.id);
              const isFav = favorites.includes(route.id);
              return (
                <div key={route.id} style={{
                  ...styles.card,
                  border: `2px solid ${route.color}44`,
                  borderTop: `4px solid ${route.color}`,
                }}>
                  <div style={{ display: "flex", justifyContent: "space-between", alignItems: "start" }}>
                    <div>
                      <div style={{ fontSize: 22, fontWeight: 900, color: route.color }}>#{route.id}</div>
                      <div style={{ fontSize: 14, fontWeight: 600, marginTop: 2 }}>{route.name}</div>
                    </div>
                    <button style={styles.favBtn(isFav)}
                      onClick={() => setFavorites(f => isFav ? f.filter(x => x !== route.id) : [...f, route.id])}>
                      {isFav ? "★ Saved" : "☆ Save"}
                    </button>
                  </div>
                  <div style={{ display: "flex", gap: 12, marginTop: 12 }}>
                    <div style={styles.statBox}>
                      <div style={{ fontSize: 18, fontWeight: 700, color: route.color }}>{routeBuses.length}</div>
                      <div style={{ fontSize: 10, color: dm ? "#64748b" : "#6b7280" }}>Buses Active</div>
                    </div>
                    <div style={styles.statBox}>
                      <div style={{ fontSize: 18, fontWeight: 700, color: route.color }}>{route.stops}</div>
                      <div style={{ fontSize: 10, color: dm ? "#64748b" : "#6b7280" }}>Total Stops</div>
                    </div>
                    <div style={styles.statBox}>
                      <div style={{ fontSize: 18, fontWeight: 700, color: route.color }}>
                        {routeBuses[0]?.eta || "–"}{routeBuses[0] ? "m" : ""}
                      </div>
                      <div style={{ fontSize: 10, color: dm ? "#64748b" : "#6b7280" }}>Next ETA</div>
                    </div>
                  </div>
                  {routeBuses.map(bus => (
                    <div key={bus.id} style={{ marginTop: 10, padding: "8px", background: dm ? "#0f1c35" : "#e0f2fe", borderRadius: 7 }}>
                      <div style={{ display: "flex", justifyContent: "space-between", fontSize: 11 }}>
                        <span style={{ fontWeight: 700 }}>{bus.id}</span>
                        <span style={{ color: bus.status === "active" ? "#10b981" : "#ef4444" }}>
                          {bus.status === "active" ? "● On Time" : "⚠ Delayed"}
                        </span>
                      </div>
                      <div style={{ fontSize: 10, color: dm ? "#94a3b8" : "#64748b", marginTop: 3 }}>
                        Driver: {bus.driver} • Speed: {bus.speed}km/h • ETA: {bus.eta}min
                      </div>
                    </div>
                  ))}
                </div>
              );
            })}
          </div>
        </div>
      )}

      {/* DASHBOARD TAB */}
      {activeTab === "dashboard" && (
        <div style={{ padding: 20, overflowY: "auto", flex: 1 }}>
          <h2 style={{ marginBottom: 16, fontSize: 18, fontWeight: 800 }}>My Dashboard</h2>
          <div style={{ display: "grid", gridTemplateColumns: "repeat(auto-fill,minmax(280px,1fr))", gap: 16 }}>
            {/* Favourites */}
            <div style={styles.card}>
              <div style={styles.cardTitle}>⭐ {t.favRoutes}</div>
              {favorites.length === 0
                ? <div style={{ color: "#64748b", fontSize: 12 }}>No saved routes yet.</div>
                : favorites.map(fid => {
                  const r = ROUTES.find(x => x.id === fid);
                  const bus = buses.find(b => b.routeId === fid);
                  return r ? (
                    <div key={fid} style={{ display: "flex", justifyContent: "space-between", alignItems: "center", padding: "8px 0", borderBottom: dm ? "1px solid #1e3a5f" : "1px solid #e0f2fe" }}>
                      <span style={styles.routeChip(r.color)}>#{r.id} {r.name.split("→")[0].trim()}</span>
                      <span style={{ fontSize: 11, color: dm ? "#94a3b8" : "#64748b" }}>ETA {bus?.eta || "–"}min</span>
                    </div>
                  ) : null;
                })
              }
            </div>
            {/* Notifications */}
            <div style={styles.card}>
              <div style={styles.cardTitle}>🔔 {t.notifications}</div>
              {notifications.map(n => (
                <div key={n.id} style={{ padding: "8px 0", borderBottom: dm ? "1px solid #1e3a5f" : "1px solid #e0f2fe" }}>
                  <div style={{ fontSize: 11, display: "flex", gap: 6 }}>
                    <span>{n.read ? "○" : "●"}</span>
                    <span>{n.msg}</span>
                  </div>
                  <div style={{ fontSize: 10, color: "#64748b", marginTop: 2 }}>{n.time}</div>
                </div>
              ))}
            </div>
            {/* Recent Searches */}
            <div style={styles.card}>
              <div style={styles.cardTitle}>🕐 {t.recentSearches}</div>
              {["Colombo → Kandy", "Route 120", "Negombo → Colombo", "Route 177"].map(s => (
                <div key={s} style={{ padding: "7px 0", borderBottom: dm ? "1px solid #1e3a5f" : "1px solid #e0f2fe", fontSize: 12, cursor: "pointer", color: dm ? "#60a5fa" : "#1d4ed8" }}>
                  🔍 {s}
                </div>
              ))}
            </div>
            {/* Stats */}
            <div style={styles.card}>
              <div style={styles.cardTitle}>📈 Network Overview</div>
              <div style={styles.statGrid}>
                {[
                  [buses.length, "Active Buses", "#3b82f6"],
                  [buses.filter(b => b.status === "active").length, "On Time", "#10b981"],
                  [buses.filter(b => b.status === "delayed").length, "Delayed", "#ef4444"],
                  [ROUTES.length, "Routes", "#8b5cf6"],
                  [SRI_LANKA_CITIES.length, "Cities", "#f59e0b"],
                  [buses.reduce((a, b) => a + b.passengers, 0), "Passengers", "#06b6d4"],
                ].map(([num, lab, color]) => (
                  <div key={lab} style={{ ...styles.statBox, borderTop: `3px solid ${color}` }}>
                    <div style={{ fontSize: 20, fontWeight: 800, color }}>{num}</div>
                    <div style={{ fontSize: 10, color: dm ? "#64748b" : "#6b7280" }}>{lab}</div>
                  </div>
                ))}
              </div>
            </div>
          </div>
        </div>
      )}

      {/* ADMIN TAB */}
      {activeTab === "admin" && (
        <div style={{ padding: 20, overflowY: "auto", flex: 1 }}>
          <h2 style={{ marginBottom: 16, fontSize: 18, fontWeight: 800 }}>⚙ Admin Dashboard</h2>
          <div style={{ display: "grid", gridTemplateColumns: "repeat(auto-fill,minmax(280px,1fr))", gap: 16, marginBottom: 20 }}>
            {[
              { label: "Total Buses", val: buses.length, icon: "🚌", color: "#3b82f6" },
              { label: "Active Routes", val: ROUTES.length, icon: "🗺", color: "#10b981" },
              { label: "Total Passengers", val: buses.reduce((a, b) => a + b.passengers, 0), icon: "👥", color: "#8b5cf6" },
              { label: "Avg Speed", val: Math.round(buses.reduce((a, b) => a + b.speed, 0) / buses.length) + " km/h", icon: "⚡", color: "#f59e0b" },
            ].map(s => (
              <div key={s.label} style={{ ...styles.card, borderTop: `4px solid ${s.color}`, display: "flex", alignItems: "center", gap: 14 }}>
                <span style={{ fontSize: 32 }}>{s.icon}</span>
                <div>
                  <div style={{ fontSize: 24, fontWeight: 900, color: s.color }}>{s.val}</div>
                  <div style={{ fontSize: 11, color: dm ? "#64748b" : "#6b7280" }}>{s.label}</div>
                </div>
              </div>
            ))}
          </div>

          {/* Bus Management Table */}
          <div style={styles.card}>
            <div style={styles.cardTitle}>🚌 Bus Fleet Management</div>
            <div style={{ overflowX: "auto" }}>
              <table style={{ width: "100%", borderCollapse: "collapse", fontSize: 12 }}>
                <thead>
                  <tr style={{ background: dm ? "#1a2d50" : "#eff6ff" }}>
                    {["Bus ID", "Route", "Driver", "Speed", "ETA", "Passengers", "Status", "Actions"].map(h => (
                      <th key={h} style={{ padding: "8px 10px", textAlign: "left", fontWeight: 700, fontSize: 11, color: dm ? "#60a5fa" : "#1d4ed8" }}>{h}</th>
                    ))}
                  </tr>
                </thead>
                <tbody>
                  {buses.map((bus, i) => (
                    <tr key={bus.id} style={{ background: i % 2 === 0 ? "transparent" : (dm ? "#0f1c3522" : "#f0f9ff") }}>
                      <td style={{ padding: "7px 10px", fontWeight: 600 }}>{bus.id}</td>
                      <td style={{ padding: "7px 10px" }}><span style={styles.routeChip(bus.color)}>#{bus.routeId}</span></td>
                      <td style={{ padding: "7px 10px", color: dm ? "#94a3b8" : "#475569" }}>{bus.driver}</td>
                      <td style={{ padding: "7px 10px" }}>{bus.speed} km/h</td>
                      <td style={{ padding: "7px 10px" }}>{bus.eta} min</td>
                      <td style={{ padding: "7px 10px" }}>{bus.passengers}/{bus.capacity}</td>
                      <td style={{ padding: "7px 10px" }}>
                        <span style={{
                          padding: "2px 8px", borderRadius: 10, fontSize: 10, fontWeight: 700,
                          background: bus.status === "active" ? "#10b98122" : "#ef444422",
                          color: bus.status === "active" ? "#10b981" : "#ef4444",
                        }}>
                          {bus.status === "active" ? "● Active" : "⚠ Delayed"}
                        </span>
                      </td>
                      <td style={{ padding: "7px 10px" }}>
                        <div style={{ display: "flex", gap: 4 }}>
                          <button style={{ ...styles.btn(false), padding: "3px 7px", fontSize: 10, background: "#3b82f622", color: "#3b82f6" }}>Edit</button>
                          <button style={{ ...styles.btn(false), padding: "3px 7px", fontSize: 10, background: "#ef444422", color: "#ef4444" }}>Remove</button>
                        </div>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
