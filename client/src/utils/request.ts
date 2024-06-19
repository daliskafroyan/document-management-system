import router from "@/router";
import axios from "axios";
import type { AxiosInstance } from "axios";
import toast from "primevue/toast";


const request: AxiosInstance = axios.create({
    baseURL: 'http://localhost:8080/',
    timeout: 10000,
});

request.interceptors.request.use((config) => {
    const accessToken = localStorage.getItem('token');

    if (accessToken) {
        config.headers.Authorization = `Bearer ${accessToken}`;
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});

request.interceptors.response.use((response) => {
    const res = response;

    // () => toast.add({ severity: 'error', summary: 'Error', detail: 'MNew', life: 3000 });

    return res;

    // ElMessage.error(res.data.message);
}, (error) => {
    const { message, response } = error;

    if (response.status === 401) {
        localStorage.removeItem('token');

        router.replace({ path: '/login' })
    }
    // if (message.indexOf('timeout') != -1) {
    //     ElMessage.error('timeout！');
    // } else if (message === 'Network error') {
    //     ElMessage.error('Network error！');
    // } else if (response.data) ElMessage.error(response.statusText)
    // else ElMessage.error('Error')
    return Promise.reject(error)
});

export default request;
