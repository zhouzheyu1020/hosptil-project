import { defineStore } from 'pinia'
import { ref } from 'vue'
import { login, getCurrentUser, logout } from '../api/auth'

export const useUserStore = defineStore('user', () => {
  // 用户信息
  const userInfo = ref(null)

  // 登录方法
  const doLogin = async (username, password) => {
    const res = await login(username, password)
    if (res.data.code === 200) {
      userInfo.value = res.data.data
      return { success: true }
    } else {
      return { success: false, message: res.data.message }
    }
  }

  // 获取当前用户（用于路由守卫恢复状态）
  const fetchCurrentUser = async () => {
    try {
      const res = await getCurrentUser()
      if (res.data.code === 200) {
        userInfo.value = res.data.data
        return true
      }
      return false
    } catch {
      return false
    }
  }

  // 退出登录
  const doLogout = async () => {
    await logout()
    userInfo.value = null
  }

  return { userInfo, doLogin, fetchCurrentUser, doLogout }
})