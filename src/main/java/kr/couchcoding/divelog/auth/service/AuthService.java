package kr.couchcoding.divelog.auth.service;

import kr.couchcoding.divelog.auth.dto.AuthInfo;
import kr.couchcoding.divelog.exception.InvalidAuthTokenException;
import kr.couchcoding.divelog.user.User;
import kr.couchcoding.divelog.user.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AuthService {
    private UserService userService;

    public abstract AuthInfo verifyToken(String token) throws InvalidAuthTokenException;
    public User loginOrSignUp(AuthInfo authInfo) {
        User user;
        try{
            user = userService.getUser(authInfo.id());
        } catch (IllegalArgumentException e) {
            log.info("User with id {} was not found in the database, creating user", authInfo.id());
            user = userService.createUser(authInfo);
        }
        return user;
    }
}
