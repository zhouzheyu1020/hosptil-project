import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../stores/user'

import LoginView from '../views/LoginView.vue'
import PatientsView from '../views/PatientsView.vue'
import DepartmentsView from '../views/DepartmentsView.vue'
import DoctorsView from '../views/DoctorsView.vue'
import AppointmentsView from '../views/AppointmentsView.vue'
import RegisterView from '../views/RegisterView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/login', name: 'login', component: LoginView },
    {
      path: '/',
      redirect: '/patients'
    },
    {
      path: '/patients',
      name: 'patients',
      component: PatientsView,
      meta: { requiresAuth: true }  // 需要登录
    },
    {
      path: '/departments',
      name: 'departments',
      component: DepartmentsView,
      meta: { requiresAuth: true }
    },
    {
      path: '/doctors',
      name: 'doctors',
      component: DoctorsView,
      meta: { requiresAuth: true }
    },
    {
      path: '/appointments',
      name: 'appointments',
      component: AppointmentsView,
      meta: { requiresAuth: true }
    },
    {
  path: '/register',
  name: 'register',
  component: RegisterView,
  // 注册页不需要登录，所以不加 requiresAuth
}
  ]
})

// 全局路由守卫
router.beforeEach(async (to, from, next) => {
  const userStore = useUserStore()

  // 如果目标是登录页，直接放行
  if (to.path === '/login') {
    return next()
  }

  // 如果不需要登录，直接放行
  if (!to.meta.requiresAuth) {
    return next()
  }

  // 需要登录的页面：检查用户是否已登录
  if (userStore.userInfo) {
    // 已登录，放行
    return next()
  }

  // 用户信息为空，尝试从后端 Session 恢复（刷新页面场景）
  const isLoggedIn = await userStore.fetchCurrentUser()
  if (isLoggedIn) {
    return next()
  }

  // 未登录，跳转到登录页
  next('/login')
})

export default router