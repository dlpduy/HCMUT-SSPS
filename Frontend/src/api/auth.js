import { methods } from '../config/api/methods.config'

const url = "auth"
export const login = (data) => {
    return methods.post(`${url}/login`, JSON.stringify(data));
}
export const register = (data) => {
    return methods.post(`${url}/register`, JSON.stringify(data));
}
export const logout = (data) => {
    return methods.post(`${url}/logout`, JSON.stringify(data));
}

