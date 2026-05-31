<script setup>
defineProps({
  courses: {
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

function formatPriority(priority) {
  return priority === 'REQUIRED' ? 'Required' : 'Elective'
}
</script>

<template>
  <div class="course-table-wrap">
    <div v-if="loading" class="status">Loading courses...</div>

    <div v-else-if="courses.length === 0" class="status empty">
      Add your first course to start building a schedule plan.
    </div>

    <table v-else class="course-table">
      <thead>
        <tr>
          <th>Code</th>
          <th>Name</th>
          <th>Priority</th>
          <th class="actions-heading">Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="course in courses" :key="course.id">
          <td data-label="Code">
            <strong>{{ course.code }}</strong>
          </td>
          <td data-label="Name">{{ course.name }}</td>
          <td data-label="Priority">
            <span class="priority-pill" :class="course.priority.toLowerCase()">
              {{ formatPriority(course.priority) }}
            </span>
          </td>
          <td class="actions" data-label="Actions">
            <button type="button" @click="$emit('edit', course)">Edit</button>
            <button
              class="danger-button"
              type="button"
              :disabled="deletingId === course.id"
              @click="$emit('delete', course)"
            >
              {{ deletingId === course.id ? 'Deleting...' : 'Delete' }}
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
.course-table-wrap {
  min-height: 16rem;
}

.status {
  display: grid;
  min-height: 16rem;
  place-items: center;
  border: 1px dashed #cfd6e4;
  border-radius: 8px;
  padding: 2rem;
  color: #5d687c;
  text-align: center;
}

.empty {
  background: #f8fafc;
}

.course-table {
  width: 100%;
  border-collapse: collapse;
}

th,
td {
  border-bottom: 1px solid #e2e7f0;
  padding: 0.9rem 0.75rem;
  text-align: left;
  vertical-align: middle;
}

th {
  color: #687386;
  font-size: 0.78rem;
  letter-spacing: 0;
  text-transform: uppercase;
}

td {
  color: #202a3c;
}

.actions-heading {
  text-align: right;
}

.priority-pill {
  display: inline-flex;
  min-width: 5.5rem;
  justify-content: center;
  border-radius: 999px;
  padding: 0.25rem 0.6rem;
  font-size: 0.8rem;
  font-weight: 800;
}

.priority-pill.required {
  color: #17407c;
  background: #dbeafe;
}

.priority-pill.elective {
  color: #35611f;
  background: #dcfce7;
}

.actions {
  display: flex;
  justify-content: flex-end;
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
  .course-table,
  .course-table thead,
  .course-table tbody,
  .course-table th,
  .course-table td,
  .course-table tr {
    display: block;
  }

  .course-table thead {
    display: none;
  }

  .course-table tr {
    border-bottom: 1px solid #dbe1ea;
    padding: 0.85rem 0;
  }

  .course-table td {
    display: flex;
    justify-content: space-between;
    gap: 1rem;
    border-bottom: 0;
    padding: 0.45rem 0;
  }

  .course-table td::before {
    content: attr(data-label);
    color: #687386;
    font-weight: 800;
  }

  .actions {
    justify-content: space-between;
  }
}
</style>
