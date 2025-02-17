- 스프링에서 사용되는 Filter, Interceptor, AOP 세 가지 기능은 모두 무슨 행동을 하기전에 먼저 실행하거나, 실행한 후에 추가적인 행동을 할 때 사용되는 기능들이다.예를들어 로그인 관련(세션체크)처리, 권한체크, XSS(Cross site script)방어, pc와 모바일웹의 분기처리, 로그, 페이지 인코딩 변환 등이 있다.

# Filter
- HTTP 요청과 응답을 변경할 수 있는 재사용 가능한 코드이며, 서블릿 2.3 규약에 새롭게 추가되었다
- 필터는 객체의 형태로 존재하며 클라이언트에서 오는 요청(Request)과 최종자원(JSP, 서블릿, 기타 자원) 사이에 위치하여  클라이언트의 요청 정보를 알맞게 변경 할 수 있다. 최종 자원과 클라이언트로 가는 응답(response) 사이에 위치하여 최종 자원의 요청 결과를 알맞게 변경할 수도 있다. 
- 필터는 클라이언트와 자원 사이의 위치하고있다. 실제 자원이 받는 요청 정보는 필터가 변경한 요청 정보가 되며, 또한 클라이언트가 보는 응답 정보는 필터가 변경한 응답 정보가 된다.
- 클라이언트와 자원 사이에 한개의 필터만 존재할 수 있는 것은 아니며 여러개의 필터가 모여 하나의 필터체인을 형성하게 된다.
- Filter에서는 주로 스프링과 무관한 전역적인 요청을 처리한다. Filter는 Spring에 들어오기 전에 실행되며 톰캣과 같은 웹 컨테이너(WAS)에서 처리를 해주게 된다. 그렇기에 Request에 대한 조작이 가능하다. Interceptor와 AOP는 모두 Request의 내용을 가지고 추가적인 작업을 해야 하는 입장이지만, Filter는 그 요청 자체를 조작할 수 있다.

## Filter의 기본 구조

![image](https://github.com/user-attachments/assets/9a033fc8-7f44-4ada-b6ea-013141a1a73d)

- 그림으로 보면 자원이 받게 되는 요청 정보는 클라이언트와 자원 사이에 존재하는 필터에 의해 변경된 요청정보가 되며, 클라이언트가 보게 되는 응답 정보는 클라이언트와 자원사이에 존재하는 필터에 의해 변경된 응답정보가 된다. 필터는 클라이언트와 자원사이에 1개가 존재하는 경우가 보통이지만, 여러개의 필터가 모여서 하나의 체인(chain)을 형성할 수 있다.
- 여러 개의 필터가 모여서 하나의 체인을 형성 할떄 첫번쨰 필터가 변경하는 요청 정보는 클라이언트의 요청 정보가 되지만, 체인의 두번쨰 필터가 변경하는 요청 정보는 첫번쨰 필터를 통해서 변경된 요청 정보가된다. 즉, 요청 정보는 변경에 변경을 거듭하게되는 것.
- 필터는 정보를 변경할 뿐만 아니라 흐름도 변경할 수 있다. 즉, 필터는 클라이언트의 요청을 필터 체인의 다음 단계(결과적으로는 클라이언트가 요청한 자원)에 보내는 것이 아니라 다른 자원의 결과를 클라이언트에 전송할 수도있다.
- 필터의 이런 기능은 사용자 인증이나 권한 검사 같은 기능을 구현할 떄 용이하게 사용할 수 있다.

## 필터의 구현
- javax.servlet.Filter 인터페이스 : 클라이언트와 최종자원 사이에 위치하는 필터를 나타내는 객체가 구현해야하는 인터페이스
- javax.servlet.ServletReuqestWrapper 클래스 : 필터가 요청을 변경한 결괄르 저정하는 래퍼이다.
- javax.servlet.ServletResponseWrapper 클래스 : 필터가 응답을 변경하기위해 사용하는 래퍼 이다.

## Filter 메서드
- 필터를 추가하기 위해서는 javax.servlet의 Filter 인터페이스를 구현(implements)해야 하며 이는 다음의 3가지 메소드를 가지고 있다.

### init 메소드
- init 메소드는 필터 객체를 초기화하고 서비스에 추가하기 위한 메소드이다. 웹 컨테이너가 1회 init 메소드를 호출하여 필터 객체를 초기화하면 이후의 요청들은 doFilter를 통해 처리된다.

### doFilter 메소드
- doFilter 메소드는 url-pattern에 맞는 모든 HTTP 요청이 디스패처 서블릿으로 전달되기 전에 웹 컨테이너에 의해 실행되는 메소드이다. doFilter의 파라미터로는 FilterChain이 있는데, FilterChain의 doFilter 통해 다음 대상으로 요청을 전달하게 된다. chain.doFilter() 전/후에 우리가 필요한 처리 과정을 넣어줌으로써 원하는 처리를 진행할 수 있다.
 
### destroy 메소드
- destroy 메소드는 필터 객체를 서비스에서 제거하고 사용하는 자원을 반환하기 위한 메소드이다. 이는 웹 컨테이너에 의해 1번 호출되며 이후에는 이제 doFilter에 의해 처리되지 않는다.

## Filter 인터페이스
- 필터 인터페이스를 알맞게 구현해줘야함.
    - init() : 필터를 초기화할떄 호출된다
    - doFilter() : 필터기능을 수행한다. chain을 이용해서 체인의 다음 필터로 처리를 전달할 수 있다.
    - destory() : 필터가 웹 컨테이너에 삭제될떄 호출된다.
- 필터의 역할을 하는 메서드가 바로 doFilter() 메서드. 서블릿 컨테이너는 사용자가 특정한 자원을 요청했을때 그 자원 사이에 필터가 존재하는 경우 필터객체의 doFilter() 메서드를 호출된다.이 시점부터 필터를 적용하기 시작한다.

## 요청 및 응답 래퍼 클래스
- 필터가 필터로서의 제 기능을 하려면 클라이언트의 요청을 변경하고 클라이언트로 가는 응답을 변경할 수있어야한다.
- ServletRequestWrapper, ServletResponseWrapper
    - 요청 정보를 변경하여 최종 자원인 서블릿/JSP/HTML기타 자원에 전달한다.
    - 최종자원으로부터의 응답을 변경하여 새로운 응답정보를 클라이언트에 보낸다.
- 서블릿의 요청 래퍼와 응답래퍼를 만들려면 javax.servlet 패키지에 정의되어있는 ServeltRequestWrapper 클래스와 ServletWrapper 클래스 상속 받아서 구현해야한다. 하지만 대부분 필터는 HTTP 프로토콜에 대한 요청과 응답을 필터링하기때문에 이 두 클래스를 상속받아 알맞게 HttpServletRequestWrapper 클래스와 HttpServletResponseWrapper 클래스를 상속받아 구현하는 것이 좋다.
- 필터를 사용하는 방법에는 제한이 없으며 필터의 특징을 잘 활용하느냐에 따라서 필터의 응용범위가 달라질수있다.
    - 사용자 인증, 캐싱필터, 자원접근에 대한 로깅, 응답데이터 변환(HTML변환,응답헤더 변환, 데이터 암호화등), 공통기능 실행 

### Intercepter

![image](https://github.com/user-attachments/assets/0d59bba2-db22-41b7-95ca-894e7665edba)

- 인터셉터(Interceptor)는 J2EE 표준 스펙인 필터(Filter)와 달리 Spring이 제공하는 기술로써, 디스패처 서블릿(Dispatcher Servlet)이 컨트롤러를 호출하기 전과 후에 요청과 응답을 참조하거나 가공할 수 있는 기능을 제공한다. 즉, 웹 컨테이너(서블릿 컨테이너)에서 동작하는 필터와 달리 인터셉터는 스프링 컨텍스트에서 동작을 하는 것이다.
- 디스패처 서블릿은 핸들러 매핑을 통해 적절한 컨트롤러를 찾도록 요청하는데, 그 결과로 실행 체인(HandlerExecutionChain)을 돌려준다. 그래서 이 실행 체인은 1개 이상의 인터셉터가 등록되어 있다면 순차적으로 인터셉터들을 거쳐 컨트롤러가 실행되도록 하고, 인터셉터가 없다면 바로 컨트롤러를 실행한다.
- 인터셉터는 스프링 컨테이너 내에서 동작하므로 필터를 거쳐 프론트 컨트롤러인 디스패처 서블릿이 요청을 받은 이후에 동작하게 되는데, 이러한 호출 순서를 그림으로 표현하면 다음과 같다. (실제로는 Interceptor가 Controller로 요청을 위임하지는 않는다. 위의 그림은 처리 순서를 도식화한 것으로만 이해하면 된다. 
- Interceptor는 스프링의 기술이며 Controller 동작 전 후로 동작한다. Dispatcher Servlet이 Controller를 호출하기 전과 후에 요청과 응답을 가공하기 위해 사용한다. 서버에 들어온 Request 객체를 컨트롤러의 핸들러로 도달하기 전에 낚아채서 부가적인 기능이 실행되게끔 만들어준다. Dispatcher Servlet은 핸들러 매핑을 통해 적절한 컨트롤러를 찾도록 요청하는데, 그 결과로 실행 체인(HandlerExecutionChain)을 반환한다.
- Filter와의 차이점은 Intercepter가 스프링의 기술이기 때문에 스프링에서 관리하는 빈들을 사용할 수 있다. 추가로 Interceptor에서는 filter와는 다르게 트랜잭션과 관련된 처리도 진행할 수 있다.
- 인터셉터는 주로 아래와 같은 작업에 적합하다.
    - 1. 세부적인 보안 및 인증/인가 작업
    - 2. API 호출에 대한 로깅 또는 검증
    - 3. Controller로 넘겨주는 정보의 가공

## 인터셉터 메소드
- 인터셉터를 추가하기 위해서는 org.springframework.web.servlet의 HandlerInterceptor 인터페이스를 구현(implements)해야 하며, 이는 다음의 3가지 메소드를 가지고 있다.

### preHandle 메소드
- preHandle 메소드는 컨트롤러가 호출되기 전에 실행된다. 그렇기 때문에 컨트롤러 이전에 처리해야 하는 전처리 작업이나 요청 정보를 가공하거나 추가하는 경우에 사용할 수 있다. preHandle의 3번째 파라미터인 handler 파라미터는 핸들러 매핑이 찾아준 컨트롤러 빈에 매핑되는 HandlerMethod라는 새로운 타입의 객체로써, @RequestMapping이 붙은 메소드의 정보를 추상화한 객체이다.
- 또한 preHandle의 반환 타입은 boolean인데 반환값이 true이면 다음 단계로 진행이 되지만, false라면 작업을 중단하여 이후의 작업(다음 인터셉터 또는 컨트롤러)은 진행되지 않는다.
 
### postHandle 메소드
- postHandle 메소드는 컨트롤러를 호출된 후에 실행된다. 그렇기 때문에 컨트롤러 이후에 처리해야 하는 후처리 작업이 있을 때 사용할 수 있다. 이 메소드에는 컨트롤러가 반환하는 ModelAndView 타입의 정보가 제공되는데, 최근에는 Json 형태로 데이터를 제공하는 RestAPI 기반의 컨트롤러(@RestController)를 만들면서 자주 사용되지는 않는다. 또한 컨트롤러 하위 계층에서 작업을 진행하다가 중간에 예외가 발생하면 postHandle은 호출되지 않는다.
 
### afterCompletion 메소드
- afterCompletion 메소드는 이름 그대로 모든 뷰에서 최종 결과를 생성하는 일을 포함해 모든 작업이 완료된 후에 실행된다. 요청 처리 중에 사용한 리소스를 반환할 때 사용하기에 적합하다.postHandle과 달리 컨트롤러 하위 계층에서 작업을 진행하다가 중간에 예외가 발생하더라도 afterCompletion은 반드시 호출된다.

## 인터셉터 동작 위치 및 순서 
![Image](https://github.com/user-attachments/assets/6bdedd30-0bcf-438b-aa17-3694e70d44a4)

- 1) 사용자는 서버에 자신이 원하는 작업을 요청하기 위해 url을 통해 Request 객체를 보낸다.
- 2) DispatcherServlet은 해당 Request 객체를 받아서 분석한뒤 '핸들러 매핑(HandlerMapping)' 에게 사용자의 요청을 처리할 핸들러를 찾도록 요청 한다.
- 3) 그결과로 핸들러 실행체인(HandlerExectuonChanin)이 동작하게 되는데, 이 핸들러 실행체인은 하나이상의 핸들러 인터셉터를 거쳐서 컨트롤러가 실행될수 있도록 구성되어 있다.(핸들러 인터셉터를 등록하지 않았다면, 곧바로 컨트롤러가 실행된다. 반대로 하나이상의 인터셉터가 지정되어 있다면 지정된 순서에 따라서 인터셉터를 거쳐서 컨트롤러를 실행한다)

## 인터셉터(Interceptor)와 AOP의 비교
인터셉터 대신에 컨트롤러들에 적용할 부가기능을 어드바이스로 만들어 AOP(Aspect Oriented Programming, 관점 지향 프로그래밍)를 적용할 수도 있다. 하지만 다음과 같은 이유들로 컨트롤러의 호출 과정에 적용되는 부가기능들은 인터셉터를 사용하는 편이 낫다.
- 컨트롤러는 타입과 실행 메소드가 모두 제각각이라 포인트컷(적용할 메소드 선별)의 작성이 어렵다.
- 컨트롤러는 파라미터나 리턴 값이 일정하지 않다.
- AOP에서는 HttpServletRequest/Response를 객체를 얻기 어렵지만 인터셉터에서는 파라미터로 넘어온다.

## 필터(Filter) vs 인터셉터(Interceptor) 차이 및 용도
![Image](https://github.com/user-attachments/assets/d4544a01-5c0c-4140-86cc-32e499e56187)

### 관리되는 컨테이너
- 앞서 살펴본 그림에서 보이듯이 필터와 인터셉터는 관리되는 영역이 다르다. 필터는 스프링 이전의 서블릿 영역에서 관리되지만, 인터셉터는 스프링 영역에서 관리되는 영역이기 때문에 필터는 스프링이 처리해주는 내용들을 적용 받을 수 없다. 이로 인한 차이로 발생하는 대표적인 예시가 스프링에 의한 예외처리가 되지 않는다는 것이다.
- 참고로 일부 포스팅 또는 자료에서 필터(Filter)가 스프링 빈으로 등록되지 못하며, 빈을 주입 받을 수도 없다고 하는데, 이는 잘못된 설명이다. 이는 매우 옛날의 이야기이며, 필터는 현재 스프링 빈으로 등록이 가능하며, 다른 곳에 주입되거나 다른 빈을 주입받을 수도 있다. 이와 관련해서는 다음 포스팅에서 자세히 살펴보도록 하자.
 
### 스프링의 예외 처리 여부
- 일반적으로 스프링을 사용한다면 ControllerAdvice와 ExceptionHandler를 이용한 예외처리 기능을 주로 사용한다. 예를 들어 원하는 멤버를 찾지 못하여 로직에서 MemberNotFoundException을 던졌다면 404 Status로 응답을 반환하길 원할 것이다. 그리고 이를 위해 우리는 다음과 같은 예외 처리기를 구현하여 활용할 것이다. 이를 통해 예외가 서블릿까지 전달되지 않고 처리된다.

```
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<Object> handleMyException(MemberNotFoundException e) {
        return ResponseEntity.notFound()
            .build();
    }
    
    ...
}
 
```
- 하지만 앞서 설명하였듯 필터는 스프링 앞의 서블릿 영역에서 관리되기 때문에 스프링의 지원을 받을 수 없다. 그래서 만약 필터에서  MemberNotFoundException이 던져졌다면, 에러가 처리되지 않고 서블릿까지 전달된다. 서블릿은 예외가 핸들링 되기를 기대했지만, 예외가 그대로 올라와서 예상치 못한 Exception을 만난 상황이다. 따라서 내부에 문제가 있다고 판단하여 500 Status로 응답을 반환한다. 이를 해결하려면 필터에서 다음과 같이 응답(Response) 객체에 예외 처리가 필요하다.

```
public class MyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        servletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
        servletResponse.getWriter().print("Member Not Found");
    }
}
 
```

### Request/Response 객체 조작 가능 여부
- 필터는 Request와 Response를 조작할 수 있지만 인터셉터는 조작할 수 없다. 여기서 조작한다는 것은 내부 상태를 변경한다는 것이 아니라 다른 객체로 바꿔친다는 의미이다. 이는 필터와 인터셉터의 코드를 보면 바로 알 수 있다.
- 필터가 다음 필터를 호출하기 위해서는 필터 체이닝(다음 필터 호출)을 해주어야 한다. 그리고 이때 Request/Response 객체를 넘겨주므로 우리가 원하는 Request/Response 객체를 넣어줄 수 있다. NPE가 나겠지만 null로도 넣어줄 수 있는 것이다.

```
public MyFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        // 개발자가 다른 request와 response를 넣어줄 수 있음
        chain.doFilter(new MockHttpServletRequest(), new MockHttpServletResponse());       
    }
    
}
 
```

- 하지만 인터셉터는 처리 과정이 필터와 다르다. 디스패처 서블릿이 여러 인터셉터 목록을 가지고 있고, for문으로 순차적으로 실행시킨다. 그리고 true를 반환하면 다음 인터셉터가 실행되거나 컨트롤러로 요청이 전달되며, false가 반환되면 요청이 중단된다. 그러므로 우리가 다른 Request/Response 객체를 넘겨줄 수 없다. 그리고 이러한 부분이 필터와 확실히 다른 점이다.

```
public class MyInterceptor implements HandlerInterceptor {

    default boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Request/Response를 교체할 수 없고 boolean 값만 반환할 수 있다.
        return true;
    }

}
```
 
## 필터(Filter)와 인터셉터(Interceptor)의 용도 및 예시 
### 필터(Filter)의 용도 및 예시
    - 공통된 보안 및 인증/인가 관련 작업
    - 모든 요청에 대한 로깅 또는 감사
    - 이미지/데이터 압축 및 문자열 인코딩
    - Spring과 분리되어야 하는 기능
- 필터에서는 기본적으로 스프링과 무관하게 전역적으로 처리해야 하는 작업들을 처리할 수 있다. 대표적으로 보안 공통 작업이 있다. 필터는 인터셉터보다 앞단에서 동작하므로 전역적으로 해야하는 보안 검사(XSS 방어 등)를 하여 올바른 요청이 아닐 경우 차단을 할 수 있다. 그러면 스프링 컨테이너까지 요청이 전달되지 못하고 차단되므로 안정성을 더욱 높일 수 있다. 또한 필터는 이미지나 데이터의 압축이나 문자열 인코딩과 같이 웹 애플리케이션에 전반적으로 사용되는 기능을 구현하기에 적당하다. Filter는 다음 체인으로 넘기는 ServletRequest/ServletResponse 객체를 조작할 수 있다는 점에서 Interceptor보다 훨씬 강력한 기술이다.
 
- 대표적으로 필터(Filter)를 인증과 인가에 사용하는 도구로는 SpringSecurity가 있다. SpringSecurity의 특징 중 하나는 Spring MVC에 종속적이지 않다는 것인데, 이러한 이유로는 필터 기반으로 인증/인가 처리를 하기 때문이다.
- 위의 내용 중에서 필터는 스프링 빈으로 등록이 가능하며, 다른 곳에 주입되거나 다른 빈을 주입받을 수도 있다고 하였다. 사실 서블릿 컨테이너에 관리되는 필터가 스프링 빈에 주입되는 등의 작업이 가능하다는 것이 이상하기도 하며, 몇몇 블로그들은 이와 관련해서 잘못 설명하고 있기도 하다.  관련 블로그 포스팅 ( https://mangkyu.tistory.com/221)

### 인터셉터(Interceptor)의 용도 및 예시
    - 세부적인 보안 및 인증/인가 공통 작업
    - API 호출에 대한 로깅 또는 감사
    - Controller로 넘겨주는 정보(데이터)의 가공
- 인터셉터에서는 클라이언트의 요청과 관련되어 전역적으로 처리해야 하는 작업들을 처리할 수 있다. 대표적으로 세부적으로 적용해야 하는 인증이나 인가와 같이 클라이언트 요청과 관련된 작업 등이 있다. 예를 들어 특정 그룹의 사용자는 어떤 기능을 사용하지 못하는 경우가 있는데, 이러한 작업들은 컨트롤러로 넘어가기 전에 검사해야 하므로 인터셉터가 처리하기에 적합하다. 또한 인터셉터는 필터와 다르게 HttpServletRequest나 HttpServletResponse 등과 같은 객체를 제공받으므로 객체 자체를 조작할 수는 없다. 대신 해당 객체가 내부적으로 갖는 값은 조작할 수 있으므로 컨트롤러로 넘겨주기 위한 정보를 가공하기에 용이하다. 예를 들어 사용자의 ID를 기반으로 조회한 사용자 정보를 HttpServletRequest에 넣어줄 수 있다.
- 그 외에도 우리는 다양한 목적으로 API 호출에 대한 정보들을 기록해야 할 수 있다. 이러한 경우에 HttpServletRequest나 HttpServletResponse를 제공해주는 인터셉터는 클라이언트의 IP나 요청 정보들을 포함해 기록하기에 용이하다.
 

### AOP
- 관점지향 프로그래밍(AOP)은 어떤 로직을 핵심적인 관점, 부가적인 관점으로 나누어서 보고 그 관점을 기준으로 각각 모듈화 (공통된 로직이나 기능을 하나의 단위로 묶는 것) 하는 프로그래밍 패러다임이다.
- **Spring AOP는 스프링 프레임워크에서 제공하는 기능**으로, 애플리케이션에서 발생하는 특정 이벤트(메서드 호출, 예외 발생 등)에 대한 공통적인 기능(로깅, 트랜잭션 처리 등)을 적용할 수 있도록 해준다. 이를 통해 애플리케이션 전체에서 중복되는 코드를 줄이고, 유지/보수성을 향상시킬 수 있다.
-  주로 로깅, 트랜잭션, 에러처리에 사용되며, Filter와 Intercepter와 다르게 비즈니스 로직을 처리할 때 사용된다. 메소드 전 후로 자유롭게 설정할 수 있다.
- InterCepter, Filter는 주소로 대상을 구분해서 걸러내야하는 반면, AOP는 주소, 파라미터, 애노테이션 등 다양한 방법으로 대상을 지정할 수 있다.
- AOP의 Advice와 HandlerIntercepter의 가장 큰 차이는 파라미터의 차이다. Advice의 경우 JoinPoint나 ProceedingJoinPoint 등을 활용해서 호출하지만, HandlerIntercepter는 FIiter와 유사하게 HttpServletRequest, HttpServletResponse를 파라미터로 사용한다. 
- **Interceptor는 주로 Controller와 관련된 처리를 진행하는 반면, AOP는 세부적인 비즈니스 로직, 메소드와 관련된 처리를 진행하기 위해 사용된다.**

## Filter, Interceptor, AOP의 흐름
![image](https://github.com/user-attachments/assets/b3042972-66ae-4cee-a9c3-6f81f7b8eb0f)
- Interceptor와 Filter는 Servlet 단위에서 실행된다. <> 반면 AOP는 메소드 앞에 Proxy패턴의 형태로 실행된다.ㆍ
- 실행순서를 보면 Filter가 가장 밖에 있고 그안에 Interceptor, 그안에 AOP가 있는 형태이다.따라서 요청이 들어오면 Filter → Interceptor → AOP → Interceptor → Filter 순으로 거치게 된다.
    - 1. 서버를 실행시켜 서블릿이 올라오는 동안에 init이 실행되고, 그 후 doFilter가 실행된다. 
    - 2. 컨트롤러에 들어가기 전 preHandler가 실행된다
    - 3. 컨트롤러에서 나와 postHandler, after Completion, doFilter 순으로 진행이 된다.
    - 4. 서블릿 종료 시 destroy가 실행된다.

### Filter, Interceptor, AOP 차이
![image](https://github.com/user-attachments/assets/573e9382-eb41-4523-b974-19ebbb1bfc1f)

## Filter(필터)
- HTTP 요청과 응답을 거른뒤 정제하는 역할을 한다.서블릿 필터는 DispatcherServlet 이전에 실행이 되는데 필터가 동작하도록 지정된 자원의 앞단에서 요청내용을 변경하거나,  여러가지 체크를 수행할 수 있다.또한 자원의 처리가 끝난 후 응답내용에 대해서도 변경하는 처리를 할 수가 있다.보통 web.xml에 등록하고, 일반적으로 인코딩 변환 처리, XSS방어 등의 요청에 대한 처리로 사용된다.

###  필터의 실행메서드ㆍ
- init() - 필터 인스턴스 초기화ㆍ
- doFilter() - 전/후 처리ㆍ
- destroy() - 필터 인스턴스 종료

## Interceptor(인터셉터)
- 요청에 대한 작업 전/후로 가로챈다고 보면 된다.필터는 스프링 컨텍스트 외부에 존재하여 스프링과 무관한 자원에 대해 동작한다. 하지만 인터셉터는 스프링의 DistpatcherServlet이 컨트롤러를 호출하기 전, 후로 끼어들기 때문에 스프링 컨텍스트(Context, 영역) 내부에서 Controller(Handler)에 관한 요청과 응답에 대해 처리한다. 스프링의 모든 빈 객체에 접근할 수 있다.
- 인터셉터는 여러 개를 사용할 수 있고 로그인 체크, 권한체크, 프로그램 실행시간 계산작업 로그 확인 등의 업무처리를 한다.

### 인터셉터의 실행메서드
- preHandler() - 컨트롤러 메서드가 실행되기 전
- postHanler() - 컨트롤러 메서드 실행직 후 view페이지 렌더링 되기 전
- afterCompletion() - view페이지가 렌더링 되고 난 후

## AOP
- 관점지향 프로그래밍(AOP)은 어떤 로직을 핵심적인 관점, 부가적인 관점으로 나누어서 보고 그 관점을 기준으로 각각 모듈화 (공통된 로직이나 기능을 하나의 단위로 묶는 것) 하는 프로그래밍 패러다임이다.
- Spring AOP는 스프링 프레임워크에서 제공하는 기능으로, 애플리케이션에서 발생하는 특정 이벤트(메서드 호출, 예외 발생 등)에 대한 공통적인 기능(로깅, 트랜잭션 처리 등)을 적용할 수 있도록 해준다. 이를 통해 애플리케이션 전체에서 중복되는 코드를 줄이고, 유지/보수성을 향상시킬 수 있다.
- Interceptor는 주로 Controller와 관련된 처리를 진행하는 반면, AOP는 세부적인 비즈니스 로직, 메소드와 관련된 처리를 진행하기 위해 사용된다. 주로 '로깅', '트랜잭션', '에러 처리'등 비즈니스단의 메서드에서 조금 더 세밀하게 조정하고 싶을 때 사용합니다.Interceptor나 Filter와는 달리 메소드 전후의 지점에 자유롭게 설정이 가능하다. Interceptor와 Filter는 주소로 대상을 구분해서 걸러내야하는 반면, AOP는 주소, 파라미터, 애노테이션 등 다양한 방법으로 대상을 지정할 수 있다.
- AOP의 Advice와 HandlerInterceptor의 가장 큰 차이는 파라미터의 차이다.Advice의 경우 JoinPoint나 ProceedingJoinPoint 등을 활용해서 호출한다.반면 HandlerInterceptor는 Filter와 유사하게 HttpServletRequest, HttpServletResponse를 파라미터로 사용한다.

### AOP의 포인트컷
- @Before: 대상 메서드의 수행 전
- @After: 대상 메서드의 수행 후
- @After-returning: 대상 메서드의 정상적인 수행 후
- @After-throwing: 예외발생 후
- @Around: 대상 메서드의 수행 전/후

## Spring AOP의 구현
- Spring AOP에서는 Aspect, Join Point, Pointcut, Advice, Target 이라는 개념이 사용된다.
    - Aspect는 공통적인 기능을 모듈화한 것을 의미한다.
    - Join Point는 애플리케이션 실행 중에 발생하는 특정 이벤트이다.
    - Pointcut은 Join Point 중에서 실제로 적용할 Join Point를 선택하는 기능이다.
    - Advice는 Pointcut에서 선택된 Join Point에서 수행될 로직이다.
    - Target은 Advice를 적용할 대상 객체이다.
- Spring AOP는 자바의 프록시 패턴을 기반으로 만들어진다. 기존 클래스의 빈이 만들어질 때 프록시 객체를 자동으로 만들고 원본 객체 대신 프록시 객체를 빈으로 등록한다

### 질문
#### 1: 필터, 인터셉터, AOP의 주요 차이점은 무엇인가요? 각각 어떤 상황에서 사용하는 것이 적합할까요?
- 필터는 요청과 응답을 조작하거나 흐름을 변경할 수 있는 기능을 제공하며, WAS(Web Application Server) 수준에서 동작합니다. 이를 통해 인증/인가, 요청 데이터 변환, 캐싱 및 로깅 같은 전역적인 요청/응답 처리를 수행할 수 있습니다. 필터는 스프링 컨텍스트와 독립적으로 동작하므로 컨트롤러와 관계없이 요청이나 응답 자체를 조작해야 할 때 적합합니다.인터셉터는 DispatcherServlet과 컨트롤러 사이에서 동작하며, 요청과 응답의 흐름을 제어합니다. 이를 활용하면 API 호출 검증, 로깅, 컨트롤러로 전달하기 전 정보 가공 같은 작업을 수행할 수 있습니다. 스프링 컨텍스트 내에서 동작하기 때문에 스프링 빈을 사용하는 작업에 적합합니다.AOP는 메소드 실행 전/후나 예외 발생 시 특정 로직을 삽입할 수 있는 기능으로, 메소드 레벨에서 동작합니다. 주로 트랜잭션 관리, 로깅, 성능 모니터링과 같은 비즈니스 로직에 대한 세부 제어에 사용되며, 메소드 단위의 공통 로직 처리가 필요할 때 적합합니다.

#### 2: 필터 체인의 구조에서 필터 순서를 어떻게 설정하며, 잘못된 순서 설정이 어떤 문제를 초래할 수 있나요?
- 필터의 순서는 web.xml 파일의 <filter> 및 <filter-mapping> 태그를 사용해 설정하거나, Spring Boot 환경에서는 @Order 애노테이션 또는 FilterRegistrationBean을 활용해 지정할 수 있습니다.잘못된 순서 설정은 여러 문제를 초래할 수 있습니다. 예를 들어, 인증 필터가 로깅 필터 뒤에 위치하면 비인가된 요청도 로그에 기록될 수 있어 보안상의 문제가 발생할 수 있습니다. 또한, 특정 필터가 이전 필터의 변경된 요청이나 응답 정보를 필요로 할 때 순서가 맞지 않으면 예상대로 동작하지 않을 가능성이 있습니다.예를 들어, 올바른 순서는 보안 필터 → 캐싱 필터 → 로깅 필터입니다. 반면, 캐싱 필터가 보안 필터 앞에 있을 경우 비인가된 요청이 캐시되거나 로그에 기록될 위험이 있습니다. 따라서 필터 간 의존성을 고려한 올바른 순서 설정이 중요합니다.

---
참고링크 

https://mangkyu.tistory.com/173

https://twofootdog.github.io/Spring-%ED%95%84%ED%84%B0%28Filter%29%EB%9E%80-%EB%AC%B4%EC%97%87%EC%9D%B8%EA%B0%80/ 

https://goddaehee.tistory.com/154 

https://kimdirector1090.tistory.com/129

https://popo015.tistory.com/115