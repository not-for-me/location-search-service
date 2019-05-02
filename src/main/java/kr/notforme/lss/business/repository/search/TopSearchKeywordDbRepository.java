package kr.notforme.lss.business.repository.search;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.notforme.lss.business.model.search.TopSearchKeyword;

public interface TopSearchKeywordDbRepository extends JpaRepository<TopSearchKeyword, Long> {

    List<TopSearchKeyword> findTop10ByOrderByCountDesc();
}
