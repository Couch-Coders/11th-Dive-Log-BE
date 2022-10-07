package kr.couchcoding.divelog.auth.service;

import org.springframework.stereotype.Service;

import kr.couchcoding.divelog.auth.dto.AuthInfo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestAuthService extends AuthService {

    @Override
    public AuthInfo verifyToken(String token) {
        return AuthInfo.builder().id(token)
        .name(token)
        .email(token+"@test.com")
        .profileImage("")
        .idToken(token)
        .build();
    }
}
