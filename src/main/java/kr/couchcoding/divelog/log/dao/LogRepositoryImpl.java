package kr.couchcoding.divelog.log.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.couchcoding.divelog.location.Location;
import kr.couchcoding.divelog.log.Log;
import kr.couchcoding.divelog.log.dto.SearchLogParams;
import kr.couchcoding.divelog.user.User;

import static kr.couchcoding.divelog.log.QLog.log;

import java.time.LocalDate;
import java.util.List;

public class LogRepositoryImpl extends QuerydslRepositorySupport implements LogRepositoryCustom {
    @Autowired
    private JPAQueryFactory queryFactory;
    
    public LogRepositoryImpl() {
        super(Log.class);
    }

    @Override
    public Page<Log> findAllBySearchOption(User user, Pageable pageable, SearchLogParams params) {
        JPQLQuery<Log> query = queryFactory.selectFrom(log)
                .where(
                    userEq(user),
                    dateBetween(params.getStartDate(), params.getEndDate()),
                    locationEq(params.getLocation()),
                    temperatureBetween(params.getMinTemperature(), params.getMaxTemperature()),
                    depthBetween(params.getMinDepth(), params.getMaxDepth()),
                    keywordLike(params.getKeyword())
                );
        List<Log> logs = this.getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<Log>(logs, pageable, query.fetchCount());
    }

    private BooleanExpression userEq(User user) {
        if(user != null){
            return log.user.eq(user);
        } 
        return null;
    }

    private BooleanExpression dateBetween(LocalDate start, LocalDate end) {
        if(start != null && end != null)
        {
            return log.date.between(start.atStartOfDay(), end.plusDays(1).atStartOfDay());
        }
        return null;
    }

    private BooleanExpression locationEq(Location location) {
        if(location != null){
            return log.location.eq(location);
        }
        return null;
    }

    private BooleanExpression temperatureBetween(Integer min, Integer max) {
        if(min != null && max != null)
        {
            return log.temperature.between(min, max);
        }
        return null;
    }

    private BooleanExpression depthBetween(Integer min, Integer max) {
        if(min != null && max != null)
        {
            return log.maxDepth.between(min, max);
        }
        return null;
    }

    private BooleanExpression keywordLike(String keyword) {
        if(keyword != null){
            return log.content.contains(keyword);
        }
        return null;
    }


}
    
