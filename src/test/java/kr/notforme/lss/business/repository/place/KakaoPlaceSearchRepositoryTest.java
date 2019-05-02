package kr.notforme.lss.business.repository.place;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import kr.notforme.lss.Fixtures;
import kr.notforme.lss.api.response.PlaceSearchResult;
import kr.notforme.lss.business.repository.place.KakaoPlaceSearchRepository.KakaoPlaceSearchResult;
import kr.notforme.lss.config.props.ExternalServiceProp;
import kr.notforme.lss.config.props.ExternalServiceProp.ExternalService;
import kr.notforme.lss.config.props.ExternalServiceProp.LocalPlaceSearch;

public class KakaoPlaceSearchRepositoryTest {
    private KakaoPlaceSearchRepository sut;

    @Before
    public void setUp() throws Exception {
        ExternalServiceProp fixture =  prepareFixture();
        sut = new KakaoPlaceSearchRepository(fixture);
    }

    private ExternalServiceProp prepareFixture() {
        ExternalService externalService = new ExternalService();
        LocalPlaceSearch localPlaceSearch = new LocalPlaceSearch();
        localPlaceSearch.setKakao(externalService);

        ExternalServiceProp externalServiceProp = new ExternalServiceProp();
        externalServiceProp.setLocalPlaceSearch(localPlaceSearch);

        return externalServiceProp;
    }

    @Test
    public void constructor_given_valid_prop_then_create_it() {
        // Given
        ExternalServiceProp fixture = prepareFixture();

        // When
        sut = new KakaoPlaceSearchRepository(fixture);

        // Then
        assertThat(sut).isNotNull();
    }

    @Test
    public void convert_given_api_result_then_convert_it() {
        // Given
        KakaoPlaceSearchResult fixture = Fixtures.generateKakaoPlaceSearchResult();

        // When
        PlaceSearchResult actual = sut.convert(fixture);

        // Then
        assertThat(actual).isNotNull();
        assertThat(actual.getPlaceName()).isEqualTo(fixture.getPlaceName());
        assertThat(actual.getPlaceUrl()).isEqualTo(fixture.getPlaceUrl());
        assertThat(actual.getAddress()).isEqualTo(fixture.getAddressName());
        assertThat(actual.getRoadAddress()).isEqualTo(fixture.getRoadAddressName());
        assertThat(actual.getPhone()).isEqualTo(fixture.getPhone());
        assertThat(actual.getMapUrl()).startsWith("http://");
    }
}