package kr.notforme.lss.support;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.data.domain.PageRequest;

import kr.notforme.lss.support.cache.CacheKeyResolver;

public class CacheKeyResolverTest {

    @Test
    public void testPlaceSearchCacheKey() {
        // Given
        final String keyword = "hello";
        final int page = 1;
        final int size = 10;

        // When
        String actual = CacheKeyResolver.getPlaceSearchKey(keyword, PageRequest.of(page, size));

        // Then
        assertThat(actual).isNotBlank();
        assertThat(actual.split(CacheKeyResolver.DELEMITER)[0]).isEqualTo(CacheKeyResolver.CACHE_KEY_PREFIX);
        assertThat(actual.split(CacheKeyResolver.DELEMITER)[1]).isEqualTo(keyword);
        assertThat(actual.split(CacheKeyResolver.DELEMITER)[2]).isEqualTo(String.valueOf(page));
        assertThat(actual.split(CacheKeyResolver.DELEMITER)[3]).isEqualTo(String.valueOf(size));

    }
}