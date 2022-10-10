package kr.couchcoding.divelog.log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;

import kr.couchcoding.divelog.exception.BucketCreateException;
import kr.couchcoding.divelog.exception.InvalidLogAccessException;
import kr.couchcoding.divelog.log.dto.CreateLogRequest;
import kr.couchcoding.divelog.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogService {
    private final LogRepository logRepository;
    private final Bucket bucket;

    @Transactional
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

    @Transactional
    public Log addImages(Long id, User user, List<MultipartFile> images) throws InvalidLogAccessException, BucketCreateException {
        Log diveLog = logRepository.findById(id).orElseThrow(() -> new InvalidLogAccessException("해당 로그가 없습니다."));
        if(!diveLog.getUser().getId().equals(user.getId())){
            throw new InvalidLogAccessException("해당 로그의 작성자가 아닙니다.");
        }

        List<String> imageUrls = new ArrayList<>();
        for(int i = 0; i < images.size(); i++){
            MultipartFile image = images.get(i);
            String fileName = image.getOriginalFilename();
            String filePath = "/logs/" + id + "/" + fileName + i;
            imageUrls.add(filePath);
            try {
                bucket.create(filePath, image.getBytes(), image.getContentType());
            } catch (IOException e) {
                log.error("Bucket Create Error", e);
                throw new BucketCreateException(e.getMessage());
            }
        }

        diveLog.addImages(imageUrls);

        return diveLog;
    }

    public byte[] getImage(Long id, User user, String imageName) {
        String filePath = "/logs/" + id + "/" + imageName;
        Blob blob = bucket.get(filePath);
        return blob.getContent();
    }
    
}
