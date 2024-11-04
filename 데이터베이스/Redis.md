# Redis
- Redis는 Remote Dicionary Server의 약자로 key-value 쌍의 해시맵과 같은 구조를 가진 비관계형(NoSql)데이터 베이스 관리 시스템(DBMS)이다.
- Redis는 옾느 소시 기반으로 인 메모리(in-memory) 데이터 구조 저장소로 메모리에 데이터를 저장한다.
- 따라서 별도의 쿼리문이 필요로 하지않고 인 메모리에 저장되기때문에 상당히 빠른 속도로 처리할 수 있다.

# Redis 특징 및 장단점
### 성능
- 모든 Redis 데이터는 메모리에 저장되어 대기 시간을 낮추고 처리량을 높인다.
- 평균적으로 읽기 및 쓰기 작업의 속도고 1ms로 디스크 기반 데이터베이스보다 빠르다.
### 유연한 구조
- Redis의 데이터는 String, List, Set, Hash, Sorted Set, Bitmap, JSON 등 다양한 데이터 타입을 지원한다.
따라서, 애플리케이션의 요구 사항에 알맞은 다양한 데이터 타입을 활용할 수 있다.
 

참고 링크 

 https://ittrue.tistory.com/317