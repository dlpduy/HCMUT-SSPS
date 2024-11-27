import { methods } from '../config/api/methods.config'

const url1 = "spso"
const url2 = url1 + "/print"
const url3 = url1 + "/page"
const url4 = url1 + "/printer"
// const url5 = url1 + "/filetype"
const url6 = url1 + "/paper"

export const getAllPrintingHistory = ({page,size=10}) => {
    return methods.get(`${url2}?page=${page}&size=${size}`);
}
export const getAllPagePurchaseHistory = ({page,size=10}) => {
    return methods.get(`${url3}?page=${page}&size=${size}`);
}
export const getStatistic = () => {
    return methods.get(`${url1}/statistic`);
}
export const getAllPrinters = ({page,size=10}) => {
    return methods.get(`${url4}?page=${page}&size=${size}`);
}
export const createNewPrinter = (data) => {
    return methods.post(`${url4}`,JSON.stringify(data));
}
export const updatePrinter = (data) => {
    return methods.put(`${url4}/${data.id}`,JSON.stringify(data));
}
export const getPrinterById = (id) => {
    return methods.get(`${url4}/${id}`);
}
export const deletePrinterById = (id) => {
    return methods.delete(`${url4}/${id}`);
}
export const getAllPapers = ({page,size=10}) => {
    return methods.get(`${url6}?page=${page}&size=${size}`);
}
export const createNewPaper = (data) => {
    return methods.post(`${url6}`,JSON.stringify(data));
}
export const updatePaper = (data) => {
    return methods.put(`${url6}`,JSON.stringify(data));
}
export const getPaper = (id) => {
    return methods.get(`${url6}/${id}`);
}
export const deletePaper = (id) => {
    return methods.delete(`${url6}/${id}`);
}