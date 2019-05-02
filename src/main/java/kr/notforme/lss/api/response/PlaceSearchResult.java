package kr.notforme.lss.api.response;

import lombok.Data;

@Data
public class PlaceSearchResult {
    private String placeName;
    private String placeUrl;
    private String mapUrl;
    private String address;
    private String roadAddress;
    private String phone;
}
