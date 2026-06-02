<script setup>
import { computed } from 'vue'
import { summarizeSchedule } from './savedScheduleUtils'

const props = defineProps({
  schedule: {
    type: Object,
    required: true,
  },
})

const summary = computed(() => summarizeSchedule(props.schedule.sections))
</script>

<template>
  <dl class="summary-grid">
    <div>
      <dt>Score</dt>
      <dd>{{ schedule.score.total }}</dd>
    </div>
    <div>
      <dt>Days</dt>
      <dd>{{ summary.daysOnCampus }}</dd>
    </div>
    <div>
      <dt>Earliest</dt>
      <dd>{{ summary.earliestStart }}</dd>
    </div>
    <div>
      <dt>Latest</dt>
      <dd>{{ summary.latestEnd }}</dd>
    </div>
    <div>
      <dt>Meetings</dt>
      <dd>{{ summary.meetingCount }}</dd>
    </div>
  </dl>
</template>

<style scoped>
.summary-grid {
  display: grid;
  grid-template-columns: repeat(5, minmax(0, 1fr));
  gap: 0.6rem;
  margin: 0;
}

.summary-grid div {
  border-radius: 8px;
  padding: 0.65rem;
  background: #f4f7fb;
  text-align: center;
}

dt {
  color: #687386;
  font-size: 0.72rem;
  font-weight: 900;
  text-transform: uppercase;
}

dd {
  margin: 0.2rem 0 0;
  color: #172033;
  font-weight: 900;
}

@media (max-width: 780px) {
  .summary-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}
</style>
