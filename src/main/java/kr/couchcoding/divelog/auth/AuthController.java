package kr.couchcoding.divelog.auth;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import kr.couchcoding.divelog.auth.dto.AuthInfo;
import kr.couchcoding.divelog.auth.service.AuthService;
import kr.couchcoding.divelog.exception.InvalidAuthTokenException;
import kr.couchcoding.divelog.user.User;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestHeader("Authentication") String bearerToken) throws InvalidAuthTokenException{
        String token = getTokenString(bearerToken);

        AuthInfo authInfo = authService.verifyToken(token);
        User user = authService.loginOrSignUp(authInfo);

        ResponseCookie cookie = createTokenCookie(authInfo);

        return ResponseEntity.ok().header("idToken", cookie.toString()).body(user);
    }

    private String getTokenString(String bearerToken) throws InvalidAuthTokenException {
        String token = bearerToken.substring(7);
        if(token == null || token.isEmpty()){
            throw new InvalidAuthTokenException("no token provided");
        }
        return token;
    }

    private ResponseCookie createTokenCookie(AuthInfo authInfo) {
        return ResponseCookie.from("idToken", authInfo.idToken())
            .httpOnly(true)
            .secure(true)
            .path("/")
            .build();
    }
    
    @ExceptionHandler(InvalidAuthTokenException.class)
    public ResponseEntity<String> handleInvalidAuthTokenException(InvalidAuthTokenException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
}
