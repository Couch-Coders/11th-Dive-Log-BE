package kr.couchcoding.divelog.log.dao;

import org.springframework.data.domain.Page;

import kr.couchcoding.divelog.log.Log;
import kr.couchcoding.divelog.user.User;

public class LogRepositoryCustom {
    Page<Log> findAllBySearchOption(User user, ,  Pageable pageable);
}
