<script setup>
import { onMounted, ref } from 'vue'
import {
  deleteSavedSchedule,
  getSavedSchedules,
  markSavedSchedulePreferred,
} from '@/api/savedSchedules'
import SavedScheduleCard from './SavedScheduleCard.vue'

const savedSchedules = ref([])
const loading = ref(false)
const deletingId = ref(null)
const markingPreferredId = ref(null)
const errorMessage = ref('')
const successMessage = ref('')

function describeError(error) {
  return error?.message || 'Something went wrong. Please try again.'
}

async function loadSavedSchedules() {
  loading.value = true
  errorMessage.value = ''

  try {
    savedSchedules.value = await getSavedSchedules()
  } catch (error) {
    errorMessage.value = describeError(error)
  } finally {
    loading.value = false
  }
}

async function handleMarkPreferred(schedule) {
  markingPreferredId.value = schedule.id
  errorMessage.value = ''
  successMessage.value = ''

  try {
    const preferredSchedule = await markSavedSchedulePreferred(schedule.id)
    savedSchedules.value = savedSchedules.value.map((currentSchedule) => ({
      ...currentSchedule,
      preferred: currentSchedule.id === preferredSchedule.id,
    }))
    successMessage.value = 'Preferred schedule updated.'
  } catch (error) {
    errorMessage.value = describeError(error)
  } finally {
    markingPreferredId.value = null
  }
}

async function handleDelete(schedule) {
  if (!window.confirm(`Delete ${schedule.name}?`)) {
    return
  }

  deletingId.value = schedule.id
  errorMessage.value = ''
  successMessage.value = ''

  try {
    await deleteSavedSchedule(schedule.id)
    savedSchedules.value = savedSchedules.value.filter(
      (currentSchedule) => currentSchedule.id !== schedule.id,
    )
    successMessage.value = 'Saved schedule deleted.'
  } catch (error) {
    errorMessage.value = describeError(error)
  } finally {
    deletingId.value = null
  }
}

onMounted(loadSavedSchedules)
</script>

<template>
  <section class="saved-view">
    <header class="page-header">
      <div>
        <p class="eyebrow">Saved Schedules</p>
        <h1>Compare Saved Options</h1>
        <p class="subtitle">
          Review saved schedule snapshots, compare their summaries, and choose a preferred plan.
        </p>
      </div>
      <button class="refresh-button" type="button" :disabled="loading" @click="loadSavedSchedules">
        Refresh
      </button>
    </header>

    <div v-if="errorMessage" class="alert error" role="alert">{{ errorMessage }}</div>
    <div v-if="successMessage" class="alert success" role="status">{{ successMessage }}</div>

    <div v-if="loading" class="status">Loading saved schedules...</div>
    <div v-else-if="savedSchedules.length === 0" class="status empty">
      Save generated schedules to compare them here.
    </div>
    <div v-else class="saved-list">
      <SavedScheduleCard
        v-for="schedule in savedSchedules"
        :key="schedule.id"
        :schedule="schedule"
        :deleting-id="deletingId"
        :marking-preferred-id="markingPreferredId"
        @delete="handleDelete"
        @mark-preferred="handleMarkPreferred"
      />
    </div>
  </section>
</template>

<style scoped>
.saved-view {
  width: min(1180px, 100%);
  margin: 0 auto;
  padding: 2rem;
}

.page-header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 1.5rem;
  margin-bottom: 1.25rem;
}

.eyebrow {
  margin: 0 0 0.35rem;
  color: #1f6feb;
  font-size: 0.82rem;
  font-weight: 900;
  text-transform: uppercase;
}

h1,
.subtitle {
  margin: 0;
}

h1 {
  color: #111827;
  font-size: clamp(2rem, 5vw, 3.5rem);
  line-height: 1;
}

.subtitle {
  max-width: 44rem;
  margin-top: 0.75rem;
  color: #556174;
}

.refresh-button {
  min-height: 2.5rem;
  border: 1px solid #cfd6e4;
  border-radius: 6px;
  padding: 0 1rem;
  color: #253044;
  background: #ffffff;
  font: inherit;
  font-weight: 800;
  cursor: pointer;
}

.alert {
  border-radius: 6px;
  margin-bottom: 1rem;
  padding: 0.8rem 1rem;
  font-weight: 800;
}

.error {
  color: #9f1c14;
  background: #fef3f2;
}

.success {
  color: #17643a;
  background: #ecfdf3;
}

.status {
  display: grid;
  min-height: 16rem;
  place-items: center;
  border: 1px dashed #cfd6e4;
  border-radius: 8px;
  padding: 2rem;
  color: #5d687c;
  background: #ffffff;
  text-align: center;
}

.empty {
  background: #f8fafc;
}

.saved-list {
  display: grid;
  gap: 1rem;
}

@media (max-width: 760px) {
  .saved-view {
    padding: 1rem;
  }

  .page-header {
    display: grid;
  }
}
</style>
