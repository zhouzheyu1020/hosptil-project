import axios from 'axios'

const BASE_URL = 'http://localhost:8080'

const api = axios.create({
  baseURL: BASE_URL,
  timeout: 5000,
  withCredentials: true
})

// 查询预约列表（支持搜索参数）
export const getAppointments = (params = {}) => {
  return api.get('/appointments', { params })
}

// 其他接口保持不变
export const getAppointmentById = (id) => api.get(`/appointments/${id}`)
export const addAppointment = (data) => api.post('/appointments', data)
export const updateAppointment = (id, data) => api.put(`/appointments/${id}`, data)
export const deleteAppointment = (id) => api.delete(`/appointments/${id}`)
export const updateAppointmentStatus = (id, status) =>
  api.put(`/appointments/${id}/status?status=${status}`)