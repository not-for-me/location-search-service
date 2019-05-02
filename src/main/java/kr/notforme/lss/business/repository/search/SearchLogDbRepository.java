package kr.notforme.lss.business.repository.search;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.notforme.lss.business.model.search.SearchLog;
import kr.notforme.lss.business.model.search.TopSearchKeyword;

public interface SearchLogDbRepository extends JpaRepository<SearchLog, Long> {
   @Query("SELECT new kr.notforme.lss.business.model.search.TopSearchKeyword(SL.keyword, COUNT(SL.keyword)) "
          + "FROM SearchLog SL WHERE SL.createdTime > :minDate "
          + "GROUP BY SL.keyword "
          + "ORDER BY COUNT(SL.keyword) DESC")
   List<TopSearchKeyword> findTopSearchKeyword(@Param("minDate") LocalDateTime minDate, Pageable pageable);
}
