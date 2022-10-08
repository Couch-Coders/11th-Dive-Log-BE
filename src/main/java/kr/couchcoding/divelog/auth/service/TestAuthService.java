package kr.couchcoding.divelog.auth.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import kr.couchcoding.divelog.auth.dto.AuthInfo;
import kr.couchcoding.divelog.exception.RevokeTokenException;
import kr.couchcoding.divelog.user.User;
import kr.couchcoding.divelog.user.UserService;
import lombok.extern.slf4j.Slf4j;

@Service
@Profile("local")
@Slf4j
public class TestAuthService extends AuthService {

    public TestAuthService(UserService userService) {
        super(userService);
    }

    @Override
    public AuthInfo verifyToken(String token)  {
        return AuthInfo.builder().id(token)
        .name(token)
        .email(token+"@test.com")
        .profileImage("")
        .idToken(token)
        .build();
    }

    @Override
    public void revokeToken(User user) throws RevokeTokenException {
        // TODO Auto-generated method stub
        log.info("revoke token : {}", user.getId());
    }
}
