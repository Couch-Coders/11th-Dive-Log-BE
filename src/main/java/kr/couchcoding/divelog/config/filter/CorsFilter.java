package kr.couchcoding.divelog.config.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 모든 요청에 대해서 전부 지나간다
// 인터셉터 -> 어떤 url로 요청했는지 로그를 찍어야할때 사용한다
// 진행예정 -> 진행중 (날짜)
// 신청하려고 할때 날짜에 따라서 신청여부를 판단


//
// 진행예정 , 진행중 => 같은 상태로 둔다 () ,  , 완료
// 진행중일때 새로운 사람이 신청할수 없게 처리
// 날짜를 계산해서
// 두개
// @Component
// @Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class CorsFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Credentials", "true");
        res.setHeader("Access-Control-Allow-Methods","*");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Headers", "*");

        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}
