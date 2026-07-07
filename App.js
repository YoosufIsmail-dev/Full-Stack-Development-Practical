import React, { useState } from 'react';
import format from 'date-fns/format';
import BpkCalendar, { CALENDAR_SELECTION_TYPE } from 'bpk-component-calendar';
import BpkButton from 'bpk-component-button';
import logo from './logo.svg';
import './App.scss';

const formatMonth = (date) => format(date, 'MMMM yyyy');
const formatDateFull = (date) => format(date, 'EEEE, do MMMM yyyy');

const daysOfWeek = [
  { name: 'Sunday', nameAbbr: 'Sun', index: 0, isWeekend: true },
  { name: 'Monday', nameAbbr: 'Mon', index: 1, isWeekend: false },
  { name: 'Tuesday', nameAbbr: 'Tue', index: 2, isWeekend: false },
  { name: 'Wednesday', nameAbbr: 'Wed', index: 3, isWeekend: false },
  { name: 'Thursday', nameAbbr: 'Thu', index: 4, isWeekend: false },
  { name: 'Friday', nameAbbr: 'Fri', index: 5, isWeekend: false },
  { name: 'Saturday', nameAbbr: 'Sat', index: 6, isWeekend: true },
];

function App() {
  const [selectionConfiguration, setSelectionConfiguration] = useState({
    type: CALENDAR_SELECTION_TYPE.single,
    date: null,
  });

  const handleDateSelect = (date) => {
    setSelectionConfiguration({
      type: CALENDAR_SELECTION_TYPE.single,
      date,
    });
  };

  const handleContinueClick = () => {
    // Existing click functionality left as-is.
    // eslint-disable-next-line no-console
    console.log('Continue clicked', selectionConfiguration.date);
  };

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <h1>Flight Schedule</h1>
      </header>
      <main className="App-main">
        <BpkCalendar
          id="calendar"
          weekStartsOn={1}
          daysOfWeek={daysOfWeek}
          formatMonth={formatMonth}
          formatDateFull={formatDateFull}
          selectionConfiguration={selectionConfiguration}
          onDateSelect={handleDateSelect}
          changeMonthLabel="Change month"
          markToday
          markOutsideDays
        />
        <BpkButton onClick={handleContinueClick}>Continue</BpkButton>
      </main>
    </div>
  );
}

export default App;
