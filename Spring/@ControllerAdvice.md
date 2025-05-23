## ControllerAdvice
- @ControllerAdivce 어노테이션은 모든 @Controller에 대해 예외를 잡아 처리해주는 어노테이션이다. 클래스 파일위에 @ControllerAdvice 어노테이션을 붙이면 돼서 간편하며 클래스 안에는 @ExceptionHandler를 통해 내가 핸들링하고 싶은 예외를 잡아서 처리하면 된다. 
- 즉, **@ControllerAdvice는 @ExceptionHandler를 전역적으로 등록해서 모든 컨트롤러에서 발생하는 예외를 한 곳에서 관리하고 처리할 수 있게 해주는 어노테이션이다.**
- @ControllerAdvice 말고도 @RestControllerAdvice 어노테이션도 존재하는데 이는 Controller와 RestController의 차이와 비슷하게 @ResponseBody 어노테이션이 추가된 형태인 것이다. @RestControllerAdvice는 @ResponseBody 어노테이션이 추가되었기 때문에 예외를 객체로 리턴할 수 있게 된다. 
- 에러 페이지로 리다이렉트를 시키기를 원한다면 @ControllerAdvice를 사용하여 예외처리를 진행하면 되고, API 서버를 운영하면서 객체만 리턴 시키려면 @RestControllerAdvice를 사용하면 된다. 만약 적절하게 섞어 사용하기 위해서는 @ControllerAdvice를 사용하고 객체를 리턴해야 하는 부분에만 @ResponseBody을 붙여 사용하면 된다.
- @ControllerAdvice가 AOP로 구현되어 있으며 예외 처리를 공통의 부가 기능으로 제공한다.
- @Controller 어노테이션을 가지거나, xml 설정 파일에서 컨트롤러로 명시된 클래스에서 Exception이 발생되면 이를 감지하며 유사하게 @RestControllerAdvice라는 어노테이션도 존재한다. Controller와 RestController만 ExceptionHandler의 감시 대상이 된다. 즉, Service만 감시 대상으로 등록할 수는 없다. 하지만 Controller에서 Service를 호출한 경우, Service에서 Exception이 발생해도 결국은 Controller로 부터 문제가 발생했음을 감지하여 Handler가 작동한다. @ControllerAdvice(com.freeboard01.api.BoardApi)와 같이 특정한 클래스만 명시하는 것도 가능하다.
- Spring 부팅 시 (애플리케이션 시작 시) Spring은 모든 빈을 스캔하고, @ControllerAdvice가 붙은 클래스를 찾아 별도로 저장한다. ExceptionHandlerExceptionResolver가 초기화될 때, 이 Advice 클래스들을 모두 수집해서 등록한다. 등록된 @ControllerAdvice 클래스 안의 @ExceptionHandler 메서드들도 미리 수집해서 예외 처리용 Resolver에 등록해둔다. 나중에 요청 처리 중 예외가 발생하면, DispatcherServlet은 ExceptionHandlerExceptionResolver를 통해 해당 컨트롤러 내 @ExceptionHandler부터 찾고 없다면 @ControllerAdvice에 등록된 핸들러 메서드들 중에서 예외에 맞는 걸 실행합니다.


```
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ControllerAdvice
@ResponseBody
public @interface RestControllerAdvice {
    ...
}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ControllerAdvice {
    ...
}

@ControllerAdvice
public class GlobalExceptionHandler {

    // 특정 예외 처리: IllegalArgumentException 발생 시 실행
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("잘못된 요청입니다: " + ex.getMessage());
    }

    // 모든 예외 처리 (최상위 Exception)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("서버 내부 오류가 발생했습니다.");
    }
}

```


## ControllerAdvice의 동작 과정 

![Image](https://github.com/user-attachments/assets/a7545a44-d616-48b6-91fa-91de680c0acb)

### 디스패처 서블릿이 에러를 catch함
- 스프링에서 모든 요청을 가장 먼저 받는 곳은 디스패처 서블릿이다. 그러다보니 에러가 발생하면 에러 처리가 시작되는 곳 역시 디스패처 서블릿인데, 디스패처 서블릿의 핵심 메소드인 doDispatch에는 다음과 같이 모든 Exception과 Throwable을 catch하고 있다.

### 해당 에러를 처리할 수 있는 처리기(HandlerExceptionResolver)가 에러를 처리함
- 디스패처 서블릿은 다양한 예외 처리기(HandlerExceptionResolver)를 가지고있다. 예외 처리 시에는 각가의 구현체들로부터 예외를 핸들링시키는데, 반환 결과가 null이 아니라면 정상적으로 처리된 것이다. HandlerExceptionResolver의 구현체 중에서 ControllerAdvice는 ExceptionHandlerExceptionResolver에 의해 처리된다.

### 컨트롤러의 ExceptionHandler로 처리가능한지 검사함
- ExceptionHandler는 Controller에 구현할 수도 있고, ControllerAdvice에도 구현할 수 있다. ControllerAdvice에 구현하는 것은 전역적인 반면에 Controller에 구현하는 것은 지역적이다. 그러므로 Controller에 있는 ExceptionHandler가 우선 순위를 갖도록 먼저 컨트롤러의 ExceptionHandler를 검사한다. 그리고 컨트롤러에 있는 ExceptionHandler가 예외를 처리할 수 있다면 (예외를 처리할 빈, 예외를 처리할 ExceptionHandler 메소드, 애플리케이션 컨텍스트)를 담은 ServletInvocableHandlerMethod를 만들어 반환한다. 여기서 예외를 처리할 빈에는 컨트롤러가 존재한다.

### ControllerAdvice의 ExceptionHandler로 처리가능한지 검사함
- 컨트롤러에서 갖는 ExceptionHandler로 처리가 불가능하다면 등록된 모든 ControllerAdvice 빈을 검사한다. 그리고 처리 가능한 ControllerAdvice의 ExceptionHandler가 있다면 마찬가지로 ServletInvocableHandlerMethod를 만들어 반환한다. 아까와는 다르게 ServletInvocableHandlerMethod의 예외를 처리할 빈에는 컨트롤러가 아닌 ControllerAdvice 빈이 존재한다.

### ControllerAdvice의 ExceptionHandler 메소드를 invoke하여 예외를 반환함
- 반환받은 ServletInvocableHandlerMethod에는 ExceptionHandler를 갖는 빈과 ExceptionHandler의 구현 메소드가 존재한다. 스프링은 리플렉션 API를 이용해 ExceptionHandler의 구현 메소드를 호출해 처리한 에러를 반환한다.

## @ExceptionHandler와 @ControllerAdvice의 차이
- @ExceptionHandler와 @ControllerAdvice는 Spring MVC에서 예외 처리를 위해 사용되지만, 역할과 적용 범위에서 중요한 차이가 있다. @ExceptionHandler는 특정 컨트롤러 안에서 예외를 처리할 때 사용한다. 예를 들어, UserController에서 발생한 예외를 그 컨트롤러 내부에서만 처리하고 싶을 때 사용한다.
- 반면에 @ControllerAdvice는 여러 컨트롤러에 공통적으로 적용되는 전역 예외 처리기이다. 이 애너테이션을 붙인 클래스는 전체 애플리케이션의 모든 컨트롤러에서 발생하는 예외를 처리할 수 있다. 즉, @ExceptionHandler는 국소적인 예외 처리, @ControllerAdvice는 전역 예외 처리를 위한 도구라고 생각하면 된다. 둘 다 예외를 잡는 방식은 같지만, 적용 범위와 목적이 다르다.
- 동작 흐름 자체는 거의 동일하다. 핵심 차이는 어디서 등록 되었냐(적용범위)에 있다. Spring 내부적으로 처리하는 매커니즘은 거의 비슷하다. 사실상 둘의 동작 흐름은 똑같고 @ControllerAdvice는 애플리케이션 부팅 시 등록되며 실제 예외 발생시에는 @ExceptionHandelr와 동일한 방식으로 동작한다. @ControllerAdvice 안에 @ExceptionHandelr를 정의하기때문이다.
    - 컨트롤러(@Controller, @RestController)에서 예외 발생
    - 예외는 DispatcherServlet으로 전파됨
    - DispatcherServlet이 HandlerExceptionResolver 목록을 순차적으로 실행
    - 내부적으로 ExceptionHandlerExceptionResolver가 동작한다. ExceptionHandlerExceptionResolver는 다음 순서로 예외 핸들러를 찾는다.
        - 1순위: 해당 컨트롤러 클래스의 @ExceptionHandler 메서드
        - 2순위: 등록된 @ControllerAdvice 클래스들의 @ExceptionHandler 메서드
    - 해당 예외를 처리할 수 있는 @ExceptionHandler 메서드를 찾음
    - 찾아서 있으면 실행 → 결과를 View로 렌더링 또는 JSON으로 응답


---

참고링크 

https://mangkyu.tistory.com/246

https://mozzi-devlog.tistory.com/25#google_vignette

https://velog.io/@hanblueblue/Spring-ExceptionHandlerz