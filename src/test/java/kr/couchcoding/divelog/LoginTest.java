package kr.couchcoding.divelog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.web.WebAppConfiguration;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import kr.couchcoding.divelog.user.UserDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginTest {
    
    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    FirebaseAuth firebaseAuth;

    @Test
    public void loginTest() throws FirebaseAuthException { 
        //given
        String customToken = firebaseAuth.createCustomToken("testUser");
        log.info("customToken : " + customToken);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + customToken);
        HttpEntity requestEntity = new HttpEntity<>(headers);

        // when
        ResponseEntity<UserDto> responseEntity = 
            restTemplate.exchange("/auth/login", HttpMethod.POST, requestEntity , UserDto.class);
        
        // then
        assert responseEntity.getStatusCodeValue() == 200;
        assert responseEntity.getBody().id().equals("testUser");
        // get cookie idToken equals customToken
        assert responseEntity.getHeaders().get("Set-Cookie").get(0).contains(customToken);

    }
}
