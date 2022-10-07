package kr.couchcoding.divelog.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import kr.couchcoding.divelog.auth.service.AuthService;
import kr.couchcoding.divelog.config.filter.AuthFilter;
import kr.couchcoding.divelog.user.UserService;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    @Autowired
    UserService userService;
    @Autowired
    AuthService authService;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            setDefaultSecurityConfigs(http);
            log.info("authService : " + authService);
            http.addFilterBefore(new AuthFilter(userService, authService),
                      UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    private void setDefaultSecurityConfigs(HttpSecurity http) throws Exception {
        http
                .formLogin().disable()
                .httpBasic().disable()//rest api만 고려
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //시큐리티가 세션을 생성하지 않음(JWT를 사용)
                .and()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .csrf().disable();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/")
                .antMatchers("/auth/login") //로그인은 로그인이 필요 없다
                .antMatchers("/favicon.ico");
    }
}
