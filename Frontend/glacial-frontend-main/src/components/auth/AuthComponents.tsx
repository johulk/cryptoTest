import React from "react";
import styles from "../../styles/AuthFrame.module.css";

// Input Field Component
interface InputFieldProps {
  type: string;
  name: string;
  placeholder: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  isFirst?: boolean;
  required?: boolean;
}

export const InputField: React.FC<InputFieldProps> = ({
  type,
  name,
  placeholder,
  value,
  onChange,
  isFirst = false,
  required = true,
}) => {
  return (
    <input
      type={type}
      name={name}
      placeholder={placeholder}
      value={value}
      onChange={onChange}
      className={`${styles.inputField} ${isFirst ? styles.firstInput : ""}`}
      required={required}
    />
  );
};

// Auth Button Component
interface AuthButtonProps {
  type?: "button" | "submit" | "reset";
  onClick?: () => void;
  children: React.ReactNode;
}

export const AuthButton: React.FC<AuthButtonProps> = ({
  type = "submit",
  onClick,
  children,
}) => {
  return (
    <button type={type} onClick={onClick} className={styles.authButton}>
      {children}
    </button>
  );
};

// Auth Link Component
interface AuthLinkProps {
  onClick: () => void;
  children: React.ReactNode;
}

export const AuthLink: React.FC<AuthLinkProps> = ({ onClick, children }) => {
  return (
    <div className={styles.authLink} onClick={onClick}>
      {children}
    </div>
  );
};

// Banner Component
interface BannerProps {
  logoSrc: string;
}

export const Banner: React.FC<BannerProps> = ({ logoSrc }) => {
  return (
    <div className={styles.banner}>
      <img src={logoSrc} className={styles.logo} alt="Logo" />
      <div className={styles.navItem}>Home</div>
      <div className={styles.navItem}>Whitepaper</div>
    </div>
  );
};
