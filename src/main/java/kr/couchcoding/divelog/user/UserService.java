package kr.couchcoding.divelog.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import kr.couchcoding.divelog.auth.dto.AuthInfo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public User getUser(String id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No user found with that id"));
    }

    public User createUser(AuthInfo authInfo) {
        if(userRepository.existsById(authInfo.id())){
            throw new IllegalArgumentException("User with id '" + authInfo.id() + "' already exists");
        }
        User user = new User(authInfo);
        return userRepository.save(user);
    }
    
}
