<template>
  <div id="app">
    <!-- 导航栏 -->
    <el-menu
      v-if="$route.path !== '/login' && $route.path !== '/register'"
      :default-active="$route.path"
      mode="horizontal"
      background-color="#3a4a5a"
      text-color="#e8edf2"
      active-text-color="#7ec8e3"
      router
      style="display: flex; justify-content: space-between; align-items: center; padding-right: 20px; border-bottom: none;"
    >
      <div style="display: flex;">
        <el-menu-item index="/patients">患者管理</el-menu-item>
        <el-menu-item index="/departments">科室管理</el-menu-item>
        <el-menu-item index="/doctors">医生管理</el-menu-item>
        <el-menu-item index="/appointments">预约管理</el-menu-item>
      </div>
      <div style="display: flex; align-items: center; gap: 15px; color: #e8edf2;">
        <span>欢迎, {{ userStore.userInfo?.username || '游客' }}</span>
        <el-button type="warning" size="small" @click="handleLogout">退出登录</el-button>
      </div>
    </el-menu>

    <router-view />
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useUserStore } from './stores/user'

const router = useRouter()
const userStore = useUserStore()

const handleLogout = async () => {
  await userStore.doLogout()
  router.push('/login')
}
</script>

<style>
/* ============================================================
   配色方案：暖灰质感（不白、不AI）
   ============================================================ */
:root {
  /* ---- 主色（蓝灰色，更柔和） ---- */
  --el-color-primary: #5b7f8c;
  --el-color-primary-light-3: #8aaab5;
  --el-color-primary-light-5: #b0c8d0;
  --el-color-primary-light-7: #d3e2e8;
  --el-color-primary-light-9: #edf3f5;
  --el-color-primary-dark-2: #3d5c68;

  /* ---- 成功色（苔绿，更自然） ---- */
  --el-color-success: #6b8f71;
  --el-color-success-light-3: #9bb8a0;
  --el-color-success-light-5: #bfd4c2;
  --el-color-success-light-7: #dce8de;
  --el-color-success-light-9: #f0f5f0;

  /* ---- 警告色（暖橘） ---- */
  --el-color-warning: #d49a6a;

  /* ---- 危险色（砖红） ---- */
  --el-color-danger: #b56a6a;

  /* ---- 页面背景（暖白灰，不刺眼） ---- */
  --el-bg-color-page: #f2efe8;

  /* ---- 卡片/表格/弹窗背景（柔和米白） ---- */
  --el-bg-color: #fcfaf5;

  /* ---- 边框色（浅褐灰） ---- */
  --el-border-color-light: #e5dfd6;

  /* ---- 文字主色（深灰褐） ---- */
  --el-text-color-primary: #3d3a35;
  --el-text-color-regular: #5a554a;
}

/* ===== 全局 ===== */
body {
  margin: 0;
  background-color: #f2efe8;
  font-family: 'Helvetica Neue', Arial, 'PingFang SC', 'Microsoft YaHei', sans-serif;
}

#app {
  padding: 20px;
  min-height: 100vh;
  background-color: #f2efe8;
}

/* ===== 表格 ===== */
.el-table {
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 1px 6px rgba(90, 85, 74, 0.08);
}

.el-table th.el-table__cell {
  background-color: #ece7de !important;
  color: #4a453e;
  font-weight: 600;
}

/* ===== 弹窗 ===== */
.el-dialog {
  border-radius: 12px;
}

/* ===== 输入框 ===== */
.el-input__wrapper {
  border-radius: 6px;
}

/* ===== 按钮圆角 ===== */
.el-button {
  border-radius: 6px;
}

/* ===== 卡片内边距微调 ===== */
.el-dialog .el-dialog__body {
  padding: 20px 30px;
}

/* ===== 导航栏菜单悬停 ===== */
.el-menu-item:hover {
  background-color: rgba(126, 200, 227, 0.12) !important;
}

/* ===== 表格行悬停 ===== */
.el-table--striped .el-table__body tr.el-table__row--striped td {
  background-color: #f8f6f1;
}
</style>