# Virtual Thread 가상 스레드의 등장 배경
- 자바에서 전통적인 스레드 모델은 운영체제의 스레드에 직접 매핑되어 작동한다. 이러한 방식은 스레드가 많아질수록 컨텍스트 스위칭에 따른 오버헤드가 증가하고, 리소스 사용량이 늘어나는 문제를 가지고 있다.

- 자바에서는 동시성 문제를 해결하기위해 Thread를 생성하여 처리.
- 스프링이 톰캣 서버를 사용하기때문에 하나의 요청을 처리하기위해 하나의 Thread를 생성한다(thread per reqeust) -> 동시 요청이 많으면 스레드 수 역시 증가 
- 하지만 java의 스레드는 실제 운영 체제 스레드 하나와 매핑되는 형태(Platform Thread)로 동작하기때문에, 하나의 스레드가 가지는 스택의 크기와 리소스 양은 매우 크다 -> OS가 최소 프로그램 하나를 돌리기위해 생성하는 스레드를 java는 내부 스레드 하나를 사용하는데 쓰고있다고 생각하면 됨.
(물론 내부적으로 적절한 만큼의 메모리를 할당하겠지만 그럼에도 OS 수준에서 사용될 메모리이기때문에 적지 않다는 것.)

- 너무 많은 스레드를 생성할 경우 효율성이 떨어짐

### 효율성이 떨어지는 이유
- java의 스레드는 운영체제에 의해 스케쥴링 되기때문.
- 스레드들은 작업을 수행하다보면 I/O작업등으로 인해 유휴상태에 빠지는 경우가 있는데 이러한 유휴상태가 되면 CPU를 필요로 하는 다른 스레드를 동작시키기위해 컨택스트 스위칭이 발생. -> CPU를 효율적으로 처리하기위해 기다리는 시간에 다른 스레드를 동작시키는 것

#### 1. 스레드가 제한적이다.
- 운영체제는 스레드 생성, 유지 비용이 비싸기때문에 효율이 좋지않다.
- 자바에서 요청을 처리하기위해 많은 스레드를 생성하려고해도 운영체제에서 스레드 생성이 불가능하다면 자바는 스레드를 생성 할 수 없다.
- 만약 요청을 처리 하려다가 대기 상태가 되어 컨텍스트 스위칭을 하려고 봤더니 모든 스레드가 전부 I/O작업을 수행중이라고 가정하면 CPU는 아무것도 하지않는 상태가 될것. -> CPU를 효율적으로 사용한 것이 아니게 된다.

#### 2. 컨택스트 스위칭 비용이 비싸다.
- java 스레드는 OS에 의해 스케쥴링 되기때문에, 하나의 스레드에서 다른 스레드로 컨택스트 스위칭을 하기위해서는 OS레벨에서 동작해야한다. 


# Virtual Thread
- 자바의 기존 스레드 모델은 **I/O가 빈번하면서 동시에 처리할 양이 많은 프로그램을 구현하기에는 크게 효율적이지 못함.**
- 이러한 한계점을 극복하기위해 나온것이 Virtual Thread.
- OS가 아닌 JVM 위에서 스케쥴링 되는 경량화된 스레드를 말함.
- 주어진 자원 내에서 스레드를 최대한 많이 생성하면서도 효율적으로 컨텍스트 스위칭 비용을 줄일 수 있다. 

![image](https://github.com/user-attachments/assets/2b531c1c-5327-4200-b27c-f74de6c68bcd)

- Virtual Thread가 도입 된 후로, Platform Thread Pool은 스케줄러에 의해 관리된다. 기본 스케줄러는 ForkJoinPool을 사용하며, Virtual Thread의 작업 분배를 담당한다.



----

참고링크 

https://techblog.woowahan.com/15398/

https://f-lab.kr/insight/understanding-virtual-threads?gad_source=1&gclid=Cj0KCQjwpvK4BhDUARIsADHt9sR_3ypG2wI8znWpgCtRKVwHrOrw9JKSKGbWLfUILob6T3Eyrl_4kHMaAp5_EALw_wcB

https://nangmandeveloper.tistory.com/6

https://velog.io/@yukicow/Virtual-Thread%EB%9E%80-%EB%AC%B4%EC%97%87%EC%9D%B4%EA%B3%A0-%EC%96%B8%EC%A0%9C-%EC%82%AC%EC%9A%A9%ED%95%98%EB%A9%B4-%EC%A2%8B%EC%9D%84%EA%B9%8C