<script setup>
import { reactive, watch } from 'vue'

const props = defineProps({
  preferences: {
    type: Object,
    default: null,
  },
  submitting: {
    type: Boolean,
    default: false,
  },
})

const emit = defineEmits(['submit'])

const form = reactive({
  avoidEarlyMorning: false,
  avoidLateEvening: false,
  preferCompactSchedule: true,
  preferNoFridayClasses: false,
  maxClassesPerDay: 3,
  minimumGapMinutes: 30,
})

function resetForm() {
  form.avoidEarlyMorning = props.preferences?.avoidEarlyMorning ?? false
  form.avoidLateEvening = props.preferences?.avoidLateEvening ?? false
  form.preferCompactSchedule = props.preferences?.preferCompactSchedule ?? true
  form.preferNoFridayClasses = props.preferences?.preferNoFridayClasses ?? false
  form.maxClassesPerDay = props.preferences?.maxClassesPerDay ?? 3
  form.minimumGapMinutes = props.preferences?.minimumGapMinutes ?? 30
}

function handleSubmit() {
  emit('submit', {
    avoidEarlyMorning: form.avoidEarlyMorning,
    avoidLateEvening: form.avoidLateEvening,
    preferCompactSchedule: form.preferCompactSchedule,
    preferNoFridayClasses: form.preferNoFridayClasses,
    maxClassesPerDay: Number(form.maxClassesPerDay),
    minimumGapMinutes: Number(form.minimumGapMinutes),
  })
}

watch(() => props.preferences, resetForm, { immediate: true })
</script>

<template>
  <form class="preference-form" @submit.prevent="handleSubmit">
    <h2>Schedule Preferences</h2>

    <label class="check-row">
      <input v-model="form.avoidEarlyMorning" type="checkbox" />
      <span>Avoid early morning classes</span>
    </label>

    <label class="check-row">
      <input v-model="form.avoidLateEvening" type="checkbox" />
      <span>Avoid late evening classes</span>
    </label>

    <label class="check-row">
      <input v-model="form.preferCompactSchedule" type="checkbox" />
      <span>Prefer compact schedules</span>
    </label>

    <label class="check-row">
      <input v-model="form.preferNoFridayClasses" type="checkbox" />
      <span>Prefer no Friday classes</span>
    </label>

    <div class="field-grid">
      <label class="field">
        <span>Max Classes Per Day</span>
        <input v-model.number="form.maxClassesPerDay" type="number" min="1" max="8" />
      </label>

      <label class="field">
        <span>Minimum Gap Minutes</span>
        <input v-model.number="form.minimumGapMinutes" type="number" min="0" max="240" step="5" />
      </label>
    </div>

    <button class="primary-button" type="submit" :disabled="submitting">
      {{ submitting ? 'Saving...' : 'Save Preferences' }}
    </button>
  </form>
</template>

<style scoped>
.preference-form {
  display: grid;
  gap: 0.85rem;
}

h2 {
  margin: 0 0 0.15rem;
  color: #172033;
  font-size: 1.1rem;
}

.check-row {
  display: flex;
  align-items: center;
  gap: 0.65rem;
  color: #2f394c;
  font-weight: 800;
}

.check-row input {
  width: 1rem;
  height: 1rem;
}

.field-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 0.75rem;
}

.field {
  display: grid;
  gap: 0.35rem;
  color: #3d4658;
  font-size: 0.86rem;
  font-weight: 800;
}

.field input {
  width: 100%;
  min-height: 2.5rem;
  border: 1px solid #cfd6e4;
  border-radius: 6px;
  padding: 0.55rem 0.65rem;
  color: #182235;
  background: #ffffff;
  font: inherit;
  font-weight: 500;
}

.field input:focus {
  border-color: #2f6fed;
  outline: 3px solid rgba(47, 111, 237, 0.16);
}

.primary-button {
  min-height: 2.4rem;
  border: 0;
  border-radius: 6px;
  padding: 0 0.9rem;
  color: #ffffff;
  background: #1f6feb;
  font: inherit;
  font-weight: 800;
  cursor: pointer;
}

.primary-button:disabled {
  cursor: not-allowed;
  background: #9aa8bf;
}

@media (max-width: 720px) {
  .field-grid {
    grid-template-columns: 1fr;
  }
}
</style>
