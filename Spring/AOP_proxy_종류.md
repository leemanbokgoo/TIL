# JDK Dynamic Proxy
- 리플랙션의 Proxy 클래스가 동적으로 Proxy를 생성해준다하여 JDK Dynamic Proxy라 불린다. JDK 에서 제공하는 Dynamic Proxy는 1.3 버젼부터 생긴 기능이며, Interface를 기반으로 Proxy를 생성해주는 방식이다. 그렇기때문에 proxy 생성을 위해 interface가 필요하다. Dynamic Proxy는 Invocation Handler를 상속받아서 실체를 구현한다.
- Spring은 프록시를 구현할 때 프록시를 구현한 객체(RateDiscountServiceProxy)를 마치 실제 빈(RateDiscountService)인 것처럼 포장하고, 2개의 빈을 모두 등록하는 것이 아니라 실제 빈을 프록시가 적용된 빈으로 바꿔치기한다.
- Spring은 AOP Proxy를 생성하는 과정에서 자체 검증 로직을 통해 타깃의 인터페이스 유무를 판단한다.이때 만약 타깃이 하나 이상의 인터페이스를 구현하고 있는 클래스라면 JDK Dynamic Proxy의 방식으로 생성되고 인터페이스를 구현하지 않은 클래스라면 CGLIB의 방식으로 AOP 프록시를 생성한다.

## 장점
- 프록시 클래스를 직접 구현하지않아도 된다.
- ByteCode 를 조작하는것 보다는 덜 복잡하고,
- 코드레벨에서 이해하기 좋다.
- 별다른 별도의 라이브러리가 없어도 Proxy 를 만들수 있다.

## 단점
- Interface 가 없으면 Proxy 를 생성할 수 없기 때문에, Interface 가 없는 객체는 프록시를 적용할 수가 없다.
- 내부적으로 Java Reflection API 라는 비싼 Cost 의 API 를 사용하기 때문에 성능에 문제가 될 수 있다.
- 별도의 Library 가 없어도 Proxy를 만들 수 있다는 건 간단한 수준에서만 그렇고, 복잡한것들이 포함되면 SpringContainer 나, 기타 다른 라이브러리가 필요하게 된다.

## 리플렉션
- 자바 프로그래밍 언어의 한 특징으로 실행 중인 자바 프로그램 내에서 클래스, 인터페이스, 메서드, 필드의 정보를 동적으로 검사하고 접근(호출) 할 수 있는 기능. 주로 런타임시 동적으로 클래스를 로드하고 메서드를 호출하는 작업에 사용한다.
- Class.forName(), Method.invoke() 등을 사용하여 컴파일 시점이 아니라 실행 중(런타임)에 동작을 결정할 수 있음

- JDK Dynamic Proxy는 Java의 리플렉션(Reflection) 기능을 활용하여 동적으로 인터페이스를 구현하는 프록시 객체를 생성하는데 java.lang.reflect.Proxy 클래스를 사용하여 인터페이스를 구현하는 익명 클래스(프록시 객체)를 런타임에 생성한다.

### JDK Dynamic Proxy를 활용한 프록시 생성

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyExample {
public static void main(String[] args) {
// 원본 객체 생성
OrderService realService = new OrderServiceImpl();

// 프록시 생성
OrderService proxyInstance = (OrderService) Proxy.newProxyInstance(
realService.getClass().getClassLoader(), // 클래스로더
new Class[]{OrderService.class}, // 프록시가 구현할 인터페이스
new InvocationHandler() { // 리플렉션을 활용한 메서드 가로채기
@Override
public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
System.out.println("[로그] " + method.getName() + " 메서드 실행 전");
Object result = method.invoke(realService, args); // 원본 메서드 실행
System.out.println("[로그] " + method.getName() + " 메서드 실행 후");
return result;
}
}
);

// 프록시 객체로 메서드 호출
proxyInstance.placeOrder();
}
}

- jDK Dynamic Proxy가 리플렉션을 활용한다는 뜻은 프록시 객체는 원본 객체를 직접 상속하지 않고 대신 리플렉션(InvocationHandler + Method.invoke)을 사용하여 원본 객체의 메서드를 동적으로 호출한다는 뜻이다. 이 방식 덕분에 AOP(Aspect-Oriented Programming)에서 런타임에 메서드를 가로채고 부가기능(트랜잭션, 로깅 등)을 추가할 수 있다.


## CGLib(Code Generator Library) Proxy
- CGLib은 Code Generator Library의 약자로, 클래스의 바이트코드를 조작하여 Proxy 객체를 생성해주는 라이브러리다. 이 방식은 JDK Dynamic Proxy와는 다르게 Reflection을 사용하지 않고, Extends(상속) 방식을 이용해서 Proxy화 할 메서드를 오버라이딩 하는 방식이다.하지만 상속을 이용하므로 final이나 private와 같이 상속에 대해 오버라이딩을 지원하지 않는 경우에는 Aspect를 적용할 수 없다는 단점이 있다.
- 바이트 고드를 조작해서 프록시를 생성하며 인터페이스에도 강제로 적용 가능하다. 이 경우 클래스에도 프록시를 적용해야한다.
- Dynamic Proxy 보다 약 3배 가까이 빠르다. 메서드가 처음 호출되었을 때 동적으로 타깃 클래스의 바이트 코드를 조작하고 이후 호출 시엔 조작된 바이트 코드를 재사용한다.
- MethodInterceptor를 재정의한 intercept를 구현해야 부가 기능이 추가된다.
- 메서드에 final을 붙이면 오버라이딩이 불가능

## 단점
- 기본적으로 클래스 상속(extends)을 통해 프록시 구현이 되기 때문에, 타겟 클래스가 상속이 불가능할때는 당연히 프록시 등록이 불가능하다. 또한 메서드에 final 키워드가 붙게되면 그 메서드를 오버라이딩하여 사용 할수 없게되어 결과적으로 프록시 메서드 로직이 작동되지 않는다.
- net.sf.cglib.proxy.Enhancer 의존성을 추가해야한다
- Default 생성자 필요로 하며 없는 경우 예외 발생한다.
- 기존에는 CGLIB를 이용하면 디폴트 생성자가 필요했고 원본 객체의 생성자를 두 번 호출했다. 실제 빈을 생성할 때 한번, 프록시 생성을 위해 총 2번 호출 한다.

## 장점
- 인터페이스가 아닌 클래스를 대상으로 동작 가능하고 바이트코드를 조작해 프록시를 만들기 때문에 Dynamic Proxy에 비해 성능이 우수하다
- 인터페이스가 없어도 프록시 생성이 가능하다.


## JDK Dynamic Proxy vs CGLib
- 두 방식의 차이는 인터페이스의 유무 로서, Spring은 프록시 타겟 객체에 인터페이스가 있다면 그 인터페이스를 구현한 JDK Dynamic Proxy 방식으로 객체를 생성하고 구현하지 않았다면 CGLIB 방식을 사용한다. 사용자가 어떻게 설정하느냐에 따라서 인터페이스를 구현했다 하더라도 CGLIB 방식을 강제하거나 AspectJ를 사용할 수 있다.
- 하지만 JDK 동적 프록시 방식은 인터페이스를 반드시 생성해야한다는 단점과 구체 클래스로는 빈을 주입받을 수 없고 반드시 인터페이스로만 주입받야아한다는 단점 때문에 스프링은 CGLib 방식의 프록시를 강제하는 옵션을 제공하고 있는데, 이것이 바로 proxyTargetClass이며, 이 값을 true로 지정해주면 Spring은 인터페이스가 있더라도 무시하고 클래스 프록시를 만들게 된다.
- SpringBoot에서는 CGLib 라이브러리가 갖는 단점들을 모두 해결하였고, Spring Boot 2.0부터부터는 proxyTargetClass 옵션의 기본값을 true로 사용하고 있다. 단점은 다음과 같다.
- net.sf.cglib.proxy.Enhancer 의존성을 추가해야함. -> 3.2 ver. Spring Core 패키지에 포함
- Default 생성자 필요함 -> 4.0 ver.부터 Objensis 라이브러리
- 타겟의 생성자 두번 호출함 - > 4.0 ver.부터 Objensis 라이브러리
- 오픈 소스였다. 신뢰하고 사용해도 될 정도로 검증할 시간이 필요했고 Spring에 내장되어있지 않아 별도로 의존성을 추가해야한다는 문제도 있었다. Spring 3.2버전부터 spring-core로 리패키징된 상태라 의존성을 추가할 필요가 없어짐.

- Spring 4.3과 Spring boot 1.4부터 default로 CGLIB 프록시 사용

### 질문
#### Spring에서 프록시를 생성할 때 JDK Dynamic Proxy와 CGLib 중 어떤 방식을 선택하는 기준은 무엇인가?
- Spring은 타깃 객체가 인터페이스를 구현하고 있으면 JDK Dynamic Proxy를 사용하고, 인터페이스가 없거나 proxyTargetClass=true로 설정한 경우 CGLib을 사용한다. Spring Boot 2.0부터는 기본적으로 CGLib을 사용하도록 설정되어 있다.

### JDK Dynamic Proxy와 CGLib의 성능 차이는 왜 발생하는가?
- JDK Dynamic Proxy는 Java Reflection API를 사용하여 메서드를 호출하기 때문에 성능이 비교적 낮다. 반면, CGLib은 바이트코드를 직접 조작하여 프록시 객체를 생성하고, 초기 한 번만 바이트코드를 조작한 후에는 재사용하기 때문에 성능이 더 우수하다.

----


참고링크

https://notypie.dev/proxy%EC%97%90-%EB%8C%80%ED%95%9C-%EC%9D%B4%ED%95%B4-1-jdk-dynamic-proxy/

https://suyeonchoi.tistory.com/81

https://gmoon92.github.io/spring/aop/2019/04/20/jdk-dynamic-proxy-and-cglib.html

https://mangkyu.tistory.com/175


https://velog.io/@joohr1234/Spring-AOP%EA%B0%80-%EC%A0%9C%EA%B3%B5%ED%95%98%EB%8A%94-%EB%91%90-%EA%B0%80%EC%A7%80-AOP-Proxy