import { methods } from '../config/api/methods.config'

const url1 = "student"
const url2 = url1 + "/page"
const url3 = url1 + "/pages-history"
const url4 = url1 + "/print"
const url5 = url1 + "/file"
export const getPageLeft = () => {
    return methods.get(`${url2}`);
}

export const getPagePurchaseHistory = () => {
    return methods.get(`${url3}`);
}

export const createPrintService = (data) => {
    return methods.post(`${url4}`, JSON.stringify(data));
}

export const getPrintHistory = () => {
    return methods.get(`${url4}s`);
}

export const getFilesUploaded = () => {
    return methods.get(`${url5}s`);
}

export const uploadFiled = (data) => {
    return methods.post(`${url5}`, JSON.stringify(data));
}