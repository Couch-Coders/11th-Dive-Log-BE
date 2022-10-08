package kr.couchcoding.divelog.auth.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import kr.couchcoding.divelog.auth.dto.AuthInfo;
import kr.couchcoding.divelog.exception.InvalidAuthTokenException;
import kr.couchcoding.divelog.exception.RevokeTokenException;
import kr.couchcoding.divelog.user.User;
import kr.couchcoding.divelog.user.UserService;
import lombok.extern.slf4j.Slf4j;

@Service
@Profile("prod")
@Slf4j
public class FirebaseAuthService extends AuthService {
    private FirebaseAuth firebaseAuth;

    public FirebaseAuthService(UserService userService, FirebaseAuth firebaseAuth) {
        super(userService);
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public AuthInfo verifyToken(String token) throws InvalidAuthTokenException{
        try {
            FirebaseToken firebaseToken = firebaseAuth.verifyIdToken(token);
            AuthInfo authInfo = AuthInfo.builder()
                    .id(firebaseToken.getUid())
                    .name(firebaseToken.getName())
                    .email(firebaseToken.getEmail())
                    .profileImage(firebaseToken.getPicture())
                    .idToken(token)
                    .build();
            return authInfo;
        } catch (FirebaseAuthException e) {
            log.error("Invalid token : {}", e.getMessage());
            throw new InvalidAuthTokenException(e.getMessage());
        }
    }

    public void revokeToken(User user) throws RevokeTokenException {
        try {
            firebaseAuth.revokeRefreshTokens(user.getId());
        } catch (FirebaseAuthException e) {
            log.error("revoke token error : {}", e.getMessage());
            throw new RevokeTokenException(e.getMessage());
        }
    }

}