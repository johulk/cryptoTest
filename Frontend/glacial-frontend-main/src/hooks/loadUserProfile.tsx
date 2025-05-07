import { useState, useEffect, useCallback } from "react";
import { jwtDecode } from "jwt-decode";
import { getProfile } from "../services/ProfileService.js";
import { clearData, saveUser } from "../services/api/AuthData.js";

export function useLoadUserProfile() {
    const [user, setUser] = useState(null);

    const checkExpiredToken = useCallback((token) => {
        const decoded = jwtDecode(token);
        return decoded.exp < Date.now() / 1000;
    }, []);

    useEffect(() => {
        const token = localStorage.getItem("jwtToken");
        if (!token || checkExpiredToken(token)) {
            clearData();
            return;
        }

        const cachedUser = localStorage.getItem("user");
        if (cachedUser) {
            setUser(JSON.parse(cachedUser));
        } else {
            getProfile()
                .then((response) => {
                    setUser(response.data);
                    saveUser(response.data);
                })
                .catch(() => {
                    clearData();
                });
        }
    }, [checkExpiredToken]);

    return user;
}