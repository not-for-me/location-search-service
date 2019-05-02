package kr.notforme.lss.business.service.search;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import kr.notforme.lss.business.repository.search.SearchLogDbRepository;

@RunWith(MockitoJUnitRunner.class)
public class SearchLogWriterServiceTest {
    @InjectMocks
    private SearchLogWriterService sut;

    @Mock
    private SearchLogDbRepository searchLogDbRepository;

    @Test
    public void writeSearchLog_given_valid_keyword_then_save_it() {
        // Given
        final String keyword = "hello";

        // When
        sut.writeSearchLog(keyword);

        // Then
        then(searchLogDbRepository).should().save(any());
    }

    @Test
    public void writeSearchLog_given_empty_or_null_keyword_then_do_nothing() {
       // Given
        String keyword = null; // <--- null

        // When
        sut.writeSearchLog(keyword);

        // Then
        then(searchLogDbRepository).should(never()).save(any());


        // Given
        keyword = ""; // <--- empty

        // When
        sut.writeSearchLog(keyword);

        // Then
        then(searchLogDbRepository).should(never()).save(any());
    }
}