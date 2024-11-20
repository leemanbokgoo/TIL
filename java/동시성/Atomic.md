# 먼저 선행되어야할 개념

![image](https://github.com/user-attachments/assets/088461e2-854c-4c09-96a8-5ad718aa89ce)

# 블로킹 (blocking)
![image](https://github.com/user-attachments/assets/861417ea-62c0-4651-91cd-fdee6c136f94)
- A 함수가 B 함수를 호출 할 때, B 함수가 자신의 작업이 종료되기 전까지 A 함수에게 제어권을 돌려주지 않는 것.
## 논블로킹 (non-blocking)
![image](https://github.com/user-attachments/assets/efcf7442-6b7a-40e4-b643-92d5d475b570)
- A함수가 B함수를 호출 할때, B함수가 제어권을 바로 A함수에게 넘겨주면서, A함수가 다른 일을 할 수 있도록 하는 것.논블로킹 코드는 특정 작업이 완료될 때까지 기다리지 않고, 다른 작업을 수행할 수 있는 코드를 말한다.

 
## Atomic Type 
- atomic 변수는 멀티 스레드 환경에서 원자성을 보장하기 위해 나온 개념이다.멀티쓰레드 환경에서 동기화 문제를 synchronized 키워드를 사용하여 , 락을걸곤하는데 이런 키워드 없이 동기화문제를 해결하기 위해 고안된 방법이다.
- synchronized는 특정 Thead가 해당 블럭 전체를 lock을 하기떄문에 다른 Thread는 아무런 작업을 하지 못하고 기다리는 상황이 될수 있기때문에 , 낭비가 심하다. 그래서 NonBlocking하면서 동기화 문제를 해결하기 위한 방법이 Atomic이다.
-  공유 중인 가변 데이터에 여러개의 스레드가 동시에 접근하게되면 경쟁상태(Race Condition)이 발생할 수 있고 가시성 문제가 발생할 수 있다. 일반적으로 자바에서는 동기화에 대한 문제를 synchronized, volatile, Atomic.class를 사용해서 해결한다
- synchronized 는 선언된 메서드의 코드 섹션 전체에 락을 걸고 접근하는 스레드들은 block or suspended 상태로 변경되게 된다. 스레드들이 blocking 되는 과정과 다시 resuming 되는 과정에서 시스템의 자원을 소모하게 된다. 100개의 스레드가 동시에 접근을 한다면, 99개의 스레드가 이러한 과정을 거치게 되는 것이다. 바로 이 부분에서 성능 저하가 발생한다.
- Atomic의 핵심은 이러한 소모 비용을 줄이는 non-blocking 방식을 사용한다는 점에서 차이점이 존재한다. 즉, 어떤 스레드도 suspended 되지 않기 때문에 context switch를 피할 수 있다.
- 흔하게 사용되는 Atomic 변수 클래스로는 AtomicInteger, AtomicLong, AtomicBoolean, AtomicReference 등이 있고, 이들은 atomic하게(동시성을 보장받으며) 업데이트 될 수 있는  int, long, boolean, object reference 를 각각 나타낸다
- Atomic 변수가 갖는 장점은 가장 적은 비용으로 동시성을 제어할 수 있다는 점이다. 이는 다시 말해 Atomic 변수를 사용하는 것이 동시성을 제어하면서도 병행성을 가장 적게 희생시키는 방법이라는 뜻이다. 그렇다면 Atomic 변수는 어떠한 방식으로 동시성을 제어하고 있는지 살펴보자.


## CAS 알고리즘
-  CAS란 변수의 값을 변경하기 전에 기존에 가지고 있던 값이 내가 예상하던 값과 같을 경우에만 새로운 값으로 할당하는 방법.CAS는 값을 변경하기 전에 한 번 더 확인하는 거라고 보면 됨.
- Java에서 제공하는 Atomic Type은 이러한 CAS를 하드웨어(CPU)의 도움을 받아 한 번에 단 하나의 스레드만 변수의 값을 변경할 수 있도록 제공하고있다.
-  Atomic 변수는 내부적으로 CAS 방식을 사용해 동시성을 제어한다. CAS란 멀티쓰레드 환경에서 동시성을 보장하기 위한 컴퓨터 명령어(instruction)이다. 
- 가령 하나의 쓰레드에서 동작하고 있는 프로그램이 특정 메모리에 접근하여 얻어온 값 A를 B로 업데이트 하는 상황을 생각해보자. 
- CAS 명령어는 메모리 M에 위치한 값을 주어진 A값(up-to-date)과 비교하여 두 값이 일치할 때에만 메모리의 값을 B로 업데이트 하는데, 이 모든 과정이 하나의 atomic한 명령어 안에서 수행된다. 그리고 이러한 명령어의 원자성이 동시성을 보장해주게 된다.
- CAS는 다른 스레드의 접근을 막지 않는다
    - Java에서 동시성 제어를 위해 Atomic 변수를 사용하는 것이  lock 기반의 방식들(예. synchronized)를 사용하는 것에 비해 성능이 좋은 이유는 CAS 명령어는 값이 업데이트 되는 중에도 다른 스레드의 접근을 허용하기 때문이다. 
    - 즉, 하나의 스레드에서 특정 변수의 값을 업데이트 하고자 할 때 다른 쓰레드의 접근을 금지하는 lock기반의 방식들과 달리, CAS 를 이용할 경우 변수의 값을 업데이트 하기 위해 경합하던 쓰레드들은 그들이 값 없데이트에 성공했는지 여부(true/false)만 알 수 있을 뿐이다.
    -  따라서 CAS를 이용해서 동시성을 제어하는 경우 쓰레드 들은 값을 업데이트 하기 위해 lock이 해제되길 기다리는 게 아니라 계속해서 그들의 작업을 수행할 수 있고, 이를 통해 Context-switching 이 발생하는 것을 피할 수 있게 된다. 

![image](https://github.com/user-attachments/assets/28665818-1702-4cda-bdf2-42be013b221a)
![image](https://github.com/user-attachments/assets/eb088f4b-7b5a-46c5-bac2-edd68a0d82d7)

- 멀티 쓰레드 환경, 멀티 코어 환경에서 각 CPU는 메인 메모리에서 변수값을 참조하는게 아닌, 각 CPU의 캐시 영역에서 메모리를 값을 참조하게 된다.
-  ([그림2] CPU 캐시 메모리 참고) 이때, 메인 메모리에 저장된 값과 CPU 캐시에 저장된 값이 다른 경우가 있다. (이를 가시성 문제라고 한다.) 그래서 사용되는 것이 CAS 알고리즘이다. 
- 현재 쓰레드에 저장된 값과 메인 메모리에 저장된 값을 비교하여 일치하는 경우 새로운 값으로 교체하고, 일치 하지 않는 다면 실패하고 재시도를 한다. 
- 이렇게 처리되면 CPU캐시에서 잘못된 값을 참조하는 가시성 문제가 해결되게 된다. 참고로 synchronized 블락의 경우 synchronized 블락 진입전 후에 메인 메모리와 CPU 캐시 메모리의 값을 동기화 하기 때문에 문제가 없도록 처리한다
- 하지만 AtomicInteger를 보면 CAS 알고리즘을 사용하지 않고 공유 변수를 읽거나 쓰는 작업이 있다. 이때 get() 과 set() 은 그 자체로 atomic 연산이므로 원자성 문제가 발생하지 않으므로 volatile을 통해 가시성 문제만 해결해 주는 것이다. 단순 대입 연산은 원자 연산임을 기억하자.


## atomic variable VS synchornized
![image](https://github.com/user-attachments/assets/d5c5b7e1-71c2-439a-bc36-9989b2dee4e9)
- atomic 변수가 활용하는 CAS 알고리즘은 원자성뿐 아니라 가시성 문제도 해결해주는 것을 볼 수 있다. 또한 non-blocking 이 가능하므로 blocking 방식인 synchornized 보다 성능상 이점이 있다.
- Atomic 변수는 가벼운 연산에서 효과적이고 성능이 뛰어난 반면, synchronized는 더 복잡한 상황에서 안전하고 유연하게 사용할 수 있습니다. 상황에 따라 적절한 방법을 선택하는 것이 중요.
- synchronized는 락을 사용하여 한 번에 하나의 스레드만 특정 코드 블록이나 메서드를 실행하도록 보장.JVM 수준에서 모니터 락(monitor lock)을 활용.
    - 성능: 락 오버헤드가 있으므로, 특히 경합이 많은 경우 성능이 떨어질 수 있다.그러나 최신 JVM에서는 락 최적화(예: 경량 락, 비관적 락)를 통해 성능이 개선되었음.
    - 사용 사례: 여러 연산이 복잡하게 얽혀있거나, Atomic 변수만으로 처리하기 어려운 경우에 적합하다.
- Atomic 변수 (예: AtomicInteger, AtomicLong)
    - 구현 방식: Atomic 변수는 내부적으로 CAS(Compare-And-Swap) 연산을 사용하여 락(lock)을 사용하지 않고도 동시성을 처리함.AS는 값이 특정 상태에서만 업데이트되도록 보장하며, 실패 시 재시도한다.
    - 성능: 락이 필요 없기 때문에 성능이 더 우수하며, 특히 경합이 적을 때 효과적이다.
    - 사용 사례: 간단한 카운터 증가/감소, 플래그 설정 등 가벼운 연산에서 주로 사용된다.
    - CAS 방식으로 스레드 Lock 없이 동기화를 보장한다.
    - Unsafe를 이용하여 메모리를 저수준으로 다뤄 성능을 최적화한다.
    - 쓰기가 많이 발생해서 스레드 경합(Racing)이 자주 발생한다면, 필요없는 가시성 보장(volatile)이 자주 발생한다.
    - 이 경우 Atomic의 API를 사용하면 많은 성능 향상을 얻을 수 있다. 읽기에는 그냥 사용하다가 Dynamic Striping 을 통해 경합이 발생한다면 스레드 별 메모리(Cell)에 따로 연산한 후, 나중에 합치는 방법이다.
    - 또한 Cpu Cache의 False Sharing을 최소화하기 위해 Cpu Cache Line의 크기로 패딩을 구현한다.
 

---

참고 링크 

반드시 읽어볼 링크 
https://inpa.tistory.com/entry/%F0%9F%91%A9%E2%80%8D%F0%9F%92%BB-%EB%8F%99%EA%B8%B0%EB%B9%84%EB%8F%99%EA%B8%B0-%EB%B8%94%EB%A1%9C%ED%82%B9%EB%85%BC%EB%B8%94%EB%A1%9C%ED%82%B9-%EA%B0%9C%EB%85%90-%EC%A0%95%EB%A6%AC

https://rachel0115.tistory.com/entry/Java-%EB%A9%80%ED%8B%B0-%EC%8A%A4%EB%A0%88%EB%93%9C-%ED%99%98%EA%B2%BD%EC%97%90%EC%84%9C-%EB%B0%9C%EC%83%9D%ED%95%A0-%EC%88%98-%EC%9E%88%EB%8A%94-%EB%8F%99%EC%8B%9C%EC%84%B1-%EC%9D%B4%EC%8A%88%EC%99%80-%ED%95%B4%EA%B2%B0-%EB%B0%A9%EB%B2%95#Atomic%20Type-1

https://velog.io/@seongwop/Java-volatile-synchronized-Atomic-%EC%9D%84-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EB%8F%99%EA%B8%B0%ED%99%94

https://velog.io/@eunsiver/%ED%94%84%EB%A1%9C%EC%84%B8%EC%8A%A4-%EB%8F%99%EA%B8%B0%ED%99%94

https://velog.io/@sherlockid8/Java-Atomic-type%EC%9D%80-%EC%99%9C-%EC%93%B0%EB%8A%94%EC%A7%80-CAS-Compared-And-Swap%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98%EC%9D%B4%EB%9E%80-AtomicInteger%EC%9D%98-%ED%99%9C%EC%9A%A9%EB%B2%95

https://saltyzun.tistory.com/37