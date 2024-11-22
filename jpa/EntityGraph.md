# @EntityGraph
- 연관관계가 있는 엔티티를 조회할 경우, 지연로딩으로 설정되어있다면 연관관계에서 종속된 엔티티는 쿼리 실행시 select 쿼리 대신 proxy 객체를 만들어 엔티티가 적용시킨다.
- 쿼리 실행시 select 쿼리 대신 proxy 객체를 만들어 엔티티가 적용시킨다.
- 그후 해당하는 proxy 객체가 호출 될때마다 select 쿼리가 전송된다.
- @EntityGraph는 연관관계가 지연로딩을 되어있을떄 fetch조인을 사용하여 여러번의 쿼리를 한번에 해결 할 수 있다는 점에서 fetch-join을 어노테이션을 통해 사용할수 있도록 한 기능이다. (fetch-join은 일반 join 과 달리 연관 엔티티도 함께 영속 상태가 된다.)
- EntityGraph의 경우 fetchType을 eager로 변환하는 방식으로 outer left join을 수행하여 데이터를 가져오지만, fetch join의 경우 따로 outer join으로 명시하지 않는 경우 inner join을 수행한다.
- 이 때문에 fetch join의 단점을 피할 수 있다.(1:N 컬렉션 join시 하나만 join할 수 있는 제약, distinct사용)
- 이렇게 되는 이유는 EntityGraph의 경우 fetchType을 전환하여 조회하는 개념이기 때문.
- JPA가 엔티티를 조회할떄 연관된 엔티티를 명시적으로 가져오는 방식을 정의하는 매커니즘, 이는 JPQL을 작성하지않고도 특정 연관 데이터를 로드하는 방법을 제공함. EntityGraph는 내부적으로 JPA의 쿼리 생성과정에 형향을 미쳐 지연로딩 또는 즉시로딩 대신 지정된 연관 데이터를 명시적으로 로드함.

# @EntityGraph 동작 방식
- 기본적으로 fetchType.Lazy, fetchType.Eager는 static정보로 runtime에 변경할 수 없으며 EntityGraph는 이를 변경할 수 있게 하는 기능으로 fetchType.Lazy로 설정해놓은 경우에 fetchType.Eager로 사용할 수 있다.
- 이 때문에 fetch join에서 1:N연관관계로 조회 할경우 1개의 컬렉션까지밖에 최대 같이 조회 가능한 부분이나, distinct가 필요한 단점을 극복할 수 있다.
- 다만 1:N 연관관계 컬렉션을 같이 조회 해 오는 경우 paging은 fetch join과 동일하게 수행할 수 없음.(수행하면 전체 테이블 join결과를 application으로 가져온 후 memory에서 paging처리를 하기 때문에 메모리 관련 장애가 발생할 수 있다.)

## Entity 로드 시 작동 흐름

#### 1. EntityGraph 정의
- 엔티티 클래스에서 @NamedEntityGraph 또는 코드에서 동적으로 EntityGraph를 설정한다. 이렇게 "어떤 연관 필드를 로드할 것인가?"를 JPA에게 알려주는 역할을 한다.

```
@Entity
@NamedEntityGraph(
    name = "User.withOrders",
    attributeNodes = @NamedAttributeNode("orders")
)
public class User {
    @Id
    private Long id;

    private String name;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;
}
```

#### 2. EntityGraph 사용 요청
- EntityGraph를 사용한 쿼리가 실행되면, JPA는 EntityGraph 정의를 읽고 연관 필드 로딩 전략을 구성.

```
public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(value = "User.withOrders", type = EntityGraph.EntityGraphType.LOAD)
    User findByName(String name);
}
```

#### 3. JPQL 없이 쿼리 생성
- JPA는 EntityGraph 정보를 바탕으로 SQL 쿼리를 생성한다. 예를 들어, 위의 메서드는 다음과 같은 SQL로 변환됨.
```
SELECT u.id, u.name, o.id, o.amount 
FROM User u
LEFT JOIN Order o ON u.id = o.user_id
WHERE u.name = :name
```
- 기본적으로 LEFT JOIN으로 연관 데이터를 가져온다. FetchType.LAZY로 설정된 필드도 명시적으로 로드한다.

#### 4. 영속성 컨텍스트 관리
- 조회된 데이터는 JPA의 영속성 컨텍스트에 저장된다. EntityGraph가 사용된 경우, 지정된 연관 필드만 초기화되고 나머지 필드는 지연 로딩 상태를 유지합니다.

##  EntityGraph 적용 시 유의점
### FetchType과의 조합
- FetchType.LAZY로 설정된 필드도 EntityGraph를 사용하면 즉시 로딩된다.EntityGraphType.LOAD는 Fetch 전략을 덮어쓰지 않지만,EntityGraphType.FETCH는 모든 Fetch 전략을 무시함.

### 중첩 로딩 문제
- 너무 많은 연관 필드를 로드하려고 하면 복잡한 쿼리가 생성되어 성능 문제가 발생할 수 있습니다. 필요한 필드만 로드하도록 신중히 설계해야 합니다.

### JPQL과의 차이
- JPQL의 FETCH JOIN과 유사하지만, 더 선언적이고 재사용성이 높습니다.
JPQL과 함께 사용할 경우 쿼리가 중복될 수 있어 주의해야 합니다.

### 질문
#### 1. EntityGraph의 동작 방식은 무엇인가요?
- EntityGraph는 JPA가 엔티티를 조회할 때 연관된 데이터를 명시적으로 로드할 수 있도록 제공하는 기능입니다. 기본적으로 FetchType.LAZY로 설정된 필드도 EntityGraph를 통해 한 번의 쿼리로 함께 로드되며, 이 과정에서 LEFT JOIN 방식으로 연관 데이터를 가져옵니다. 이는 Fetch 전략을 동적으로 변경하여 JPQL 없이도 특정 연관 데이터를 로드할 수 있게 만듭니다.

### 명시적으로 로드한다는 건 어떤 의미인가요?
- "명시적으로 로드한다"는 것은 JPA가 기본적으로 제공하는 Fetch 전략(FetchType.LAZY 또는 FetchType.EAGER)에 의존하지 않고, 개발자가 EntityGraph를 통해 어떤 연관 필드를 반드시 로드할지를 명확하게 지정하는 것을 의미합니다. 이는 EntityGraph 설정에 따라 필요한 연관 데이터를 한 번의 쿼리로 강제로 가져오게 하며, 기본 Fetch 설정과 무관하게 동작할 수 있도록 합니다.

### EntityGraph를 사용할 때 발생할 수 있는 성능 문제는 무엇인가요?
- EntityGraph를 통해 너무 많은 연관 데이터를 한꺼번에 로드하려고 하면 복잡한 쿼리가 생성되어 성능이 저하될 수 있습니다. 특히, 1:N 관계를 포함한 연관 데이터를 로드할 경우 쿼리 결과가 중복되거나, 페이징이 비효율적으로 수행될 수 있습니다. 이는 필요하지 않은 데이터를 과도하게 로드하거나 메모리 소모를 초래할 수 있으므로 필요한 필드만 신중히 설계해야 합니다.

### 3. EntityGraph와 JPQL의 Fetch Join의 차이는 무엇인가요?
- EntityGraph는 선언적 방식으로 연관 데이터를 로드하도록 설계되어 JPQL의 Fetch Join과 유사한 기능을 제공합니다. 그러나 Fetch Join은 기본적으로 INNER JOIN을 사용하고, 명시적으로 OUTER JOIN을 설정하지 않으면 일부 데이터를 제외할 수 있습니다. 반면, EntityGraph는 FetchType을 EAGER로 전환하여 LEFT JOIN을 사용해 데이터를 가져오므로 Fetch Join의 일부 제약을 피할 수 있습니다.

---

참고링크 

https://velog.io/@xogml951/EntityGraph%EC%99%80-fetch-join-%EC%B0%A8%EC%9D%B4

https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/EntityGraph.html

