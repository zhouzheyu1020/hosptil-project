<template>
  <div class="login-container">
    <div class="login-card">
      <!-- 左侧装饰区 -->
      <div class="login-left">
        <div class="login-left-content">
          <div class="logo-icon">🏥</div>
          <h1>医院预约管理系统</h1>
          <p>高效 · 便捷 · 智能</p>
          <div class="login-left-decoration">
            <span></span>
            <span></span>
            <span></span>
          </div>
        </div>
      </div>

      <!-- 右侧表单区 -->
      <div class="login-right">
        <h2>欢迎登录</h2>
        <p class="login-subtitle">请输入您的账号信息</p>

        <form @submit.prevent="handleLogin">
          <div class="form-group">
            <label>用户名</label>
            <el-input
              v-model="form.username"
              placeholder="请输入用户名"
              size="large"
              prefix-icon="User"
              clearable
            />
          </div>

          <div class="form-group">
            <label>密码</label>
            <el-input
              v-model="form.password"
              type="password"
              placeholder="请输入密码"
              size="large"
              prefix-icon="Lock"
              show-password
              @keyup.enter="handleLogin"
            />
          </div>

          <div class="error" v-if="errorMsg">{{ errorMsg }}</div>

          <el-button type="primary" size="large" @click="handleLogin" class="login-btn">
            登录
          </el-button>

          <div class="login-footer">
            <span>还没有账号？</span>
            <a href="#" @click.prevent="router.push('/register')">去注册</a>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../stores/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()

const form = ref({ username: '', password: '' })
const errorMsg = ref('')

const handleLogin = async () => {
  errorMsg.value = ''
  const result = await userStore.doLogin(form.value.username, form.value.password)
  if (result.success) {
    ElMessage.success('登录成功')
    router.push('/patients')
  } else {
    errorMsg.value = result.message || '用户名或密码错误'
  }
}
</script>

<style scoped>
/* ===== 容器：100vh 无滚动 ===== */
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100vw;
  height: 100vh;
  background-color: #f2efe8;
  overflow: hidden;
  margin: 0;
  padding: 0;
}

/* ===== 卡片：左右分栏 ===== */
.login-card {
  display: flex;
  width: 820px;
  max-width: 92vw;
  height: 520px;
  max-height: 90vh;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 8px 40px rgba(90, 85, 74, 0.15);
  background: #fcfaf5;
}

/* ===== 左侧装饰区 ===== */
.login-left {
  flex: 0 0 280px;
  background: linear-gradient(145deg, #6b8f7a, #5b7f8c);
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 40px 30px;
  position: relative;
  overflow: hidden;
}

.login-left::after {
  content: '';
  position: absolute;
  top: -60%;
  right: -30%;
  width: 300px;
  height: 300px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.06);
}

.login-left::before {
  content: '';
  position: absolute;
  bottom: -40%;
  left: -20%;
  width: 200px;
  height: 200px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.04);
}

.login-left-content {
  position: relative;
  z-index: 1;
  text-align: center;
  color: #ffffff;
}

.logo-icon {
  font-size: 48px;
  margin-bottom: 16px;
  display: block;
}

.login-left-content h1 {
  font-size: 22px;
  font-weight: 600;
  margin: 0 0 8px 0;
  letter-spacing: 2px;
}

.login-left-content p {
  font-size: 14px;
  opacity: 0.8;
  margin: 0;
  letter-spacing: 4px;
}

.login-left-decoration {
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-top: 30px;
}

.login-left-decoration span {
  display: block;
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.25);
  transition: all 0.3s;
}

.login-left-decoration span:nth-child(2) {
  background: rgba(255, 255, 255, 0.5);
  width: 10px;
  height: 10px;
}

.login-left-decoration span:nth-child(3) {
  background: rgba(255, 255, 255, 0.35);
}

/* ===== 右侧表单区 ===== */
.login-right {
  flex: 1;
  padding: 48px 44px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.login-right h2 {
  font-size: 26px;
  font-weight: 600;
  color: #3d3a35;
  margin: 0 0 4px 0;
}

.login-subtitle {
  font-size: 14px;
  color: #8a857a;
  margin: 0 0 28px 0;
}

.form-group {
  margin-bottom: 18px;
}

.form-group label {
  display: block;
  font-size: 14px;
  font-weight: 500;
  color: #5a554a;
  margin-bottom: 6px;
}

.form-group :deep(.el-input__wrapper) {
  border-radius: 8px;
  background-color: #f5f3ee;
  box-shadow: none !important;
  border: 1px solid #e5dfd6;
  transition: all 0.3s;
}

.form-group :deep(.el-input__wrapper:hover) {
  border-color: #b0c8d0;
}

.form-group :deep(.el-input__wrapper.is-focus) {
  border-color: #5b7f8c;
  box-shadow: 0 0 0 3px rgba(91, 127, 140, 0.12) !important;
}

.error {
  color: #b56a6a;
  font-size: 13px;
  margin: -6px 0 12px 0;
  text-align: center;
}

.login-btn {
  width: 100%;
  height: 44px;
  font-size: 16px;
  font-weight: 500;
  border-radius: 8px;
  background: #5b7f8c;
  border: none;
  margin-top: 4px;
}

.login-btn:hover {
  background: #4d6d79 !important;
}

.login-btn:active {
  background: #3d5c68 !important;
}

.login-footer {
  text-align: center;
  margin-top: 20px;
  font-size: 14px;
  color: #8a857a;
}

.login-footer a {
  color: #5b7f8c;
  text-decoration: none;
  font-weight: 500;
}

.login-footer a:hover {
  color: #3d5c68;
  text-decoration: underline;
}

/* ===== 响应式：小屏幕左右变上下 ===== */
@media (max-width: 700px) {
  .login-card {
    flex-direction: column;
    height: auto;
    max-height: 96vh;
    width: 92vw;
  }

  .login-left {
    flex: 0 0 auto;
    padding: 24px 20px;
    min-height: 140px;
  }

  .login-left-content h1 {
    font-size: 18px;
  }
  .logo-icon {
    font-size: 32px;
    margin-bottom: 8px;
  }
  .login-left-decoration {
    display: none;
  }

  .login-right {
    padding: 28px 24px;
  }

  .login-right h2 {
    font-size: 22px;
  }
}

/* ===== 阻止页面滚动 ===== */
html,
body {
  overflow: hidden;
}
</style>