package kr.couchcoding.divelog.log.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.couchcoding.divelog.log.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Long>, LogRepositoryCustom {

}
    
