#  HashMap 

```
public class HashMap<K,V> extends AbstractMap<K,V>
    implements Map<K,V>, Cloneable, Serializable {
}

```
- HashMap은 Map 인터페이스를 구현하고있는 대표적인 클래스로 Map 구조인 key-value 쌍으로 구성되어있다.

 여기서 HashMap을 설명하기전에 먼저 해싱이라는 개념을 짚고 넘어가도록 한다. 

### 해싱과 HashMap 

![image](https://github.com/user-attachments/assets/35e9a2f1-438c-4699-9d33-eb38e0bb8738)
- 이미지 출처 : https://velog.io/@cchoijjinyoung/%EC%9E%90%EB%A3%8C%EA%B5%AC%EC%A1%B0-5-HashMap%ED%95%B4%EC%8B%9C%EB%A7%B5%EC%9D%84-%EC%95%8C%EC%95%84%EB%B3%B4%EC%9E%90
- 해시 맵의 핵심은 해싱이라는 과정을 통해 데이터의 저장 위치를 결정하는 것이다. key를 hashMap에 주면 해싱 함수에 의해 해시코드로 변환된다.
- 해싱이 된 key를 해시 코드라 하는데 해시 코드는 데이터의 저장 위치를 빠르게 찾는데 사용되며 이는 해시맵의 검색 속도를 향상시키는 핵심 요소이다. 
- 해싱은 키의 해시코드를 계산하고나면 이를 기반으로 데이터가 저장될 위치 즉, 버킷을 결정한다. 여기서 **버킷**이란 hashMap에서 값을 담는 배열이다.
- 이는 모두 hashMap의 인덱스를 구하기위한 과정이다.
- HashMap은 자료구조로 배열(array)을 사용한다. 배열은 **인덱스**를 통해 바로 접근이 가능하다는 장점이 있다. HashMap은 해싱(Hashing)을 통해 Map 데이터가 저장 될 위치의 인덱스를 구한다. 그래서 이름이 HashMap이다.
 
## HashMap 특징
- 앞서 말해듯이 hashMap은 해싱이라는 과정을 통해 인덱스를 가지게된다. 키 기반의 빠른 인덱스로 키를 사용하여 값을 빠르게 검색하거나 수정 할 수 있다.  이렇듯, HashMap은 key만 있다면 해싱함수를 통해 바로 해당 인덱스의 위치로 이동할 수 있다. key를 통해 인덱스를 산출 후, 데이터에 접근하면 시간복잡도가 O(1)이다.
- 해싱 과정으로 인덱스를 정함으로 내부적으로 키의 순서를 보장하지않는다. 또한 키는 중복될 수 없다.키가 중복되면 키를 통해 값을 찾으려 할때 문제가 생길 것이다. 그러므로 키는 중복 될 수 없다. 이미 존재하는 키에 대해 값을 저장하면 기존 값이 덮어씌워진다.
- null 키와 값을 저장할 수있다.
- 어떤 객체든 키로 사용할 수 있다.

여기까지 읽으면 hashMap에서 키가 중요한 동작 원리임을 알 수 있다. 하지만 hashMap에서 키를 해싱하는 과정에서 **해시 충돌**이 일어나면 어떻게 할까?

## 해시 충돌
- key는 기본 자료형부터 객체까지 저장할 수 있다. 하지만 인덱스는 배열의 크기보다 작은 정수로 한정되어 있므로 key들 사이의 충돌 즉, 해싱 충돌은 불가피하다. 고로 해싱 충돌은 **서로 다른 key들이 같은 인덱스를 부여받는 것이다.** 

### 여기서 잠깐. 왜 해시 충돌은 불가피할까?
- hashMap은 각 객체의 hashCode()메서드가 반환하는 값을 사용하는데 결과 반환값이 int이다.
- int형의 데이터 크기인 32bit 이기때문에 이 크기를 넘어서는 해시 함수를 만들 수없다. 또한 모든 HashMap 객체에서 O(1)로 랜덤 접근하려면 모든 HashMap의 원소가  2^32인 배열을 갖고 있어야하기때문에 비효율적이다.
- 따라서 해시함수를 이용하는 연관 배열(key-value형 자료구조)에서는 메모리를 절약하기위해서 실제 해수 함수가 표현할 수 있는 정수의 범위보다 작은 M개의 원소가 있는 배열 만을 사용한다. 

```
int index = X.hashCode() % M;

```

### 해시충돌 해결 방법

#### 개별 체이닝 Separate chaing 
- 각 버킷(배열)마다 LikedList의 첫부분을 저장해둔 후 동일한 인덱스 값이 들어올 경우 기존의 값을 뒤로 미룬 후 새로 들어온값을 head로 사용하는 방식이다. 즉, 충돌이 생기면 해시 버킷을 연결하여 해결 하는 방식이다. 충돌이 발생하면 해당 버킷에 있는 연결 리스트에 새로운 키-쌍을 추가한다
![image](https://github.com/user-attachments/assets/68d45850-6491-43d9-8591-b0c43761d979)
- 이미지 출처 : https://velog.io/@dlzlqlzl/%EC%9E%90%EB%A3%8C%EA%B5%AC%EC%A1%B0-%ED%95%B4%EC%8B%9C-%ED%85%8C%EC%9D%B4%EB%B8%94
- 개별 체이닝의 절차 
    - 1. 캐의 해시값을 계산
    - 2. 해시값을 이용해 배열의 인덱스를 구함
    - 3. 같은 인덱스가 있으면 Linked List로 연결
- 기본적으로 시간복잡도가 O(1)이지만 최악의 경우, 즉 모든 키값에 대하여 해시 충돌이 일어날 경우 O(n)이 된다. 

#### 그런데 계속 충돌이 일어나면 계속 Linked List로 연결을 하는 걸까?
- 연결리스트를 탐색할때의 시간복잡도는 얼마일까? 데이터가 n번 충돌해 연결 리스트의 n번째 노드에 저장된다면 해당 노드 탐색을 위한 시간복잡도는 O(n)이 된다.
- 따라서 충돌이 많아질수록 효율이 점점 떨어진다. 따라서 일정 충돌 수가 넘어가면 트리 방식을 사용해 성능을 O(log n)으로 개선한다.
- 자바 8의 해시 테이블 구현체인 HashMap은 연결 리스트 구조를 좀 더 최적화 해서 데이터 개수가 많아지만 레드-블랙 트리에 저장하는 형태를 병행하기도 한다.
- 레드-블랙 트리 : https://velog.io/@kku64r/rbtree
![image](https://github.com/user-attachments/assets/412675b7-8eda-421d-af1d-0b31714c3bde)
- 자바 8의 hashMap을 들어가보면 상수로 기준이 정해져있다. 즉, 하나의 해시버킷에 8개의 key-value가 모이면 Linked List를 트리로 변경한다. 만약 데이터를 삭제해 6개가 되면 다시 연결 리스트로 변경한다. 
- 자바도 계속 해서 성능을 튜닝하고 진화 해오고 있다는 것을 알수있다. 


#### 개방주소법 Open addressing
- 해시 충돌시 충돌이 발생한 해시버킷이 아닌 다른 해시 버킷을 찾아 새로운 데이터를 저장하는 방법을 말한다. 즉, 개방 주소법은 해시 충돌이 발생하면 해시 함수를 다시 계산하여 새로운 인덱스를 찾는다.
- Linear Probing: 현재의 버킷 index로부터 고정폭 만큼씩 이동하여 차례대로 검색해 비어 있는 버킷에 데이터를 저장한다.
- Quadratic Probing: 해시의 저장순서 폭을 제곱으로 저장하는 방식이다. 예를 들어 처음 충돌이 발생한 경우에는 1만큼 이동하고 그 다음 계속 충돌이 발생하면 2^2, 3^2 칸씩 옮기는 방식이다.
- Double Hashing Probing: 해시된 값을 한번 더 해싱하여 해시의 규칙성을 없애버리는 방식이다. 해시된 값을 한번 더 해싱하여 새로운 주소를 할당하기 때문에 다른 방법들보다 많은 연산을 하게 된다.

그렇담 자바에서는 어떻게 해시 충돌을 해결하고있을까?

### 자바의 해시 충돌 해결 방법의 역사

- jdk7까지는 linked list를 사용한 separate chaning과 보조해시 함수를 활용했다.

### 왜 separate chaning을 사용했을까?
-  Open Addressing은 데이터를 삭제할 때 처리가 효율적이기 어려운데, HashMap에서 remove() 메서드는 매우 빈번하게 호출될 수 있기 때문이다. 게다가 HashMap에 저장된 키-값 쌍 개수가 일정 개수 이상으로 많아지면, 일반적으로 Open Addressing은 Separate Chaining보다 느리다. 
- Open Addressing의 경우 해시 버킷을 채운 밀도가 높아질수록 Worst Case 발생 빈도가 더 높아지기 때문이다. 반면 Separate Chaining 방식의 경우 해시 충돌이 잘 발생하지 않도록 '조정'할 수 있다면 최악 또는 최악에 가까운 일이 발생하는 것을 줄일 수 있다
-  Java 7에서의 해시 버킷 관련 구현

    ```
    transient Entry<K,V>[] table = (Entry<K,V>[]) EMPTY_TABLE;  
    // transient로 선언된 이유는 직렬화(serializ)할 때 전체, table 배열 자체를 직렬화하는 것보다
    // 키-값 쌍을 차례로 기록하는 것이 더 효율적이기 때문이다.

    static class Entry<K,V> implements Map.Entry<K,V> {  
            final K key;
            V value;
            Entry<K,V> next;
            int hash;

    Entry(int h, K k, V v, Entry<K,V> n) {  
                value = v;
                next = n;
                key = k;
                hash = h;
            }

            public final K getKey() { … }
    public final V getValue() { …}  
            public final V setValue(V newValue) { … }
            public final boolean equals(Object o) { … }
            public final int hashCode() {…}
            public final String toString() { …}

    void recordAccess(HashMap<K,V> m) {… }

    void recordRemoval(HashMap<K,V> m) {…}  
    }
    ```
- Java 7에서의 put() 메서드 구현

    ```
    public V put(K key, V value) { if (table == EMPTY_TABLE) { inflateTable(threshold); // table 배열 생성 } // HashMap에서는 null을 키로 사용할 수 있다. if (key == null) return putForNullKey(value); // value.hashCode() 메서드를 사용하는 것이 아니라, 보조 해시 함수를 이용하여 // 변형된 해시 함수를 사용한다. "보조 해시 함수" 단락에서 설명한다.  
        int hash = hash(key);

        // i 값이 해시 버킷의 인덱스이다.
        // indexFor() 메서드는 hash % table.length와 같은 의도의 메서드다.
        int i = indexFor(hash, table.length);



        // 해시 버킷에 있는 링크드 리스트를 순회한다.
        // 만약 같은 키가 이미 저장되어 있다면 교체한다.
        for (Entry<K,V> e = table[i]; e != null; e = e.next) {
            Object k;
            if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
                V oldValue = e.value;
                e.value = value;
                e.recordAccess(this);
                return oldValue;
            }
        }

        // 삽입, 삭제 등으로 이 HashMap 객체가 몇 번이나 변경(modification)되었는지
        // 관리하기 위한 코드다.
        // ConcurrentModificationException를 발생시켜야 하는지 판단할 때 사용한다.
        modCount++;


        // 아직 해당 키-값 쌍 데이터가 삽입된 적이 없다면 새로 Entry를 생성한다. 
        addEntry(hash, key, value, i);
        return null;
    }

    ```
    출처 : https://d2.naver.com/helloworld/831311


## Java 8 HashMap에서의 Separate Chaining은?
- jdk8에서 linked list와 red black tree를 혼용한 separate chaining을 활용하여  충돌을 한 key-value쌍이 적을때는 Linked List로 작동을 한다.
충돌을 한 key-value쌍이 특정 임계치에 도달하면 red-black tree로 작동을 한다.
    - Linked List는 탐색하는데 시간복잡도가 O(n)의 비용이 드나 red black tree는 O(log n) 이 들기 때문에 jdk8 에서는 성능적으로 개선이 되었다고 할 수 있다.

#### 여기서 링크드 리스트를 사용할것인가 트리를 사용할 것인가에 대한 기준은 뭘까? 
![image](https://github.com/user-attachments/assets/33dd5e16-8743-4386-9bf1-be427dceeea1)
- 링크드 리스트를 사용할 것인가 트리를 사용할 것인가에 대한 기준은 하나의 해시 버킷에 할당된 키-값 쌍의 개수이다. 위의 그림에서 보듯 Java 8 HashMap에서는 상수 형태로 기준을 정하고 있다.
-  즉 하나의 해시 버킷에 8개의 키-값 쌍이 모이면 링크드 리스트를 트리로 변경한다. 만약 해당 버킷에 있는 데이터를 삭제하여 개수가 6개에 이르면 다시 링크드 리스트로 변경한다.
-  트리는 링크드 리스트보다 메모리 사용량이 많고, 데이터의 개수가 적을 때 트리와 링크드 리스트의 Worst Case 수행 시간 차이 비교는 의미가 없기 때문이다.
-  8과 6으로 2 이상의 차이를 둔 것은, 만약 차이가 1이라면 어떤 한 키-값 쌍이 반복되어 삽입/삭제되는 경우 불필요하게 트리와 링크드 리스트를 변경하는 일이 반복되어 성능 저하가 발생할 수 있기 때문.

### 질문

#### 1. HashMap에서 key의 순서를 보장하지 않는 이유는 무엇인가요?
-HashMap은 내부적으로 해시 값을 기반으로 데이터를 저장하기 때문에 키의 순서를 보장하지 않습니다. 해시 함수에 의해 계산된 해시 값은 데이터를 삽입한 순서와 무관하게 저장될 인덱스를 결정하므로, 입력 순서와 저장 순서가 일치하지 않습니다. 이를 통해 효율적인 데이터 검색과 수정이 가능하게 설계되었습니다.

#### 2. HashMap에서 연결 리스트를 트리로 변경하는 기준은 무엇인가요?
- Java 8부터 HashMap은 한 버킷에 저장된 key-value 쌍이 8개 이상일 경우, 연결 리스트를 Red-Black Tree로 변환합니다. 반대로 데이터가 삭제되어 key-value 쌍이 6개 이하로 줄어들면 다시 연결 리스트로 변경됩니다. 이는 충돌이 많아질 경우 검색 효율을 O(log n)으로 유지하여 성능을 개선하기 위한 전략입니다.



---

참고링크 

https://f-lab.kr/insight/understanding-java-hashmap

https://d2.naver.com/helloworld/831311

https://mangkyu.tistory.com/102