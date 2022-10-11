package kr.couchcoding.divelog.user;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    UserService userService;

    @DeleteMapping("/me")
    public void deleteUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        userService.deleteUser(user);
    }
    
}
