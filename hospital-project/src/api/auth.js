import axios from 'axios'

const BASE_URL = 'http://localhost:8080'

const api = axios.create({
  baseURL: BASE_URL,
  timeout: 5000,
  withCredentials: true  // 允许携带 Cookie（Session ID）
})

// 登录
export const login = (username, password) => {
  return api.post('/login', { username, password })
}

// 获取当前用户（用于刷新页面恢复状态）
export const getCurrentUser = () => {
  return api.get('/current-user')
}

// 退出登录
export const logout = () => {
  return api.post('/logout')
}