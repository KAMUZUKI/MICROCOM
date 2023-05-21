import axios from 'axios';
import config from '@/js/api/config'
const request = axios.create({
  baseURL: config.getUrl,
  timeout: 5000,
});

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