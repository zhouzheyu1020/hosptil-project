markdown
# 医院预约挂号管理系统

## 项目简介

本项目是一个基于 Vue3 + SpringBoot 的前后端分离的医院预约挂号后台管理系统。系统面向医院门诊管理场景，覆盖了患者管理、科室管理、医生管理、预约挂号及预约状态跟踪等核心业务流程，帮助医院管理人员高效完成日常预约调度工作。

项目采用三层架构设计（Controller → Service → Mapper），代码职责清晰，具备良好的可维护性和可扩展性。

---

## 功能模块

| 模块 | 功能说明 |
|------|----------|
| 用户认证 | 注册、登录、退出，密码采用 BCrypt 加密存储 |
| 患者管理 | 患者信息的增删改查，支持按姓名、手机号模糊搜索 |
| 科室管理 | 科室信息的增删改查，支持按科室名称搜索 |
| 医生管理 | 医生信息的增删改查，支持按姓名、职称搜索 |
| 预约管理 | 预约挂号的增删改查，支持按患者姓名、医生姓名、状态搜索 |
| 预约状态变更 | 支持"待就诊 → 已就诊 / 已取消"状态切换 |
| 权限控制 | 未登录用户无法访问后台页面，通过路由守卫实现强制拦截 |

---

## 技术栈

### 前端

| 技术 | 说明 |
|------|------|
| Vue3 | 前端框架（Composition API） |
| Vue Router | 路由管理 |
| Pinia | 全局状态管理 |
| Axios | HTTP 请求封装 |
| Element Plus | UI 组件库 |
| Vite | 构建工具 |

### 后端

| 技术 | 说明 |
|------|------|
| SpringBoot | 后端框架 |
| MyBatis | ORM 持久层框架 |
| MySQL | 关系型数据库 |
| BCrypt | 密码加密 |
| Maven | 项目构建管理 |

---

## 快速开始

### 环境要求

- JDK 21+
- MySQL 8.0+
- Node.js 18+
- Maven 3.6+

### 1. 克隆项目

```bash
git clone https://github.com/your-username/hospital-system.git
2. 数据库配置
创建数据库并执行 SQL 脚本：

sql
CREATE DATABASE hospital_db;
USE hospital_db;
-- 执行建表语句
修改 application.yml 中的数据库连接信息：

yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/hospital_db
    username: root
    password: 你的密码
3. 启动后端
bash
cd hospital-system
mvn clean install
mvn spring-boot:run
后端默认运行在 http://localhost:8080

4. 启动前端
bash
cd hospital-project
npm install
npm run dev
前端默认运行在 http://localhost:5173

5. 访问系统
浏览器打开 http://localhost:5173，使用以下账号登录：

用户名	密码	角色
admin	123456	管理员
zzy	123456	普通用户
项目结构
后端结构
text
src/main/java/com/hospital/
├── common/              # 通用类
│   ├── Result.java      # 统一响应格式
│   └── PageResult.java  # 分页结果封装
├── config/              # 配置类
│   ├── CorsConfig.java  # 跨域配置
│   └── PasswordEncoderConfig.java  # BCrypt 加密配置
├── controller/          # 控制器层
│   ├── PatientController.java
│   ├── DoctorController.java
│   ├── DepartmentController.java
│   ├── AppointmentController.java
│   └── LoginController.java
├── dto/                 # 数据传输对象
│   └── LoginRequest.java
├── entity/              # 实体类
│   ├── Patient.java
│   ├── Doctor.java
│   ├── Department.java
│   ├── Appointment.java
│   └── User.java
├── vo/                  # 视图对象
│   └── AppointmentVO.java
├── mapper/              # MyBatis Mapper 接口
│   ├── PatientMapper.java
│   ├── DoctorMapper.java
│   ├── DepartmentMapper.java
│   ├── AppointmentMapper.java
│   └── UserMapper.java
├── service/             # 业务逻辑层
│   ├── PatientService.java
│   ├── DoctorService.java
│   ├── DepartmentService.java
│   ├── AppointmentService.java
│   └── UserService.java
└── service/impl/        # 业务逻辑实现类
    ├── PatientServiceImpl.java
    ├── DoctorServiceImpl.java
    ├── DepartmentServiceImpl.java
    ├── AppointmentServiceImpl.java
    └── UserServiceImpl.java
前端结构
text
hospital-project/
├── src/
│   ├── api/                 # API 接口封装
│   │   ├── patient.js
│   │   ├── doctor.js
│   │   ├── department.js
│   │   ├── appointment.js
│   │   └── auth.js
│   ├── stores/              # Pinia 状态管理
│   │   └── user.js
│   ├── router/              # 路由配置
│   │   └── index.js
│   ├── views/               # 页面组件
│   │   ├── LoginView.vue
│   │   ├── RegisterView.vue
│   │   ├── PatientsView.vue
│   │   ├── DoctorsView.vue
│   │   ├── DepartmentsView.vue
│   │   └── AppointmentView.vue
│   ├── App.vue              # 根组件
│   └── main.js              # 入口文件
├── index.html
├── package.json
└── vite.config.js
API 接口文档
基础路径
所有接口前缀为 http://localhost:8080

统一响应格式
json
{
  "code": 200,
  "message": "success",
  "data": {}
}
认证接口
方法	路径	说明
POST	/login	用户登录
POST	/register	用户注册
POST	/logout	退出登录
GET	/current-user	获取当前登录用户信息
患者接口
方法	路径	说明
GET	/patients	查询患者列表（支持 name、phone 搜索）
GET	/patients/{id}	根据 ID 查询患者
POST	/patients	新增患者
PUT	/patients/{id}	修改患者
DELETE	/patients/{id}	删除患者
医生接口
方法	路径	说明
GET	/doctors	查询医生列表（支持 name、title 搜索）
GET	/doctors/{id}	根据 ID 查询医生
POST	/doctors	新增医生
PUT	/doctors/{id}	修改医生
DELETE	/doctors/{id}	删除医生
科室接口
方法	路径	说明
GET	/departments	查询科室列表（支持 name 搜索）
GET	/departments/{id}	根据 ID 查询科室
POST	/departments	新增科室
PUT	/departments/{id}	修改科室
DELETE	/departments/{id}	删除科室
预约接口
方法	路径	说明
GET	/appointments	查询预约列表（支持 patientName、doctorName、status 搜索）
GET	/appointments/{id}	根据 ID 查询预约
POST	/appointments	新增预约
PUT	/appointments/{id}	修改预约
DELETE	/appointments/{id}	删除预约
PUT	/appointments/{id}/status	修改预约状态
数据库设计
ER 图简要说明
系统共包含 5 张核心数据表：

表名	说明
user	用户表（存储管理员账号信息）
patient	患者表（存储患者档案）
doctor	医生表（关联科室表）
department	科室表
appointment	预约表（关联患者表和医生表）
关键表结构
预约表（appointment）

字段	类型	说明
id	BIGINT	主键自增
patient_id	BIGINT	患者 ID（外键）
doctor_id	BIGINT	医生 ID（外键）
appointment_time	DATETIME	预约时间
status	VARCHAR(20)	状态：待就诊 / 已就诊 / 已取消
remark	VARCHAR(255)	备注
create_time	DATETIME	创建时间
项目亮点
三层架构设计：严格按照 Controller → Service → Mapper 分层，职责清晰，易于维护和扩展

VO 封装优化：预约模块通过 LEFT JOIN 联表查询，使用自定义 AppointmentVO 封装返回数据，减少前端请求次数

密码安全：采用 BCrypt 加密存储用户密码，避免明文泄露

权限控制：通过 Vue Router 路由守卫实现未登录用户强制跳转

联动交互：新增预约时"选科室 → 自动过滤医生"的联动效果，提升操作体验

统一响应格式：所有接口返回统一 Result 格式，便于前端全局拦截处理

演示截图
登录页

患者管理页

预约管理页

作者
姓名：zzy

邮箱：3304177915@qq.com

GitHub：https://github.com/zhouzheyu1020/hosptil-project
