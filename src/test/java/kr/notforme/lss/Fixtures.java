package kr.notforme.lss;

import org.apache.commons.lang3.RandomStringUtils;

import kr.notforme.lss.business.model.user.User;
import kr.notforme.lss.business.model.user.UserRegistration;
import kr.notforme.lss.business.repository.place.KakaoPlaceSearchRepository.KakaoPlaceSearchResult;

public class Fixtures {
    public static User generateUser() {
        return generateUser(RandomStringUtils.randomAlphabetic(User.MAX_ID_LEN));
    }

    public static User generateUser(String id) {
        User user = new User();
        user.setId(id);
        user.setPassword(RandomStringUtils.randomAlphanumeric(User.MAX_PASSWORD_LEN));

       return  user;
    }

    public static UserRegistration generateUserRegistration() {
        String id = RandomStringUtils.randomAlphabetic(User.MAX_ID_LEN);
        String password = RandomStringUtils.randomAlphanumeric(User.MAX_PASSWORD_LEN);
        return generateUserRegistration(id, password);
    }

    public static UserRegistration generateUserRegistration(String id, String password) {
       return new UserRegistration(id, password);
    }


    public static KakaoPlaceSearchResult generateKakaoPlaceSearchResult() {
        KakaoPlaceSearchResult fixture = new KakaoPlaceSearchResult();
        fixture.setPlaceName(RandomStringUtils.randomAlphanumeric(8));
        fixture.setPlaceUrl(RandomStringUtils.randomAlphanumeric(24));
        fixture.setAddressName(RandomStringUtils.randomAlphanumeric(24));
        fixture.setRoadAddressName(RandomStringUtils.randomAlphanumeric(24));
        fixture.setPhone(RandomStringUtils.randomNumeric(11));

        return fixture;
    }


}
