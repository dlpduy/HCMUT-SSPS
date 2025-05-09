import httpHandler from './axios.config.js';
import { API_DOMAIN } from '../constant/apiDomain.constant.js';

function get(domain, url, config = {}) {
  return httpHandler(domain).get(`${url}`, config);
}

function post(domain, url, data, config = {}) {
  return httpHandler(domain).post(`${url}`, data, config);
}

function put(domain, url, data, config = {}) {
  return httpHandler(domain).put(`${url}`, data, config);
}

function del(domain, url, config = {}) {
  return httpHandler(domain).delete(`${url}`, config);
}

export const methods = {
  get: (url, config = {}) => {
    return get(API_DOMAIN.API, url, config);
  },
  post: (url, data, config = {}) => {
    return post(API_DOMAIN.API, url, data, config);
  },
  put: (url, data, config = {}) => {
    return put(API_DOMAIN.API, url, data, config);
  },
  delete: (url, config = {}) => {
    return del(API_DOMAIN.API, url, config);
  },
};