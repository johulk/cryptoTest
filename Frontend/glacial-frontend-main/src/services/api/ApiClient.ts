import axios from 'axios';
import { getAuthHeaders } from './AuthData';

const api = axios.create({
    baseURL: 'http://localhost:8080/api',
    withCredentials: true,
});

api.interceptors.request.use(
    (config) => {
        try {
            const authHeaders = getAuthHeaders();
            config.headers = { ...config.headers, ...authHeaders };
        } catch {
            console.warn("Missing or invalid token.");
        }
        return config;
    },
    (error) => Promise.reject(error)
);

export default api;