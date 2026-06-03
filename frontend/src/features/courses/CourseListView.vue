<script setup>
import { computed, onMounted, ref } from 'vue'
import { createCourse, deleteCourse, getCourses, updateCourse } from '@/api/courses'
import CourseForm from './CourseForm.vue'
import CourseTable from './CourseTable.vue'
import SectionPanel from '@/features/sections/SectionPanel.vue'

const courses = ref([])
const loading = ref(false)
const saving = ref(false)
const deletingId = ref(null)
const editingCourse = ref(null)
const selectedCourse = ref(null)
const errorMessage = ref('')
const successMessage = ref('')

const sortedCourses = computed(() =>
  [...courses.value].sort((first, second) => first.code.localeCompare(second.code)),
)

function describeError(error) {
  return error?.message || 'Something went wrong. Please try again.'
}

async function loadCourses() {
  loading.value = true
  errorMessage.value = ''

  try {
    courses.value = await getCourses()
  } catch (error) {
    errorMessage.value = describeError(error)
  } finally {
    loading.value = false
  }
}

async function handleSubmit(payload) {
  saving.value = true
  errorMessage.value = ''
  successMessage.value = ''

  try {
    if (editingCourse.value) {
      const updatedCourse = await updateCourse(editingCourse.value.id, payload)
      courses.value = courses.value.map((course) =>
        course.id === updatedCourse.id ? updatedCourse : course,
      )
      if (selectedCourse.value?.id === updatedCourse.id) {
        selectedCourse.value = updatedCourse
      }
      successMessage.value = 'Course updated.'
    } else {
      const createdCourse = await createCourse(payload)
      courses.value = [...courses.value, createdCourse]
      successMessage.value = 'Course added.'
    }

    editingCourse.value = null
  } catch (error) {
    errorMessage.value = describeError(error)
  } finally {
    saving.value = false
  }
}

function handleEdit(course) {
  editingCourse.value = course
  errorMessage.value = ''
  successMessage.value = ''
}

async function handleDelete(course) {
  if (!window.confirm(`Delete ${course.code}?`)) {
    return
  }

  deletingId.value = course.id
  errorMessage.value = ''
  successMessage.value = ''

  try {
    await deleteCourse(course.id)
    courses.value = courses.value.filter((currentCourse) => currentCourse.id !== course.id)

    if (editingCourse.value?.id === course.id) {
      editingCourse.value = null
    }

    if (selectedCourse.value?.id === course.id) {
      selectedCourse.value = null
    }

    successMessage.value = 'Course deleted.'
  } catch (error) {
    errorMessage.value = describeError(error)
  } finally {
    deletingId.value = null
  }
}

function handleManageSections(course) {
  selectedCourse.value = selectedCourse.value?.id === course.id ? null : course
}

onMounted(loadCourses)
</script>

<template>
  <section class="course-view">
    <header class="page-header">
      <div>
        <p class="eyebrow">Course Planning</p>
        <h1>Course + Work Schedule Optimizer</h1>
        <p class="subtitle">
          Manage the courses that will feed into your schedule options.
        </p>
      </div>
      <button class="refresh-button" type="button" :disabled="loading" @click="loadCourses">
        Refresh
      </button>
    </header>

    <div v-if="errorMessage" class="alert error" role="alert">{{ errorMessage }}</div>
    <div v-if="successMessage" class="alert success" role="status">{{ successMessage }}</div>

    <div class="content-grid">
      <aside class="panel form-panel">
        <CourseForm
          :course="editingCourse"
          :submitting="saving"
          @submit="handleSubmit"
          @cancel="editingCourse = null"
        />
      </aside>

      <section class="panel table-panel" aria-labelledby="courses-heading">
        <div class="section-heading">
          <div>
            <h2 id="courses-heading">Courses</h2>
            <p>{{ courses.length }} total</p>
          </div>
        </div>

        <CourseTable
          :courses="sortedCourses"
          :loading="loading"
          :deleting-id="deletingId"
          :selected-course-id="selectedCourse?.id"
          @edit="handleEdit"
          @delete="handleDelete"
          @manage-sections="handleManageSections"
        />
      </section>

      <SectionPanel :course="selectedCourse" />
    </div>
  </section>
</template>

<style scoped>
.course-view {
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
.subtitle,
.section-heading p {
  margin: 0;
}

h1 {
  color: #111827;
  font-size: clamp(2rem, 5vw, 3.75rem);
  line-height: 1;
}

.subtitle {
  max-width: 42rem;
  margin-top: 0.75rem;
  color: #556174;
  font-size: 1rem;
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
  grid-template-columns: minmax(17rem, 22rem) 1fr;
  gap: 1rem;
  align-items: start;
}

.panel {
  border: 1px solid #dbe1ea;
  border-radius: 8px;
  background: #ffffff;
  box-shadow: 0 12px 30px rgba(24, 34, 53, 0.06);
}

.form-panel {
  padding: 1.25rem;
}

.table-panel {
  overflow: hidden;
}

.section-heading {
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid #e2e7f0;
  padding: 1.25rem;
}

.section-heading h2 {
  color: #172033;
  font-size: 1.1rem;
}

.section-heading p {
  margin-top: 0.25rem;
  color: #687386;
  font-size: 0.9rem;
}

@media (max-width: 860px) {
  .course-view {
    padding: 1rem;
  }

  .page-header {
    display: grid;
  }

  .content-grid {
    grid-template-columns: 1fr;
  }
}
</style>
