# Redis
[Redis TIL 참고](https://github.com/leemanbokgoo/TIL/blob/main/%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4/%08Redis.md)

## 장점
### Memcache에 비해 발전속도가 빠름.
- Redis는 멤캐쉬와 비슷한 분산 캐시이며 저장소의 개념이 추가되었다고 생각하면 된다.
- 맴맴캐쉬의 지지부진한 발전에 비해 Redis는 엄청난 속도로 발전하고있음.

### Redis는 Memcached에서 제공하지않는 여러 기능을 제공함.
- DataType : List, Sorted Set Hash등의 자료구조를 제공 Collection 사용가능 
- Replication : Maseter/Slave로 사용할 수 있는 리플리케이션을 제공
- Persistence : RDB라는 현재 메모리의 Data Set에 Snapshot을 만들 수 있는 기능 제공
- Pub/Sub : Redis는 Publisher/subscribe 형태로 이용할 수 있는 기능 제공
- Spring data Redis에서는 SessionCallback 인터페이스를 통해 여러 명령을 동시에 처리하는 기능을 제공.
- Spring Data Redis에서 기존의 데이터와의 호환성을 위해 JAVA객체를 Redis에 저장할때 Hash자료구조 형태로 저장.
- 스프링에서는 SPRing data에서 제공하는 CrudRepository를 상속받아, Redis의 CRUD를 간단하게 처리할 수 있음.
### Redis는 디스크에 데이터를 기록하고있기때문에 메모리가 날라가도 데이터를 복구 할 수 있음.
- 데이터 복구 방식 : RDB , AOF
    - RDB : 현재 메모리 상태의 snapshot을 만들어 사용.
    - AOF : 로그에 남긴 Write/Update 이벤트를 기반으로 복구 
    - 두 방법은 결국 파일에 데이터를 기록하는 것임으로 성능을 저하시킴 
    - 실제 Master는 서비스만 하고 Slave에서 AOF RDB를 이용해서 백업하는 형태로 사용.
    ![image](https://github.com/user-attachments/assets/14397a66-d5be-4efa-8faa-328e2d23b54c)
### Redis는 Key에 저장할 수 있는 값의 법위가 Memcached보다 보다 큼.
- 한개의 KEY에 저장할 수 있는 VALUE의 범위 : Reids : 512Mb  |  Memcached : 1MB 

## 단점
### Redis는 메모리를 2배로 사용함.
- 싱글 스레드인 Redis는 snapshot을 뜰떄, 자식 프로세스를 하나 만들고 난 후 새로 변경된 메모리 페이지를 복사해서 사용. 보통 Redis는 데이터의 변경이 잦을 때 사용하기때문에 실제 메모리양만큼 자식 프로세스가 복사하게됨.
### Redis는 jemalloc를 이용하여 메모리 파편화가 발생하기 쉬움.
- Redis는 jemalloc를 이용하여 매번 malloc/free를 통해 메모리 할당이 이루어짐. 결국 Redis에서 메모리 파편화가 발생하게 되어 할당 비용때문에 응답 속도가 느려짐. 다만 이는 극단적으로 봤을때 발생하는 일이며 치명적인 문제가 아니라고 함. 또한 jemalloc 4.X버전부터 메모리 파편화를 줄이기위한 jemalloc에 힌트를 주는 기능이 들어갔다고 함.


## Redis 사용이유
### 다양한 자료구조 및 용량 지원
- Memcached는 key 이름을 250 byte까지 제한하고, 단순히 string만 사용한다. Redis는 keys, value 이름을 512mb까지 지원한다. hash, set, list, string 등 다양한 데이터 구조도 있어서 개발자들이 캐싱 및 캐시된 데이터 조작에 편리성을 제공한다.

### 다양한 삭제(eviction) 정책 지원
- Cache는 메모리에 오래된 데이터를 삭제해서 새로운 데이터 공간을 확보하는 data eviction(데이터 삭제)라는 방식을 사용한다. Memcached의 데이터 방식은 LRU이고 새로운 데이터와 크기가 비슷한 데이터를 임의 제거한다. Redis는 사용자가 6가지의 다른 데이터 삭제 정책을 제공한다. 또한 메모리 관리와 데이터 삭제 선택에 더 정교한 접근법을 제공한다.  또한 lazy, active 삭제를 지원한다.

### 디스크 영속화(persistence) 지원
- Memcached와 달리, Redis는 디스크 영구 저장이 가능하다. 레디스의 데이터베이스에 있는 데이터들은 서버 충돌이나 재부팅 시에도 복구될 수 있다. (물론 유형에 따라서 수초에서 수분 사이에 데이터가 변경 될 수도 있다.) AOF, RDB Snapshot 2가지 방식이 있다.

### 복제(replication) 지원
- 복제는 하나의 인스턴스로부터 또다른 레플리카 인스턴스를 복사하는 것이다. 목적은 데이터의 복제본이 또다른 인스턴스에 유지되는 것이다. 또한 레디스는 하나 이상의 레플리카를 가질 수 있다. Memcached는 써드 파티를 사용하지 않고서는 복제본을 가질 수 없다.

### 트랜잭션(Transaction) 지원
- Membercached는 원자적으로 동작하지만, 트랜잭션을 지원하지 않다.Redis는 명령을 실행하기 위해서 트랜잭션을 지원한다. MULTI 커맨드를 통해서 트랜잭션을 시작하며 EXEC로 추가 명령어를 실행한다. WATCH를 통해서 트랜잭션을 종료한다.


# Memcached

![image](https://github.com/user-attachments/assets/83e76562-8062-4d54-8c02-c7668cecf2ef)


## 장점
### 서버 한대에 장애가 발생해도 문제가 발생하지않으며, 검색시간이 짧다.
- Memcached 자체에는 분산 기능이 없지만 Memcachaed 라이브러리인 Consistent Hashing을 통해서 데이터를 분산.
- Consistent Hasing을 이용하여 클라이언트가 DBMS에서 장애 시점을 읽어 다시 캐시하는 로직을 미리 만들어둘수있음.
- Memcachaed 서비스 구조를 보면 클라이언트가 Memcached 서버의 주소를 모두 알고있어 Memcached 서버 한대가 장애가 발생하더라도 크게 문제가 발생하지않음.
- 해시 형태로 O(1) 시간 복잡도를 가지고있어 검색 시간이 매우 짧다
- 만약 Consistent Hashing이 아니라면, 장애가 발생할 때 일반적으로 다른 프로그램에서는 남아있는 서버만큼만 트래픽을 보내기 위해 서버 설정을 다시 배포해야함.

### Memcached에는 리플리케이션이 가능함.
- Memcached를 Master/Master 리플리케이션이 가능하도록 만든 Repacahed 프로그래밍이 있어 메모리가 날라가도 **원본 데이터로 즉시 복구** 할 수 있음.
- 다만 전원이 ㅇ내려가면 메모리의 내용이 모두 사라지는 메모리 캐시 서버는 주 목적이 캐시임으로 내부 데이터가 사라져도 상관이 없을 수도있음.

### Memcached는 트래픽이 몰려도 Redis에 비해 응답속도가 안정적인 편이며 메모리 파편화 문제가 적다.
- Memcached의 메모리 할당 구조가 slab할당자를 이용한 형태임. -> 메모리 재할당을 하지않고 관리하는 형태를 취함.

### Redis에 비하면 메타 데이터를 적게 사용하기때문에 메모리 사용량이 상대적으로 낮음 
- Memcached는 작고 변하지않는 정적인 데이터를 캐싱할때 내부 메모리 관리가 REDIS만큼 복잡하지않음. -> Redis에 비해 능률적으로 이루어져 메모리 사용량이 낮음.

## 단점
- Redis처럼 데이터 타입과 API가 다양하지않음
- 데이터 변경이 잦은 경우에 메모리 파편화가 발생하기 쉬움

## 사용이유
### 정적 데이터 캐싱에 효과적이다.
- Memcached는 HTML같은 작은, 정적 데이터를 캐싱할 때 효율적이다. Redis만큼 정교하지는 않지만 내부 메모리관리는 단순한 경우에 매우 뛰어나다. (metadata에 더 적은 작원을 소모하기 때문) Strings(유일한 지원 데이터 타입)은 추가처리가 필요없어 읽기 전용에 적합하다.
- 큰 규모의 직렬화된 데이터는 큰 저장공간이 필요하다. Redis 데이터 구조는 데이터의 모든 형태를 그대로 저장할 수 있다. Memcached는 직렬화된 형태로 데이터 저장하도록 제한적이므로 효과적이다. 따라서 Memcached를 사용할 때 좀 더 직렬화 오버헤드를 줄일 수 있다.

### 멀티 쓰레드 기능 지원
- Memcached는 멀티쓰레드이기 때문에, Redis에 비해 스케일링에 유리하다. 컴퓨팅 자원을 추가함으로 스케일 업을 할 수 있습니있다다. 하지만 캐시된 데이터를 유실 할 확률도 높아진다. Redis는 단일 쓰레드이기 때문에, 데이터 손실없이 수평으로 스케일링할 수 있다.

## Redis와 Memcached의 차이

### 스레드 모델
- Redis는 싱글 스레드 기반으로 동작
- Memcached는 멀티스레드를 지원해서 멀티 프로세싱이 가능하다.(스케일 업 가능)


---

참고링크 

https://escapefromcoding.tistory.com/704

https://velog.io/@sileeee/Redis-vs-Memcached