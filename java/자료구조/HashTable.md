# HashTable
![image](https://github.com/user-attachments/assets/1f3f7c77-6383-48fe-9c01-642da285744f)
- 해시 테이블은 Key, Value로 데이터를 저장하는 자료구조 중 하나로 빠르게 데이터를 검색 할 수 있는 자료구조이다. 해시 테이블이 빠른 검색 속도를 제공하는 이유는 내부적으로 배열(버킷)을 사용하여 데이터를 저장하기때문이다. 해시 테이블은 각각의 key값에 해시 함수를 적용해 배열의 고유한 Index를 생성하고 이 index를 이용해 값을 저장하거나 검색하게 된다. 여기서 실제 값이 저장되는 장소를 버킷 또는 슬롯이라고 한다. 
- 예를 들어  Key, Value가 ("John Smith", "521-1234")인 데이터를 크기가 16인 해시 테이블에 저장한다고 하자. 그러면 먼저 index = hash_function("John Smith") % 16 연산을 통해 index 값을 계산한다. 그리고 array[index] = "521-1234" 로 전화번호를 저장하게 된다.
이러한 해싱 구조로 데이터를 저장하면 Key값으로 데이터를 찾을 때 해시 함수를 1번만 수행하면 되므로 매우 빠르게 데이터를 저장/삭제/조회할 수 있다. 해시테이블의 평균 시간복잡도는 O(1)이다.

## HashTable의 특징
- HashTable은 HashMap과 내부 구조가 동일하며 사용방법 또한 매우 유사.
- 마찬가지로 키는 중복이 안되지만 값은 중복을 허용한다.
- HashTable의 경우 병렬 프로그래밍을 지원하여 병렬 처리를 하면서 자원의 동기화를 고려해야 하는 상황일 때 사용하기 적합하다. 그 외 병렬 처리를 하지 않거나 동기화를 고려하지 않는 상황이라면 HashMap을 사용한다.
- 또한, HashMap은 값으로 null이 입력이 가능하지만, HashTable에서는 null 입력이 불가능하다.

## HashTable 장점
- 해시 충돌이 없는 상태에서 배열, 리스트 같은 선형적인 구조는 물론 트리와 구조보다 빠른 탐색
- 해시를 사용하기에 해시 값을 알아도 key를 예측하기 어려움

## HashTable 단점
- 동작은 HashMap과 비슷하지만, 모든 메서드에 대해 전체 맵에 락을 걸기 때문에 성능이 떨어진다
    - HashTable은 Java 초기 버전부터 제공되어 온 thread-safe한 Map 구현체이다. HashTable의 모든 공개 메서드는 'synchronized'키워드를 사용하여 동기화된다(전체 레벨에서 lock). 이로 인해 한 시점에 하나의 스레드만이 맵의 메서드를 실행할 수 있게 된다. 
    - 모든 메서드가 동기화되어있기 때문에, 다수의 스레드가 동시에 맵에 접근할 경우 성능저하가 발생하게 된다. 한 스레드가 맵의 메서드를 실행하는 동안 다른 스레드는 대기상태가 되어야하기 때문이다.
    - 따라서 HashTable은 더 이상 권장되지 않는다. Java 1.5부터는 동시성을 위해 ConcurrentHashMap이 도입되었고 HashTable은 레거시로 간주된다.
    - HashTable은 thread-safe하기 때문에, 멀티 쓰레드 환경에서 사용할 수 있다. 이는 데이터를 다루는 메소드(get(), put(), remove() 등)에 synchronized 키워드가 붙어 있다. 해당 키워드는 메소드를 호출하기 전에 쓰레드간 동기화 락을 건다. 그래서 멀티 쓰레드 환경에서도 데이터의 무결성을 보장한다. 그러나, 쓰레드간 동기화 락은 매우 느린 동작이라는 단점이 있다

이러한 맥락에서 ConcurrentHashMap은 부분 락(Fine-grained Locking) (Fine-grained Locking)을 제공한다. ConcurrentHashMap는 내부적으로 여러개의 세그먼트들로 나뉘어져 있는데 각 세그먼트는 별도로 락되기 때문에 여러 스레드가 동시에 맵의 다른 부분을 수정할 수 있어 HashTable에 비해 훨씬 높은 동시성과 성능을 제공한다.
- 해시 충돌 발생 시 탐색이 시간 복잡도 O(N)에 점점 수렴함
- 정렬이나 순차적인 메모리 저장이 필요한 경우 적합하지 않음
- 해시 함수의 성능에 따라 해시 테이블 전체 성능이 크게 영향을 받는다.


## HashMap / HashTable / ConCurrentHashMap

![image](https://github.com/user-attachments/assets/ebf13b4d-3591-4993-8e43-be396f699a65)
출처 : https://sharonprogress.tistory.com/327

- HashMap: 가장 일반적으로 사용되는 Map 구현체로, 키-값 쌍을 저장한다. 동기화를 지원하지 않아 멀티 스레드 환경에서는 적합하지 않지만 단일 스레드 환경에서는 빠른 성능을 제공한다.
- Hashtable: HashMap과 유사하지만, 모든 메서드가 동기화되어 있다. 이로 인해 멀티 스레드 환경에서 안전하게 사용할 수 있지만, 그에 따른 성능 저하가 있다. 또한, Hashtable은 null 값을 키나 값으로 허용하지 않습니다.
- ConcurrentHashMap: 멀티 스레드 환경에서의 성능을 최적화하기 위해 설계된 Map 구현체이다. 전체 맵을 잠그지 않고도 동시성을 관리할 수 있는 세분화된 락(세그먼트 락) 메커니즘을 사용한다. 이를 통해 높은 동시성과 성능을 제공하며, 멀티 스레드 환경에서의 데이터 일관성을 유지한다. ConcurrentHashMap은 thread-safe하기 때문에, 멀티 쓰레드 환경에서 사용할 수 있다. 이 구현체는 HashMap의 동기화 문제를 보완하기 위해 나타났다. 동기화 처리를 할 때, 어떤 Entry를 조작하는 경우에 해당 Entry에 대해서만 락을 건다. 그래서 HashTable보다 데이터를 다루는 속도가 빠르다. 즉, Entry 아이템별로 락을 걸어 멀티 쓰레드 환경에서의 성능을 향상시킨다.

#### 결론 
- 싱글 쓰레드 환경이면 HashMap을, 멀티 쓰레드 환경이면 HashTable이 아닌 ConcurrentHashMap을 쓰자. 그 이유는 HashTable보다 ConcurrentHashMap이 성능적으로 우수하기 때문이다. 앞에서도 언급했듯이 HashTable은 쓰레드간 락을 걸어 데이터를 다루는 속도가 느리다. 반면, ConcurrentHashMap은 Entry 아이템별로 락을 걸어 데이터를 다루는 속도가 빠르다.

### 질문
#### Q1. 왜 HashTable보다 ConcurrentHashMap을 사용하는 것이 권장되는가?
- ConcurrentHashMap은 HashTable보다 성능이 우수하기 때문입니다. HashTable은 모든 메서드에 대해 전체 맵에 락을 걸어 한 번에 하나의 스레드만 작업할 수 있어 성능 저하를 유발합니다. 반면, ConcurrentHashMap은 세분화된 락(Fine-grained Locking)을 적용하여 여러 스레드가 동시에 맵의 서로 다른 부분을 수정할 수 있어 동시성과 성능이 크게 향상됩니다.


#### Q2. 해시 테이블이 빠른 탐색을 제공하지만 성능에 영향을 줄 수 있는 주요 요인은 무엇인가?
- 해시 테이블의 성능은 해시 함수의 품질과 충돌 해결 방식에 크게 영향을 받습니다. 해시 충돌이 발생하면 평균 시간 복잡도가 O(1)에서 O(N)으로 증가할 수 있으며, 해시 함수가 고르게 인덱스를 분배하지 못하면 특정 버킷에 데이터가 몰려 성능 저하가 발생할 수 있습니다.




---

참고링크 : 

https://mangkyu.tistory.com/102

https://ittrue.tistory.com/153

https://sharonprogress.tistory.com/327

https://tecoble.techcourse.co.kr/post/2021-11-26-hashmap-hashtable-concurrenthashmap/