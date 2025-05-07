import apiClient from "./api/ApiClient.js";

export const createPost = async (post) => {
    let response = await apiClient.post("/post", post);
    console.log(response)
    return response;
}

export const getFeed = async () => {
    let response = await apiClient.get("/post");
    console.log(response)
    return response.data;
}