package dev.m1guel.glacial.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RegisterDto {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 30, message = "Password must be at least 8 to 30 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*]).{8,}$", message = "Password must contain at least one number, one letter, and one special character")
    private String password;

    @Pattern(regexp = "^$|^[1-9A-HJ-NP-Za-km-z]{44}$", message = "Invalid Solana wallet address")
    private String walletSol;

    @Pattern(regexp = "^$|^(0x)?[0-9a-fA-F]{40}$", message = "Invalid Ethereum wallet address")
    private String walletEth;

}