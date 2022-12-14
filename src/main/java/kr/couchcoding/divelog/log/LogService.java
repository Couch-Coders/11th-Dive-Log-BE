package kr.couchcoding.divelog.log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;

import kr.couchcoding.divelog.exception.BucketCreateException;
import kr.couchcoding.divelog.exception.ImageNotFoundException;
import kr.couchcoding.divelog.exception.InvalidLogAccessException;
import kr.couchcoding.divelog.log.dao.LogRepository;
import kr.couchcoding.divelog.log.dto.CreateLogRequest;
import kr.couchcoding.divelog.log.dto.SearchLogParams;
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
    public void deleteLog(Long id, User user) throws InvalidLogAccessException {
        Log diveLog = getDiveLogWithVerifyAccess(id, user);
        logRepository.delete(diveLog);
    }

    @Transactional
    public Log addImages(Long id, User user, List<MultipartFile> images) throws InvalidLogAccessException, BucketCreateException {
        Log diveLog = getDiveLogWithVerifyAccess(id, user);

        List<String> imageUrls = new ArrayList<>();
        for(int i = 0; i < images.size(); i++){
            MultipartFile image = images.get(i);
            String fileName = image.getOriginalFilename();
            int number = i + diveLog.getImages().size();
            String filePath = "/logs/" + id + "/images/" + fileName + number;
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

    public byte[] getImage(Long id, String imageName) throws ImageNotFoundException {
        String filePath = "/logs/" + id + "/images/" + imageName;
        Blob blob = bucket.get(filePath);
        if(blob == null){
            throw new ImageNotFoundException(filePath + " is not found");
        }
        return blob.getContent();
    }

    @Transactional
    public void deleteImage(Long id, User user, String imageName) throws InvalidLogAccessException {
        Log diveLog = getDiveLogWithVerifyAccess(id, user);
        String filePath = "/logs/" + id + "/images" + imageName;

        Blob blob = bucket.get(filePath);
        blob.delete();

        diveLog.deleteImage(filePath);
    }

    public Log getDiveLogWithVerifyAccess(Long id, User user) throws InvalidLogAccessException {
        Log diveLog = logRepository.findById(id).orElseThrow(() -> new InvalidLogAccessException("?????? ????????? ????????????."));
        if(!diveLog.getUser().getId().equals(user.getId())){
            throw new InvalidLogAccessException("?????? ????????? ???????????? ????????????.");
        }
        return diveLog;
    }

    public Page<Log> getLogs(User user, Pageable pageable, SearchLogParams params) {
        return logRepository.findAllBySearchOption(user, pageable, params);
    }

    @Transactional
    public Log updateLog(Long id, User user, CreateLogRequest request) throws InvalidLogAccessException {
        Log diveLog = getDiveLogWithVerifyAccess(id, user);
        diveLog.update(request);
        return diveLog;
    }

    @Transactional
    public Log updateFavorite(Long id, User user) throws InvalidLogAccessException {
        Log diveLog = getDiveLogWithVerifyAccess(id, user);
        diveLog.updateFavorite();
        return diveLog;
    }
}
