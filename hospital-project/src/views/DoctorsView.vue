<template>
  <div>
    <h2 style="margin-bottom: 20px;">医生管理</h2>

    <!-- ========== 搜索栏（原始风格 + flex-wrap） ========== -->
    <div style="display: flex; flex-wrap: wrap; gap: 10px; align-items: center; margin-bottom: 20px;">
      <el-input
        v-model="searchForm.name"
        placeholder="医生姓名"
        clearable
        style="width: 180px;"
        @keyup.enter="handleSearch"
      />
      <el-input
        v-model="searchForm.title"
        placeholder="职称"
        clearable
        style="width: 180px;"
        @keyup.enter="handleSearch"
      />
      <el-button type="primary" @click="handleSearch">搜索</el-button>
      <el-button @click="resetSearch">重置</el-button>
      <el-button type="success" @click="openAddModal" style="margin-left: auto;">新增医生</el-button>
    </div>

    <!-- ========== 表格 ========== -->
    <el-table
      :data="doctors"
      border
      style="width: 100%"
      stripe
      v-loading="loading"
    >
      <el-table-column prop="name" label="姓名" />
      <el-table-column prop="gender" label="性别" />
      <el-table-column prop="age" label="年龄" />
      <el-table-column prop="phone" label="电话" />
      <el-table-column prop="title" label="职称" />
      <el-table-column prop="departmentId" label="科室ID" />
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
      :title="isEdit ? '编辑医生' : '新增医生'"
      width="500px"
      destroy-on-close
    >
      <el-form :model="form" label-width="80px">
        <el-form-item label="姓名" required>
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="性别">
          <el-input v-model="form.gender" />
        </el-form-item>
        <el-form-item label="年龄">
          <el-input-number v-model="form.age" :min="0" :max="150" />
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.phone" />
        </el-form-item>
        <el-form-item label="职称">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="科室ID">
          <el-input-number v-model="form.departmentId" :min="1" />
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
import { getDoctors, addDoctor, updateDoctor, deleteDoctor } from '../api/doctor'
import { ElMessage, ElMessageBox } from 'element-plus'

const doctors = ref([])
const loading = ref(false)
const modalVisible = ref(false)
const isEdit = ref(false)
const form = ref({
  id: null,
  name: '',
  gender: '',
  age: '',
  phone: '',
  title: '',
  departmentId: ''
})
const searchForm = ref({ name: '', title: '' })

const loadDoctors = async () => {
  loading.value = true
  try {
    const params = {}
    if (searchForm.value.name) params.name = searchForm.value.name
    if (searchForm.value.title) params.title = searchForm.value.title
    const res = await getDoctors(params)
    doctors.value = res.data.data || []
  } catch {
    ElMessage.error('加载失败')
  } finally {
    loading.value = false
  }
}

const handleSearch = () => loadDoctors()
const resetSearch = () => {
  searchForm.value = { name: '', title: '' }
  loadDoctors()
}
const openAddModal = () => {
  isEdit.value = false
  form.value = { id: null, name: '', gender: '', age: '', phone: '', title: '', departmentId: '' }
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
      await updateDoctor(form.value.id, form.value)
      ElMessage.success('修改成功')
    } else {
      await addDoctor(form.value)
      ElMessage.success('新增成功')
    }
    modalVisible.value = false
    await loadDoctors()
  } catch {
    ElMessage.error('操作失败')
  }
}
const handleDelete = (id) => {
  ElMessageBox.confirm('确认删除该医生吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await deleteDoctor(id)
    ElMessage.success('删除成功')
    await loadDoctors()
  }).catch(() => {})
}

onMounted(() => loadDoctors())
</script>

<style scoped></style>