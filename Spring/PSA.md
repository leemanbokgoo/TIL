## PSA(Portable Service Abstraction)란?
- Spring은 Spring Triangle이라고 부르는 세가지 개념을 제공한다. 각각 IOC, AOP, PSA를 말한다.
- PSA란 환경의 변화와 관계없이 일관된 방식의 기술로의 접근 환경을 제공하는 추상화 구조를 말한다. 특정 클래스가 추상화된 상위 클래스를 일관되게 바라보며 하위 클래스의 기능을 사용하는 것을 PSA의 기본 개념이다. 따라서 PSA가 적용된 코드는 개발자의 기존에 작성된 코드를 수정하지 않으면서 확장할 수 있으며, 어느 특정 기술에 특화되어 있지 않는 코드이다.
- Spring에서 동작할 수 있는 라이브러리들은 POJO 원칙을 지키기 위해 PSA 형태의 추상화가 되어있으며, Spring Web MVC, Spring Transaction, Spring Cache, Sprind Data, 메일 서비스 등의 다양한 PSA를 제공하고 있다.
- PSA는 코어 기술 위에 한 단계 추상화된 구조로 되어있다. 이는 개발자가 특정 기술에 종속되지 않고, 쉽게 전환할 수 있게 해준다. 예를 들어, JPA로 만든 코드를 JDBC로 변경해도 큰 변경 없이 사용할 수 있다.
- 스프링 삼각형의 마지막 요소인 PSA(Portable Service Abstractions)는 일관성 있는 서비스 추상화를 뜻한다. 여기서 추상화란 하위 시스템의 공통점을 뽑아내서 분리시키는 것을 말한다. 이를 통해 하위 시스템을 알지 못하거나 변경이 있더라도 일관된 방식으로 접근할 수 있게 한다. 뿐만 아니라 서비스 추상화를 함으로써 '단일 책임 원칙'을 준수하며 코드가 간결해지고 작업 목적이 분명하게 드러나 객체 지향적인 코드를 작성할 수 있다.

 
## 서비스에 적용하는 PSA 기법
![Image](https://github.com/user-attachments/assets/cff692cf-5914-4f85-808c-46e39ba4802e)

- 서비스 추상화(Service Abstraction)는 추상화의 개념을 애플리케이션에서 사용하는 서비스에 적용하는 기법이다.
- 위 그림은 Java 콘솔 애플리케이션에서 클라이언트가 데이터베이스에 연결하기 위해 JdbcConnector를 사용하기 위한 서비스 추상화의 다이어그램이다.즉, JdbcConnector 인터페이스가 애플리케이션에서 이용하는 하나의 서비스가 되는 것이다.
- 위 그림에서의 DbClient 클래스는 OracleJdbcConnector, MariaDBJdbcConnector, SQLiteJdbcConnector와 같은 구현체에 직접적으로 연결해서 얻는 것이 아닌 JdbcConnector 인터페이스를 통해 간접적으로 연결되어 Connection 객체를 얻을 수 있게 된다. 또한, DbClient 클래스에서 JdbcConnector 구현체를 사용하더라도 Connection을 얻는 방식은 getConnection() 메서드로 다른 구현체와 동일하다. 즉, 일관된 방식으로 해당 서비스의 기능을 사용할 수 있는 것이다.
- 이처럼 애플리케이션에서 특정 서비스를 이용할 때, 서비스의 기능을 접근하는 방식 자체를 일관되게 유지하면서 기술 자체를 유연하게 사용할 수 있도록 하는 것을 PSA(일관된 서비스 추상화)라고 한다.
 
## PSA가 필요한 이유
- PSA는 어떤 서비스를 이용하기 위한 접근 방식을 일관된 방식으로 유지하여 애플리케이션에서 사용하는 기술이 변경되더라도 최소한의 변경만으로 변경된 요구 사항을 반영하기 위해 사용한다. 즉, PSA를 통해서 애플리케이션의 요구 사항 변경에 유연하게 대처할 수 있다.
- Spring은 상황에 따라 기술이 바뀌더라도 변경된 기술에 일관된 방식으로 접근할 수 있는 PSA를 지원하고 있다. Spring Web MVC, Spring Transaction, Spring Cache, Spring Data, 메일 서비스 등이 있다.

## Spring PSA의 원리
- Spring PSA는 추상화 계층을 추가해 서비스를 추상화하고 여러 서비스를 비즈니스 로직을 수정하지 않고 교체할 수 있도록 하는 것을 의미한다. 즉, 추상화 계층의 핵심사항이다.

![Image](https://github.com/user-attachments/assets/d89ca634-fb81-490f-aa09-02a6d3472f80)

### @Transactional
```
public interface PlatformTransactionManager extends TransactionManager {

  TransactionStatus getTransaction(@Nullable TransactionDefinition definition) throws TransactionException;

  void commit(TransactionStatus status) throws TransactionException;

  void rollback(TransactionStatus status) throws TransactionException;
}

```
- 추상화 계층의 인터페이스인 PlatformTransactionManager 인터페이스를 두고 이를 구현하는 다양한 서비스의 비즈니스 로직을 추상화 해두었다. 개발자는 단순히 PlatformTransactionManager를 선언해서 이용하면 되고 언제든지 이를 구현하는 다른 구현체로 변경할 수 있다.
- 예를 들어 JDBC를 사용하는 DatasourceTransactionManager, JPA를 사용하는 JPATransactionManager, Hibernate를 사용하는 HibernateTransactionManager를 유연하게 바꿔서 사용할 수 있다.

### Spring Web MVC
- Spring Web MVC에서의 PSA도 살펴보자.일반 클래스에 @Controller 애노테이션을 사용하면 요청을 매핑할 수 있는 컨트롤러 역할을 수행하는 클래스가 된다.
```
@RequiredArgsConstructor
@Controller
public class PostsController {
    private final PostsService postsService;
    @GetMapping("/")
    public String list(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum
    , Pageable pageable, @LoginUser UsersDto.Response user){
        .. 생략
    }
    ... 생략
    }
```
- Spring Web MVC를 사용하면 이렇듯 서블릿을 간편하게 개발할 수 있는데, 그 이유는 뒷단에 Spring이 제공해주는 여러 기능들이 숨겨져 있기 때문이다. 직접 HttpServlet을 상속받고 doGet(), doPost()를 구현하는 등의 작업을 직접해주는 않아도 된다는 것이다. 이러한 이유는 서비스 추상화에 있다. 서비스 추상화의 목적 중 하나가 이러한 편의성을 제공하는 것이다.
- 또한, Spring Web MVC는 코드를 거의 바꾸지 않고도 톰캣이 아닌 다른 기술 스택으로 실행할 수 있다. 예를 들어 spring-boot-stater-web 의존성 대신 spring-boot-stater-webflux 의존성을 받도록 바꿔주기만 하면 톰캣이 아닌 netty 기반으로 실행할 수 있다.
- 이렇듯 애노테이션과 여러가지 복잡한 인터페이스들 그리고 기술들을 기반으로 사용자가 기존의 코드를 거의 변경하지 않고, 웹기술 스택을 간편하게 바꿀 수 있도록 한다.


### 질문
#### PSA가 개발자의 코드 유지보수성과 확장성에 미치는 영향은 무엇인가요?
- PSA는 특정 기술에 종속되지 않고 일관된 방식으로 서비스에 접근할 수 있도록 하기 때문에, 기존 코드를 최소한의 변경으로 유지하면서도 새로운 기술을 쉽게 적용할 수 있습니다. 이를 통해 코드의 유지보수성과 확장성이 크게 향상됩니다.

#### Spring Web MVC에서 PSA가 적용된 대표적인 예시는 무엇이며, 그로 인해 얻는 이점은 무엇인가요?
- Spring Web MVC에서는 @Controller 애노테이션을 사용하여 서블릿을 직접 구현하지 않고도 컨트롤러를 만들 수 있습니다. 이는 Spring이 내부적으로 서블릿 관련 기능을 추상화해 제공하기 때문입니다. 이를 통해 개발자는 코드 변경 없이도 톰캣이 아닌 Netty 기반의 WebFlux로 쉽게 전환할 수 있는 유연성을 갖게 됩니다.

--- 

참고링크 

https://ittrue.tistory.com/214

https://memodayoungee.tistory.com/137

https://shinsunyoung.tistory.com/133