<script setup>
import { computed, reactive, watch } from 'vue'

const props = defineProps({
  block: {
    type: Object,
    default: null,
  },
  submitting: {
    type: Boolean,
    default: false,
  },
})

const emit = defineEmits(['submit', 'cancel'])

const weekdays = [
  ['MONDAY', 'Monday'],
  ['TUESDAY', 'Tuesday'],
  ['WEDNESDAY', 'Wednesday'],
  ['THURSDAY', 'Thursday'],
  ['FRIDAY', 'Friday'],
  ['SATURDAY', 'Saturday'],
  ['SUNDAY', 'Sunday'],
]

const form = reactive({
  label: '',
  dayOfWeek: 'MONDAY',
  startTime: '09:00',
  endTime: '17:00',
  blockType: 'WORK',
})

const isEditing = computed(() => Boolean(props.block))
const canSubmit = computed(
  () =>
    form.label.trim() &&
    form.dayOfWeek &&
    form.startTime &&
    form.endTime &&
    !props.submitting,
)

function resetForm() {
  form.label = props.block?.label ?? ''
  form.dayOfWeek = props.block?.dayOfWeek ?? 'MONDAY'
  form.startTime = props.block?.startTime ?? '09:00'
  form.endTime = props.block?.endTime ?? '17:00'
  form.blockType = props.block?.blockType ?? 'WORK'
}

function handleSubmit() {
  if (!canSubmit.value) {
    return
  }

  emit('submit', {
    label: form.label.trim(),
    dayOfWeek: form.dayOfWeek,
    startTime: form.startTime,
    endTime: form.endTime,
    blockType: form.blockType,
  })
}

function handleCancel() {
  resetForm()
  emit('cancel')
}

watch(() => props.block, resetForm, { immediate: true })
</script>

<template>
  <form class="block-form" @submit.prevent="handleSubmit">
    <div class="form-header">
      <h2>{{ isEditing ? 'Edit Block' : 'Add Block' }}</h2>
      <button v-if="isEditing" class="ghost-button" type="button" @click="handleCancel">
        Cancel
      </button>
    </div>

    <label class="field">
      <span>Label</span>
      <input v-model="form.label" type="text" placeholder="Work shift" required />
    </label>

    <div class="field-grid">
      <label class="field">
        <span>Day</span>
        <select v-model="form.dayOfWeek" required>
          <option v-for="[value, label] in weekdays" :key="value" :value="value">
            {{ label }}
          </option>
        </select>
      </label>

      <label class="field">
        <span>Type</span>
        <select v-model="form.blockType" required>
          <option value="WORK">Work</option>
          <option value="UNAVAILABLE">Unavailable</option>
          <option value="PREFERENCE">Preference</option>
        </select>
      </label>
    </div>

    <div class="field-grid">
      <label class="field">
        <span>Start</span>
        <input v-model="form.startTime" type="time" required />
      </label>

      <label class="field">
        <span>End</span>
        <input v-model="form.endTime" type="time" required />
      </label>
    </div>

    <button class="primary-button" type="submit" :disabled="!canSubmit">
      {{ submitting ? 'Saving...' : isEditing ? 'Save Block' : 'Add Block' }}
    </button>
  </form>
</template>

<style scoped>
.block-form {
  display: grid;
  gap: 1rem;
}

.form-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 1rem;
}

h2 {
  margin: 0;
  color: #172033;
  font-size: 1.1rem;
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

input,
select {
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

input:focus,
select:focus {
  border-color: #2f6fed;
  outline: 3px solid rgba(47, 111, 237, 0.16);
}

.primary-button,
.ghost-button {
  min-height: 2.4rem;
  border-radius: 6px;
  padding: 0 0.9rem;
  font: inherit;
  font-weight: 800;
  cursor: pointer;
}

.primary-button {
  border: 0;
  color: #ffffff;
  background: #1f6feb;
}

.primary-button:disabled {
  cursor: not-allowed;
  background: #9aa8bf;
}

.ghost-button {
  border: 1px solid #cfd6e4;
  color: #415066;
  background: #ffffff;
}

@media (max-width: 720px) {
  .field-grid {
    grid-template-columns: 1fr;
  }
}
</style>
