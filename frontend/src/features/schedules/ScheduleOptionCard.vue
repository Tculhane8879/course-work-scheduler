<script setup>
import { formatDay } from './calendarUtils'
import WeeklyCalendar from './WeeklyCalendar.vue'

defineProps({
  option: {
    type: Object,
    required: true,
  },
  rank: {
    type: Number,
    required: true,
  },
})

defineEmits(['save'])

function formatModality(modality) {
  return {
    IN_PERSON: 'In person',
    ONLINE: 'Online',
    HYBRID: 'Hybrid',
  }[modality]
}
</script>

<template>
  <article class="schedule-card">
    <header class="card-header">
      <div>
        <p class="rank">Option {{ rank }}</p>
        <h3>{{ option.score.total }} / 100</h3>
      </div>
      <div class="header-tools">
        <dl class="score-grid">
          <div>
            <dt>Compact</dt>
            <dd>{{ option.score.compactnessScore }}</dd>
          </div>
          <div>
            <dt>Gaps</dt>
            <dd>{{ option.score.gapScore }}</dd>
          </div>
          <div>
            <dt>Prefs</dt>
            <dd>-{{ option.score.preferencePenalty }}</dd>
          </div>
          <div>
            <dt>Load</dt>
            <dd>-{{ option.score.maxClassesPenalty }}</dd>
          </div>
        </dl>
        <button class="save-button" type="button" @click="$emit('save', option, rank)">
          Save
        </button>
      </div>
    </header>

    <WeeklyCalendar :sections="option.sections" />

    <div class="section-list">
      <section v-for="section in option.sections" :key="section.sectionId" class="section-item">
        <div class="section-heading">
          <div>
            <h4>{{ section.courseCode }} · Section {{ section.sectionCode }}</h4>
            <p>
              {{ section.courseName }}
              <template v-if="section.instructor"> · {{ section.instructor }}</template>
            </p>
          </div>
          <span>{{ formatModality(section.modality) }}</span>
        </div>

        <ul v-if="section.meetings.length" class="meeting-list">
          <li v-for="meeting in section.meetings" :key="`${meeting.dayOfWeek}-${meeting.startTime}`">
            {{ formatDay(meeting.dayOfWeek) }} {{ meeting.startTime }}-{{ meeting.endTime }}
          </li>
        </ul>
        <p v-else class="online-note">No fixed meeting time.</p>
      </section>
    </div>
  </article>
</template>

<style scoped>
.schedule-card {
  border: 1px solid #dbe1ea;
  border-radius: 8px;
  background: #ffffff;
  box-shadow: 0 12px 30px rgba(24, 34, 53, 0.06);
}

.card-header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 1rem;
  border-bottom: 1px solid #e2e7f0;
  padding: 1.25rem;
}

.rank,
h3,
h4,
p,
dl,
dd {
  margin: 0;
}

.rank {
  color: #1f6feb;
  font-size: 0.8rem;
  font-weight: 900;
  text-transform: uppercase;
}

h3 {
  margin-top: 0.2rem;
  color: #111827;
  font-size: 2rem;
}

.score-grid {
  display: grid;
  grid-template-columns: repeat(4, minmax(4rem, 1fr));
  gap: 0.6rem;
}

.header-tools {
  display: grid;
  gap: 0.75rem;
  justify-items: end;
}

.score-grid div {
  border-radius: 8px;
  padding: 0.55rem;
  background: #f4f7fb;
  text-align: center;
}

dt {
  color: #687386;
  font-size: 0.75rem;
  font-weight: 900;
  text-transform: uppercase;
}

dd {
  color: #172033;
  font-weight: 900;
}

.save-button {
  min-height: 2.35rem;
  border: 0;
  border-radius: 6px;
  padding: 0 1rem;
  color: #ffffff;
  background: #1f6feb;
  font: inherit;
  font-weight: 900;
  cursor: pointer;
}

.section-list {
  display: grid;
  gap: 0.75rem;
  padding: 1.25rem;
}

.section-item {
  border: 1px solid #e2e7f0;
  border-radius: 8px;
  padding: 1rem;
}

.section-heading {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
}

h4 {
  color: #172033;
  font-size: 1rem;
}

.section-heading p,
.online-note {
  margin-top: 0.25rem;
  color: #687386;
  font-size: 0.9rem;
}

.section-heading span {
  color: #17407c;
  font-weight: 900;
}

.meeting-list {
  display: flex;
  flex-wrap: wrap;
  gap: 0.45rem;
  margin: 0.8rem 0 0;
  padding: 0;
  list-style: none;
}

.meeting-list li {
  border-radius: 999px;
  padding: 0.3rem 0.65rem;
  color: #17407c;
  background: #dbeafe;
  font-size: 0.86rem;
  font-weight: 800;
}

@media (max-width: 760px) {
  .card-header,
  .section-heading {
    display: grid;
  }

  .score-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }

  .header-tools {
    justify-items: stretch;
  }
}
</style>
