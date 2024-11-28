import { methods } from '../config/api/methods.config'

const url1 = "printer"

export const getAllPrinters = ({ page, size = 10 }) => {
    return methods.get(`${url1}?page=${page}&size=${size}`);
}

export const getPrinterById = (id) => {
    return methods.get(`${url1}/${id}`);
}