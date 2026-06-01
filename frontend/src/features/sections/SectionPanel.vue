<script setup>
import { computed, ref, watch } from 'vue'
import { createSection, deleteSection, getSections, updateSection } from '@/api/sections'
import SectionForm from './SectionForm.vue'
import SectionList from './SectionList.vue'

const props = defineProps({
  course: {
    type: Object,
    default: null,
  },
})

const sections = ref([])
const loading = ref(false)
const saving = ref(false)
const deletingId = ref(null)
const editingSection = ref(null)
const errorMessage = ref('')
const successMessage = ref('')

const sortedSections = computed(() =>
  [...sections.value].sort((first, second) =>
    first.sectionCode.localeCompare(second.sectionCode),
  ),
)

function describeError(error) {
  return error?.message || 'Something went wrong. Please try again.'
}

async function loadSections() {
  if (!props.course) {
    sections.value = []
    return
  }

  loading.value = true
  errorMessage.value = ''
  successMessage.value = ''

  try {
    sections.value = await getSections(props.course.id)
  } catch (error) {
    errorMessage.value = describeError(error)
  } finally {
    loading.value = false
  }
}

async function handleSubmit(payload) {
  if (!props.course) {
    return
  }

  saving.value = true
  errorMessage.value = ''
  successMessage.value = ''

  try {
    if (editingSection.value) {
      const updatedSection = await updateSection(editingSection.value.id, payload)
      sections.value = sections.value.map((section) =>
        section.id === updatedSection.id ? updatedSection : section,
      )
      successMessage.value = 'Section updated.'
    } else {
      const createdSection = await createSection(props.course.id, payload)
      sections.value = [...sections.value, createdSection]
      successMessage.value = 'Section added.'
    }

    editingSection.value = null
  } catch (error) {
    errorMessage.value = describeError(error)
  } finally {
    saving.value = false
  }
}

function handleEdit(section) {
  editingSection.value = section
  errorMessage.value = ''
  successMessage.value = ''
}

async function handleDelete(section) {
  if (!window.confirm(`Delete section ${section.sectionCode}?`)) {
    return
  }

  deletingId.value = section.id
  errorMessage.value = ''
  successMessage.value = ''

  try {
    await deleteSection(section.id)
    sections.value = sections.value.filter((currentSection) => currentSection.id !== section.id)

    if (editingSection.value?.id === section.id) {
      editingSection.value = null
    }

    successMessage.value = 'Section deleted.'
  } catch (error) {
    errorMessage.value = describeError(error)
  } finally {
    deletingId.value = null
  }
}

watch(() => props.course?.id, loadSections, { immediate: true })
</script>

<template>
  <section class="section-panel" aria-labelledby="sections-heading">
    <div v-if="!course" class="placeholder">
      Select a course to manage its sections and meeting times.
    </div>

    <template v-else>
      <header class="panel-header">
        <div>
          <p class="eyebrow">Sections</p>
          <h2 id="sections-heading">{{ course.code }} · {{ course.name }}</h2>
        </div>
        <button class="refresh-button" type="button" :disabled="loading" @click="loadSections">
          Refresh
        </button>
      </header>

      <div v-if="errorMessage" class="alert error" role="alert">{{ errorMessage }}</div>
      <div v-if="successMessage" class="alert success" role="status">{{ successMessage }}</div>

      <div class="section-grid">
        <div class="form-wrap">
          <SectionForm
            :section="editingSection"
            :submitting="saving"
            @submit="handleSubmit"
            @cancel="editingSection = null"
          />
        </div>

        <SectionList
          :sections="sortedSections"
          :loading="loading"
          :deleting-id="deletingId"
          @edit="handleEdit"
          @delete="handleDelete"
        />
      </div>
    </template>
  </section>
</template>

<style scoped>
.section-panel {
  grid-column: 1 / -1;
  border: 1px solid #dbe1ea;
  border-radius: 8px;
  padding: 1.25rem;
  background: #ffffff;
  box-shadow: 0 12px 30px rgba(24, 34, 53, 0.06);
}

.placeholder {
  display: grid;
  min-height: 9rem;
  place-items: center;
  color: #687386;
  text-align: center;
}

.panel-header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 1rem;
  margin-bottom: 1rem;
}

.eyebrow,
h2 {
  margin: 0;
}

.eyebrow {
  margin-bottom: 0.25rem;
  color: #1f6feb;
  font-size: 0.78rem;
  font-weight: 900;
  letter-spacing: 0;
  text-transform: uppercase;
}

h2 {
  color: #172033;
  font-size: 1.15rem;
}

.refresh-button {
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

.section-grid {
  display: grid;
  grid-template-columns: minmax(20rem, 1fr) minmax(20rem, 1.15fr);
  gap: 1rem;
  align-items: start;
}

.form-wrap {
  border: 1px solid #e2e7f0;
  border-radius: 8px;
  padding: 1rem;
  background: #f8fafc;
}

@media (max-width: 960px) {
  .section-grid,
  .panel-header {
    grid-template-columns: 1fr;
    display: grid;
  }
}
</style>
