package kr.couchcoding.divelog.log.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import kr.couchcoding.divelog.location.Location;
import kr.couchcoding.divelog.log.DiveType;
import kr.couchcoding.divelog.log.Log;
import kr.couchcoding.divelog.user.UserDto;
import lombok.Data;

@Data
public class LogResponse {
    private Long id;
    private UserDto user;
    private LocalDateTime date;
    private DiveType diveType;
    private LocalDateTime enterTime;
    private LocalDateTime leaveTime;
    private int sight;
    private int maxDepth;
    private int temperature;
    private int maxOxygen;
    private int minOxygen;
    private Location location;
    private String content;
    private List<String> images = new ArrayList<>();
    private double longitude;
    private double latitude;

    public LogResponse(Log log){
        this.id = log.getId();
        this.user = new UserDto(log.getUser());
        this.date = log.getDate();
        this.diveType = log.getDiveType();
        this.enterTime = log.getEnterTime();
        this.leaveTime = log.getLeaveTime();
        this.sight = log.getSight();
        this.maxDepth = log.getMaxDepth();
        this.temperature = log.getTemperature();
        this.maxOxygen = log.getMaxOxygen();
        this.minOxygen = log.getMinOxygen();
        this.location = log.getLocation();
        this.content = log.getContent();
        this.images = log.getImages();
        this.longitude = log.getLongitude();
        this.latitude = log.getLatitude();
    }
}
