# Location Search Service (LSS)

![build](https://img.shields.io/circleci/project/github/not-for-me/location-search.svg)
![coverage](https://img.shields.io/codecov/c/github/not-for-me/location-search-service.svg)

## How to run
* 프로젝트의 실행은 프로젝트 루트 폴더 위치에서 다음 명령을 통해 실행 합니다.
```sh
$ ./gradlew clean bootRun
```
* 서비스는 10080 포트로 서버를 띄우게 됩니다.
* 브라우저에서 접속하면 로그인 화면을 만날 수 있습니다. 
  * 계정 ID/비번: admin/admin
http://localhost:10080

* (토요일 추가작업): 싱글페이지 앱이 추가되어 위에서 `gradlew`로 was 띄울 때 웹앱 기동
* 다음 url로 접속하여 `admin/admin`으로 로그인 후 검색 및 지도 확인가능
```
 http://localhost:10080
```
* API만 호출하기 원할 경우에는 로그인 후에는 다음 2가지 api를 호출 가능
  * 검색 API: http://localhost:10080/places/search?keyword=피자&page=1&size=10
  * 인기 키워드 API: http://localhost:10080/keywords/ranking


## Design 
`LSS`의 전체 서비스 구성은 다음 컴포넌트 다이어그램을 기반으로 구현한다.
![component_diagram](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/not-for-me/location-search-service/master/docs/service-conceptual-component.puml)
위 다이어그램과 설계의 고려사항은 [#8](https://github.com/not-for-me/location-search-service/issues/8#issuecomment-487375863)의 논의를 참고한다.


### Tech Stack
설계안을 바탕으로 `LSS`는 다음과 같은 기술셋을 사용한다.
* `Spring boot`: `Webflux`, `Security`, `JPA`, `Cache` 스타터 사용하여 비즈니스 로직 및 서비스 API 구현
* `Vue.js`: 프론트엔드 웹 애플리케이션 개발
* `H2 DB`: 사용자 정보기 검색 질의 로그 및 인기 검색어 snapshot데이터 저장용 메모리 DB
* 기타 테스팅 도구들: `junit`, `mockito`, `assertj`
  * `assertj`은 `junit`이 지닌 단정문보다 좀 더 `fluent`한 API를 제공하기 때문에 테스트 코드에서 사용한다.

### 라이브러리  사용 목록
* spring-boot-starter-data-jpa: 사용자 정보, 관계형 데이터 저장용 jpa 사용
* spring-boot-starter-security: 신속하게 권한기능 추가할 용도
* spring-boot-starter-cache: 로컬 캐시용
* spring-boot-starter-webflux: 구현 서비스가 외부 api호출이라 non blocking 웹 스택 구축을 위해 사용
* caffeine:2.6.2: 로컬 캐시용 (구 guava 캐시)
* reactor-extra:3.2.2.RELEASE: 리액터 로컬 캐시 도구
* commons-lang3:3.8.1: 가독성 및 개발 편의를 위한 유틸리티 용
* commons-collections4:4.3: 가독성 및 개발 편의를 위한 유틸리티 용
* lombok:1.18.6: 코드 가독성 용도 (운영에선 delombok해도 됨)
* com.h2database:h2: db구현체로 요구사항에 맞춰 메모리 db
* 기타 테스팅 및 툴 지원 도구

