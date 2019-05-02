package kr.notforme.lss.support.cache;

import org.springframework.data.domain.Pageable;

public class CacheKeyResolver {
    public static final String CACHE_KEY_PREFIX = "lss";
    public static final String DELEMITER = ":";

    public static String getPlaceSearchKey(String keyword, Pageable page) {
        StringBuilder sb = new StringBuilder(CACHE_KEY_PREFIX);
        return sb.append(DELEMITER).append(keyword)
                 .append(DELEMITER).append(page.getPageNumber())
                 .append(DELEMITER).append(page.getPageSize()).toString();
    }
}
