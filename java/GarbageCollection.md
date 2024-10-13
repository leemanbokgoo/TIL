
# 가비지 컬렉터

가비지 컬렉터란 불필요한 메모리를 해제해주는 JVM안의 실행 엔진 입니다. heap영역에서 더이상 참조되지않아 불필요한 값들을 제거 한다고 할 수 있습니다. 여기서 불필요한 메모리는 무엇일까요? heap 영역에서 더이상 참조되지않는 값을 말합니다. 그렇담 heap영역에 대해 알 필요가 있습니다 .

![image](https://github.com/user-attachments/assets/bfd7f7cc-b110-42da-9168-66defa4ea3a1)

위의 그림은 힙 영역을 나타낸 그림입니다. Yong영역, old영역이 있습니다 . young영역과 old영역은 다음과 같습니다.

**yong영역**

새롭게 생성된 객체가 할당되는 영역
대부분의 객체가 금방 접근 불가능 상태가 되기때문에 매우 많은 객체가 Young영역에 생성되었다가 사라진다.
이 영역에서 객체가 사라질때 Minor GC가 발생한다고 말한다.

**old영역**


접근 불가능한 상태로 되지않아 Young영역에서 살아남은 객체가 여기로 복사된다.
대부분 Youn영역보다 크게 할당하며 크기가 큰 만큼 Young영역보다는 GC가 적게 발생한다.
이 영역에서 객체가 사라질때 Major GC(혹은 Full GC)가 발생한다고 말한다. 
가비지 컬렉션은 바로 이 heap의 young영역과 old 영역에서 불필요한 메모리를 제거합니다. 그림과 같이 young 영역은 Eden, S1, S2로 이루어져있습니다.

 

---


## 가비지 컬렉션 동작원리

**stop the world**

GC실행을 위해 JVM이 어플리케이션 실행을 멈추는 것으로 GC가 실행될때는 GC를 실행하는 쓰레드를 제외한 모든 스레드들이 작업을 멈춥니다. GC작업이 완료된 후에 중단했던 작업을 시작합니다. GC튜닝이란 보통 stop-the-world시간을 줄이는 걸 말합니다.
 

**mark and sweep**

GC의 과정을 mark and sweep이라고도 하고 GC가 stack의 모든 변수 또는 Reachable 객체를 스캔하면서 각각 어떤 객체를 참조하고있는지 찾는 과정이 Mark라고 한다 <- 이과정에서 stop the world가 발생한다.
이후 mark되어있지않은 객체들을 heap에서 제거하는 과정이 sweep 이다
 

먼저 새로운 값이 생성되면 Eden으로 할당됩니다. 두개의 survivor은 비워진채로 시작합니다.
Eden 영역이 가득차면 MinorGC가 발생하고 Reachable 오브젝트( 특정객체나 변수에 의해 참조되고있으며 프로그램 실행중 접근 가능함을 의미, 객체에서 사용중이기때문에 메모리에서 제거 되지않음.) 은 전부 S0으로 옮겨집니다.

 Unreachable 오브젝트들은(어떤 객체나 변수에서도 참조되지않는 객체,이경우 프로그램은 더이상 해당 객체를 사용하지않기때문에 GC에 의해 메모리에서 해체 될수 있음)  지워지고 eachable 오브젝트들은 Survivor Space 로 이동한합니다  기존에 S1 에 있었던 Reachable 오브젝트들은 S0 으로 옮겨지는데, 이때, age 값이 증가되어 옮겨집니다. 살아남은 모든 오브젝트들이 S0 으로 모두 옮겨지면, S1 와 Eden 은 클리어 되고 Survivor Space 에서 Survivor Space 로의 이동은 이동할때마다 age 값이 증가합니다.

MinorGC가 발생하면 2번 과정이 반복되는데 S0가 가득차있으면 살아남은 오브젝트들은 S1으로 옮겨지면서 또 한번 Eden과 S2는 클리어됩니다. 이때도 age값은 증가됩니다. Suvivor 영역으로 이동할때마다 age값은 증가합니다.

young 영역에서 계속 해서 살아남으며 age값이 증가되면 old영역으로 옮겨집니다.
MinorGC가 계속 해서 반복되면 위의 작업도 계속해서 반복합니다.
old영역이 가득차게 되면 MajorGC가 발생하게 됩니다.
 

 

 

 

 

참고링크 

https://yaboong.github.io/java/2018/06/09/java-garbage-collection/
