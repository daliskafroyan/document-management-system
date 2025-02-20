<template>
    <div class="flex align-items-center justify-content-center h-screen">
        <div class="surface-card p-4 shadow-2 border-round w-full lg:w-6">
            <form @submit="onSubmit">
                <div class="text-center mb-5">
                    <div class="text-900 text-3xl font-medium mb-3">Welcome Back</div>
                    <span class="text-600 font-medium line-height-3">Don't have an account?</span>
                    <RouterLink to="/signup">
                        <a class="font-medium no-underline ml-2 text-blue-500 cursor-pointer">Create today!</a>
                    </RouterLink>
                </div>

                <div class="flex gap-3 flex-column">
                    <div>
                        <label for="username" class="block text-900 font-medium mb-2">Username</label>
                        <InputText :class="{ 'p-invalid': errors.username }" class="w-full" id="username" type="text"
                            v-model="username" />
                        <p class="text-xs text-red-500">{{ errors.username }}</p>
                    </div>

                    <div>
                        <label for="password" class="block text-900 font-medium mb-2">Password</label>
                        <InputText :class="{ 'p-invalid': errors.password }" id="password" type="password"
                            class="w-full" v-model="password" />
                        <p class="text-xs text-red-500">{{ errors.password }}</p>
                    </div>

                    <Button type="submit" label="Sign In" icon="pi pi-user" class="w-full mt-6" />
                </div>
            </form>
        </div>
    </div>
</template>

<script setup lang="ts">
import { login, type LoginRequest } from '@/api/auth';
import router from '@/router';
import { onMounted, reactive, ref } from 'vue';
import * as yup from 'yup';
import { useForm } from 'vee-validate';
import { useToast } from 'primevue/usetoast';

const toast = useToast();

const schema = yup.object({
    username: yup.string().required().label('Username'),
    password: yup.string().required().min(4).label('Password'),
});

const { defineField, handleSubmit, resetForm, errors } = useForm<LoginRequest>({
    validationSchema: schema,
});

const [username] = defineField('username');
const [password] = defineField('password');

const onSubmit = handleSubmit((values) => {
    login(values)
        .then((result) => {
            localStorage.setItem('token', result.data.accessToken);
            toast.add({ severity: 'success', summary: 'Success', detail: 'Login Success', life: 3000 });

            router.replace({ path: "/" });
        })
        .catch((error) => {
            toast.add({ severity: 'error', summary: 'Error', detail: 'Login Error', life: 3000 });
        });
});

</script>
