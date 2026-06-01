<script setup>
import { computed, onMounted, ref } from 'vue'
import {
  createAvailabilityBlock,
  deleteAvailabilityBlock,
  getAvailabilityBlocks,
  updateAvailabilityBlock,
} from '@/api/availability'
import { getPreferences, updatePreferences } from '@/api/preferences'
import AvailabilityBlockForm from './AvailabilityBlockForm.vue'
import AvailabilityBlockList from './AvailabilityBlockList.vue'
import SchedulePreferenceForm from './SchedulePreferenceForm.vue'

const blocks = ref([])
const preferences = ref(null)
const loadingBlocks = ref(false)
const loadingPreferences = ref(false)
const savingBlock = ref(false)
const savingPreferences = ref(false)
const deletingId = ref(null)
const editingBlock = ref(null)
const errorMessage = ref('')
const successMessage = ref('')

const sortedBlocks = computed(() =>
  [...blocks.value].sort((first, second) => {
    if (first.dayOfWeek === second.dayOfWeek) {
      return first.startTime.localeCompare(second.startTime)
    }

    return first.dayOfWeek.localeCompare(second.dayOfWeek)
  }),
)

function describeError(error) {
  return error?.message || 'Something went wrong. Please try again.'
}

async function loadBlocks() {
  loadingBlocks.value = true
  errorMessage.value = ''

  try {
    blocks.value = await getAvailabilityBlocks()
  } catch (error) {
    errorMessage.value = describeError(error)
  } finally {
    loadingBlocks.value = false
  }
}

async function loadPreferences() {
  loadingPreferences.value = true
  errorMessage.value = ''

  try {
    preferences.value = await getPreferences()
  } catch (error) {
    errorMessage.value = describeError(error)
  } finally {
    loadingPreferences.value = false
  }
}

async function handleBlockSubmit(payload) {
  savingBlock.value = true
  errorMessage.value = ''
  successMessage.value = ''

  try {
    if (editingBlock.value) {
      const updatedBlock = await updateAvailabilityBlock(editingBlock.value.id, payload)
      blocks.value = blocks.value.map((block) =>
        block.id === updatedBlock.id ? updatedBlock : block,
      )
      successMessage.value = 'Availability block updated.'
    } else {
      const createdBlock = await createAvailabilityBlock(payload)
      blocks.value = [...blocks.value, createdBlock]
      successMessage.value = 'Availability block added.'
    }

    editingBlock.value = null
  } catch (error) {
    errorMessage.value = describeError(error)
  } finally {
    savingBlock.value = false
  }
}

function handleEditBlock(block) {
  editingBlock.value = block
  errorMessage.value = ''
  successMessage.value = ''
}

async function handleDeleteBlock(block) {
  if (!window.confirm(`Delete ${block.label}?`)) {
    return
  }

  deletingId.value = block.id
  errorMessage.value = ''
  successMessage.value = ''

  try {
    await deleteAvailabilityBlock(block.id)
    blocks.value = blocks.value.filter((currentBlock) => currentBlock.id !== block.id)

    if (editingBlock.value?.id === block.id) {
      editingBlock.value = null
    }

    successMessage.value = 'Availability block deleted.'
  } catch (error) {
    errorMessage.value = describeError(error)
  } finally {
    deletingId.value = null
  }
}

async function handlePreferenceSubmit(payload) {
  savingPreferences.value = true
  errorMessage.value = ''
  successMessage.value = ''

  try {
    preferences.value = await updatePreferences(payload)
    successMessage.value = 'Schedule preferences saved.'
  } catch (error) {
    errorMessage.value = describeError(error)
  } finally {
    savingPreferences.value = false
  }
}

onMounted(() => {
  loadBlocks()
  loadPreferences()
})
</script>

<template>
  <section class="constraints-view">
    <header class="page-header">
      <div>
        <p class="eyebrow">Constraints</p>
        <h1>Work Availability + Preferences</h1>
        <p class="subtitle">
          Capture the fixed blocks and scheduling preferences that will guide generated schedules.
        </p>
      </div>
      <button class="refresh-button" type="button" @click="loadBlocks">Refresh Blocks</button>
    </header>

    <div v-if="errorMessage" class="alert error" role="alert">{{ errorMessage }}</div>
    <div v-if="successMessage" class="alert success" role="status">{{ successMessage }}</div>

    <div class="content-grid">
      <aside class="panel form-panel">
        <AvailabilityBlockForm
          :block="editingBlock"
          :submitting="savingBlock"
          @submit="handleBlockSubmit"
          @cancel="editingBlock = null"
        />
      </aside>

      <section class="panel list-panel" aria-labelledby="blocks-heading">
        <div class="section-heading">
          <div>
            <h2 id="blocks-heading">Availability Blocks</h2>
            <p>{{ blocks.length }} total</p>
          </div>
        </div>

        <AvailabilityBlockList
          :blocks="sortedBlocks"
          :loading="loadingBlocks"
          :deleting-id="deletingId"
          @edit="handleEditBlock"
          @delete="handleDeleteBlock"
        />
      </section>

      <section class="panel preference-panel">
        <div v-if="loadingPreferences" class="preference-loading">Loading preferences...</div>
        <SchedulePreferenceForm
          v-else
          :preferences="preferences"
          :submitting="savingPreferences"
          @submit="handlePreferenceSubmit"
        />
      </section>
    </div>
  </section>
</template>

<style scoped>
.constraints-view {
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
  font-size: clamp(2rem, 5vw, 3.5rem);
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

.form-panel,
.preference-panel {
  padding: 1.25rem;
}

.list-panel {
  overflow: hidden;
}

.section-heading {
  border-bottom: 1px solid #e2e7f0;
  padding: 1.25rem;
}

.section-heading h2 {
  color: #172033;
  font-size: 1.1rem;
}

.section-heading p,
.preference-loading {
  margin-top: 0.25rem;
  color: #687386;
  font-size: 0.9rem;
}

.preference-panel {
  grid-column: 1 / -1;
}

@media (max-width: 860px) {
  .constraints-view {
    padding: 1rem;
  }

  .page-header,
  .content-grid {
    display: grid;
    grid-template-columns: 1fr;
  }
}
</style>
