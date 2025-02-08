## ExceptionHandler

![Image](https://github.com/user-attachments/assets/3a1ab8e5-8464-48f8-89b4-1de7f8c32c46)

- @ExceptionHandler는 Controller 내 메소드에 정의되어 해당 컨트롤러에서 발생하는 (특정 혹은 모든) 에러를 받아 처리한다.
- @ControllerAdvice 또는 특정 컨트롤러 클래스 내에 특정 예외가 발생했을 때 이를 처리하기 위한 메서드를 정의할 수 있도록 지원하는 어노테이션이고 이를 통해 예외 발생 시 프로그램의 흐름을 중단하지 않고, 오류를 보다 유연하게 처리하거나 사용자에게 알맞은 메시지를 반환하게 한다.
- 스프링은 API 예외 처리 문제를 해결하기 위해 @ExceptionHandler 라는 애노테이션을 사용하는 매우 편리한 예외 처리 기능을 제공하는데, 이것이 바로 ExceptionHandlerExceptionResolver 이다. 컨트롤러의 메소드에 @ExceptionHandler를 추가함으로써 에러를 처리할 수 있다. @ExceptionHandler에 의해 발생한 예외는 ExceptionHandlerExceptionResolver에 의해 처리가 된다.
- 즉 예외 처리를 WAS로 바로 넘기는게 아니라 이를 직접처리하여 로깅 혹은 유저 친화적인 에러 메시지로 변경할 수 있다는 것이 중요하다.
- @ExceptionHandler 는 등록된 해당 Controller 에서만 적용이 된다. 다른 컨트롤러의 예외는 잡을 수 없다. 같은 예외가 발생한 것이고 같은 처리를 해주고 싶은 경우가 있을 수 있다. 다른 컨트롤러에서의 작업이라면 해당 컨트롤러에 같은 @ExceptionHandler 를 적용해주어야 한다. 똑같은 기능을 하는 똑같이 생긴 코드를 반복하는 것은 번거럽고 낭비이다. 이러한 번거로움을 해결할 수 있는 방법이 있는데, 바로 @ControllerAdvice 이다.

## @ExceptionHandler의 장점
- 위에서 봤다시피 @ExeceptionHandler의 큰 장점은 에러 발생 시 was에 넘기기 전에 사용자 정의 메서드를 거친다는 것이다. 이 뿐만이 아니라 몇가지 장점이 더 있는데 아래와 같다.
    - 예외 처리를 중앙에서 관리할 수 있어 코드 가독성 향상 및 유지보수 쉬워짐
    - 예외 상황에 따라 사용자 맞춤 응답을 정의
    - 응답 상태 코드, 메시지 등을 일관되게 관리

## @ExeceptionHandler 동작 원리

![Image](https://github.com/user-attachments/assets/b5b878f5-57b0-4ad6-b12f-4d77950aa862)

### 1. 예외 발생 시 컨트롤러 호출 중단
- HTTP 요청을 처리하는 컨트롤러에서 예외가 발생하면 Spring은 즉시 해당 요청의 처리를 중단하고, 예외 핸들링 로직으로 흐름을 전환
### 2. @ExceptionHandler 메서드 탐색
- 예외가 발생한 컨트롤러 클래스 내에서 @ExceptionHandler가 정의된 메서드를 찾아, 해당 예외에 대한 처리 메서드가 있는지 탐색한다.(1단계 예외 발생한 컨트롤러 내부 -> 2단계 @ControllerAdvice로 정의된 전역 예외 핸들러)
### 3. 예외 타입 일치 여부 확인
- 탐색된 @ExceptionHandler 메서드에서 매개변수로 선언된 예외 타입과 발생한 예외 타입을 비교하여 타입이 일치하거나 상속 관계가 있는지 확인
- 예외 타입이 일치하는 @ExceptionHandler 메서드가 발견되면 해당 메서드를 실행하고, 일치하는 메서드가 없으면 기본 오류 페이지 또는 사용자 정의 오류 페이지를 반환한다.
### 4. ExceptionHandlerMethodResolver
- Spring은 내부적으로 ExceptionHandlerMethodResolver라는 클래스를 사용하여 @ExceptionHandler가 붙은 메서드들을 관리하고, 요청에 대해 적합한 예외 처리 메서드를 선택한다.
### 5, 예외 처리 메서드의 응답 반환
- 선택된 @ExceptionHandler 메서드가 예외를 처리하고, 응답을 생성하여 클라이언트에게 반환하고 이때 응답은 ResponseEntity, JSON 응답, 텍스트 응답 등으로 지정할 수 있다.

```
1. 예외 발생: 컨트롤러에서 예외가 발생합니다. 예를 들어, 유효하지 않은 데이터로 POST 요청이 왔다면 MethodArgumentNotValidException 예외가 발생할 수 있습니다.

2. 예외 탐색: 스프링은 @RestControllerAdvice가 붙은 클래스를 검색하여, 발생한 예외와 일치하는 @ExceptionHandler 메서드를 찾습니다.

3. 매칭되는 핸들러 실행: 예외와 일치하는 핸들러가 있다면 해당 메서드가 실행됩니다. 예를 들어, MethodArgumentNotValidException 예외가 발생하면 handleValidationException() 메서드가 실행됩니다.

4. 예외 응답 반환: 핸들러 메서드의 로직을 통해 클라이언트에 일관된 형식의 예외 응답이 반환됩니다.

```
 

- Spring MVC에서 @ExceptionHandler는 컨트롤러 내부에서 발생한 특정 예외를 처리하는 메서드를 정의할 때 사용된다. 예외가 발생하면 Spring은 먼저 해당 컨트롤러에서 @ExceptionHandler가 붙은 메서드를 찾아 실행한다.
- 만약 컨트롤러 내에서 처리할 수 있는 @ExceptionHandler가 없다면, 전역 예외 처리 역할을 하는 @ControllerAdvice에서 적절한 예외 처리 메서드를 탐색한다. 그래도 처리되지 않으면 Spring의 기본 예외 처리 메커니즘(BasicErrorController)이 동작하여 기본적인 오류 응답을 반환한다.
- 즉, 예외 처리의 우선순위는 1) 컨트롤러 내부의 @ExceptionHandler → 2) @ControllerAdvice의 전역 예외 처리 → 3) Spring 기본 예외 처리 순으로 진행된다. 이러한 구조를 통해 개발자는 특정 컨트롤러에서만 예외를 처리할지, 아니면 전역적으로 처리할지를 선택할 수 있으며, 클라이언트에게 일관된 에러 응답을 제공할 수 있다.


```
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body("Null 값이 발생했습니다.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("서버 오류가 발생했습니다.");
    }
}

```
-  예시의 동작 흐름을 살펴보면 우선 예외가 발생하면 ExceptionHandlerMethodResolver를 통해 @ExceptionHandler 메서드를 찾는다.
- 일치하는 메서드를 발견하면 이를 호출하여 예외를 처리하고, HTTP 상태 코드와 응답 본문을 설정하여 반환
- 일치하는 @ExceptionHandler 메서드가 없을 경우, 기본 예외 처리 로직으로 돌아간다.
- 이 과정을 통해 @ExceptionHandler는 요청 흐름에 개입하여 예외를 효율적으로 처리하고, 사용자에게 적절한 메시지를 전달.

### 질문
####  @ExceptionHandler와 @ControllerAdvice의 차이점은 무엇인가요?
- @ExceptionHandler는 특정 컨트롤러 내에서 발생한 예외만 처리할 수 있으며, 다른 컨트롤러에서 발생한 예외는 처리하지 못합니다. 반면, @ControllerAdvice를 사용하면 애플리케이션 전역에서 발생하는 예외를 처리할 수 있어 코드 중복을 줄이고 유지보수를 쉽게 할 수 있습니다.

#### @ExceptionHandler의 동작 과정에서 ExceptionHandlerMethodResolver의 역할은 무엇인가요?
- ExceptionHandlerMethodResolver는 Spring 내부에서 @ExceptionHandler가 붙은 메서드를 관리하고, 요청된 예외에 대해 적절한 예외 처리 메서드를 탐색하는 역할을 합니다. 발생한 예외와 가장 적절한 예외 핸들러 메서드를 매칭하여 실행함으로써, 예외가 적절히 처리되도록 돕습니다.

#### @ExceptionHandler와 @ControllerAdvice의 차이는 무엇이며, 각각의 사용 목적은 무엇인가요?
- @ExceptionHandler는 특정 컨트롤러 내부에서 발생하는 예외를 처리하는 데 사용되며, 해당 컨트롤러에서만 적용됩니다. 반면, @ControllerAdvice는 여러 컨트롤러에서 발생하는 예외를 전역적으로 처리할 수 있도록 도와줍니다. 따라서 같은 예외를 여러 컨트롤러에서 공통적으로 처리해야 할 경우, @ControllerAdvice를 활용하는 것이 더 효율적입니다.

#### @ExceptionHandler가 예외를 처리하는 과정에서 Spring이 내부적으로 사용하는 주요 클래스는 무엇인가요?
- Spring은 내부적으로 ExceptionHandlerMethodResolver를 사용하여 @ExceptionHandler가 적용된 메서드를 찾고, 적절한 핸들러를 실행합니다. 예외가 발생하면 ExceptionHandlerExceptionResolver가 이를 가로채어 ExceptionHandlerMethodResolver를 통해 적절한 예외 처리 메서드를 찾아 실행하며, 최종적으로 클라이언트에게 응답을 반환합니다.


--- 

참고링크 

https://velog.io/@homelala/ExceptionHandler-%EB%8F%99%EC%9E%91%EC%9B%90%EB%A6%AC

https://tecoble.techcourse.co.kr/post/2021-05-10-controller_advice_exception_handler/

https://sung-98.tistory.com/168

https://mangkyu.tistory.com/204

https://velog.io/@e1psycongr00/Spring-%EC%98%88%EC%99%B8-%EC%B2%98%EB%A6%AC-%EB%8F%99%EC%9E%91-%EC%9B%90%EB%A6%AC