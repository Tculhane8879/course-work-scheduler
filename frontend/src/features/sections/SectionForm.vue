<script setup>
import { computed, reactive, watch } from 'vue'

const props = defineProps({
  section: {
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
  sectionCode: '',
  instructor: '',
  location: '',
  modality: 'IN_PERSON',
  meetings: [],
})

const isEditing = computed(() => Boolean(props.section))
const canSubmit = computed(() => {
  const hasInvalidMeeting = form.meetings.some(
    (meeting) => !meeting.dayOfWeek || !meeting.startTime || !meeting.endTime,
  )

  return form.sectionCode.trim() && !hasInvalidMeeting && !props.submitting
})

function createBlankMeeting() {
  return {
    dayOfWeek: 'MONDAY',
    startTime: '09:00',
    endTime: '10:15',
  }
}

function resetForm() {
  form.sectionCode = props.section?.sectionCode ?? ''
  form.instructor = props.section?.instructor ?? ''
  form.location = props.section?.location ?? ''
  form.modality = props.section?.modality ?? 'IN_PERSON'
  form.meetings = props.section?.meetings?.length
    ? props.section.meetings.map((meeting) => ({
        dayOfWeek: meeting.dayOfWeek,
        startTime: meeting.startTime,
        endTime: meeting.endTime,
      }))
    : [createBlankMeeting()]
}

function addMeeting() {
  form.meetings.push(createBlankMeeting())
}

function removeMeeting(index) {
  form.meetings.splice(index, 1)
}

function handleSubmit() {
  if (!canSubmit.value) {
    return
  }

  emit('submit', {
    sectionCode: form.sectionCode.trim(),
    instructor: form.instructor.trim(),
    location: form.location.trim(),
    modality: form.modality,
    meetings: form.meetings.map((meeting) => ({ ...meeting })),
  })
}

function handleCancel() {
  resetForm()
  emit('cancel')
}

watch(() => props.section, resetForm, { immediate: true })
</script>

<template>
  <form class="section-form" @submit.prevent="handleSubmit">
    <div class="form-header">
      <h3>{{ isEditing ? 'Edit Section' : 'Add Section' }}</h3>
      <button v-if="isEditing" class="ghost-button" type="button" @click="handleCancel">
        Cancel
      </button>
    </div>

    <div class="field-grid">
      <label class="field">
        <span>Section</span>
        <input v-model="form.sectionCode" type="text" placeholder="A" required />
      </label>

      <label class="field">
        <span>Modality</span>
        <select v-model="form.modality" required>
          <option value="IN_PERSON">In person</option>
          <option value="ONLINE">Online</option>
          <option value="HYBRID">Hybrid</option>
        </select>
      </label>
    </div>

    <div class="field-grid">
      <label class="field">
        <span>Instructor</span>
        <input v-model="form.instructor" type="text" placeholder="Dr. Rivera" />
      </label>

      <label class="field">
        <span>Location</span>
        <input v-model="form.location" type="text" placeholder="Science 204" />
      </label>
    </div>

    <div class="meetings-header">
      <h4>Meeting Times</h4>
      <button class="ghost-button" type="button" @click="addMeeting">Add Time</button>
    </div>

    <div v-if="form.meetings.length === 0" class="no-meetings">
      Online sections can be saved without a meeting time.
    </div>

    <div v-for="(meeting, index) in form.meetings" :key="index" class="meeting-row">
      <label class="field">
        <span>Day</span>
        <select v-model="meeting.dayOfWeek" required>
          <option v-for="[value, label] in weekdays" :key="value" :value="value">
            {{ label }}
          </option>
        </select>
      </label>

      <label class="field">
        <span>Start</span>
        <input v-model="meeting.startTime" type="time" required />
      </label>

      <label class="field">
        <span>End</span>
        <input v-model="meeting.endTime" type="time" required />
      </label>

      <button class="remove-button" type="button" @click="removeMeeting(index)">Remove</button>
    </div>

    <button class="primary-button" type="submit" :disabled="!canSubmit">
      {{ submitting ? 'Saving...' : isEditing ? 'Save Section' : 'Add Section' }}
    </button>
  </form>
</template>

<style scoped>
.section-form {
  display: grid;
  gap: 1rem;
}

.form-header,
.meetings-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 1rem;
}

h3,
h4 {
  margin: 0;
  color: #172033;
}

h3 {
  font-size: 1.05rem;
}

h4 {
  font-size: 0.95rem;
}

.field-grid,
.meeting-row {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 0.75rem;
}

.meeting-row {
  grid-template-columns: minmax(7rem, 1fr) minmax(6rem, 0.8fr) minmax(6rem, 0.8fr) auto;
  align-items: end;
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
.ghost-button,
.remove-button {
  min-height: 2.35rem;
  border-radius: 6px;
  padding: 0 0.85rem;
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

.ghost-button,
.remove-button {
  border: 1px solid #cfd6e4;
  color: #415066;
  background: #ffffff;
}

.remove-button {
  color: #b42318;
  border-color: #f3b4ae;
}

.no-meetings {
  border: 1px dashed #cfd6e4;
  border-radius: 6px;
  padding: 0.85rem;
  color: #687386;
  background: #f8fafc;
}

@media (max-width: 720px) {
  .field-grid,
  .meeting-row {
    grid-template-columns: 1fr;
  }
}
</style>
