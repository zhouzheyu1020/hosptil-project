import axios from 'axios'

const BASE_URL = 'http://localhost:8080'

const api = axios.create({
  baseURL: BASE_URL,
  timeout: 5000,
  withCredentials: true
})

// 查询患者列表（支持搜索参数）
export const getPatients = (params = {}) => {
  return api.get('/patients', { params })
}

// 其他接口保持不变
export const getPatientById = (id) => api.get(`/patients/${id}`)
export const addPatient = (data) => api.post('/patients', data)
export const updatePatient = (id, data) => api.put(`/patients/${id}`, data)
export const deletePatient = (id) => api.delete(`/patients/${id}`)