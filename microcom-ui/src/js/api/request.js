import axios from 'axios';

const request = axios.create({
  baseURL: 'http://localhost:9999/framework/',
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