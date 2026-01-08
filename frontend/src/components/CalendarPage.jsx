import React, { useState } from "react";

/* HOLIDAYS (applies to ALL years using MM-DD) */
const holidays = {
  "01-01": "New Year 🎉",
  "01-26": "Republic Day 🇮🇳",
  "03-08": "Women's Day",
  "05-01": "Labour Day",
  "08-15": "Independence Day 🇮🇳",
  "10-02": "Gandhi Jayanti",
  "12-25": "Christmas 🎄",
};

const months = [
  "January",
  "February",
  "March",
  "April",
  "May",
  "June",
  "July",
  "August",
  "September",
  "October",
  "November",
  "December",
];

const CalendarPage = () => {
  const today = new Date();

  const [currentMonth, setCurrentMonth] = useState(today.getMonth());
  const [currentYear, setCurrentYear] = useState(today.getFullYear());

  const firstDay = new Date(currentYear, currentMonth, 1).getDay();
  const daysInMonth = new Date(currentYear, currentMonth + 1, 0).getDate();

  const goPrevMonth = () => {
    if (currentMonth === 0) {
      setCurrentMonth(11);
      setCurrentYear((y) => y - 1);
    } else {
      setCurrentMonth((m) => m - 1);
    }
  };

  const goNextMonth = () => {
    if (currentMonth === 11) {
      setCurrentMonth(0);
      setCurrentYear((y) => y + 1);
    } else {
      setCurrentMonth((m) => m + 1);
    }
  };

  return (
    <div className="calendar-page">
      <div className="calendar-wrapper">
        {/* HEADER */}
        <div className="calendar-top">
          {/* <div className="calendar-nav"> */}
            <button onClick={goPrevMonth}>◀</button>
            <h2>
              {months[currentMonth]} {currentYear}
            </h2>
            <button onClick={goNextMonth}>▶</button>
          {/* </div> */}

          <select
            value={currentYear}
            onChange={(e) => setCurrentYear(Number(e.target.value))}
          >
            {Array.from({ length: 2050 - 1990 + 1 }, (_, i) => {
              const year = 1990 + i;
              return (
                <option key={year} value={year}>
                  {year}
                </option>
              );
            })}
          </select>
        </div>

        {/* CALENDAR */}
        <div className="calendar-card">
          <div className="calendar-grid">
            {["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"].map((day) => (
              <div key={day} className="calendar-header">
                {day}
              </div>
            ))}

            {Array.from({ length: firstDay }).map((_, i) => (
              <div key={i} className="calendar-cell empty"></div>
            ))}

            {Array.from({ length: daysInMonth }, (_, i) => {
              const date = i + 1;

              const isToday =
                date === today.getDate() &&
                currentMonth === today.getMonth() &&
                currentYear === today.getFullYear();

              const dayOfWeek = new Date(
                currentYear,
                currentMonth,
                date
              ).getDay();

              const isWeekend = dayOfWeek === 0 || dayOfWeek === 6;

              const monthDay = `${String(currentMonth + 1).padStart(
                2,
                "0"
              )}-${String(date).padStart(2, "0")}`;
              const holiday = holidays[monthDay];

              return (
                <div
                  key={date}
                  className={`calendar-cell
                    ${isToday ? "today" : ""}
                    ${isWeekend ? "weekend" : ""}
                    ${holiday ? "holiday" : ""}`}
                >
                  <span className="date-number">{date}</span>
                  {holiday && <span className="holiday-text">{holiday}</span>}
                </div>
              );
            })}
          </div>
        </div>
      </div>
    </div>
  );
};

export default CalendarPage;
