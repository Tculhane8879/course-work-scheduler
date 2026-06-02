import { createRouter, createWebHistory } from 'vue-router'
import CourseListView from '@/features/courses/CourseListView.vue'
import ConstraintsView from '@/features/constraints/ConstraintsView.vue'
import ScheduleGeneratorView from '@/features/schedules/ScheduleGeneratorView.vue'
import SavedSchedulesView from '@/features/saved-schedules/SavedSchedulesView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'courses',
      component: CourseListView,
    },
    {
      path: '/constraints',
      name: 'constraints',
      component: ConstraintsView,
    },
    {
      path: '/schedules',
      name: 'schedules',
      component: ScheduleGeneratorView,
    },
    {
      path: '/saved-schedules',
      name: 'saved-schedules',
      component: SavedSchedulesView,
    },
  ],
})

export default router
