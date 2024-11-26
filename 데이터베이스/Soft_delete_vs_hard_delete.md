# Soft Delete VS Hard delete

### 미리 알고 가면 좋은 개념
#### audit table
- 관리권한이 있는 경우에만 접근할 수 있는 테이블. 특별한 기능이 있는 테이블은 아니다. 특정 테이블에서 수행되는 작업을 추적하는 기능을 한다.(Who, What, When)
- 누가 어떤 레코드(종종 스냅샷 전후 포함)를 수행했는지 추적하는 것 외에도 audit table이 한 번 기록된다는 것이 중요한 특징이다.
- audit table의 레코드는 업데이트하거나 삭제할 수 없으며(참고 참조) 삽입만 할 수 있다.
- 오래된 레코드를 지우려면 추가 승인 등의 프로세스가 필요하다.
- audit table 는 추가적으로 민감한/기밀 테이블의 변경 사항을 추적하려는 경우에 사용된다.

## soft Delete
- 논리적으로만 삭제하는 방법으로 물리적인 데이터는 그대로 DB에 남아있게 된다.
- 삭제 칼럼을 추가하여 칼럼의 값을 통해 삭제가 되었다고 논리적으로 판단하는 것. (ex is_deleted, deleted_flag)

### 단점
- 데이터베이스 용량이 커질 수 밖에 없다. 실제로 사용되지 않는 데이터가 존재하기에 저장 공간이 무거워질 수 있다. 
- select 조회시 불필요한 검색 조건을 추가해야함.좋은 개발에서 중요한 요소 중 하나는 직관적이어야 한다는 것인데, where을 통한 필터링이 꼭 추가되어야 한다는 사실은 꽤나 직관적이지 않다고 볼 수 있다. 매번 삭제여부를 고려해야한다. (삭제여부를 고려해 쿼리나 로직을 작성해야한다)

## Hard Delete
- 테이블에서 테이터 행을 직접 삭제하는 것을 말한다.
- 나중에 디버깅을 돕기위해 데이터를 감사 

## 비교 
- 설치의 용이성(Ease of Setup)
    - Soft Delete 는 update 만 하므로 더 세팅하기 쉽다. hard delete 는 audit table 에 복사하는 작업이 포함되므로 구현하기 더 복잡하다.
- 디버깅
    - Soft Delete를 사용하면 deleted_flag로 인해 데이터 문제를 쉽게 디버그할 수 있습니다.
    - 그러나 Audit table을 통한 디버깅도 쉽게 가능합니다

- 데이터 복구
    - delete_flag 설정 해제만 포함되어 있기 때문에 일시 삭제를 통해 '삭제된' 데이터를 복원하는 것은 매우 쉽습니다. 그러나 데이터 복원은 극히 드문 경우이다.

- 데이터 쿼리
    - 경험상 개발자가 문제가 발생한 선택 쿼리에 "deleted_flag = '0'" 조건을 추가하는 것을 잊었을 때 많은 문제가 발생했다고 말할 수 있다. 하지만 ORM 을 사용한다면 @Query 나 @SQLDelet 와 같은 기능들이 제공되므로 이러한 체크 문제로 발생할 수 있는 상황을 회피할 수 있다.

- 단순한 보기 
    - 테이블의 모든 데이터를 active data 로 유지하는 것에서 soft delete 는 한테이블에 존재하므로 분리가 되지 않는다.
    - Hard Delete에서 모든 '삭제된' 데이터는 audit table 에만 있고 시스템의 나머지 테이블에는 active data가 있다. 따라서 Hard Delete에 대한 관심 분리가 되어있다.

- 운영 성능
    - Update 는 delete 보다 약간 빠르다 (microseconds). 따라서 soft delete는 기술적으로 hard delete(고려할 audit table 삽입도 포함)보다 빠르다.

- 어플리케이션 성능
    - 속도
        - soft delete를 지원하려면 모든 선택 쿼리에 "WHERE deleted_flag = '0'" 조건이 있어야 한다. JOIN이 관련된 상황에서는 이러한 조건이 여러 개 있을 것이다.조건이 적은 select 쿼리는 조건이 많은 쿼리보다 빠르다
    - 크기
        - 더 빠른 soft delete 를 지원하려면 모든 테이블의 모든 deleted_flag에 대한 인덱스가 필요하다. 또한 테이블에 ‘soft deleted’ data + active data 가 있으므로 테이블 크기가 계속 증가한다. 테이블 크기가 증가하면 쿼리가 느려질 수 있다

- 데이터 베이스 기능 호환성
    - Unique Index(고유 인덱스)
    - Unique index 는 데이터베이스 수준에서 행이 여러 번 발생하는 것을 방지하여 데이터 무결성을 보장한다.
    - soft delete를 사용하면 Unique index를 사용할 수 없다.
    - 필드 A 및 필드 B 및 deleted_flag에는 composite unique index 가 있다. 데이터 A1 & B1이 있는 행이 'soft deleted'된 경우 고유 인덱스는 값 A1-B1-1(즉, deleted_flag)에 대한 것이다. 새 항목 A1-B1이 테이블에 추가되면 고유 인덱스로 인해 다시 soft deleted 될 수 없다. 또한 old soft deleted entry of A1-B를 업데이트할 수 없다. 이는 일부 데이터를 다시 작성하여 기록된 데이터가 손실되는 것을 의미하기 때문이다(예: update date time or some other deleted_by column if it exists).


## 결론

- soft Delete 는 결국 쿼리 조건이 늘어나 성능에 영향을 미치고 , soft Delete는 테이블 내부에서 분리가 없기때문에 가시성이 좋지않음.
- 조건문 추가에서 발생할 수 있는 개발자의 실수는 ORM 에서 자체적으로 제공하는 다양한 기능들로 막을 수 있다.
- 실무 수준에서 hard delete 된 데이터를 audit table 에서 관리하는 환경이 조성된다면 hard delete 를 토이 프로젝트 등 단순한 경우에는 soft delet 추천한다.

### 질문
#### Q1. Soft Delete와 Hard Delete 중 어떤 상황에서 각각 사용하는 것이 적합한가?
- Soft Delete는 데이터 복구 가능성이 높거나 데이터의 삭제 기록이 필요할 때 적합합니다. 반면, Hard Delete는 데이터 보존 요구가 없거나 시스템 성능 및 가시성을 유지하려는 경우에 적합합니다. 특히, 대규모 시스템에서는 Audit Table과 함께 Hard Delete를 사용하는 것이 데이터 관리와 성능 면에서 효율적입니다.

#### Q2. Soft Delete의 단점인 성능 저하를 완화할 방법은 무엇인가?
-  Soft Delete로 인한 성능 저하는 deleted_flag에 인덱스를 생성하거나 ORM의 @SQLDelete, @Query 기능을 활용하여 조건 추가 실수를 방지함으로써 완화할 수 있습니다. 또한, 일정 주기로 실제 삭제를 수행하는 백그라운드 프로세스를 설계하여 테이블 크기를 줄이는 것도 도움이 됩니다.



출처 링크 : 

https://abstraction.blog/2015/06/28/soft-vs-hard-delete#recommendation
https://velog.io/@im_h_jo/DB-Soft-Delete-vs-Hard-Delete