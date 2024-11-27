import { methods } from '../config/api/methods.config'

const url1 = "student"
const url2 = url1 + "/page"
export const createNewPagePurchase = (data) => {
    return methods.post(`${url2}`,JSON.stringify(data));
}