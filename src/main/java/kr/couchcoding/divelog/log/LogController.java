package kr.couchcoding.divelog.log;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.couchcoding.divelog.exception.BucketCreateException;
import kr.couchcoding.divelog.exception.ImageNotFoundException;
import kr.couchcoding.divelog.exception.InvalidLogAccessException;
import kr.couchcoding.divelog.location.Location;
import kr.couchcoding.divelog.log.dto.CreateLogRequest;
import kr.couchcoding.divelog.log.dto.LogResponse;
import kr.couchcoding.divelog.log.dto.SearchLogParams;
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
    @ResponseStatus(value = HttpStatus.CREATED)
    public LogResponse createLog(Authentication authentication, @RequestBody CreateLogRequest request) {
        User user = (User) authentication.getPrincipal();
        return new LogResponse(logService.createLog(user, request));
    }

    @GetMapping(value="/{id}")
    public LogResponse getLog(@PathVariable Long id, Authentication authentication) throws InvalidLogAccessException {
        User user = (User) authentication.getPrincipal();
        return new LogResponse(logService.getDiveLogWithVerifyAccess(id, user));
    }

    @PutMapping(value="/{id}")
    public LogResponse updateLog(@PathVariable(name="id") Long id, Authentication authentication, @RequestBody CreateLogRequest request) throws InvalidLogAccessException {
        User user = (User) authentication.getPrincipal();
        return new LogResponse(logService.updateLog(id, user, request));
    }

    @DeleteMapping(value="/{id}")
    public void deleteLog(Authentication authentication, @PathVariable Long id) throws InvalidLogAccessException {
        User user = (User) authentication.getPrincipal();
        logService.deleteLog(id, user);
    }

    @GetMapping(value="")
    public Page<LogResponse> getLogs(Authentication authentication, Pageable pageable, SearchLogParams params) {
        log.info("params: {}", params);
        User user = (User) authentication.getPrincipal();
        Page<Log> logs = logService.getLogs(user, pageable, params);
        return logs.map(LogResponse::new);
    }

    @PostMapping(value="/{id}/images")
    @ResponseStatus(value = HttpStatus.CREATED)
    public LogResponse addImages(Authentication authentication, @RequestParam List<MultipartFile> images, @PathVariable Long id) throws InvalidLogAccessException, BucketCreateException {
        User user = (User) authentication.getPrincipal();
        return new LogResponse(logService.addImages(id, user, images));
    }

    @GetMapping(value="/{id}/images/{imageName}")
    public byte[] getImage(@PathVariable Long id,
         @PathVariable String imageName) throws ImageNotFoundException {
        return logService.getImage(id, imageName);
    }

    @DeleteMapping(value="/{id}/images/{imageName}")
    public void deleteImage(Authentication authentication, @PathVariable Long id, @PathVariable String imageName) throws InvalidLogAccessException, BucketCreateException {
        User user = (User) authentication.getPrincipal();
        logService.deleteImage(id, user, imageName);
    }

    @PutMapping(value="/{id}/favorite")
    public LogResponse toggleFavorite(Authentication authentication, @PathVariable(name="id") Long id) throws InvalidLogAccessException {
        User user = (User) authentication.getPrincipal();
        return new LogResponse(logService.updateFavorite(id, user));
    }

    @ExceptionHandler(InvalidLogAccessException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String invalidLogAccessException(InvalidLogAccessException e) {
        return e.getMessage();
    }

    @ExceptionHandler(BucketCreateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String bucketCreateException(BucketCreateException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ImageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String imageNotFoundException(ImageNotFoundException e) {
        return e.getMessage();
    }
}
