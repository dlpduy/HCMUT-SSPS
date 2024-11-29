import { methods } from '../config/api/methods.config'

const url1 = "printer"
const url2 = "paper"
const url3 = "payment/vn-pay-callback"
export const getAllPrinters = ({ page, size = 10 }) => {
    return methods.get(`${url1}?page=${page}&size=${size}`);
}

export const getPrinterById = (id) => {
    return methods.get(`${url1}/${id}`);
}
export const getAllPaper = () => {
    return methods.get(`${url2}`);
}

export const callbackPayment = (data) => {
    return methods.get(`${url3}${data}`);
}