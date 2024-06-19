<template>
    <div class="flex align-items-center justify-content-center h-screen">
        <div class="surface-card p-4 shadow-2 border-round w-full lg:w-6">
            <form @submit="onSubmit">
                <div class="text-center mb-5">
                    <div class="text-900 text-3xl font-medium mb-3">Create Account</div>
                    <span class="text-600 font-medium line-height-3">Have an account?</span>
                    <RouterLink to="/login">
                        <a class="font-medium no-underline ml-2 text-blue-500 cursor-pointer">Sign in!</a>
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

                    <div>
                        <label for="name" class="block text-900 font-medium mb-2">Name</label>
                        <InputText :class="{ 'p-invalid': errors.name }" class="w-full" id="name" type="text"
                            v-model="name" />
                        <p class="text-xs text-red-500">{{ errors.name }}</p>
                    </div>

                    <div>
                        <label for="email" class="block text-900 font-medium mb-2">Email</label>
                        <InputText :class="{ 'p-invalid': errors.email }" class="w-full" id="email" type="text"
                            v-model="email" />
                        <p class="text-xs text-red-500">{{ errors.email }}</p>
                    </div>

                    <Button type="submit" label="Sign In" icon="pi pi-user" class="w-full mt-6" />
                </div>
            </form>
        </div>
    </div>
</template>

<script setup lang="ts">
import { signup, type SignupRequest } from '@/api/auth';
import router from '@/router';
import * as yup from 'yup';
import { useForm } from 'vee-validate';
import { useToast } from 'primevue/usetoast';

const toast = useToast();

const schema = yup.object({
    username: yup.string().required().label('Username'),
    password: yup.string().required().min(4).label('Password'),
    name: yup.string().required().label('Name'),
    email: yup.string().required().label('Email'),
});

const { defineField, handleSubmit, errors } = useForm<SignupRequest>({
    validationSchema: schema,
});

const [username] = defineField('username');
const [password] = defineField('password');
const [name] = defineField('name');
const [email] = defineField('email');

const onSubmit = handleSubmit((values) => {
    signup(values)
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
