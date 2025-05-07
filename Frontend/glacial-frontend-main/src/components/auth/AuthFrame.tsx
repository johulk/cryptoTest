"use client";

import React, { useState } from "react";
import styles from "../../styles/AuthFrame.module.css";
import { Banner } from "./AuthComponents";
import LoginForm from "./LoginForm";
import RegisterForm from "./RegisterForm";
import { login,register } from '../../services/AuthService';
import { useNavigate } from 'react-router-dom';

interface LoginFormData {
  username: string;
  password: string;
}

interface RegisterFormData extends LoginFormData {
  solanaWallet: string;
  ethereumWallet: string;
}

const AuthFrame: React.FC = () => {
  const navigate = useNavigate();
  const [isRegisterActive, setIsRegisterActive] = useState(false);
  const [error, setError] = useState<string | null>(null);

  // Form data states
  const [loginData, setLoginData] = useState<LoginFormData>({
    username: "",
    password: "",
  });

  const [registerData, setRegisterData] = useState<RegisterFormData>({
    username: "",
    password: "",
    solanaWallet: "",
    ethereumWallet: "",
  });

  const handleLoginChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setError(null);
    const { name, value } = e.target;
    setLoginData((prev) => ({ ...prev, [name]: value }));
  };

  const handleRegisterChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setError(null);
    const { name, value } = e.target;
    setRegisterData((prev) => ({ ...prev, [name]: value }));
  };

  const handleLoginSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    try {
      const response = await login(loginData);
      if (response.token) {
        navigate('/'); // Navigate to home page upon successful login
      }
    } catch (error: any) {
      setError(error.response?.data?.message || 'Login failed. Please try again.');
    }
  };

  const handleRegisterSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    try {
      const response = await register(registerData);
      if (response.token) {
        setIsRegisterActive(false); // Switch to login form after successful registration
        setError('Registration successful! Please login.');
      }
    } catch (error: any) {
      setError(error.response?.data?.message || 'Registration failed. Please try again.');
    }
  };

  const toggleForm = () => {
    setError(null);
    setIsRegisterActive((prev) => !prev);
  };

  return (
    <div className={styles.authContainer}>
      <div className={styles.authContent}>
        {/* Full-page background image */}
        <img src="https://cdn.builder.io/api/v1/image/assets/TEMP/b36c6676338a40ca25adb0b49506388168f283e0?placeholderIfAbsent=true&apiKey=5464ab180b9142b8982844fc7916f513" className={styles.backgroundImage} alt="Background" />

        {/* Top navigation banner */}
        <Banner logoSrc="https://cdn.builder.io/api/v1/image/assets/TEMP/8395271f1eff1c9eeb1ea1833c0196d0758d3cb9?placeholderIfAbsent=true&apiKey=5464ab180b9142b8982844fc7916f513" />

        {/* Forms container with sliding animation */}
        <div className={styles.formsContainer}>
          {error && <div className={styles.errorMessage}>{error}</div>}
          <div
            className={`${styles.formsWrapper} ${isRegisterActive ? styles.registerActive : ""}`}
          >
            {/* Login Form */}
            <LoginForm
              formData={loginData}
              onChange={handleLoginChange}
              onSubmit={handleLoginSubmit}
              onSwitchForm={toggleForm}
            />

            {/* Register Form */}
            <RegisterForm
              formData={registerData}
              onChange={handleRegisterChange}
              onSubmit={handleRegisterSubmit}
              onSwitchForm={toggleForm}
            />
          </div>
        </div>
      </div>
    </div>
  );
};

export default AuthFrame;
