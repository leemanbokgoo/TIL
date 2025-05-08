# @Profile

## Profile이 필요한 이유
- 실제 회사(현업)에서 개발을 할 때엔 N개의 Profile을 설정한다. (ex. local, dev, test, prod 등) Profile을 나누는 이유는 환경별로 설정해야 하는 Property 값들이 다르기 때문이다. 더불어 만약 환경별로 로깅 레벨을 다르게 두고 싶을 때 profile을 사용하여 손쉽게 애플리케이션 동작을 조정할 수 있기에 profile을 알아둘 필요가 있다.

## Spring Profiles
- Spring Profiles는 애플리케이션 구성의 일부를 분리하고 특정 환경에서만 사용할 수 있도록 방법을 제공한다.  예를 들어, 개발 환경에서만 사용할 수 있는 Bean이 있고, 프로덕션용 Bean이 있을 수 있다.  Profile을 사용하면 특정 환경(ex. dev, test, prod 등)에만 적용되는 Bean을 정의하여 현재 환경에 따라 적절한 구성 요소만 로드되도록 할 수 있다. 

## Profile의 주요 개념.
### 환경별 설정 분리
- 개발(Development), 테스트(Test), 운영(Production) 등 서로 다른 실행 환경에 따라 설정값을 분리하고, 특정 환경에 맞는 설정을 쉽게 적용할 수 있다.

### 설정 파일 관리
- application.properties 또는 application.yml 파일에서 환경별 설정을 작성할 수 있으며, 프로파일별로 파일을 분리해 관리할 수 있다.

### 유연한 실행
- spring.profiles.active 속성을 통해 실행 시 활성화할 프로파일을 지정할 수 있다.


### Profile 사용 방법

### application.properties에서 지정
- spring.profiles.active=dev

### JVM 옵션으로 지정
- java -jar -Dspring.profiles.active=prod myapp.jar

### 환경 변수로 지정
- export SPRING_PROFILES_ACTIVE=dev

### IDE에서 지정
- 실행 설정에서 -Dspring.profile.active=dev를 추가합니다.

### 프로파일별 Bean 정의
- Spring에서는 프로파일별로 Bean을 다르게 설정할 수 있다.

```
@Configuration
public class DataSourceConfig {
    
    @Bean
    @Profile("dev")
    public DataSource devDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/dev_db")
                .username("dev_user")
                .password("dev-password")
                .build();
    }
    
    @Bean
    @Profile("prod")
    public DataSource prodDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc://prod-db.example.com:3306/prod_db")
                .username("prod-user")
                .password("prod-password")
                .build();
    }
}

```

### Profile 사용 예시
- 다양한 환경(dev, test, prod 등 환경을 위한 다양한 데이터베이스 구성)에 적용해야 하는 구성을 구별해야할 때 
    - application-local.yml, application-prod.yml 2개의 파일이 있을 때
- JVM 시스템 속성 사용 (Dspring.profiles.active)
    - 애플리케이션을 실행할 때 -D 옵션을 사용하여 활성화할 profile을 지정
    - Dspring.profiles.active은 애플리케이션 context에 대해 "활성"으로 인식해야하는 Spring profile을 지정하는 JVM 시스템 속성이다. 이 속성을 설정하는 것은 특정 profile을 활성화하는 방법 중 하나다. 
    - java -Dspring.profiles.active=prod -jar my-application.jar
    - 위 예시 명령은 "prod" profile을 활성화하고 @Profile("prod") 주석이 표시된 Bean 및 구성만 로드된다. 
    - 즉, Spring profile과 Dspring.profiles.active 시스템 속성은 코드를 변경하지 않고도 애플리케이션이 다양한 환경에서 작동하는 방식을 제어할 수 있는 방법을 제공한다. 
    - 이러한 기능을 활용하면 개발, 테스트, 준비 및 프로덕션 환경에 맞게 애플리케이션 동작을 조정할 수 있다.
- 개발 환경에서는 DEBUG 레벨의 상세 로그를 출력하고, 운영 환경에서는 ERROR 수준의 로그만 출력할 수 있다.
-  API 키나 민감 정보 관리하기 위해 사용한다. 테스트 환경에서는 Mock API 키를 사용하고, 운영 환경에서는 실제 API 키를 적용하는 식이다.
-  서버 포트 변경을 위해 사용한다. 각 환경에 따라 애플리케이션의 실행 포트를 다르게 설정.

## Profile의 장점.
### 유지보수성 향상
- 설정 파일을 환경별로 분리해 코드와 설정 간의 의존도를 줄이고, 변경 사항을 쉽게 관리할 수 있다.

### 배포 자동화에 유리
- CI/CD 파이프라인에서 환경에 맞는 프로파일을 활성화하여 배포를 자동화할 수 있다.

### 안정성
- 환경별로 설정을 격리함으로써 잘못된 설정(예: 운영 DB를 개발 환경에서 접근하는 문제)을 방지할 수 있다.

## @ActiveProfiles
- @ActiveProfiles 어노테이션은 스프링 테스트가 실행될 때 활성화되어야 하는 프로필을 지정하는데 사용된다. 이를 통해 기본 애플리케이션 구성을 수정하지 않고도 다양한 구성과 종속성을 테스트할 수 있다.
- 현재의 실행 프로파일(환경)을 지정하도록 도와주는 어노테이션으로 내가 원하는 환경을 선택하여 실행하는 것이다. 

```
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class MyTest {
    // test code goes here
}

```

## @ActiveProfiles와 @Profile의 차이 
- @Profile은 특정 프로파일에서만 빈을 등록하고 싶을 때 사용한다. 적용 대상은 클래스 레벨 (주로 @Component, @Configuration 등과 함께 사용)이고 @ActiveProfiles는 테스트 클래스에서 어떤 프로파일을 활성화할지 지정한다. 적용 대상은 테스트 클래스 레벨이다.
- @Profile과 @ActiveProfiles는 Spring에서 환경별 구성을 분리할 때 사용하는 어노테이션이다. 먼저 @Profile은 특정 프로파일이 활성화되어 있을 때만 해당 빈이 등록되도록 제어할 때 사용한다. 주로 클래스 레벨에 사용하며, 예를 들어 @Profile("dev")가 붙은 클래스는 dev 프로파일이 활성화된 경우에만 빈으로 등록된다. 이렇게 하면 개발, 운영 환경에 따라 서로 다른 구현체를 등록할 수 있어 유연한 환경 구성이 가능하다.
- 반면에 @ActiveProfiles는 테스트 클래스에서 사용되며, 테스트 실행 시 어떤 프로파일을 활성화할지를 지정한다. 예를 들어 @ActiveProfiles("dev")가 붙은 테스트 클래스는 테스트 실행 시 dev 프로파일이 활성화되어, 해당 프로파일 조건을 만족하는 빈들만 등록되고 테스트에 사용된다. 즉, @Profile은 빈 등록 조건을 제어하고, @ActiveProfiles는 테스트 환경에서 어떤 프로파일을 사용할지 지정하는 차이가 있다.


---

참고링크 

https://k9want.tistory.com/entry/Spring-Profile-%EC%84%A4%EC%A0%95-feat-Dspringprofilesactive

https://www.devkobe24.com/Spring/2024-11-20-what-is-profile.html
