import { timeToMinutes } from '@/features/schedules/calendarUtils'

export function summarizeSchedule(sections) {
  const meetings = sections.flatMap((section) => section.meetings)
  const meetingDays = new Set(meetings.map((meeting) => meeting.dayOfWeek))

  if (meetings.length === 0) {
    return {
      daysOnCampus: 0,
      earliestStart: 'No fixed time',
      latestEnd: 'No fixed time',
      meetingCount: 0,
    }
  }

  const earliest = meetings.reduce((earliestMeeting, meeting) =>
    timeToMinutes(meeting.startTime) < timeToMinutes(earliestMeeting.startTime)
      ? meeting
      : earliestMeeting,
  )
  const latest = meetings.reduce((latestMeeting, meeting) =>
    timeToMinutes(meeting.endTime) > timeToMinutes(latestMeeting.endTime)
      ? meeting
      : latestMeeting,
  )

  return {
    daysOnCampus: meetingDays.size,
    earliestStart: earliest.startTime,
    latestEnd: latest.endTime,
    meetingCount: meetings.length,
  }
}
