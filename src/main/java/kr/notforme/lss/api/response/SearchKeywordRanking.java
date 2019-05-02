package kr.notforme.lss.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchKeywordRanking {
    private Integer rankPosition;
    private String keyword;
    private Long count;
}
