<template>
  <div>
    <h2 style="margin-bottom: 20px;">预约管理</h2>

    <!-- ========== 搜索栏（原始风格 + flex-wrap） ========== -->
    <div style="display: flex; flex-wrap: wrap; gap: 10px; align-items: center; margin-bottom: 20px;">
      <el-input
        v-model="searchForm.patientName"
        placeholder="患者姓名"
        clearable
        style="width: 180px;"
        @keyup.enter="handleSearch"
      />
      <el-input
        v-model="searchForm.doctorName"
        placeholder="医生姓名"
        clearable
        style="width: 180px;"
        @keyup.enter="handleSearch"
      />
      <el-select
        v-model="searchForm.status"
        placeholder="全部状态"
        clearable
        style="width: 150px;"
      >
        <el-option label="待就诊" value="待就诊" />
        <el-option label="已就诊" value="已就诊" />
        <el-option label="已取消" value="已取消" />
      </el-select>
      <el-button type="primary" @click="handleSearch">搜索</el-button>
      <el-button @click="resetSearch">重置</el-button>
      <el-button type="success" @click="openAddModal" style="margin-left: auto;">新增预约</el-button>
    </div>

    <!-- ========== 表格 ========== -->
    <el-table
      :data="appointments"
      border
      style="width: 100%"
      stripe
      v-loading="loading"
    >
      <el-table-column prop="patientName" label="患者姓名" />
      <el-table-column prop="doctorName" label="医生姓名" />
      <el-table-column prop="departmentName" label="科室" />
      <el-table-column prop="appointmentTime" label="预约时间">
        <template #default="{ row }">
          {{ formatTime(row.appointmentTime) }}
        </template>
      </el-table-column>
      <el-table-column label="状态">
        <template #default="{ row }">
          <el-tag :type="statusTagType(row.status)" size="small" style="margin-right: 5px;">
            {{ row.status }}
          </el-tag>
          <el-select
            v-model="row.status"
            size="small"
            style="width: 100px;"
            @change="changeStatus(row.id, row.status)"
          >
            <el-option label="待就诊" value="待就诊" />
            <el-option label="已就诊" value="已就诊" />
            <el-option label="已取消" value="已取消" />
          </el-select>
        </template>
      </el-table-column>
      <el-table-column prop="remark" label="备注" />
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
      :title="isEdit ? '编辑预约' : '新增预约'"
      width="500px"
      destroy-on-close
    >
      <el-form :model="form" label-width="80px">
        <el-form-item label="选择患者" required>
          <el-select v-model="form.patientId" placeholder="请选择患者" style="width: 100%;">
            <el-option
              v-for="p in patients"
              :key="p.id"
              :label="p.name + ' (' + p.phone + ')'"
              :value="p.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="选择科室" required>
          <el-select
            v-model="selectedDeptId"
            placeholder="请选择科室"
            style="width: 100%;"
            @change="onDeptChange"
          >
            <el-option
              v-for="d in departments"
              :key="d.id"
              :label="d.name"
              :value="d.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="选择医生" required>
          <el-select v-model="form.doctorId" placeholder="请选择医生" style="width: 100%;">
            <el-option
              v-for="d in filteredDoctors"
              :key="d.id"
              :label="d.name + ' (' + (d.title || '暂无职称') + ')'"
              :value="d.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="预约时间" required>
          <el-date-picker
            v-model="form.appointmentTime"
            type="datetime"
            placeholder="选择日期时间"
            format="YYYY-MM-DD HH:mm"
            value-format="YYYY-MM-DDTHH:mm:ss"
            style="width: 100%;"
          />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status" style="width: 100%;">
            <el-option label="待就诊" value="待就诊" />
            <el-option label="已就诊" value="已就诊" />
            <el-option label="已取消" value="已取消" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" />
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
import { getAppointments, addAppointment, updateAppointment, deleteAppointment, updateAppointmentStatus } from '../api/appointment'
import { getPatients } from '../api/patient'
import { getDepartments } from '../api/department'
import { getDoctors } from '../api/doctor'
import { ElMessage, ElMessageBox } from 'element-plus'

const appointments = ref([])
const loading = ref(false)
const modalVisible = ref(false)
const isEdit = ref(false)
const form = ref({
  id: null,
  patientId: '',
  doctorId: '',
  appointmentTime: '',
  status: '待就诊',
  remark: ''
})

const patients = ref([])
const departments = ref([])
const allDoctors = ref([])
const filteredDoctors = ref([])
const selectedDeptId = ref('')
const searchForm = ref({ patientName: '', doctorName: '', status: '' })

const formatTime = (datetime) => {
  if (!datetime) return ''
  return datetime.replace('T', ' ').slice(0, 16)
}

const statusTagType = (status) => {
  const map = { '待就诊': 'warning', '已就诊': 'success', '已取消': 'info' }
  return map[status] || ''
}

const loadAppointments = async () => {
  loading.value = true
  try {
    const params = {}
    if (searchForm.value.patientName) params.patientName = searchForm.value.patientName
    if (searchForm.value.doctorName) params.doctorName = searchForm.value.doctorName
    if (searchForm.value.status) params.status = searchForm.value.status
    const res = await getAppointments(params)
    const data = res.data.data || []
    appointments.value = Array.isArray(data) ? data : (data.list || [])
  } catch {
    ElMessage.error('加载失败')
  } finally {
    loading.value = false
  }
}

const loadDropdownData = async () => {
  try {
    const [patientRes, deptRes, doctorRes] = await Promise.all([
      getPatients(),
      getDepartments(),
      getDoctors()
    ])
    patients.value = patientRes.data.data || []
    departments.value = deptRes.data.data || []
    allDoctors.value = doctorRes.data.data || []
    filteredDoctors.value = allDoctors.value
  } catch {
    ElMessage.error('加载下拉数据失败')
  }
}

const onDeptChange = () => {
  if (selectedDeptId.value) {
    filteredDoctors.value = allDoctors.value.filter(
      doc => doc.departmentId === selectedDeptId.value
    )
  } else {
    filteredDoctors.value = []
  }
  form.value.doctorId = ''
}

const changeStatus = async (id, status) => {
  try {
    await updateAppointmentStatus(id, status)
    ElMessage.success('状态已更新')
    await loadAppointments()
  } catch {
    ElMessage.error('更新状态失败')
  }
}

const handleSearch = () => loadAppointments()
const resetSearch = () => {
  searchForm.value = { patientName: '', doctorName: '', status: '' }
  loadAppointments()
}

const openAddModal = () => {
  isEdit.value = false
  form.value = { id: null, patientId: '', doctorId: '', appointmentTime: '', status: '待就诊', remark: '' }
  selectedDeptId.value = ''
  filteredDoctors.value = allDoctors.value
  modalVisible.value = true
}

const openEditModal = (row) => {
  isEdit.value = true
  const formattedTime = row.appointmentTime ? row.appointmentTime.slice(0, 16) : ''
  form.value = { ...row, appointmentTime: formattedTime }
  const doctor = allDoctors.value.find(d => d.id === row.doctorId)
  if (doctor) {
    selectedDeptId.value = doctor.departmentId || ''
    onDeptChange()
  } else {
    selectedDeptId.value = ''
    filteredDoctors.value = allDoctors.value
  }
  modalVisible.value = true
}

const submitForm = async () => {
  try {
    if (isEdit.value) {
      await updateAppointment(form.value.id, form.value)
      ElMessage.success('修改成功')
    } else {
      await addAppointment(form.value)
      ElMessage.success('新增成功')
    }
    modalVisible.value = false
    await loadAppointments()
  } catch {
    ElMessage.error('操作失败')
  }
}

const handleDelete = (id) => {
  ElMessageBox.confirm('确认删除该预约吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await deleteAppointment(id)
    ElMessage.success('删除成功')
    await loadAppointments()
  }).catch(() => {})
}

onMounted(() => {
  loadAppointments()
  loadDropdownData()
})
</script>

<style scoped></style>