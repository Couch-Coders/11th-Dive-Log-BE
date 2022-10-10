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
    private Integer sight;
    private Integer maxDepth;
    private Integer temperature;
    private Integer maxOxygen;
    private Integer minOxygen;
    private Location location;
    private String content;
    private Double longitude;
    private Double latitude;
}
