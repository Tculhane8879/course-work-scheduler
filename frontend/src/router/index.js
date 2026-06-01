import { createRouter, createWebHistory } from 'vue-router'
import CourseListView from '@/features/courses/CourseListView.vue'
import ConstraintsView from '@/features/constraints/ConstraintsView.vue'

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
  ],
})

export default router
