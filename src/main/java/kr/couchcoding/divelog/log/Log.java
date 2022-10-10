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
import kr.couchcoding.divelog.user.User;
import lombok.Builder;
import lombok.Getter;


@Entity
@Table(name = "log", schema = "public")
@Getter
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
    private int sight;
    private int maxDepth;
    private int temperature;
    private int maxOxygen;
    private int minOxygen;
    @Enumerated(EnumType.STRING)
    private Location location;
    private String content;
    @ElementCollection
    private List<String> images = new ArrayList<>();
    private double longitude;
    private double latitude;


    @Builder
    public Log(User user, LocalDateTime date, DiveType diveType, LocalDateTime enterTime, LocalDateTime leaveTime,
            int sight, int maxDepth, int temperature, int maxOxygen, int minOxygen, Location location, String content,
            List<String> images, double longitude, double latitude) {
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
}
