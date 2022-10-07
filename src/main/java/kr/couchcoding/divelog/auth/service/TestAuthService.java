package kr.couchcoding.divelog.auth.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import kr.couchcoding.divelog.auth.dto.AuthInfo;
import kr.couchcoding.divelog.user.UserService;

@Service
@Profile("local")
public class TestAuthService extends AuthService {

    public TestAuthService(UserService userService) {
        super(userService);
    }

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
