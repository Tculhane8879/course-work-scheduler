<script setup>
import ScheduleOptionCard from './ScheduleOptionCard.vue'

defineProps({
  options: {
    type: Array,
    required: true,
  },
  generated: {
    type: Boolean,
    default: false,
  },
})

defineEmits(['save'])
</script>

<template>
  <div class="schedule-options">
    <div v-if="!generated" class="status">
      Select courses and generate schedules to see ranked options.
    </div>

    <div v-else-if="options.length === 0" class="status empty">
      No valid schedules found. Try adding more sections or loosening constraints.
    </div>

    <ScheduleOptionCard
      v-for="(option, index) in options"
      v-else
      :key="index"
      :option="option"
      :rank="index + 1"
      @save="(scheduleOption, rank) => $emit('save', scheduleOption, rank)"
    />
  </div>
</template>

<style scoped>
.schedule-options {
  display: grid;
  gap: 1rem;
}

.status {
  display: grid;
  min-height: 16rem;
  place-items: center;
  border: 1px dashed #cfd6e4;
  border-radius: 8px;
  padding: 2rem;
  color: #5d687c;
  background: #ffffff;
  text-align: center;
}

.empty {
  background: #f8fafc;
}
</style>
