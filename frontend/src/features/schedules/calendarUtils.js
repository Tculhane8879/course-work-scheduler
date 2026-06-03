const BASE_COLORS = [
  { background: '#dbeafe', border: '#60a5fa', text: '#17407c' },
  { background: '#dcfce7', border: '#4ade80', text: '#35611f' },
  { background: '#fef3c7', border: '#fbbf24', text: '#7a4a00' },
  { background: '#fce7f3', border: '#f472b6', text: '#831843' },
  { background: '#ede9fe', border: '#a78bfa', text: '#4c1d95' },
  { background: '#cffafe', border: '#22d3ee', text: '#155e75' },
]

const WEEKDAYS = ['MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY']
const WEEKEND_DAYS = ['SATURDAY', 'SUNDAY']

export function formatDay(dayOfWeek) {
  return dayOfWeek.charAt(0) + dayOfWeek.slice(1).toLowerCase()
}

export function timeToMinutes(time) {
  const [hours, minutes] = time.split(':').map(Number)
  return hours * 60 + minutes
}

export function formatHour(hour) {
  if (hour === 0) {
    return '12 AM'
  }

  if (hour < 12) {
    return `${hour} AM`
  }

  if (hour === 12) {
    return '12 PM'
  }

  return `${hour - 12} PM`
}

export function getMeetingBlocks(sections) {
  return sections.flatMap((section) =>
    section.meetings.map((meeting) => ({
      courseId: section.courseId,
      courseCode: section.courseCode,
      courseName: section.courseName,
      sectionCode: section.sectionCode,
      dayOfWeek: meeting.dayOfWeek,
      startTime: meeting.startTime,
      endTime: meeting.endTime,
    })),
  )
}

export function getNoFixedTimeSections(sections) {
  return sections.filter((section) => section.meetings.length === 0)
}

export function getCalendarDays(meetingBlocks) {
  const hasWeekend = meetingBlocks.some((meeting) => WEEKEND_DAYS.includes(meeting.dayOfWeek))
  return hasWeekend ? [...WEEKDAYS, ...WEEKEND_DAYS] : WEEKDAYS
}

export function getCalendarRange(meetingBlocks) {
  if (meetingBlocks.length === 0) {
    return { startHour: 8, endHour: 20 }
  }

  const earliest = Math.min(...meetingBlocks.map((meeting) => timeToMinutes(meeting.startTime)))
  const latest = Math.max(...meetingBlocks.map((meeting) => timeToMinutes(meeting.endTime)))

  return {
    startHour: Math.max(6, Math.floor(earliest / 60)),
    endHour: Math.min(22, Math.ceil(latest / 60)),
  }
}

export function getCourseColor(courseId) {
  return BASE_COLORS[Math.abs(Number(courseId)) % BASE_COLORS.length]
}
