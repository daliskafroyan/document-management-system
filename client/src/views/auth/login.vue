<template>
    <div class="login">
        <el-card class="box-card" shadow="always">
            <div class="header">Document Management System</div>
            <el-form ref="loginForm" :model="loginUser" :rules="rules" label-position="right" label-width="70px"
                size="large" class="login-form">
                <el-form-item label="Username" prop="username">
                    <el-input v-model="loginUser.username"></el-input>
                </el-form-item>
                <el-form-item label="Password" prop="password">
                    <el-input v-model="loginUser.password" show-password type="password"></el-input>
                </el-form-item>
            </el-form>
            <div class="button-container">
                <el-button size="large" class="login-button" type="primary" @click="handleLogin">Login</el-button>
            </div>
        </el-card>
    </div>
</template>

<script setup lang="ts">
import { login, type LoginRequest } from '@/api/auth';
import router from '@/router';
import { reactive, ref } from 'vue';
import { ElMessage } from 'element-plus';

const loginForm = ref();

const loginUser = reactive<LoginRequest>({
    username: "",
    password: "",
});

const handleLogin = () => {
    loginForm.value.validate((valid: boolean) => {
        if (valid) {
            login(loginUser)
                .then((result) => {
                    const token = (result as unknown as { accessToken: string }).accessToken;
                    localStorage.setItem('token', token);
                    ElMessage.success("Login Success");
                    router.push({ path: "/" });
                })
                .catch((error) => {
                    console.error(error);
                    ElMessage.error("Login failed. Please check your credentials.");
                });
        } else {
            ElMessage.warning("Please complete the form correctly.");
        }
    });
};

const rules = reactive({
    username: [
        {
            required: true,
            message: "Please enter the username",
            trigger: "blur"
        },
    ],
    password: [
        {
            required: true,
            message: "Please enter the password",
            trigger: "blur"
        },
    ],
});
</script>

<style scoped>
.login {
    background-size: 100% 100%;
    position: fixed;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    display: flex;
    justify-content: center;
    align-items: center;
}

.box-card {
    width: 800px;
    height: 500px;
    opacity: 0.92;
}

.header {
    padding: 40px 0 60px;
    text-align: center;
    color: #409eff;
    font-size: 32px;
    font-weight: bold;
}

.login-form {
    width: 360px;
    margin: 0 auto;
}

.button-container {
    text-align: right;
    width: 360px;
    margin: 40px auto 0;
}

.login-button {
    width: 300px;
}

.captcha {
    overflow: visible;
    position: relative;
    text-align: right;
    left: 12px;
}
</style>