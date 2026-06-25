import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'

// 1. 引入 Element Plus
import ElementPlus from 'element-plus'
// 2. 引入 Element Plus 的样式（必须）
import 'element-plus/dist/index.css'
// 3. 引入中文语言包（让组件显示中文）
import zhCn from 'element-plus/es/locale/lang/zh-cn'

const app = createApp(App)

app.use(createPinia())
app.use(router)
// 4. 使用 Element Plus（配置中文）
app.use(ElementPlus, {
    locale: zhCn,
})

app.mount('#app')