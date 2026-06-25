<template>
  <div>
    <h2 style="margin-bottom: 20px;">科室管理</h2>

    <!-- ========== 搜索栏 ========== -->
    <div style="display: flex; flex-wrap: wrap; gap: 10px; align-items: center; margin-bottom: 20px;">
      <el-input
        v-model="searchForm.name"
        placeholder="科室名称"
        clearable
        style="width: 200px;"
        @keyup.enter="handleSearch"
      />
      <el-button type="primary" @click="handleSearch">搜索</el-button>
      <el-button @click="resetSearch">重置</el-button>
      <el-button type="success" @click="openAddModal" style="margin-left: auto;">新增科室</el-button>
    </div>

    <!-- ========== 表格（新增 ID 列） ========== -->
    <el-table
      :data="departments"
      border
      style="width: 100%"
      stripe
      v-loading="loading"
    >
      <el-table-column prop="id" label="科室ID" width="100" />
      <el-table-column prop="name" label="科室名称" />
      <el-table-column prop="description" label="描述" />
      <el-table-column label="操作" width="160" fixed="right">
        <template #default="{ row }">
          <el-button type="primary" size="small" @click="openEditModal(row)">编辑</el-button>
          <el-button type="danger" size="small" @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- ========== 弹窗 ========== -->
    <el-dialog
      v-model="modalVisible"
      :title="isEdit ? '编辑科室' : '新增科室'"
      width="500px"
      destroy-on-close
    >
      <el-form :model="form" label-width="80px">
        <el-form-item label="科室名称" required>
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm">保存</el-button>
          <el-button @click="modalVisible = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getDepartments, addDepartment, updateDepartment, deleteDepartment } from '../api/department'
import { ElMessage, ElMessageBox } from 'element-plus'

const departments = ref([])
const loading = ref(false)
const modalVisible = ref(false)
const isEdit = ref(false)
const form = ref({ id: null, name: '', description: '' })
const searchForm = ref({ name: '' })

const loadDepartments = async () => {
  loading.value = true
  try {
    const params = {}
    if (searchForm.value.name) params.name = searchForm.value.name
    const res = await getDepartments(params)
    departments.value = res.data.data || []
  } catch {
    ElMessage.error('加载失败')
  } finally {
    loading.value = false
  }
}

const handleSearch = () => loadDepartments()
const resetSearch = () => {
  searchForm.value = { name: '' }
  loadDepartments()
}
const openAddModal = () => {
  isEdit.value = false
  form.value = { id: null, name: '', description: '' }
  modalVisible.value = true
}
const openEditModal = (row) => {
  isEdit.value = true
  form.value = { ...row }
  modalVisible.value = true
}
const submitForm = async () => {
  try {
    if (isEdit.value) {
      await updateDepartment(form.value.id, form.value)
      ElMessage.success('修改成功')
    } else {
      await addDepartment(form.value)
      ElMessage.success('新增成功')
    }
    modalVisible.value = false
    await loadDepartments()
  } catch {
    ElMessage.error('操作失败')
  }
}
const handleDelete = (id) => {
  ElMessageBox.confirm('确认删除该科室吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await deleteDepartment(id)
    ElMessage.success('删除成功')
    await loadDepartments()
  }).catch(() => {})
}

onMounted(() => loadDepartments())
</script>

<style scoped>
/* 无额外样式 */
</style>