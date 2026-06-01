<script setup>
defineProps({
  blocks: {
    type: Array,
    required: true,
  },
  loading: {
    type: Boolean,
    default: false,
  },
  deletingId: {
    type: Number,
    default: null,
  },
})

defineEmits(['edit', 'delete'])

function formatDay(dayOfWeek) {
  return dayOfWeek.charAt(0) + dayOfWeek.slice(1).toLowerCase()
}

function formatType(blockType) {
  return {
    WORK: 'Work',
    UNAVAILABLE: 'Unavailable',
    PREFERENCE: 'Preference',
  }[blockType]
}
</script>

<template>
  <div class="block-list">
    <div v-if="loading" class="status">Loading blocks...</div>

    <div v-else-if="blocks.length === 0" class="status empty">
      Add work shifts or unavailable time to shape future schedules.
    </div>

    <article v-for="block in blocks" v-else :key="block.id" class="block-item">
      <div>
        <h3>{{ block.label }}</h3>
        <p>{{ formatDay(block.dayOfWeek) }} {{ block.startTime }}-{{ block.endTime }}</p>
      </div>

      <span class="type-pill" :class="block.blockType.toLowerCase()">
        {{ formatType(block.blockType) }}
      </span>

      <div class="actions">
        <button type="button" @click="$emit('edit', block)">Edit</button>
        <button
          class="danger-button"
          type="button"
          :disabled="deletingId === block.id"
          @click="$emit('delete', block)"
        >
          {{ deletingId === block.id ? 'Deleting...' : 'Delete' }}
        </button>
      </div>
    </article>
  </div>
</template>

<style scoped>
.block-list {
  display: grid;
  gap: 0.75rem;
}

.status {
  display: grid;
  min-height: 14rem;
  place-items: center;
  border: 1px dashed #cfd6e4;
  border-radius: 8px;
  padding: 1.5rem;
  color: #5d687c;
  text-align: center;
}

.empty {
  background: #f8fafc;
}

.block-item {
  display: grid;
  grid-template-columns: 1fr auto auto;
  gap: 1rem;
  align-items: center;
  border: 1px solid #e2e7f0;
  border-radius: 8px;
  padding: 1rem;
  background: #ffffff;
}

h3,
p {
  margin: 0;
}

h3 {
  color: #172033;
  font-size: 1rem;
}

p {
  margin-top: 0.25rem;
  color: #687386;
  font-size: 0.9rem;
}

.type-pill {
  min-width: 6.5rem;
  border-radius: 999px;
  padding: 0.3rem 0.65rem;
  text-align: center;
  font-size: 0.8rem;
  font-weight: 900;
}

.type-pill.work {
  color: #17407c;
  background: #dbeafe;
}

.type-pill.unavailable {
  color: #9f1c14;
  background: #fef3f2;
}

.type-pill.preference {
  color: #35611f;
  background: #dcfce7;
}

.actions {
  display: flex;
  gap: 0.5rem;
}

button {
  min-height: 2.25rem;
  border: 1px solid #cfd6e4;
  border-radius: 6px;
  padding: 0 0.75rem;
  color: #253044;
  background: #ffffff;
  font: inherit;
  font-weight: 800;
  cursor: pointer;
}

button:disabled {
  cursor: not-allowed;
  opacity: 0.7;
}

.danger-button {
  color: #b42318;
  border-color: #f3b4ae;
}

@media (max-width: 780px) {
  .block-item {
    grid-template-columns: 1fr;
  }

  .actions {
    display: grid;
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}
</style>
