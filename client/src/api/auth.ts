import request from "@/utils/request";

export type LoginRequest = {
    username: string,
    password: string
}

export type SignupRequest = {
    username: string,
    password: string,
    name: string,
    email: string
}

export function login(data: LoginRequest) {
    return request({
        url: `auth/login`,
        method: 'POST',
        data
    })
}

export function signup(data: SignupRequest) {
    return request({
        url: `auth/signup`,
        method: 'POST',
        data
    })
}
