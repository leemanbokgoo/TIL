
# Set 이란
- 객체를 중복해서 저장할 수 없으며 하나의 null 값만 저장할 수 있다.
- 중복을 자동으로 제거
- set은 비선형 구조이기떄문에 순서의 개념과 인덱스가 존재하지않는다. (*비선형 구조란? 비선형 구조는 데이터가 일직선으로 연결되지 않고, 여러 경로로 분기하거나 계층을 이루는 방식으로 구성된 데이터 구조. 대표적인 예로는 트리와 그래프가 있다.)
- 값을 추가/삭제하는 경우 set내부에 해당 값을 검색하여 기능을 수행 -> 처리가 List구조보다 느리다.대신 성능이 이셋중 가장 나쁘다.
- 자바에서 set을 구현한 주요 클래스는 HashSet, TreeSet, LinkedHashSet이 있다. 

### 종류
* hasSet: 순서가 전혀 필요없는 데이터를 해시 테이블에 저장한다. set중에 가장 성능이 좋음.
* TreeSet: 저장된 데이터의 값에 따라서 정렬되는 셋이다. **red-blck**이라는 트리 타입으로 값이 저장되며, HashSet보다 성능이 약간 느리다.
(red-black 트리 : https://code-lab1.tistory.com/62)
* LinkedHashSet : 연결된 목록 타입으로 구현된 해시 테이블에 데이터를 저장한다. 저장된 순서에 따라서 값이 정렬된다. 

성능차이가 발생하는 이유는 데이터 정렬 떄문

#### 사용용도
- 주로 순서에 상관없이 어떤 데이터가 존재하는지를 확인하기위한 용도로 많이 사용됨. 서버에 1분동안 요청한 IP의 수 라던지 (이럴경우 한 IP가 몇번을 요청했는지는 필요없다)

## 중복을 걸러내는 과정
![image](https://github.com/user-attachments/assets/c631fa9c-02ce-4a33-a88b-c9cdc9fdab39)

- set은 데이터가 중복되는 것을 허용하지않으므로 데이터가 같은지 확인하는 작업은 set의 핵심이다. 그래서 equals()메소드와 hashCode() 메소드를 구현하는 부분은 set에서 매우 중요하다.


## hashSet
- HashSet은 Java에서 제공하는 집합(Set) 자료구조의 한 구현체로, 중복을 허용하지 않고 요소들의 순서가 중요하지 않은 데이터를 저장하는데 사용됨. HashSet은 내부적으로 **해시맵(HashMap)**을 사용하여 데이터를 관리하며, 이를 통해 빠른 조회와 삽입 성능을 제공.
- hash

### 특징
- 중복 요소를 허용하지않음
- 순서가 없음 : hashSet은 삽입된 순서를 보장하지않으며 내부적으로 hashTable을 사용하기때문에 요소들이 임의의 순서로 저장됨. 순서가 중요하다면 LinkedHashSet을 사용할 수 있음.
- 빠른 검색 , 삽입, 삭제 : 해시 기반 구조로, 일반적인 경우 O(1)의 시간 복잡도를 가지고 검색, 삽입, 삭제가 빠름
- null 값을 허용 : hashTable에서 null에 대한 처리가 따로 되어있기때문에 가능.

## TreeSet

- Set인터페이스를 구현한 클래스로서 객체를 중복해서 저장할 수 없음. 저장순서가 유지되지않는 Set의 성질을 그대로 가지고있음.
- HashSet과 다르게 이진 탐색 트리구조로 이루어져있다.
- 이진탐색 트리구조는 추가와 삭제에는 시간이 조금 더 걸리지만 **정렬, 검색에는 높은 성능**을 보이는 자료구조.
- hashSet보다 데이터의 추가 삭제는 시간이 더 걸리지만 검색과 정렬에 유리

### 레드-블랙 트리 (red-black tree)
![image](https://github.com/user-attachments/assets/39cc2a54-31d6-4e8e-a0aa-4d5cf88101f7)

이진 탐색 트리 중에서도 성능을 향상시킨 red-black 트리로 구현되어있음. 


## LinkedHashSet
- 다른 set과 달리 삽입된 순서대로 반복함. 저장된 순서에 따라 순서가 결정된다. 추가된 순서 또는 가장 최근에 접근한 순서대로 접근이 가능.


참고

자바의 신 

https://coding-factory.tistory.com/555