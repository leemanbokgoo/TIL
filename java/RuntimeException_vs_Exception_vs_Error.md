
# RuntimeException vs Exception vs Error
![image](https://github.com/user-attachments/assets/bac92be7-1e4f-42c4-9c02-496dd42de4c0)

## 예외의 종류
- checked exceptiono
- error
- runtime exception 혹은 unchecked exception

## Error (에러)
- 자바 프로그램 밖에서 발생한 예외를 말한다. 가장 흔 한 예로는 서버의 디스크가 고장났다던지, 메인보드가 맛이가서 자바프로그램이 제대로 동작하지못하는 경우가 여기에 속한다.
- 오류의 이름이 Error로 끝나면 에러이고 Exception으로 끝나면 예외이다.
- Error와 Exception으로 끝나는 오류의 가장 큰 차이는 프로그램 안에서 발생했는지 밖에서 발생했는 지 여부. 프로그램이 멈추어버리느냐 계속 실행할 수 있느냐의 차이
- Error는 프로세스에 영향을 주고 Exception은 스레드에만 영향을 준다.
- Error는 Throwable의 하위 클래스로써, 응용 프로그램의 심각한 문제를 나타내는 클르시앋.
- 시스템에 비정상적인 상황이 생겼을떄 발생한다 (시스템레벨에서 발생)
- 이러한 오류는 대부분 비정상적인 상태이다 "정상"조건이긴하지만 ThreadDeath 오류 대부분의 응용 프로그램이 이를 잡아내려고 하지않기때문에 Error의 하위 클래스이기도 하다.
- Error의 서브 클래스를 throws 선언할 필요는 없다.
- Error 및 해당 서브 클래스는 예외 컴파일 시간 검사를 위해 unchecked Exception으로 간주 된다.

## Exception (예외)
- 예외는 개발자가 구현한 로직에서 발생한 실수나 사용자의 영향에 의해 발생한다. 이는 개발자가 미리 예측 하여 방지할 수 있기때문에 상황에 맞는 예외처리를 해야한다. 
- Exception은 checked Exception이다.
- Exception 클래스와 그 subclass는 응용프로그램이 catch 할 수 있는 조건을 나타내는 Throwable 형식이다.
- Exception 클래스와 RuntimeException의 subclass가 아닌 서브 클래스는 checked exceptiondlek.
- 직접 구현하여 개발자가 비즈니스 로직에 맞게 생성 가능하다.
- 수많은 subclass가 있으므로 꼭 reference를 확인해보자

## RuntimeException
- Exception중에서도 RuntimeException과 이를 상속 받는 것들은 unchecked Exception이고 이를 제외한 나머지 것들은 checkec Exception이다.
- checkedException이란 코드 상에서 반드시 예외처리를 해야하는 Exception을 의미한다. 
- Unhandled Exception 에러는 Checked Exception 에 대한 예외처리를 하지 않았기 때문에 발생하는 것이다.
- RuntimeException은 unchecked Exception이다.
- RuntimeException은 Java Vitual Machine의 정상적인 작동 중에 발생할 수 있는 예외의 수퍼 클래스이다. RuntimeException 및 해당 서브 클래스는 unchecked exception이다. unchecked exception은 메서드 또는 생성자의 실행에 의해 발생한다
- 메서드 또는 생성자 경계 외부로 전파 될수 있는 경우 메서드 또는 생성자의 throws 절에서 선언될 필요가 없다.

##  Throwable class
- Throwalbe 클래스는 Java언어의 모든 오류 및 예외의 슈퍼 클래스
- 이 클래스의 인스턴스(또는) 서브 클래스 중 하나)인 객체 만 Java Virtual Machine 에 의해 발생하거나 Java thow 문에 의해 발생 될 수 있다.
- 예외의 compile-time-checking을 위해 RuntimeException 또는 Error의 서브 클래스가 아닌 Throwable 및 Throwable의 서브 클래스는 Checked 예외로 간주된다


![image](https://github.com/user-attachments/assets/1978822c-3d21-4d6a-a278-30d39e598b71)

checked/ unchecked exception의 가장 큰 차이는 처리 방식이다.
checked exception이 발생할 가능성이 있다면 throws로 상위로 처리를 위임하거나 try-catch문으로 처리해야한다.

### checked Exception
- 반드시 예외처리를 해야한다(try-catch를 통해서)
- 컴파일 단계에서 확인이 가능하다
- 예외 발생시 트랜잭션을 rollback하지않는다
- RuntimeException을 제외한 Exception의 하위 클래스

### unchecked Exception
- 명시적 예외 처리를 강제하지않는다
- 실행단계에서 확인 가능하다
- 예외 발생 시 크랜잭션 roll backgksek
- RumtimeException의 하위 클래스 

### 질문

#### 1. Exception, RuntimeException, Error는 각각 무엇이며, 어떤 차이점이 있나요?
- Exception, RuntimeException, Error는 모두 Java에서 발생할 수 있는 문제를 나타내며, 각각 다른 특성과 용도를 가집니다.

- Exception은 프로그램 실행 중 발생할 수 있는 예외 상황을 나타내며, 개발자가 예측 가능한 문제에 해당합니다. 사용자 입력 오류나 파일 접근 문제처럼 프로그램 내부 상황에 의해 발생하는 예외가 주로 속하며, Checked Exception으로 분류됩니다. 컴파일러는 throws 선언이나 try-catch를 통해 반드시 예외 처리를 하도록 강제합니다. 대표적인 예로 IOException과 SQLException이 있습니다.

- RuntimeException은 Exception의 하위 클래스이면서 Unchecked Exception입니다. 예외 처리를 강제하지 않으며, 주로 개발자의 논리 오류로 인해 발생할 수 있는 예외를 나타냅니다. 컴파일 시에는 확인되지 않고, 프로그램 실행 중에만 발생하며, 대표적인 예로는 NullPointerException과 ArrayIndexOutOfBoundsException이 있습니다. 개발자는 반드시 예외 처리를 하지 않아도 되지만, 필요한 경우 이를 처리해 프로그램이 멈추는 것을 방지할 수 있습니다.

- Error는 시스템 수준의 심각한 문제로, JVM의 환경적 문제나 시스템의 한계로 인해 발생합니다. 복구할 수 없는 오류로 간주되어 개발자가 직접 예외 처리를 하지 않는 경우가 많으며, 발생 시 프로그램이 비정상적으로 종료될 가능성이 큽니다. 대표적인 예로는 메모리 부족을 나타내는 OutOfMemoryError나 스택 오버플로우 오류인 StackOverflowError가 있습니다. Error는 Unchecked Exception으로 분류되며, 예외 처리가 강제되지 않습니다.

#### Checked Exception과 Unchecked Exception의 차이는 무엇인가요?
- Checked Exception은 컴파일 시 예외 처리가 요구됩니다. 예외 발생 가능성이 있는 코드에 대해 반드시 throws 선언이나 try-catch로 예외 처리를 해야 합니다.
- Unchecked Exception은 RuntimeException의 하위 클래스들이며, 컴파일 시 예외 처리가 강제되지 않습니다. 주로 실행 중에 발생하는 예외로, 개발자가 명시적으로 예외 처리를 하지 않아도 컴파일이 가능합니다.




참고링크 

https://steady-hello.tistory.com/55
자바의신 1