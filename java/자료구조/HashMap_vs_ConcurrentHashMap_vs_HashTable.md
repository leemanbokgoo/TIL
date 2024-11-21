# HashMap vs ConcurrentHashMap vs HashTable
- Hashtable, HashMap, ConcurrentHashMap 모두 Map 인터페이스를 implements한 AbstractMap을 상속받아 구현하고 있다는 공통점이 있다.

# Hash Table
- 해시 테이블 <Key, Map>형태로 데이터를 저장하는 자료구조 중 하나. 내부적으로 배열을 사용해 데이터를 저장하는데 이때 각각의 Key 값에 해시 함수를 적용해 이 배열의 고유한 index를 생성하고 값을 저장, 검색 할때 사용한다.
- 자바에서는 java.uitl 패키지안에 Map 인터페이스를 구현한 HashTable을 사용할 수 있다.
![image](https://github.com/user-attachments/assets/e539be72-9a39-4658-84ec-70d850b11c0e)
![image](https://github.com/user-attachments/assets/4db1679a-1d59-4809-b7cd-ad58d5af69bb)
- Hashtable 클래스에서 구현하고 있는 모든 메서드를 보면 synchronized 키워드가 붙어있다.
- 때문에 멀티 스레드 환경에서는 동시에 작업을 하려할때 요청마다 Lock을 걸기때문에 병목 현상이 발생할 수 있다. 모든 메서드가 synchronized라 Thread-safe 특징이 있음 -> 성능적으로 속도 저하가 올수있는 단점도 존재
- Collection Framework가 나오기 이전부터 존재하는 클래스이기때문에 최근에는 잘 쓰이지않음.


# HashMap
![image](https://github.com/user-attachments/assets/9a8c6da5-9ae8-4052-95ab-b3f190ea880a)
![image](https://github.com/user-attachments/assets/c437f033-32ef-4b47-a60e-c90788312c8d)
- Hashtable과 다르게 모든 메서드에 synchronized가 붙어있지않음 -> Lock을 걸지않기때문에 성능이 제일 좋지만 멀티 스레드 환경에서는 문제가 발생할 수 있음.
- Hashtable과 같은 동기화 장치가 없기때문에 동기화 문제가 발생할 수 있다 (non-Thread-safe)


# ConcurrentHashMap
- 자바에서는 java.util.conccurrent 패키지 안에 Map 인터페이스를 구현한 ConcurrentHashMap을 사용할 수 있다.
- Hashtable 클래스의 단점을 보완하면서 멀티 스레드 환경에서도 사용가능할 수 있는 클래스이다. 이는 JDK 1.5부터 Hashtable의 대안으로 도입되었다. 
- HashMap과는 다르게 key, value에 null값을 허용하지않는다. 
- 동기화를 지원한다는 것이 Hashtalbe과 같지만 성능은 ConcrurrentMap이 더 우수하며 Hashtable 과는 다르게 synchronized 키워드가 메소드 전체에 붙어 있지 않다.
- get 메소드에는 아예 synchronized가 존재하지 않고, put() 메소드에는 중간에 
synchronized 키워드가 존재한다.
![image](https://github.com/user-attachments/assets/584b429e-0aaa-4eba-aed3-8be773cd4bf3)
- Hashtable과 달리 일부에만 Lock을 걸어서 좀 더 성능 오버헤드를 개선했다.
- 따라서 ConcurrentHashMap은 읽기 작업에는 여러 쓰레드가 동시에 읽을 수 있지만, 쓰기 작업에는 특정 세그먼트 또는 버킷에 대한 lock을 사용한다.
ConcurrentHashMap은 버킷 단위로 lock을 사용하기 때문에 같은 버킷만 아니라면 lock을 기다릴 필요가 없다는 특징이 있다. (버킷당 하나의 lock을 가지고 있다.)
- **즉, 여러 쓰레드에서 ConcurrentHashMap 객체에 동시에 데이터를 삽입, 참조하더라도 그 데이터가 다른 세그먼트에 위치하면 서로 lock을 얻기 위해 경쟁하지 않는다.**


### 질문

#### 1. ConcurrentHashMap이 멀티 스레드 환경에서 Hashtable보다 효율적인 이유는 무엇인가요?
- ConcurrentHashMap은 **세분화된 락(fine-grained locking)**을 사용하기 때문에 Hashtable보다 효율적입니다. Hashtable은 모든 메서드에 synchronized 키워드를 사용하여 맵 전체를 락으로 보호하지만, ConcurrentHashMap은 특정 버킷 또는 세그먼트에만 락을 걸어 동시성을 높입니다. 즉, 여러 스레드가 동시에 작업하더라도 다른 버킷에 접근한다면 락 경합 없이 작업을 수행할 수 있습니다.

#### 2. ConcurrentHashMap에서 null 값을 허용하지 않는 이유는 무엇인가요?
- ConcurrentHashMap은 동시성을 지원하면서 데이터의 일관성을 유지하기 위해 설계되었습니다. null 값을 허용하면 동시성 작업 중에 null을 반환했을 때, 이는 실제 값이 null인지 아니면 데이터가 없는 것인지 구분하기 어려워집니다. 이를 방지하기 위해 key와 value에 null을 허용하지 않아 데이터의 명확성과 안정성을 보장합니다.

---

참고링크 : 

https://velog.io/@klloo/map-vs