import api from './api/ApiClient';
import { saveToken, clearData } from './api/AuthData';

interface LoginCredentials {
  username: string;
  password: string;
}

interface RegisterData extends LoginCredentials {
  solanaWallet: string;
  ethereumWallet: string;
}

export const login = async (credentials: LoginCredentials) => {
  try {
    const response = await api.post('/auth/login', credentials);
    saveToken(response.data.token);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const register = async (data: RegisterData) => {
  try {
    const response = await api.post('/auth/register', data);
    saveToken(response.data.token);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const logout = () => {
  clearData();
  window.location.href = '/login';
};