package dev.m1guel.glacial.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProfileDto {

    private String username;
    private String walletSol;
    private String walletEth;
    private String avatar;
    private String banner;
    private String bio;
    private String role;
    private int followersCount;
    private int followingCount;
    private int postsCount;

}
