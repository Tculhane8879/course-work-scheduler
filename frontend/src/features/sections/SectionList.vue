<script setup>
defineProps({
  sections: {
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

function formatModality(modality) {
  return {
    IN_PERSON: 'In person',
    ONLINE: 'Online',
    HYBRID: 'Hybrid',
  }[modality]
}

function formatDay(dayOfWeek) {
  return dayOfWeek.charAt(0) + dayOfWeek.slice(1).toLowerCase()
}
</script>

<template>
  <div class="section-list">
    <div v-if="loading" class="status">Loading sections...</div>

    <div v-else-if="sections.length === 0" class="status empty">
      Add a section to make this course schedulable.
    </div>

    <article v-for="section in sections" v-else :key="section.id" class="section-item">
      <div class="section-main">
        <div>
          <h4>Section {{ section.sectionCode }}</h4>
          <p>
            {{ formatModality(section.modality) }}
            <template v-if="section.instructor"> · {{ section.instructor }}</template>
            <template v-if="section.location"> · {{ section.location }}</template>
          </p>
        </div>

        <div class="actions">
          <button type="button" @click="$emit('edit', section)">Edit</button>
          <button
            class="danger-button"
            type="button"
            :disabled="deletingId === section.id"
            @click="$emit('delete', section)"
          >
            {{ deletingId === section.id ? 'Deleting...' : 'Delete' }}
          </button>
        </div>
      </div>

      <ul v-if="section.meetings.length" class="meeting-list">
        <li v-for="meeting in section.meetings" :key="meeting.id">
          {{ formatDay(meeting.dayOfWeek) }} {{ meeting.startTime }}-{{ meeting.endTime }}
        </li>
      </ul>
      <p v-else class="online-note">No fixed meeting time.</p>
    </article>
  </div>
</template>

<style scoped>
.section-list {
  display: grid;
  gap: 0.75rem;
}

.status {
  display: grid;
  min-height: 10rem;
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

.section-item {
  border: 1px solid #e2e7f0;
  border-radius: 8px;
  padding: 1rem;
  background: #ffffff;
}

.section-main {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 1rem;
}

h4,
p {
  margin: 0;
}

h4 {
  color: #172033;
  font-size: 1rem;
}

p,
.meeting-list,
.online-note {
  color: #687386;
  font-size: 0.9rem;
}

.section-main p {
  margin-top: 0.25rem;
}

.meeting-list {
  display: flex;
  flex-wrap: wrap;
  gap: 0.45rem;
  margin: 0.8rem 0 0;
  padding: 0;
  list-style: none;
}

.meeting-list li {
  border-radius: 999px;
  padding: 0.3rem 0.65rem;
  color: #17407c;
  background: #dbeafe;
  font-weight: 800;
}

.online-note {
  margin-top: 0.8rem;
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

@media (max-width: 720px) {
  .section-main,
  .actions {
    display: grid;
  }
}
</style>
