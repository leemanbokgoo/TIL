
# seission 저장소로 사용하기위한 두 메모리 DB의 특징
- 인메모리 데이터 저장소 

# Memcached
![image](https://github.com/user-attachments/assets/83e76562-8062-4d54-8c02-c7668cecf2ef)

## 장점
- 서버 한대에 장애가 발생해도 문제가 발생하지않으며, 검색시간이 짧다.
    - Memcached 자체에는 분산 기능이 없지만 Memcachaed 라이브러리인 Consistent Hashing을 통해서 데이터를 분산.
    - Consistent Hasing을 이용하여 클라이언트가 DBMS에서 장애 시점을 읽어 다시 캐시하는 로직을 미리 만들어둘수있음.
    - Memcachaed 서비스 구조를 보면 클라이언트가 Memcached 서버의 주소를 모두 알고있어 Memcached 서버 한대가 장애가 발생하더라도 크게 문제가 발생하지않음.
    - 해시 형태로 O(1) 시간 복잡도를 가지고있어 검색 시간이 매우 짧다
    - 만약 Consistent Hashing이 아니라면, 장애가 발생할 때 일반적으로 다른 프로그램에서는 남아있는 서버만큼만 트래픽을 보내기 위해 서버 설정을 다시 배포해야함.

- Memcached에는 리플리케이션이 가능함.
    - Memcached를 Master/Master 리플리케이션이 가능하도록 만든 Repacahed 프로그래밍이 있어 메모리가 날라가도 **원본 데이터로 즉시 복구** 할 수 있음.
    - 다만 전원이 ㅇ내려가면 메모리의 내용이 모두 사라지는 메모리 캐시 서버는 주 목적이 캐시임으로 내부 데이터가 사라져도 상관이 없을 수도있음.

- Memcached는 트래픽이 몰려도 Redis에 비해 응답속도가 안정적인 평니며 메모리 파편화 문제가 적습니다.
    - Memcached의 메모리 할당 구조가 slab할당자를 이용한 형태임. -> 메모리 재할당을 하지않고 관리하는 형태를 취함.
- Redis에 비하면 메타 데이터를 적게 사용하기때문에 메모리 사용량이 상대적으로 낮음 
    - Memcached는 작고 변하지않는 정적인 데이터를 캐싱할때 내부 메모리 관리가 REDIS만큼 복잡하지않음. -> Redis에 비해 능률적으로 이루어져 메모리 사용량이 낮음.


## 단점
- Redis처럼 데이터 타입과 API가 다양하지않음
- 데이터 변경이 잦은 경우에 메모리 파편화가 발생하기 쉬움

# Redis 
- Memcache에 비해 발전속도가 빠름.
    - Redis는 멤캐쉬와 비슷한 분산 캐시이며 저장소의 개념이 추가되었다고 생각하면 된다.
    - 맴맴캐쉬의 지지부진한 발전에 비해 Redis는 엄청난 속도로 발전하고있음.

- Redis는 Memcached에서 제공하지않는 여러 기능을 제공함.
    - DataType : List, Sorted Set Hash등의 자료구조를 제공 Collection 사용가능 
    - Replication : Maseter/Slave로 사용할 수 있는 리플리케이션을 제공
    - Persistence : RDB라는 현재 메모리의 Data Set에 Snapshot을 만들 수 있는 기능 제공
    - Pub/Sub : Redis는 Publisher/subscribe 형태로 이용할 수 있는 기능 제공
    - Spring data Redis에서는 SessionCallback 인터페이스를 통해 여러 명령을 동시에 처리하는 기능을 제공.
    - Spring Data Redis에서 기존의 데이터와의 호환성을 위해 JAVA객체를 Redis에 저장할때 Hash자료구조 형태로 저장.
    - 스프링에서는 SPRing data에서 제공하는 CrudRepository를 상속받아, Redis의 CRUD를 간단하게 처리할 수 있음.
- Redis는 디스크에 데이터를 기록하고있기때문에 메모리가 날라가도 데이터를 복구 할 수 있음.
    - 데이터 복구 방식 : RDB , AOF
        - RDB : 현재 메모리 상태의 snapshot을 만들어 사용.
        - AOF : 로그에 남긴 Write/Update 이벤트를 기반으로 복구 
        - 두 방법은 결국 파일에 데이터를 기록하는 것임으로 성능을 저하시킴 
        - 실제 Master는 서비스만 하고 Slave에서 AOF RDB를 이용해서 백업하는 형태로 사용.
        ![image](https://github.com/user-attachments/assets/14397a66-d5be-4efa-8faa-328e2d23b54c)
- Redis는 Key에 저장할 수 있는 값의 법위가 Memcached보다 보다 큼.
    - 한개의 KEY에 저장할 수 있는 VALUE의 범위 : Reids : 512Mb  |  Memcached : 1MB 

## 단점
- Redis는 메모리를 2배로 사용함.
    - 싱글 스레드인 Redis는 snapshot을 뜰떄, 자식 프로세스를 하나 만들고 난 후 새로 변경된 메모리 페이지를 복사해서 사용. 보통 Redis는 데이터의 변경이 잦을 때 사용하기때문에 실제 메모리양만큼 자식 프로세스가 복사하게됨.
- Redis는 jemalloc를 이용하여 메모리 파편화가 발생하기 쉬움.
    -Redis는 jemalloc를 이용하여 매번 malloc/free를 통해 메모리 할당이 이루어짐. 결국 Redis에서 메모리 파편화가 발생하게 되어 할당 비용때문에 응답 속도가 느려짐. 다만 이는 극단적으로 봤을때 발생하는 일이며 치명적인 문제가 아니라고 함. 또한 jemalloc 4.X버전부터 메모리 파편화를 줄이기위한 jemalloc에 힌트를 주는 기능이 들어갔다고 함.



참고링크 
https://velog.io/@sileeee/Redis-vs-Memcached