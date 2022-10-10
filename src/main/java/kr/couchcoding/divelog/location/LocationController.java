package kr.couchcoding.divelog.location;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/locations")
public class LocationController {
    @GetMapping(value="")
    public List<Location> getLocations() {
        return Arrays.asList(Location.values());
    }
    
}
