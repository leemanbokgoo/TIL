## ArrayList
- ArrayList는 List 인터페이스를 상속받은 클래스로 크기가 가변적으로 변하는 선형리스트이다.일반적인 배열과 같은 순차리스트이며 인덱스로 내부의 객체를 관리한다는점 등이 유사하지만 한번 ₩생성되면 크기가 변하지 않는 배열과는 달리 ArrayList는 객체들이 추가되어 저장 용량을 초과한다면 자동으로 부족한 크기만큼 저장 용량이 늘어난다는 특징을 가지고 있다.


## ArrayList 특징
- 연속적인 데이터의 리스트 (데이터는 연속적으로 리스트에 들어있어야 하며 중간에 빈공간이 있으면 안된다)
- ArrayList 클래스는 내부적으로 Object[] 배열을 이용하여 요소를 저장
- 배열을 이용하기 때문에 인덱스를 이용해 요소에 빠르게 접근할 수 있다.
- 크기가 고정되어있는 배열과 달리 데이터 적재량에 따라 가변적으로 공간을 늘리거나 줄인다.
- 그러나 배열 공간이 꽉 찰때 마다 배열을 copy하는 방식으로 늘리므로 이 과정에서 지연이 발생하게 된다.
- 데이터를 리스트 중간에 삽입/삭제 할 경우, 중간에 빈 공간이 생기지 않도록 요소들의 위치를 앞뒤로 자동으로 이동시키기 때문에 삽입/삭제 동작은 느리다.
- 따라서 조회를 많이 하는 경우에 사용하는 것이 좋다

## 배열의 장단점
- 처음 선언한 배열의 크기(길이)는 변경할 수 없다. 이를 정적 할당(static allocation)이라고 한다.
- 데이터 크기가 정해져있을 경우 메모리 관리가 편하다.
- 메모리에 연속적으로 나열되어 할당하기 때문에 index를 통한 색인(접근)속도가 빠르다.
- index에 위치한 하나의 데이터(element)를 삭제하더라도 해당 index에는 빈공간으로 계속 남는다. 
- 배열의 크기를 변경할 수 없기 때문에, 처음에 너무 큰 크기로 설정해주었을 경우 메모리 낭비가 될수 있고, 반대로 너무 작은 크기로 설정해주었을 경우 공간이 부족해지는 경우가 발생 할 수 있다.

## ArrayList 장단점
- 리스트의 길이가 가변적이다. 이를 동적 할당(dynamic allocation)이라고 한다.
- 배열과 달리 메모리에 연속적으로 나열되어있지 않고 주소로 연결되어있는 형태이기 때문에 index를 통한 색인(접근)속도가 배열보다는 느리다.
- 데이터(element) 사이에 빈 공간을 허용하지 않는다.
- 객체로 데이터를 다루기 때문에 적은양의 데이터만 쓸 경우 배열에 비해 차지하는 메모리가 커진다.

## ArrayList VS map
- 시간 복잡도가 O(1)이라 하더라도 실제 성능은 내부 구현 방식과 상수 계수(Constant Factor)에 따라 차이가 날 수 있다.

### 1. 조회 속도 비교 (읽기 성능)
- ArrayList: get(index)는 내부 배열에서 특정 위치의 요소를 바로 가져오므로 **O(1)**.
- HashMap: get(key)는 키를 해시 함수로 변환한 후, 해당 버킷에서 값을 찾으므로 **O(1)**이지만, 해시 충돌이 발생하면 O(log n)까지 느려질 수 있다.
- 일반적으로 ArrayList의 get(index)가 HashMap의 get(key)보다 더 빠르다.

### 2. 삽입 속도 비교
- ArrayList: add(value)는 배열의 끝에 추가하면 **O(1)**이지만, 중간에 삽입하면 O(n).
- HashMap: put(key, value)는 해시 함수를 계산하고 해당 위치에 값을 저장하므로 평균적으로 **O(1)**.
- 삽입만 놓고 보면 HashMap이 더 빠를 수 있다.

### 3. 삭제 속도 비교
- ArrayList: remove(index)는 삭제 후 나머지 요소들을 이동해야 해서 **O(n)**입니다.
- HashMap: remove(key)는 해시 계산 후 해당 버킷에서 값을 제거하면 되므로 평균적으로 **O(1)**입니다.
- 삭제는 HashMap이 ArrayList보다 빠릅니다.

### 결론
- 인덱스 기반 조회는 ArrayList가 더 빠르다.(get(index) > get(key))
- 삽입과 삭제는 HashMap이 더 빠를 가능성이 크다.(put/remove(key) > add/remove(index))
- 하지만 HashMap은 해시 충돌이 발생하면 성능이 O(log n)까지 떨어질 수도 있다.
- 실제 속도 차이는 내부 구현과 해시 충돌 여부, 데이터 크기에 따라 다를 수 있다.

--- 

참고링크 

https://inpa.tistory.com/entry/JAVA-%E2%98%95-ArrayList-%EA%B5%AC%EC%A1%B0-%EC%82%AC%EC%9A%A9%EB%B2%95

https://dev-coco.tistory.com/33