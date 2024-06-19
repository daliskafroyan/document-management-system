import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'
import dashboard from '../views/app/dashboard.vue'

const fullscreenRoute: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/auth/login.vue'),
    meta: {
      title: 'Login',
      hidden: true
    }
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    ...fullscreenRoute,
    {
      path: '/',
      name: 'Dashboard',
      component: dashboard
    },
  ]
})

export default router
