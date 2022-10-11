package kr.couchcoding.divelog.log.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import kr.couchcoding.divelog.location.Location;
import lombok.Data;

@Data
public class SearchLogParams {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate endDate;
    Location location;
    Integer minTemperature;
    Integer maxTemperature;
    Integer minDepth;
    Integer maxDepth;
    String keyword;
}
