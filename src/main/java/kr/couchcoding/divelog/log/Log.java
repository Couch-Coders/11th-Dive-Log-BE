package kr.couchcoding.divelog.log;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import kr.couchcoding.divelog.location.Location;
import kr.couchcoding.divelog.log.dto.CreateLogRequest;
import kr.couchcoding.divelog.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "log", schema = "public")
@Getter
@NoArgsConstructor
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDateTime date;
    @Enumerated(EnumType.STRING)
    private DiveType diveType;
    private LocalDateTime enterTime;
    private LocalDateTime leaveTime;
    private Integer sight;
    private Integer maxDepth;
    private Integer temperature;
    private Integer maxOxygen;
    private Integer minOxygen;
    @Enumerated(EnumType.STRING)
    private Location location;
    private String content;
    @ElementCollection
    private List<String> images = new ArrayList<>();
    private Double longitude;
    private Double latitude;


    @Builder
    public Log(User user, LocalDateTime date, DiveType diveType, LocalDateTime enterTime, LocalDateTime leaveTime,
            Integer sight, Integer maxDepth, Integer temperature, Integer maxOxygen, Integer minOxygen, Location location, String content,
            List<String> images, Double longitude, Double latitude) {
        this.user = user;
        this.date = date;
        this.diveType = diveType;
        this.enterTime = enterTime;
        this.leaveTime = leaveTime;
        this.sight = sight;
        this.maxDepth = maxDepth;
        this.temperature = temperature;
        this.maxOxygen = maxOxygen;
        this.minOxygen = minOxygen;
        this.location = location;
        this.content = content;
        this.images = images;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public void addImages(List<String> imageUrls) {
        this.images.addAll(imageUrls);
    } 

    public void deleteImage(String imageUrl) {
        this.images.remove(imageUrl);
    }

    public void update(CreateLogRequest request) {
        this.date = (request.getDate() != null)? request.getDate() : this.date;
        this.content = (request.getContent() != null)? request.getContent() : this.content;
        this.diveType = (request.getDiveType() != null)? request.getDiveType() : this.diveType;
        this.enterTime = (request.getEnterTime() != null)? request.getEnterTime() : this.enterTime;
        this.leaveTime = (request.getLeaveTime() != null)? request.getLeaveTime() : this.leaveTime;
        this.location = (request.getLocation() != null)? request.getLocation() : this.location;
        this.maxDepth = (request.getMaxDepth() != null)? request.getMaxDepth() : this.maxDepth;
        this.maxOxygen = (request.getMaxOxygen() != null)? request.getMaxOxygen() : this.maxOxygen;
        this.minOxygen = (request.getMinOxygen() != null)? request.getMinOxygen() : this.minOxygen;
        this.sight = (request.getSight() != null)? request.getSight() : this.sight;
        this.temperature = (request.getTemperature() != null)? request.getTemperature() : this.temperature;
        this.longitude = (request.getLongitude() != null)? request.getLongitude() : this.longitude;
        this.latitude = (request.getLatitude() != null)? request.getLatitude() : this.latitude;
    }
}
