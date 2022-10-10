package kr.couchcoding.divelog.log;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.couchcoding.divelog.log.dto.CreateLogRequest;
import kr.couchcoding.divelog.log.dto.LogResponse;
import kr.couchcoding.divelog.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/logs")
@RequiredArgsConstructor
@Slf4j
public class LogController {

    private final LogService logService;
    
    @PostMapping(value="")
    public LogResponse createLog(Authentication authentication, @RequestBody CreateLogRequest request) {
        User user = (User) authentication.getPrincipal();
        return new LogResponse(logService.createLog(user, request));
    }
}
