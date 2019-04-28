# Location Search Service (LSS)

![build](https://img.shields.io/circleci/project/github/not-for-me/location-search.svg)

## How to run
T.B.D.

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





