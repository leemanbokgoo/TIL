# Application.properties
- Key-Value 쌍으로 구성된, properties 파일이다. 각 설정은 새로운 라인에 위치하고, = 기호를 사용해 키와 값을 구분한다.
- 전통적이고 단순한 형식으로 구성된다. 각 설정이 별도의 라인에 위치해 있어, 특히 작은 규모의 프로젝트에서 가독성이 좋다. 이 형식은 직관적이며, 설정을 쉽게 파악할 수 있게 해준다.
- 단순하고 평면적인 구조로 인해 새로운 설정을 추가하거나 기존 설정을 변경하기가 상대적으로 쉽다. 초보자들이 접근하기에도 부담이 적으며, 복잡한 구문을 기억할 필요가 없다.
- 평면적 구조로, 계층적인 표현에는 한계가 있다. 단순한 설정 관리에는 유리하지만, 더 복잡한 구조를 표현하는 데에는 제한적이다.

## 특징
- 단순성: 구조가 매우 간단해서 작고 단순한 애플리케이션에 적합하다.
- 평면 구조: 계층적 구조를 지원하지 않아 복잡한 구성을 표현하기 어렵다.
- 주석 사용: #을 이용해 주석을 추가할 수 있어, 설정에 대한 설명을 넣기 쉽다.

## 사용 사례 
- 간단한 어플리케이션의 경우 : application.properties는 간결하고 직관적인 구조로 되어있어 작은 규모의 어플리케이션에 적합하다. 이 파일 형식은 심플한 설정이 필요할 때 가장 좋은 선택이 될 수 있다.
- 구성이 자주 바뀌지 않는 환경 : 설정이 복잡하지 않고 변경의 빈도가 적은 경우, application.properties의 평면적인 구조가 유지 관리에 용이하다. 새로운 설정 추가나 기존 설정 변경이 간단해지므로, 빠른 업데이트가 필요 없는 환경에서 유용하다.
- 계층적 구조가 필요 없는 경우 : 프로젝트가 단순하고 설정 요소가 많지 않다면, 계층적 구조의 이점이 덜 중요해진다. 이런 상황에서는 application.properties의 단순한 구조가 더 효과적일 수 있다.

# application.yml
- YAML은 "YAML Ain't Markup Language"의 약자로, 데이터를 계층적으로 표현하는 데 유용한 데이터 직렬화 언어이다.
- 계층적인 구조를 갖고 있어, 설정 간의 관계가 명확하게 드러난다. 들여 쓰기와 구조화된 형식은 복잡한 설정의 상호 관계를 한눈에 이해하기 용이하게 만들어준다.
- 계층 구조와 들여쓰기는 익숙해져야 하는 부분이지만, 한 번 익숙해지면 복잡한 구성을 효과적으로 관리할 수 있다. 들여쓰기 오류에 주의해야 하지만, 익숙해진다면 복잡한 설정도 쉽게 편집할 수 있다.
- 계층적 구조를 지원하며, 이를 통해 복잡한 설정을 명확하고 구조적으로 표현할 수 있다. 특히, 큰 규모의 프로젝트나 다양한 환경 설정이 필요한 경우에 효과적이다.

## yml
- yaml은 xml과 json 포맷과 같이 타 시스템 간에 데이터를 주고받을 때 약속된 포맷(규칙)이 정의되어있는 또 하나의 파일 형식이다. 다만 좀더 인간 친화적으로 작성해 가독성을 높이는 쪽으로 무게를 두어 고급 컴퓨터 언어에 친화적이다. json과 달리 주석도 쓸수 있으며, 위의 사진과 같이 간결한 문법으로 같은 데이터량이라도 코드길이를 많이 줄일수 있다. yaml은 주로 Doker Compose, Kubernetes, Flutter, Spring boot 프로젝트에서 설정파일을 정의할때 자주 애용된다.
			
## 특징
- 계층적 구조: 설정이 계층적으로 구성되어 있어, 복잡한 설정을 더욱 명확하게 표현할 수 있다.
- 가독성: 들여 쓰기를 이용해 구조를 나타내므로, 보다 직관적으로 이해할 수 있다.
- 다양성: 데이터 타입을 다양하게 표현할 수 있어, 리스트나 객체 등 복잡한 데이터 구조도 쉽게 표현할 수 있다.

## 장점
- 가독성 : YAML은 계층 구조를 들여쓰기로 표현하기 때문에, 구조가 복잡한 설정을 작성할 때 더 명확하고 보기 쉽다.
-  중첩 구조 표현에 용이 : List, Map 같은 복잡한 구조를 다룰 때 YAML이 훨씬 직관적임.
- 다중 프로파일 설정이 간결함 : 하나의 yml 파일 안에서 여러 프로파일(dev, prod 등)을 구분해서 정의할 수 있음.
- 유지보수 : 구조적으로 관련 설정을 한눈에 파악할 수 있고 모듈별로 묶어서 정리할 수 있다. 구조화된 표현을 통해 가독성, 논리적 정리, 실수 방지, 환경 구분, 높은 가독성으로 인해 유지보수 시 편리하다. 

## 사용 사례 
- 복잡한 구성이 필요한 애플리케이션 : 여러 모듈이나 복잡한 설정을 가진 어플리케이션에서 application.yml은 그 구조를 명확하게 표현하는 데 도움을 준다. YAML 파일의 계층적 구조는 복잡한 설정을 쉽게 관리하고 이해하는 데 유리하다.
- 다양한 환경에서의 설정 관리 : 프로덕션, 개발, 테스트 등 다양한 환경에 대한 설정을 효과적으로 구분하고 관리해야 할 때, YAML의 계층적 구조가 큰 장점을 제공한다. 각 환경에 맞는 설정을 분리하여 관리할 수 있어, 보다 유연한 환경 설정이 가능하다.
- 계층적이고 구조화된 설정이 필요한 경우 : 복잡한 프로젝트 구조와 여러 설정이 필요한 경우, application.yml의 계층적 구조는 이를 체계적으로 조직하는 데 큰 도움이 된다. 구성 요소 간의 관계를 명확하게 나타내고, 설정을 더 직관적으로 표현할 수 있다.


## properties와 yml 비교 
- 여러 개의 데이터베이스, Redis, Kafka, 외부 API 설정이 있는 프로젝트를 application.yml과 application.properties로 구성한 예시는 다음과 같다. 

### application.yml
```
spring:
  datasource:
    primary:
      url: jdbc:mysql://localhost:3306/main_db
      username: main_user
      password: main_pass
    secondary:
      url: jdbc:mysql://localhost:3306/log_db
      username: log_user
      password: log_pass

  redis:
    host: localhost
    port: 6379
    timeout: 2000

kafka:
  bootstrap-servers: localhost:9092
  consumer:
    group-id: my-service-group
    auto-offset-reset: earliest

external:
  api:
    kakao:
      base-url: https://api.kakao.com
      key: abc123
    naver:
      base-url: https://openapi.naver.com
      client-id: naver-client-id
      client-secret: naver-secret

```

### application.properties로 구성했을 경우

```
spring.datasource.primary.url=jdbc:mysql://localhost:3306/main_db
spring.datasource.primary.username=main_user
spring.datasource.primary.password=main_pass

spring.datasource.secondary.url=jdbc:mysql://localhost:3306/log_db
spring.datasource.secondary.username=log_user
spring.datasource.secondary.password=log_pass

spring.redis.host=localhost
spring.redis.port=6379
spring.redis.timeout=2000

kafka.bootstrap-servers=localhost:9092
kafka.consumer.group-id=my-service-group
kafka.consumer.auto-offset-reset=earliest

external.api.kakao.base-url=https://api.kakao.com
external.api.kakao.key=abc123
external.api.naver.base-url=https://openapi.naver.com
external.api.naver.client-id=naver-client-id
external.api.naver.client-secret=naver-secret

```
-  application.properties는 설정 하나하나가 단일한 키-값으로 표현돼서 단순하고 직관적이다. 그래서 설정이 많지 않거나 구조가 단순한 프로젝트에서는 실수도 적고 디버깅도 쉽고 Git에서도 변경 이력이 한 줄씩 명확하게 나오니까 변경사항 추적도 깔끔하고, 협업 시 충돌도 적다.
- 반면에 application.yml은 들여쓰기로 계층 구조를 표현할 수 있어서, 복잡한 설정들을 논리적으로 그룹화하고 보기 좋게 정리할 수 있다. 위의 예시처럼 여러 개의 데이터베이스 설정이나 Kafka, Redis, 다양한 외부 API 설정 같은 걸 다룰 때, 관련된 값들을 한 블록 안에 모아놓을 수 있어서 설정 파일의 구조를 한눈에 파악하기 좋다. 유지보수할 때도 관련 된 설정들이 모여있어 설정을 찾기 쉽다.


### 질문
#### application.yml이 application.properties보다 유지보수에 더 적합하다고 평가받는 이유는 무엇인가요?
- application.yml은 계층적 구조를 지원하기 때문에, 설정 항목들을 논리적으로 그룹화하여 관련 설정을 한눈에 파악할 수 있습니다. 이로 인해 설정이 복잡하거나 다양한 환경별 설정이 필요한 프로젝트에서 가독성과 구조화 측면에서 유지보수가 훨씬 수월해집니다. 특히 여러 데이터베이스, Redis, Kafka, 외부 API 설정 등이 동시에 존재할 때 설정 항목을 구조적으로 묶어 관리할 수 있어, 설정 변경 시 오류를 줄이고 이해도를 높이는 데 유리합니다.

####  질문 2
application.properties가 yml보다 더 유리한 상황은 어떤 경우인가요?
- application.properties는 키-값 기반의 평면적 구조로 되어 있어 문법이 단순하고 직관적입니다. 따라서 설정이 단순하거나, 설정 항목이 적고 변경 빈도가 낮은 소규모 프로젝트에서 사용하기에 적합합니다. 또한 Git을 통해 변경 이력을 확인할 때, 각 설정이 한 줄씩 변경되므로 이력 추적이 용이하며, 협업 시 충돌 가능성도 줄일 수 있어 안정적인 유지보수가 가능합니다.


----

참고링크 

https://curiousjinan.tistory.com/entry/spring-boot-yml-vs-properties

https://inpa.tistory.com/entry/YAML-%F0%9F%93%9A-yaml-%EA%B0%9C%EB%85%90-%EB%AC%B8%EB%B2%95-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0-%F0%9F%92%AF-%EC%B4%9D%EC%A0%95%EB%A6%AC