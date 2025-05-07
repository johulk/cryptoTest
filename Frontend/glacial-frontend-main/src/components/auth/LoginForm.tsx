import React from "react";
import styles from "../../styles/AuthFrame.module.css";
import { InputField, AuthButton, AuthLink } from "./AuthComponents";

interface LoginFormProps {
  formData: {
    username: string;
    password: string;
  };
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  onSubmit: (e: React.FormEvent) => void;
  onSwitchForm: () => void;
}

const LoginForm: React.FC<LoginFormProps> = ({
  formData,
  onChange,
  onSubmit,
  onSwitchForm,
}) => {
  return (
    <form className={styles.formBase} onSubmit={onSubmit}>
      <div className={styles.formTitle}>Login</div>

      <InputField
        type="text"
        name="username"
        placeholder="Username"
        value={formData.username}
        onChange={onChange}
        isFirst={true}
      />

      <InputField
        type="password"
        name="password"
        placeholder="Password"
        value={formData.password}
        onChange={onChange}
      />

      <AuthButton>LOGIN</AuthButton>

      <AuthLink onClick={onSwitchForm}>
        Dont have an account yet? Sign up here
      </AuthLink>
    </form>
  );
};

export default LoginForm;
