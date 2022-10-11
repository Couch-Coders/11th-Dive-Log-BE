package kr.couchcoding.divelog.log.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.couchcoding.divelog.log.Log;
import kr.couchcoding.divelog.user.User;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

    Page<Log> findAllByUser(User user, Pageable pageable);

}
    
