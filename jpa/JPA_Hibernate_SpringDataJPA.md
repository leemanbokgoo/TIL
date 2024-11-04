
# JPA
- jpa는 기술 명세다.
- Java Presistence API의 약자로 **자바 어플리케이션 관계형 데이터베이스를 사용하는 방식을 정의한 인터페이스**.
- 여기서 중요하게 여겨져야할 부분은 JPA는 말그대로 **인터페이스**라는 점이다. JPA는 특정 기능을 하는 라이브러리가 아니다.
- 일반적인 백엔드 API가 클라이언트가 어떻게 서버를 사용해야하ㅡㄴ 지를 정의한 것처럼, JPA 역시 자바 어플리케이션에서 관계형 데이터 베이스를 어떻게 사용해야하는 지를 정의하는 한 방법일 뿐.

- JPA는 단순 명세이기때문에 구현이 없다.
- JPA를 구현한 javax.persistence 패키지 대부분은 interface , enum, Exception 그리고 각종 Annotation으로 이루어져있다. 예를 들어, JPA의 핵심이 되는 EntityManager는 아래와 같이 javax.persistence.EntityManager 라는 파일에 interface로 정의되어있다. 

# Hibernate는 JPA의 구현체이다
- Hibernate는 **JPA라는 명세의 구현체**. 즉 , 위에서 언급한 javax.persistnece.EntityManager와 같은 인터페이스를 직접 구현한 라이브러리다. JPA와 Hibernate는 마치 자바의 interface와 해당 interface를 구현한 class와 같은 관계.
![image](https://github.com/user-attachments/assets/9379fa94-efa6-4a75-b0ed-c78b309e8fe4)
- 위 그림은 JPA와 Hivernate의 상속및 구현 관계를 나타낸 것이다.  JPA의 핵심인 EntityManagerFactory, EntityManager, EntityTransaction을 Hibernate에서는 각각 SessionFactory, Session, Transaction으로 상속받고 각각 Impl로 구현하고 있음을 확인할 수 있다.
- Hibernate는 JPA의 구현체이다.로 도출되는 중요한 결론 중 하나는 **JPA를 사용하기 위해서 반드시 Hibernate를 사용할 필요가 없다.**는 것이다. Hibernate의 작동 방식이 마음에 들지않는다면 언제든지 DataNucleus, EclipseLink 등 다른 JPA 구현체를 사용해도 되고, 심지어 본인이 직접 JPA를 구현해서 사용할 수도 있다. 다만 그렇게 하지 않는 이유는 단지 Hibernate가 굉장히 성숙한 라이브러리이기 때문일 뿐이다.

### 장점
- 생산성
    - Hibernate는 SQL을 직접 사용하지않고 메소드 호출만으로 query가 수행된다.
    - 즉 반복적인 SQL 작업과 CRUD 작업을 직접 하지않으므로 생산성이 매우 높아진다.
- 유지보수 
    - 테이블 컬럼이 변경되었을 경우 Mybatis에서는 관련 DAO의 파라미터, 결과, SQL등을 모두 확인하여 수정해야하지만, JPA는 JPA가 이런 일들을 대신해주기때문에 유지보수 측면에서 좋다
- 객체 지향적 개발
    - 객체지향적으로 데이터를 관리할 수 있기때문에 비즈니스 로직에 집중 할 수 있다
    - 로직을 쿼리에 집중하기보다 ㅋ객체 자체에 집중 할수 있다
- 특정 벤더에 종속적이지않다
    - 여러 DB벤더 (MySQL, ORACLE 등)마다 SQL 사용이 조금씩 다르기때문에 어플리케이션 개발 시 처음 선택한 DB를 바꾸는 건 매우 어렵다. 하지만 JPA는 추상화된 데이터 접근 계층을 제공하기때문에 특정 벤더에 종속적이지않다.
        - 설정 파일에서 JPA에게 어떤 DB를 사용하고 있는지 알려주기만 하면 얼마든지 DB를 변경할 수 있다.
### 단점
- 높은 학습 곡선
    - 많은 내용이 감싸져있기때문에 JPA를 잘 사용하기위해서는 알아야할 것이 많다.
    - 잘 이해하고 사용하지않으면 데이터 손실이 있을 수 있다.
- 성능
    - 메소드 호출로 쿼리를 실행하는 것은 내부적으로 많은 동작이 있다는 것을 의미하므로 직접 SQL를 호출하는 것보다 성능이 떨어질 수 있다.
    - 실제로 초기 ORM은 쿼리가 제대로 수행되지않았고 성능도 좋지못했다고 한다. 
        - 그러나 현재는 많이 발전하고 있고 좋은 성능을 보여주고있음.
- 세밀함이 떨어짐 
    - 메소드 호출로 SQL을 실행하기때문에 세밀함이 떨어진다. 또한 객체간의 매핑(Entity Mapping)이 잘못되거나 JPA를 잘못 사용하여 의도하지않은 동작을 할 수도있다.
    - 복잡한 통계 분석 쿼리를 메소드 호출로 처리하는 것은 힘들다
        - 이것을 보완하기위해 JPA에서는 SQL과 유사한 기술인 JPQL을 지원한다.
        - SQL 자체 쿼리를 작성할 수 있도록 지원하고 있다.

# Spring Data JPA는 JPA를 쓰기 편하게 만들어 놓은 모듈이다.
- DB에 접근할 필요가 있는 대부분의 상황에서는 Repository를 정의하여 사용한다. 이 Repository가 바로 Spring Data JPA의 핵심이다.
- Spring Data JPA는 Spring에서 제공하는 모듈 중 하나로, 개발자가 JPA를 더 쉽고 편하게 사용할 수 있도록 도와준다. 이는 JPA를 한 단계 추상화시킨 Repository라는 인터페이스를 제공함으로써 이루어진다. 사용자가 Repository 인터페이스에 정해진 규칙대로 메소드를 입력하면, Spring이 알아서 해당 메소드 이름에 적합한 쿼리를 날리는 구현체를 만들어서 Bean으로 등록해준다.
- Spring Data JPA가 JPA를 추상화했다는 말은, Spring Data JPA의 Repository의 구현에서 JPA를 사용하고 있다는 것이다. 예를 들어, Repository 인터페이스의 기본 구현체인 SimpleJpaRepository의 코드를 보면 아래와 같이 내부적으로 EntityManager을 사용하고 있는 것을 볼 수 있다.
![image](https://github.com/user-attachments/assets/34d5a9cd-8227-4932-a7df-e8514ebef929)

참고링크 

https://suhwan.dev/2019/02/24/jpa-vs-hibernate-vs-spring-data-jpa/
https://dev-coco.tistory.com/74

https://dev-coco.tistory.com/74