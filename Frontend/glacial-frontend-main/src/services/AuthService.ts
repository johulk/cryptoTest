import api from './api/ApiClient';
import {getProfile} from './ProfileService';
import {clearData, saveUser, saveToken} from './api/AuthData';

export const login = async (loginData) => {
    const response = await api.post('/auth/login', loginData);
    const token = response.data.data;
    if (token) {
        saveToken(token);
        const userResponse = await getProfile();
        saveUser(userResponse.data);
    }
    return response;
};

export const register = async (registerData) => {
    return await api.post('/auth/register', registerData);
};

export const logout = async () => {
    const response = await api.post('/auth/logout');
    clearData();
    return response;
};