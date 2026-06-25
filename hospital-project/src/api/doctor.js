import axios from 'axios'

const BASE_URL = 'http://localhost:8080'

const api = axios.create({
  baseURL: BASE_URL,
  timeout: 5000,
  withCredentials: true
})

// 查询医生列表（支持搜索参数）
export const getDoctors = (params = {}) => {
  return api.get('/doctors', { params })
}

export const getDoctorById = (id) => api.get(`/doctors/${id}`)
export const addDoctor = (data) => api.post('/doctors', data)
export const updateDoctor = (id, data) => api.put(`/doctors/${id}`, data)
export const deleteDoctor = (id) => api.delete(`/doctors/${id}`)