import axios from 'axios';
import config from '@/js/api/config'
const request = axios.create({
  baseURL: config.getUrl,
  timeout: 30000,
});

request.interceptors.request.use(
  (config) => {
    // 在请求发送之前做一些处理，例如添加请求头等
    // const userId = JSON.parse(localStorage.getItem("user")).id
    config.headers['userId'] = 1;
    return config;
  },
  (error) => {
    // 处理请求错误
    return Promise.reject(error);
  }
);

request.interceptors.response.use(
  (response) => {
    // 在接收响应数据之前做一些处理
    return response.data;
  },
  (error) => {
    // 处理响应错误
    return Promise.reject(error);
  }
);

export default request;