<template>
  <div>
    <h2 style="margin-bottom: 20px;">患者管理</h2>

    <!-- ========== 搜索栏（原始风格，仅加 flex-wrap） ========== -->
    <div style="display: flex; flex-wrap: wrap; gap: 10px; align-items: center; margin-bottom: 20px;">
      <el-input
        v-model="searchForm.name"
        placeholder="患者姓名"
        clearable
        style="width: 180px;"
        @keyup.enter="handleSearch"
      />
      <el-input
        v-model="searchForm.phone"
        placeholder="手机号"
        clearable
        style="width: 180px;"
        @keyup.enter="handleSearch"
      />
      <el-button type="primary" @click="handleSearch">搜索</el-button>
      <el-button @click="resetSearch">重置</el-button>
      <el-button type="success" @click="openAddModal" style="margin-left: auto;">新增患者</el-button>
    </div>

    <!-- ========== 表格 ========== -->
    <el-table
      :data="patients"
      border
      style="width: 100%"
      stripe
      v-loading="loading"
    >
      <el-table-column prop="name" label="姓名" />
      <el-table-column prop="gender" label="性别" />
      <el-table-column prop="age" label="年龄" />
      <el-table-column prop="phone" label="电话" />
      <el-table-column prop="idCard" label="身份证" />
      <el-table-column prop="address" label="地址" />
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
      :title="isEdit ? '编辑患者' : '新增患者'"
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
        <el-form-item label="身份证">
          <el-input v-model="form.idCard" />
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="form.address" />
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
import { getPatients, addPatient, updatePatient, deletePatient } from '../api/patient'
import { ElMessage, ElMessageBox } from 'element-plus'

const patients = ref([])
const loading = ref(false)
const modalVisible = ref(false)
const isEdit = ref(false)
const form = ref({
  id: null,
  name: '',
  gender: '',
  age: '',
  phone: '',
  idCard: '',
  address: ''
})
const searchForm = ref({ name: '', phone: '' })

const loadPatients = async () => {
  loading.value = true
  try {
    const params = {}
    if (searchForm.value.name) params.name = searchForm.value.name
    if (searchForm.value.phone) params.phone = searchForm.value.phone
    const res = await getPatients(params)
    patients.value = res.data.data || []
  } catch {
    ElMessage.error('加载失败')
  } finally {
    loading.value = false
  }
}

const handleSearch = () => loadPatients()
const resetSearch = () => {
  searchForm.value = { name: '', phone: '' }
  loadPatients()
}
const openAddModal = () => {
  isEdit.value = false
  form.value = { id: null, name: '', gender: '', age: '', phone: '', idCard: '', address: '' }
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
      await updatePatient(form.value.id, form.value)
      ElMessage.success('修改成功')
    } else {
      await addPatient(form.value)
      ElMessage.success('新增成功')
    }
    modalVisible.value = false
    await loadPatients()
  } catch {
    ElMessage.error('操作失败')
  }
}
const handleDelete = (id) => {
  ElMessageBox.confirm('确认删除该患者吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await deletePatient(id)
    ElMessage.success('删除成功')
    await loadPatients()
  }).catch(() => {})
}

onMounted(() => loadPatients())
</script>

<style scoped>
/* 没有任何额外样式，保持 Element Plus 原汁原味 */
</style>