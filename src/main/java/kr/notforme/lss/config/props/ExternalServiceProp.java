package kr.notforme.lss.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("external")
public class ExternalServiceProp {
    private LocalPlaceSearch localPlaceSearch;

    public LocalPlaceSearch getLocalPlaceSearch() {
        return localPlaceSearch;
    }

    public void setLocalPlaceSearch(LocalPlaceSearch localPlaceSearch) {
        this.localPlaceSearch = localPlaceSearch;
    }

    public static class LocalPlaceSearch {
        private ExternalService kakao;

        public ExternalService getKakao() {
            return kakao;
        }

        public void setKakao(ExternalService kakao) {
            this.kakao = kakao;
        }
    }

    public static class ExternalService {
        private String host;
        private String uri;
        private String token;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
