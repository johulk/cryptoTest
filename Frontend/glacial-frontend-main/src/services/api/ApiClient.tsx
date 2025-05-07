import axios from 'axios';
import { getAuthHeaders, saveToken, clearData } from './AuthData';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  withCredentials: true,
});

// Simple request interceptor to add auth token
api.interceptors.request.use(
  (config) => {
    const token = getAuthHeaders()?.Authorization;
    if (token) {
      config.headers['Authorization'] = token;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Simple response interceptor to handle auth errors
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      clearData();
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export default api;