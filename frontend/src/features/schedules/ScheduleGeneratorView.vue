<script setup>
import { computed, onMounted, ref } from 'vue'
import { getCourses } from '@/api/courses'
import { generateSchedules } from '@/api/schedules'
import { createSavedSchedule } from '@/api/savedSchedules'
import CourseSelectionList from './CourseSelectionList.vue'
import ScheduleOptionList from './ScheduleOptionList.vue'

const courses = ref([])
const selectedCourseIds = ref([])
const maxResults = ref(10)
const scheduleOptions = ref([])
const loadingCourses = ref(false)
const generating = ref(false)
const generated = ref(false)
const errorMessage = ref('')
const successMessage = ref('')

const sortedCourses = computed(() =>
  [...courses.value].sort((first, second) => first.code.localeCompare(second.code)),
)

const canGenerate = computed(() => selectedCourseIds.value.length > 0 && !generating.value)

function describeError(error) {
  return error?.message || 'Something went wrong. Please try again.'
}

async function loadCourses() {
  loadingCourses.value = true
  errorMessage.value = ''

  try {
    courses.value = await getCourses()
  } catch (error) {
    errorMessage.value = describeError(error)
  } finally {
    loadingCourses.value = false
  }
}

function toggleCourse(courseId) {
  if (selectedCourseIds.value.includes(courseId)) {
    selectedCourseIds.value = selectedCourseIds.value.filter((selectedId) => selectedId !== courseId)
  } else {
    selectedCourseIds.value = [...selectedCourseIds.value, courseId]
  }
}

async function handleGenerate() {
  if (!canGenerate.value) {
    return
  }

  generating.value = true
  generated.value = false
  errorMessage.value = ''
  successMessage.value = ''

  try {
    scheduleOptions.value = await generateSchedules({
      courseIds: selectedCourseIds.value,
      maxResults: Number(maxResults.value),
    })
    generated.value = true
  } catch (error) {
    errorMessage.value = describeError(error)
  } finally {
    generating.value = false
  }
}

async function handleSave(option, rank) {
  const fallbackName = `Schedule option ${rank}`
  const name = window.prompt('Name this saved schedule', fallbackName)

  if (!name || !name.trim()) {
    return
  }

  errorMessage.value = ''
  successMessage.value = ''

  try {
    await createSavedSchedule({
      name: name.trim(),
      score: option.score,
      sections: option.sections,
    })
    successMessage.value = 'Schedule saved.'
  } catch (error) {
    errorMessage.value = describeError(error)
  }
}

onMounted(loadCourses)
</script>

<template>
  <section class="schedules-view">
    <header class="page-header">
      <div>
        <p class="eyebrow">Schedule Generation</p>
        <h1>Generate Ranked Schedules</h1>
        <p class="subtitle">
          Select courses and let the engine combine sections while respecting conflicts and work blocks.
        </p>
      </div>
      <button class="refresh-button" type="button" :disabled="loadingCourses" @click="loadCourses">
        Refresh Courses
      </button>
    </header>

    <div v-if="errorMessage" class="alert error" role="alert">{{ errorMessage }}</div>
    <div v-if="successMessage" class="alert success" role="status">{{ successMessage }}</div>

    <div class="content-grid">
      <aside class="panel control-panel">
        <div class="panel-heading">
          <h2>Courses</h2>
          <p>{{ selectedCourseIds.length }} selected</p>
        </div>

        <CourseSelectionList
          :courses="sortedCourses"
          :selected-course-ids="selectedCourseIds"
          :loading="loadingCourses"
          @toggle-course="toggleCourse"
        />

        <label class="field">
          <span>Max Results</span>
          <input v-model.number="maxResults" type="number" min="1" max="25" />
        </label>

        <button class="primary-button" type="button" :disabled="!canGenerate" @click="handleGenerate">
          {{ generating ? 'Generating...' : 'Generate Schedules' }}
        </button>
      </aside>

      <section class="results-panel" aria-label="Generated schedule options">
        <ScheduleOptionList
          :options="scheduleOptions"
          :generated="generated"
          @save="handleSave"
        />
      </section>
    </div>
  </section>
</template>

<style scoped>
.schedules-view {
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
  letter-spacing: 0;
  text-transform: uppercase;
}

h1,
h2,
p {
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
  font-size: 1rem;
}

.refresh-button,
.primary-button {
  min-height: 2.5rem;
  border-radius: 6px;
  padding: 0 1rem;
  font: inherit;
  font-weight: 800;
  cursor: pointer;
}

.refresh-button {
  border: 1px solid #cfd6e4;
  color: #253044;
  background: #ffffff;
}

.primary-button {
  border: 0;
  color: #ffffff;
  background: #1f6feb;
}

.primary-button:disabled,
.refresh-button:disabled {
  cursor: not-allowed;
  opacity: 0.7;
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

.content-grid {
  display: grid;
  grid-template-columns: minmax(18rem, 23rem) 1fr;
  gap: 1rem;
  align-items: start;
}

.panel {
  border: 1px solid #dbe1ea;
  border-radius: 8px;
  background: #ffffff;
  box-shadow: 0 12px 30px rgba(24, 34, 53, 0.06);
}

.control-panel {
  display: grid;
  gap: 1rem;
  padding: 1.25rem;
}

.panel-heading h2 {
  color: #172033;
  font-size: 1.1rem;
}

.panel-heading p {
  margin-top: 0.2rem;
  color: #687386;
  font-size: 0.9rem;
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
}

@media (max-width: 900px) {
  .schedules-view {
    padding: 1rem;
  }

  .page-header,
  .content-grid {
    display: grid;
    grid-template-columns: 1fr;
  }
}
</style>
