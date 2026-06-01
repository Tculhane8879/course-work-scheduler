<script setup>
import { computed } from 'vue'
import {
  formatDay,
  formatHour,
  getCalendarDays,
  getCalendarRange,
  getCourseColor,
  getMeetingBlocks,
  getNoFixedTimeSections,
  timeToMinutes,
} from './calendarUtils'

const props = defineProps({
  sections: {
    type: Array,
    required: true,
  },
})

const meetingBlocks = computed(() => getMeetingBlocks(props.sections))
const noFixedTimeSections = computed(() => getNoFixedTimeSections(props.sections))
const calendarDays = computed(() => getCalendarDays(meetingBlocks.value))
const calendarRange = computed(() => getCalendarRange(meetingBlocks.value))
const hourLabels = computed(() => {
  const labels = []

  for (let hour = calendarRange.value.startHour; hour <= calendarRange.value.endHour; hour++) {
    labels.push(hour)
  }

  return labels
})

const totalMinutes = computed(
  () => (calendarRange.value.endHour - calendarRange.value.startHour) * 60,
)

const calendarStyle = computed(() => ({
  '--day-count': calendarDays.value.length,
  '--hour-count': hourLabels.value.length,
}))

function blocksForDay(dayOfWeek) {
  return meetingBlocks.value.filter((meeting) => meeting.dayOfWeek === dayOfWeek)
}

function blockStyle(meeting) {
  const rangeStart = calendarRange.value.startHour * 60
  const top = ((timeToMinutes(meeting.startTime) - rangeStart) / totalMinutes.value) * 100
  const height =
    ((timeToMinutes(meeting.endTime) - timeToMinutes(meeting.startTime)) / totalMinutes.value) * 100
  const color = getCourseColor(meeting.courseId)

  return {
    top: `${top}%`,
    height: `${height}%`,
    backgroundColor: color.background,
    borderColor: color.border,
    color: color.text,
  }
}
</script>

<template>
  <section class="weekly-calendar" aria-label="Weekly schedule calendar">
    <div class="calendar-grid" :style="calendarStyle">
      <div class="corner-cell"></div>

      <div v-for="day in calendarDays" :key="day" class="day-header">
        {{ formatDay(day) }}
      </div>

      <div class="time-gutter">
        <div v-for="hour in hourLabels" :key="hour" class="time-label">
          {{ formatHour(hour) }}
        </div>
      </div>

      <div v-for="day in calendarDays" :key="`${day}-column`" class="day-column">
        <div v-for="hour in hourLabels" :key="`${day}-${hour}`" class="hour-line"></div>

        <article
          v-for="meeting in blocksForDay(day)"
          :key="`${meeting.courseId}-${meeting.dayOfWeek}-${meeting.startTime}`"
          class="meeting-block"
          :style="blockStyle(meeting)"
        >
          <strong>{{ meeting.courseCode }}</strong>
          <span>Section {{ meeting.sectionCode }}</span>
          <span>{{ meeting.startTime }}-{{ meeting.endTime }}</span>
        </article>
      </div>
    </div>

    <div v-if="noFixedTimeSections.length" class="no-fixed-time">
      <h4>No Fixed Meeting Time</h4>
      <ul>
        <li v-for="section in noFixedTimeSections" :key="section.sectionId">
          {{ section.courseCode }} · Section {{ section.sectionCode }}
        </li>
      </ul>
    </div>
  </section>
</template>

<style scoped>
.weekly-calendar {
  display: grid;
  gap: 0.9rem;
  padding: 1.25rem;
}

.calendar-grid {
  display: grid;
  grid-template-columns: 4.25rem repeat(var(--day-count), minmax(7.25rem, 1fr));
  grid-template-rows: 2.5rem minmax(32rem, 1fr);
  overflow-x: auto;
  border: 1px solid #dbe1ea;
  border-radius: 8px;
  background: #ffffff;
}

.corner-cell,
.day-header {
  display: grid;
  place-items: center;
  border-bottom: 1px solid #dbe1ea;
  color: #415066;
  background: #f8fafc;
  font-size: 0.82rem;
  font-weight: 900;
}

.corner-cell,
.time-gutter {
  border-right: 1px solid #dbe1ea;
}

.time-gutter,
.day-column {
  position: relative;
  min-height: 32rem;
}

.time-label {
  height: calc(100% / var(--hour-count));
  padding-top: 0.35rem;
  padding-right: 0.45rem;
  color: #687386;
  font-size: 0.75rem;
  font-weight: 800;
  text-align: right;
}

.day-column {
  border-right: 1px solid #edf1f7;
}

.day-column:last-child {
  border-right: 0;
}

.hour-line {
  height: calc(100% / var(--hour-count));
  border-bottom: 1px solid #edf1f7;
}

.meeting-block {
  position: absolute;
  right: 0.35rem;
  left: 0.35rem;
  display: grid;
  align-content: start;
  min-height: 2.25rem;
  overflow: hidden;
  border: 1px solid;
  border-radius: 6px;
  padding: 0.4rem;
  box-shadow: 0 8px 18px rgba(24, 34, 53, 0.08);
  font-size: 0.75rem;
  line-height: 1.2;
}

.meeting-block strong,
.meeting-block span {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.no-fixed-time {
  border: 1px solid #e2e7f0;
  border-radius: 8px;
  padding: 0.9rem;
  background: #f8fafc;
}

.no-fixed-time h4 {
  margin: 0 0 0.5rem;
  color: #172033;
  font-size: 0.95rem;
}

.no-fixed-time ul {
  display: flex;
  flex-wrap: wrap;
  gap: 0.45rem;
  margin: 0;
  padding: 0;
  list-style: none;
}

.no-fixed-time li {
  border-radius: 999px;
  padding: 0.3rem 0.65rem;
  color: #17407c;
  background: #dbeafe;
  font-size: 0.82rem;
  font-weight: 800;
}

@media (max-width: 760px) {
  .weekly-calendar {
    padding: 1rem;
  }

  .calendar-grid {
    grid-template-columns: 3.75rem repeat(var(--day-count), minmax(8rem, 1fr));
  }
}
</style>
