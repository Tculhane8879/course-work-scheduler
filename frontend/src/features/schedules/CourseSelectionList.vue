<script setup>
defineProps({
  courses: {
    type: Array,
    required: true,
  },
  selectedCourseIds: {
    type: Array,
    required: true,
  },
  loading: {
    type: Boolean,
    default: false,
  },
})

const emit = defineEmits(['toggle-course'])

function isSelected(courseId, selectedCourseIds) {
  return selectedCourseIds.includes(courseId)
}
</script>

<template>
  <div class="course-selection">
    <div v-if="loading" class="status">Loading courses...</div>

    <div v-else-if="courses.length === 0" class="status empty">
      Add courses and sections before generating schedules.
    </div>

    <label v-for="course in courses" v-else :key="course.id" class="course-row">
      <input
        type="checkbox"
        :checked="isSelected(course.id, selectedCourseIds)"
        @change="emit('toggle-course', course.id)"
      />
      <span>
        <strong>{{ course.code }}</strong>
        {{ course.name }}
      </span>
    </label>
  </div>
</template>

<style scoped>
.course-selection {
  display: grid;
  gap: 0.65rem;
}

.course-row {
  display: flex;
  align-items: center;
  gap: 0.7rem;
  border: 1px solid #e2e7f0;
  border-radius: 8px;
  padding: 0.8rem;
  color: #253044;
  background: #ffffff;
  cursor: pointer;
}

.course-row input {
  width: 1rem;
  height: 1rem;
}

.course-row span {
  display: grid;
  gap: 0.15rem;
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
</style>
