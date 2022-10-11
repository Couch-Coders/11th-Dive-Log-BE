package kr.couchcoding.divelog.log;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/diveTypes")
public class DiveTypeController {
    @GetMapping("")
    public List<DiveType> getDiveTypes() {
        return Arrays.asList(DiveType.values());
    }
}
