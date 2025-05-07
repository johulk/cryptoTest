export const getAuthHeaders = () => {
    const token = localStorage.getItem('jwtToken');
    if (!token) {
        return {};
    }
    return { Authorization: `Bearer ${token}` };
};

export const saveToken = (token) => {
    localStorage.setItem('jwtToken', token);
};

export const saveUser = (user) => {
    localStorage.setItem('user', JSON.stringify(user));
};

export const clearData = () => {
    localStorage.removeItem('jwtToken');
    localStorage.removeItem('user');
};

export const getUser = () => {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
};