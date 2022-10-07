package kr.couchcoding.divelog.config.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import kr.couchcoding.divelog.auth.service.AuthService;
import kr.couchcoding.divelog.exception.ExpiresTokenException;
import kr.couchcoding.divelog.exception.InvalidAuthTokenException;
import kr.couchcoding.divelog.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class AuthFilter extends OncePerRequestFilter {

    private final UserService userDetailsService;
    private final AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        // get accessToken from cookie
        try {
            Cookie[] cookies = request.getCookies();
            String idToken = findCookie(cookies, "idToken");
  
            String id = authService.verifyToken(idToken).id();

            UserDetails user = userDetailsService.loadUserByUsername(id);//user? id 를 통해 회원 엔티티 조회
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities());//인증 객체 생성        
            SecurityContextHolder.getContext().setAuthentication(authentication);//securityContextHolder 에 인증 객체 저장
            filterChain.doFilter(request, response);
        } catch (ExpiresTokenException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("""
                {
                    "message": "Unauthorized"
                }
            """);
        } catch (InvalidAuthTokenException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(""" 
                {
                    "message": "Invalid Token"
                } """
            );
        } catch (UsernameNotFoundException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.getWriter().write(""" 
                {
                    "message": "User Not Found"
                } """
            );
        }
    }

    private String findCookie(Cookie[] cookies, String cookieName) throws ExpiresTokenException {
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(cookieName)){
                log.info(cookieName + " : " + cookie.getValue());
                return cookie.getValue();
            }
        }
        throw new ExpiresTokenException("Cookie not found");
    }
    
}
