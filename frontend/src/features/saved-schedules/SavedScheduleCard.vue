<script setup>
import WeeklyCalendar from '@/features/schedules/WeeklyCalendar.vue'
import SavedScheduleSummary from './SavedScheduleSummary.vue'

defineProps({
  schedule: {
    type: Object,
    required: true,
  },
  deletingId: {
    type: Number,
    default: null,
  },
  markingPreferredId: {
    type: Number,
    default: null,
  },
})

defineEmits(['delete', 'mark-preferred'])
</script>

<template>
  <article class="saved-card">
    <header class="card-header">
      <div>
        <p v-if="schedule.preferred" class="preferred-label">Preferred</p>
        <h2>{{ schedule.name }}</h2>
      </div>
      <div class="actions">
        <button
          type="button"
          :disabled="schedule.preferred || markingPreferredId === schedule.id"
          @click="$emit('mark-preferred', schedule)"
        >
          {{ markingPreferredId === schedule.id ? 'Saving...' : 'Mark Preferred' }}
        </button>
        <button
          class="danger-button"
          type="button"
          :disabled="deletingId === schedule.id"
          @click="$emit('delete', schedule)"
        >
          {{ deletingId === schedule.id ? 'Deleting...' : 'Delete' }}
        </button>
      </div>
    </header>

    <div class="summary-wrap">
      <SavedScheduleSummary :schedule="schedule" />
    </div>

    <WeeklyCalendar :sections="schedule.sections" />
  </article>
</template>

<style scoped>
.saved-card {
  border: 1px solid #dbe1ea;
  border-radius: 8px;
  background: #ffffff;
  box-shadow: 0 12px 30px rgba(24, 34, 53, 0.06);
}

.card-header {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
  border-bottom: 1px solid #e2e7f0;
  padding: 1.25rem;
}

h2,
.preferred-label {
  margin: 0;
}

h2 {
  color: #172033;
  font-size: 1.25rem;
}

.preferred-label {
  margin-bottom: 0.25rem;
  color: #1f6feb;
  font-size: 0.78rem;
  font-weight: 900;
  text-transform: uppercase;
}

.actions {
  display: flex;
  gap: 0.5rem;
}

button {
  min-height: 2.35rem;
  border: 1px solid #cfd6e4;
  border-radius: 6px;
  padding: 0 0.85rem;
  color: #253044;
  background: #ffffff;
  font: inherit;
  font-weight: 800;
  cursor: pointer;
}

button:disabled {
  cursor: not-allowed;
  opacity: 0.65;
}

.danger-button {
  color: #b42318;
  border-color: #f3b4ae;
}

.summary-wrap {
  border-bottom: 1px solid #e2e7f0;
  padding: 1.25rem;
}

@media (max-width: 760px) {
  .card-header,
  .actions {
    display: grid;
  }
}
</style>
