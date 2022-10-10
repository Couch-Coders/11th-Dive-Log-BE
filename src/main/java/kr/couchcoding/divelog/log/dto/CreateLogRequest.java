package kr.couchcoding.divelog.log.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;

import kr.couchcoding.divelog.location.Location;
import kr.couchcoding.divelog.log.DiveType;
import lombok.Data;

@Data
public class CreateLogRequest {
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
    @ElementCollection
    private List<String> images = new ArrayList<>();
    private double longitude;
    private double latitude;
}
