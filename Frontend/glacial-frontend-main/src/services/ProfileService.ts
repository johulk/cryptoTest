import api from './api/ApiClient';

export const getProfile = async () => {
    return (await api.get('/profile')).data;
};

export const getProfileByUsername = async (username) => {
    return (await api.get(`/profile/${username}`)).data;
};

export const getProfilePosts = async (username, page = 0, size = 10) => {
    return (await api.get(`/post/user/${username}?page=${page}&size=${size}`)).data;
}