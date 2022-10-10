package kr.couchcoding.divelog.log;

import org.springframework.stereotype.Service;

import kr.couchcoding.divelog.log.dto.CreateLogRequest;
import kr.couchcoding.divelog.user.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogService {
    private final LogRepository logRepository;

    public Log createLog(User user ,CreateLogRequest request) {
        Log log = Log.builder()
            .user(user)
            .leaveTime(request.getLeaveTime())
            .sight(request.getSight())
            .temperature(request.getTemperature())
            .date(request.getDate())
            .enterTime(request.getEnterTime())
            .latitude(request.getLatitude())
            .longitude(request.getLongitude())
            .location(request.getLocation())
            .maxDepth(request.getMaxDepth())
            .maxOxygen(request.getMaxOxygen())
            .minOxygen(request.getMinOxygen())
            .diveType(request.getDiveType())
            .content(request.getContent())
            .build();
        return logRepository.save(log);
    }
    
}
