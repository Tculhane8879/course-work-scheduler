<script setup>
import { computed, reactive, watch } from 'vue'

const props = defineProps({
  course: {
    type: Object,
    default: null,
  },
  submitting: {
    type: Boolean,
    default: false,
  },
})

const emit = defineEmits(['submit', 'cancel'])

const form = reactive({
  code: '',
  name: '',
  priority: 'REQUIRED',
})

const isEditing = computed(() => Boolean(props.course))
const canSubmit = computed(() => form.code.trim() && form.name.trim() && !props.submitting)

function resetForm() {
  form.code = props.course?.code ?? ''
  form.name = props.course?.name ?? ''
  form.priority = props.course?.priority ?? 'REQUIRED'
}

function handleSubmit() {
  if (!canSubmit.value) {
    return
  }

  emit('submit', {
    code: form.code.trim(),
    name: form.name.trim(),
    priority: form.priority,
  })
}

function handleCancel() {
  resetForm()
  emit('cancel')
}

watch(() => props.course, resetForm, { immediate: true })
</script>

<template>
  <form class="course-form" @submit.prevent="handleSubmit">
    <div class="form-header">
      <h2>{{ isEditing ? 'Edit Course' : 'Add Course' }}</h2>
      <button v-if="isEditing" class="ghost-button" type="button" @click="handleCancel">
        Cancel
      </button>
    </div>

    <label class="field">
      <span>Course Code</span>
      <input v-model="form.code" type="text" placeholder="CS 201" required />
    </label>

    <label class="field">
      <span>Course Name</span>
      <input v-model="form.name" type="text" placeholder="Data Structures" required />
    </label>

    <label class="field">
      <span>Priority</span>
      <select v-model="form.priority" required>
        <option value="REQUIRED">Required</option>
        <option value="ELECTIVE">Elective</option>
      </select>
    </label>

    <button class="primary-button" type="submit" :disabled="!canSubmit">
      {{ submitting ? 'Saving...' : isEditing ? 'Save Changes' : 'Add Course' }}
    </button>
  </form>
</template>

<style scoped>
.course-form {
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

.field {
  display: grid;
  gap: 0.4rem;
  color: #3d4658;
  font-size: 0.9rem;
  font-weight: 700;
}

input,
select {
  width: 100%;
  min-height: 2.75rem;
  border: 1px solid #cfd6e4;
  border-radius: 6px;
  padding: 0.65rem 0.75rem;
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
  min-height: 2.5rem;
  border: 0;
  border-radius: 6px;
  padding: 0 1rem;
  font: inherit;
  font-weight: 800;
  cursor: pointer;
}

.primary-button {
  color: #ffffff;
  background: #1f6feb;
}

.primary-button:disabled {
  cursor: not-allowed;
  background: #9aa8bf;
}

.ghost-button {
  color: #415066;
  background: #edf1f7;
}
</style>
