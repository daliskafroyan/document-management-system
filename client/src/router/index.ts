import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'
import dashboard from '../views/app/dashboard.vue'

const authRoute: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/auth/login.vue'),
    meta: {
      title: 'Login',
      hidden: true,
      requiresAuth: false
    }
  },
  {
    path: '/signup',
    name: 'Sign Up',
    component: () => import('../views/auth/signup.vue'),
    meta: {
      title: 'Sign Up',
      hidden: true,
      requiresAuth: false
    }
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    ...authRoute,
    {
      path: '/',
      name: 'Dashboard',
      component: dashboard,
      meta: {
        title: 'Sign Up',
        hidden: true,
        requiresAuth: true
      }
    },
  ]
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token');

  if (to.matched.some(record => record.meta.requiresAuth) && !token) {
    next({ path: '/login' });
  } else if ((to.name === 'Login' || to.name === 'Sign Up') && token) {
    next({ path: '/' });
  } else {
    next();
  }
});

export default router
