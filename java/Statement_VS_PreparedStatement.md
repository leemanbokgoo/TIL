# Statement vs. PreparedStatement

-  자바에서 데이터베이스로 쿼리문을 전송할 때, 사용할 수 있는 인터페이스는 2가지가 존재한다. Statement와 PreparedStatement이다.JDBC의 Statement는 DB에 쿼리를 보내 실행시키는 객체이다. Java에서 ORM 없이 데이터베이스에게 실행할 쿼리를 전달할 때 Statement 또는 PreparedStatement를 사용한다. PreparedStatement는 Statement를 상속받은 객체로, 확장된 기능을 가지고 있어 더 자주 사용되지만 Statement를 완전히 대체할 수 있는 것은 아니다.
    - Statement : java.sql
    - PreparedStatement: java.sql extends Statement
- 자바는 주로 웹개발을 위해서 사용되는데 웹개발에서는 보안문제(SOL 인젝션)떄문에 PreparedStatement를 사용한다. 또한 반복적으로 쿼리를 수행해야한다면 PreparedStatement가 성능이 더 ㅈ호다.

## Statement
- Statement 객체는 Statement 인터페이스를 구현한 객체를 Connection 클래스의 createStatement() 메소드를 호출함으로써 얻어진다.
- Statement 객체가 생성되면 executeQuery() 메소드를 호출하여 SQL문을 실행시킬 수 있다. 메소드의 인수로 SQL문을 담은 Srting 객체를 전달한다.
- Statement는 정적인 쿼리문을 처리할 수 있다. 즉, 쿼리문에 값이 미리 입력되어있어야한다.

## PreparedStatement
- PreparedStatement 객체는 Connection 객체의 preparedStatement()메소드를 사용해서 생성한다. 이 메소드는 인수로 SQL문을 담은 String 객체가 필요하다.
- SQL문장이 미리 컴파일되고, 실행 시간동안 인수 값을 위한 공간을 확보할 수 있다는 점에서 Statement 객체와 다르다.
- Statement 객체의 SQL은 실행될 때, 매번 서버에서 분석해야하는 반면 PreparedStatement 객체는 한 번 분석되면 재사용이 용이하다.
- 각각 인수에 대해 위치홀더(placeholder)를 사용하여 SQL문장을 정의할 수 있게 해준다. 위치 홀더는 ? 로 표현된다.
- 동일한 SQL문을 특정 값만 바꾸어서 여러번 실행해야 할 때, 인수가 많아서 SQL 문을 정리해야 될 필요가 있을 때 사용하면 유용하다.

## 표면적 차이
### 파라미터 바인딩
- 가장 표면적으로 보이는 차이점은 파라미터 여부이다. Statement는 받은 문자열을 통채로 쿼리문으로 인식하여 그대로 실행하기 때문에 파라미터 바인딩이 불가능하다.
    - stmt = con.createStatement(sql)
    - String sql = "select * from member;"
    - stmt.execute() -> 받은 쿼리 그대로 실행한다.
- PreparedStatement는 문자열을 전달받는 것은 같지만 문자열을 그대로 쿼리문으로 인식하지 않는다.
    - pstmt = con.prepareStatement(sql);
    - String sql = "insert into member(id, name) values(?, ?);"
    - pstmt.setInt(1, member.getId());
    - pstmt.setString(2, member.getName()); -> 외부에서 파라미터를 바인딩 할 수있다.
    - pstmt.execute() -> 파라미터 바인딩 후 쿼리를 완성하여 실행한다.
- PreparedStatement는 플레이스홀더(?)에 파라미터 바인딩이 가능하다. 런타임 중에 파라미터를 전달할 수 있으므로 동적인 쿼리문 생성에 유용하다. 또한 문자열만 다루는 Statement와 달리 PreparedStatement는 객체를 바인딩할 수 있기 때문에 이미지, 파일 등 바이너리 데이터를 다루는 것도 가능하다.

## 내부 동작 차이
- JDBC는 자바 애플리케이션과 데이터베이스 연결 방법을 표준화 한 인터페이스 API이고, JDBC의 구체적인 구현은 각 DB 벤더(제조사)가 자신의 DB에 맞게 제공하는 JDBC 드라이버마다 차이가 있다.따라서 PreparedStatement의 동작 방식도 드라이버마다 다르다. 자신이 사용하는 데이터베이스의 세부 동작을 알고 싶다면 JDBC API 문서가 아닌 JDBC 드라이버 문서를 참고해야 한다.

### 1. 사전 컴파일(pre-compilataion)
- 쿼리 문자열을 실행하기 위해서는 데이터베이스 엔진이 이해할 수 있는 형식으로 변환하는 컴파일 과정이 필요하다.실행 시점에 쿼리문이 컴파일되는 Statement와 달리 PreparedStatement는 실행되기 전에 데이터베이스 엔진이 이해할 수 있는 형태로 미리 컴파일된다. 덕분에 데이터베이스는 실행 시점에는 컴파일 과정을 생략하고 알맞은 형식으로 준비된 쿼리를 실행만 하면 된다.
- 그러나 전처리를 위해서는 데이터베이스와의 통신이 사전 컴파일 시에 한 번, 실행 시에 한 번, 두 번의 왕복이 필요하다. 대부분의 쿼리는 재사용 빈도가 낮아서 사전 컴파일을 하지 않고 한 번의 왕복으로 처리하는 것이 효율적이라 DB에 따라서 사전 컴파일을 지연시키는 경우도 있다고 한다.

### 2.캐싱
- Statement와 PreparedStatement의 가장 큰 차이점은 바로 캐시를 사용하는지의 여부이다.
- Statement는 서로 독립적이다. Statement는 매번 새로운 쿼리로 인식되므로 DB는 쿼리를 실행할 때마다 컴파일 작업을 수행한다. 반면 PreparedStatement는 최초 1번만 컴파일을 시행하고 결과를 캐시에 저장해둔다. 미리 컴파일 해놓은 것을 재사용하기 때문에 처리 속도가 훨씬 빠르다.
- 사전 컴파일과 캐싱 덕분에 PreparedStatement는 플레이스홀더에 들어가는 값만 달라지는 경우 쿼리 전체를 컴파일하는 것이 아니라 파라미터 부분만 처리한다.
- Statement에서는 두 개가 완전히 다른 쿼리이다 :
    - select * from member where id = 1;
    - select * from member where id = 2;
- PreparedStatement에서는 다음 쿼리를 컴파일하고 캐싱해둔다 :
    - select * from member where id = ?;
    - 이 쿼리가 다시 사용되는 경우 ?에 바인딩한 값만 처리한다.
- 따라서 파라미터만 달라지는 동일 쿼리를 반복해서 사용하는 경우 PreparedStatement를 사용하는 것이 훨씬 효율적이다.
    - statement : SQL문을 실행할 때마다 SQL을 매번 구문을 새로 작성해야하고 해석해야해서 오버헤드가 있음.
    - PreparedStatement : 선처리 방식 사용(준비된 statement) 즉 SQL문을 미리 준비해놓고 바인딩 변수(? 연산자)를 사용해서 반복되는 비슷한 SQL문을 쉽게 처리
    - 쿼리 실행순서 : 1. 쿼리문장 분석 2. 컴파일. 3 실행 
    - statement를 사용하면 매번 쿼리를 수행 할때 마다 1-3단계를 거침. PreparedStatement를 사용하면 처음에 한 번만 세단계를 거친 후 캐시에 담아 재사용함.


## 보안상 차이
### SQL Injection 공격 방지
- SQL 인젝션은 애플리케이션이 클라이언트가 제공한 데이터를 SQL 문에 사용하는 것을 이용한 공격 방식이다. Statement는 이 공격에 굉장히 취약하다.

### Statement의 경우
```
String sql = "select "
      + "customer_id, name, balance "
      + "from Accounts where customer_id = '"
      + customerId 
      + "'";
```
- Statement는 위의 예시처럼 변수(customer_id)에 클라이언트로부터 받은 값을 담고 문자열을 조합하여 쿼리문으로 사용하는 경우가 많다.
- 그런데 클라이언트의 값 조작은 매우 쉽기 때문에 숫자로 보내져야 할 customer_id 값을 해커가 악의적으로 xxx' or '1' = '1과 같이 보냈다고 하면, 최종적으로 애플리케이션은 다음과 같은 쿼리를 실행하게 된다 :
```
select customer_id, name, balance from Accounts 
where customer_id = 'xxx' or '1' = '1'
```
- '1' = '1'은 무조건 참이고 OR 연산이기 때문에 WHERE 절 전체는 참이 된다. 앞의 조건은 무효화되어 해커는 모든 값을 조회할 수 있게 된다. SQL 인젝션은 단순해 보이지만 아직까지도 자주 사용되는 공격 방법이라고 한다.

### PreparedStatement의 경우
- 그러나 위 예시의 공격 방법은 PreparedStatement에서는 무의미하다. 문자열을 그대로 쿼리문으로 사용하지 않기 때문이다.
- 예를 들어 클라이언트로부터 값을 받는 변수 customer_id의 데이터타입이 int라고 한다면 파라미터 바인딩을 할 때 abc' or '1' = '1'와 같이 유효하지 않은 값은 바인딩이 되지 않는다.
    - pstmt.setInt(1, customer_id);

## 정리 
- PreparedStatement는 Statement를 상속받은 객체로, 파라미터 바인딩이 가능하다. 사전 컴파일과 캐싱을 지원하기 때문에 파라미터만 달라지는 동일한 쿼리를 반복해서 사용하는 경우 효율성이 극대화된다. 문자열을 그대로 쿼리문으로 사용하지 않기 때문에 SQL 인젝션 공격을 방지해주기도 한다.
- PreparedStatement의 사용 빈도가 훨씬 높지만 DDL 등 한 번만 사용되는 쿼리의 경우 Statement를 사용하는 것이 좋다. 또한 로직상 PreparedStatement를 사용할 수 없는 경우도 있고 Statement로 작성된 레거시 코드를 모두 바꾸는 것이 어려울 수도 있으므로 SQL 인젝션에 대한 대책으로 항상 PreparedStatement가 답이 될 수 있는 것은 아니라고 한다.

## PreparedStatement VS Statement
- PreparedStatement는 동적인 쿼리문을 처리할 수 있으므로 같은 SQL문에서 값만 변경하여 사용한다던가 인수가 많은 경우에 사용하기 좋다. 또한 미리 컴파일되기 때문에 수행 속도가 Statement보다 빠른 장점이 있다.Statement 객체는 쿼리 실행시 값에 작은따옴표( ' )가 포함되어 있으면 작은따옴표를 두 개( ' ' ) 표시해야 한다. 예를 들어 입력할 값이 I ' am 이라고 하자. 그러면 쿼리문 작성시에 값을 I ' ' am 이렇게 입력해야 한다. 그러나 PreparedStatement 객체는 작은따옴표 문제를 쿼리 실행시 자동으로 처리하므로 신경쓸 필요가 없다는 장점이 있다. 
- 속도 면에서 PreparedStatement가 빠르다고 알려져 있다. 이유는 쿼리를 수행하기 전에 이미 쿼리가 컴파일 되어 있으며, 반복 수행의 경우 프리 컴파일된 쿼리를 통해 수행이 이루어지기 때문이다. PreparedStatement에는 보통 변수를 설정하고 바인딩하는 static sql이 사용되고 Statement에서는 쿼리 자체에 조건이 들어가는 dynamic sql이 사용된다. PreparedStatement가 파싱 타임을 줄여주는 것은 분명하지만 static sql을 사용하는데 따르는 퍼포먼스 저하를 고려하지 않을 수 없다. 하지만 성능을 고려할 때 시간 부분에서 가장 큰 비중을 차지하는 것은 테이블에서 레코드(row)를 가져오는 과정이고 SQL 문을 파싱하는 시간은 이 시간의 10 분의 1 에 불과하다. 그렇기 때문에 SQL Injection 등의 문제를 보완해주는 PreparedStatement를 사용하는 것이 옳다.
- PreparedStatement는 Statement와 비교했을 때 장점은 다음과 같다.
    - 인자를 적용하지 않고 쿼리문을 미리 컴파일하는 방식이라 캐시를 사용할 수 있다.
    - 인자가 많은 경우 Statement를 사용할 때보다 더 깔끔한 코드를 작성할 수 있다.
    - 인자 값에 특수문자가 있을 경우 escaping 하여 파싱하기 때문에 SQL Injection을 예방할 수 있다.
- PreparedStatement를 사용하기 좋은 때
    - 같은 쿼리를 조건만 변경하여 반복 실행해야 하는 경우
    - 전달할 인자가 많을 경우
    - 사용자의 입력을 전달해야 할 경우
- 사용할 때 주의할 점
    - 각 DB마다 캐싱할 수 있는 한계가 있기 때문에 정작 성능상 캐싱되어야 할 쿼리가 그렇지 않은 쿼리 때문에 캐싱이 안될 수 있기 때문에 꼭 필요한 쿼리만 PreparedStatement를 쓰는 것을 권고한다.
- 반면 동적 쿼리를 사용해야 하는 경우에는 반드시 Statement를 사용하는 것이 좋다. 동적 쿼리는 캐싱을 할 수 없어 캐싱의 장점을 잃어버리기 때문이다.

### 질문
#### PreparedStatement가 Statement보다 성능이 뛰어난 이유는 무엇인가요?
- PreparedStatement는 SQL을 미리 컴파일하여 캐싱해 두기 때문에 동일한 쿼리를 반복 실행할 때 매번 SQL을 분석하고 컴파일하는 과정이 생략됩니다. 따라서 파라미터만 변경되는 반복 실행 시 Statement보다 성능이 우수합니다.

#### PreparedStatement를 모든 SQL 실행에 사용하면 안 되는 이유는 무엇인가요?
- PreparedStatement는 사전 컴파일과 캐싱을 활용하기 때문에 반복 실행되는 SQL에 적합하지만, DDL(데이터 정의 언어)처럼 한 번만 실행되는 쿼리에서는 불필요한 오버헤드가 발생할 수 있습니다. 또한, 캐싱 가능한 쿼리 수에는 제한이 있어 불필요한 PreparedStatement 사용은 캐싱 성능을 저하시킬 수 있습니다.

---

참고링크 

https://velog.io/@dondonee/JDBC-Statement-vs.-PreparedStatement

https://all-record.tistory.com/79

https://for-basics.tistory.com/entry/Statement-Prepared-Statement-%EB%AD%90%EA%B0%80-%EB%8B%A4%EB%A5%BC%EA%B9%8C

