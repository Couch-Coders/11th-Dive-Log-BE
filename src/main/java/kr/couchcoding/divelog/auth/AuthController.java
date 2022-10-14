package kr.couchcoding.divelog.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.couchcoding.divelog.auth.dto.AuthInfo;
import kr.couchcoding.divelog.auth.service.AuthService;
import kr.couchcoding.divelog.exception.InvalidAuthTokenException;
import kr.couchcoding.divelog.exception.RevokeTokenException;
import kr.couchcoding.divelog.user.User;
import kr.couchcoding.divelog.user.UserDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestHeader("Authorization") String bearerToken) throws InvalidAuthTokenException{
        String token = getTokenString(bearerToken);

        AuthInfo authInfo = authService.verifyToken(token);
        User user = authService.loginOrSignUp(authInfo);

        ResponseCookie cookie = createTokenCookie(authInfo);

        return ResponseEntity.ok().header("idToken", cookie.toString()).body(new UserDto(user));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(Authentication authentication) throws RevokeTokenException {
        User user = (User) authentication.getPrincipal();
        authService.revokeToken(user);

        return ResponseEntity.ok().header("idToken", removeCookie("idToken").toString()).build();
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
            .sameSite("None")
            .build();
    }

    private ResponseCookie removeCookie(String name) {
        return ResponseCookie.from(name, "")
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(0)
            .sameSite("None")
            .build();
    }
    
    @ExceptionHandler(InvalidAuthTokenException.class)
    public ResponseEntity<String> handleInvalidAuthTokenException(InvalidAuthTokenException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(RevokeTokenException.class)
    public ResponseEntity<String> handleRevokeTokenException(RevokeTokenException e){
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
    }
}
