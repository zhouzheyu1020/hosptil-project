import axios from 'axios'

const BASE_URL = 'http://localhost:8080'

const api = axios.create({
  baseURL: BASE_URL,
  timeout: 5000,
  withCredentials: true
})

// 查询科室列表（支持搜索参数）
export const getDepartments = (params = {}) => {
  return api.get('/departments', { params })
}

export const getDepartmentById = (id) => api.get(`/departments/${id}`)
export const addDepartment = (data) => api.post('/departments', data)
export const updateDepartment = (id, data) => api.put(`/departments/${id}`, data)
export const deleteDepartment = (id) => api.delete(`/departments/${id}`)