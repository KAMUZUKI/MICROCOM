import axios from 'axios';

const request = axios.create({
  baseURL: 'http://localhost:8080/microcom/',
  timeout: 5000,
  withCredentials: true,
});

request.interceptors.request.use(
  (config) => {
    // 在请求发送之前做一些处理，例如添加请求头等
    const token = localStorage.getItem('tokeninfo');
    if (token) {
      config.headers['satoken'] = `${token}`;
    }
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