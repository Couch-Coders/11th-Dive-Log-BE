package kr.couchcoding.divelog.log.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.couchcoding.divelog.log.Log;
import kr.couchcoding.divelog.log.dto.SearchLogParams;
import kr.couchcoding.divelog.user.User;

public interface LogRepositoryCustom {
    Page<Log> findAllBySearchOption(User user, Pageable pageable, SearchLogParams params);
}
