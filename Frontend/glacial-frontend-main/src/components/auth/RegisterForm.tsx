import React from "react";
import styles from "../../styles/AuthFrame.module.css";
import { InputField, AuthButton, AuthLink } from "./AuthComponents";

interface RegisterFormProps {
    formData: {
      username: string;
      password: string;
      solanaWallet: string;
      ethereumWallet: string;
    };
    onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    onSubmit: (e: React.FormEvent) => void;
    onSwitchForm: () => void;
  }
  
  const RegisterForm: React.FC<RegisterFormProps> = ({
    formData,
    onChange,
    onSubmit,
    onSwitchForm,
  }) => {
    return (
      <form
        className={`${styles.formBase} ${styles.registerForm}`}
        onSubmit={onSubmit}
      >
        {/* Register form has no title as requested */}
  
        <InputField
          type="text"
          name="username"
          placeholder="Username"
          value={formData.username}
          onChange={onChange}
          isFirst={true}
          // Remove the className prop since it's causing the TypeScript error
        />
  
        <InputField
          type="password"
          name="password"
          placeholder="Password"
          value={formData.password}
          onChange={onChange}
        />
  
        <InputField
          type="text"
          name="solanaWallet"
          placeholder="Solana Wallet Address"
          value={formData.solanaWallet}
          onChange={onChange}
        />
  
        <InputField
          type="text"
          name="ethereumWallet"
          placeholder="Ethereum Wallet Address"
          value={formData.ethereumWallet}
          onChange={onChange}
        />
  
        <AuthButton>REGISTER</AuthButton>
  
        <AuthLink onClick={onSwitchForm}>
          Already have an account? Login here
        </AuthLink>
      </form>
    );
  };
  
  export default RegisterForm;