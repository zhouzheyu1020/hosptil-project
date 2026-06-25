<template>
  <div class="register-container">
    <div class="register-card">
      <!-- 左侧装饰区 -->
      <div class="register-left">
        <div class="register-left-content">
          <div class="logo-icon">📝</div>
          <h1>创建账号</h1>
          <p>加入医院预约系统</p>
          <div class="register-left-decoration">
            <span></span>
            <span></span>
            <span></span>
          </div>
        </div>
      </div>

      <!-- 右侧表单区 -->
      <div class="register-right">
        <h2>用户注册</h2>
        <p class="register-subtitle">填写信息完成注册</p>

        <form @submit.prevent="handleRegister">
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
            />
          </div>

          <div class="form-group">
            <label>确认密码</label>
            <el-input
              v-model="form.confirmPassword"
              type="password"
              placeholder="请再次输入密码"
              size="large"
              prefix-icon="Check"
              show-password
            />
          </div>

          <div class="error" v-if="errorMsg">{{ errorMsg }}</div>
          <div class="success" v-if="successMsg">{{ successMsg }}</div>

          <el-button type="primary" size="large" @click="handleRegister" class="register-btn">
            注册
          </el-button>

          <div class="register-footer">
            <span>已有账号？</span>
            <a href="#" @click.prevent="router.push('/login')">去登录</a>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'
import { ElMessage } from 'element-plus'

const router = useRouter()

const form = ref({
  username: '',
  password: '',
  confirmPassword: ''
})
const errorMsg = ref('')
const successMsg = ref('')

const handleRegister = async () => {
  errorMsg.value = ''
  successMsg.value = ''

  if (form.value.password !== form.value.confirmPassword) {
    errorMsg.value = '两次输入的密码不一致'
    return
  }

  try {
    const res = await axios.post('http://localhost:8080/register', {
      username: form.value.username,
      password: form.value.password
    })

    if (res.data.code === 200) {
      successMsg.value = '注册成功，即将跳转到登录页...'
      ElMessage.success('注册成功！')
      setTimeout(() => {
        router.push('/login')
      }, 1500)
    } else {
      errorMsg.value = res.data.message || '注册失败'
    }
  } catch {
    errorMsg.value = '网络错误，请稍后重试'
  }
}
</script>

<style scoped>
/* ===== 容器：100vh 无滚动 ===== */
.register-container {
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
.register-card {
  display: flex;
  width: 820px;
  max-width: 92vw;
  height: 540px;
  max-height: 92vh;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 8px 40px rgba(90, 85, 74, 0.15);
  background: #fcfaf5;
}

/* ===== 左侧装饰区 ===== */
.register-left {
  flex: 0 0 280px;
  background: linear-gradient(145deg, #6b8f7a, #5b7f8c);
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 40px 30px;
  position: relative;
  overflow: hidden;
}

.register-left::after {
  content: '';
  position: absolute;
  top: -60%;
  right: -30%;
  width: 300px;
  height: 300px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.06);
}

.register-left::before {
  content: '';
  position: absolute;
  bottom: -40%;
  left: -20%;
  width: 200px;
  height: 200px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.04);
}

.register-left-content {
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

.register-left-content h1 {
  font-size: 22px;
  font-weight: 600;
  margin: 0 0 8px 0;
  letter-spacing: 2px;
}

.register-left-content p {
  font-size: 14px;
  opacity: 0.8;
  margin: 0;
  letter-spacing: 4px;
}

.register-left-decoration {
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-top: 30px;
}

.register-left-decoration span {
  display: block;
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.25);
  transition: all 0.3s;
}

.register-left-decoration span:nth-child(2) {
  background: rgba(255, 255, 255, 0.5);
  width: 10px;
  height: 10px;
}

.register-left-decoration span:nth-child(3) {
  background: rgba(255, 255, 255, 0.35);
}

/* ===== 右侧表单区 ===== */
.register-right {
  flex: 1;
  padding: 40px 44px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.register-right h2 {
  font-size: 26px;
  font-weight: 600;
  color: #3d3a35;
  margin: 0 0 4px 0;
}

.register-subtitle {
  font-size: 14px;
  color: #8a857a;
  margin: 0 0 24px 0;
}

.form-group {
  margin-bottom: 14px;
}

.form-group label {
  display: block;
  font-size: 14px;
  font-weight: 500;
  color: #5a554a;
  margin-bottom: 4px;
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
  margin: 2px 0 8px 0;
  text-align: center;
}

.success {
  color: #6b8f71;
  font-size: 13px;
  margin: 2px 0 8px 0;
  text-align: center;
}

.register-btn {
  width: 100%;
  height: 44px;
  font-size: 16px;
  font-weight: 500;
  border-radius: 8px;
  background: #5b7f8c;
  border: none;
  margin-top: 4px;
}

.register-btn:hover {
  background: #4d6d79 !important;
}

.register-btn:active {
  background: #3d5c68 !important;
}

.register-footer {
  text-align: center;
  margin-top: 18px;
  font-size: 14px;
  color: #8a857a;
}

.register-footer a {
  color: #5b7f8c;
  text-decoration: none;
  font-weight: 500;
}

.register-footer a:hover {
  color: #3d5c68;
  text-decoration: underline;
}

/* ===== 响应式：小屏幕左右变上下 ===== */
@media (max-width: 700px) {
  .register-card {
    flex-direction: column;
    height: auto;
    max-height: 96vh;
    width: 92vw;
  }

  .register-left {
    flex: 0 0 auto;
    padding: 20px 20px;
    min-height: 120px;
  }

  .register-left-content h1 {
    font-size: 18px;
  }
  .logo-icon {
    font-size: 32px;
    margin-bottom: 6px;
  }
  .register-left-decoration {
    display: none;
  }

  .register-right {
    padding: 24px 20px;
  }

  .register-right h2 {
    font-size: 22px;
  }
}

/* ===== 阻止页面滚动 ===== */
html,
body {
  overflow: hidden;
}
</style>