package kr.couchcoding.divelog.auth.dto;

import lombok.Builder;

public record AuthInfo (
    String id,
    String name,
    String email,
    String profileImage,
    String idToken
){
    @Builder
    public AuthInfo(String id, String name, String email, String profileImage, String idToken) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
        this.idToken = idToken;
    }
}
