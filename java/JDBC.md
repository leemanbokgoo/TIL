
# JDBC가 등장하게 된 배경
- 데이터베이스 접근의 표준화를 위해서.
- 데이터베이스에는 Oracle Database, MySQL, PostgreSQL 와 같이 여러 종류의 데이터베이스가 있다. 각각 데이터베이스마다 SQL를 전달하거나 결과를 응답받는 방법들이 다 다르고 데이터베이스의 종류는 수십 개가 존재한다. JDBC가 존재하기 전에는 이런 데이터베이스마다 존재하는 고유한 API를 직접 사용했었다.
- 이에 따라 개발자는 기존의 데이터베이스를 다른 데이터베이스로 교체해야 하는 경우에는 데이터베이스에 맞게 기존의 코드를 모두 수정해야 했으며 심지어 각각의 데이터베이스를 사용하는 방법도 새로 학습해야 했다.
- 따라서 표준이라는 게 필요 -> JDBC의 표준 인터페이스 덕분에 개발자는 데이터베이스를 쉽게 변경할 수 있게 되었고 변경에 유연하게 대처할 수 있게 되었음

# JDBC
- JDBC(Java Database Connectivity)는 Java 기반 애플리케이의 데이터를 데이터 베이스에 저장 및 업데이트 하거나 데이터 베이스에 저장된 데이터를 Java에서 사용할 수 있도록 하는 자바 API다. 
- JDBC는 Java 애플리케이션에서 데이터 베이스에 접근하기위해 JDBC API를 사용하여 데이터 베이스에 연동 할 수 있으며, 데이터 베이스에서 자료를 쿼리(Query)하거나 업데이트 하는 방법을 제공한다.

![image](https://github.com/user-attachments/assets/beabf39a-ad9f-48dc-8707-62c062c1be66)

- JDBC는 3가지 기능을 표준 인터페이스로 정의하여 제공한다.
    - java.sql.Connection - 연결
    - java.sql.Statement - SQL을 담은 내용
    - java.sql.ResultSet - SQL요청 응답
- Spring Data JBDC, Spring data JPA 같은 기술이 등장하면서 JDBC API를 직접적으로 사용하는 일은 줄어들었다. 
- 하지만 Spring Data JBDC, Spring data JPA 기술도 데이터 베이스와 연동하기위해 내부적으로 JDBC를 이용하기때문에 JDBC의 동작 흐름에 대해 알필요가 있다.

## JDBC 클래스의 생성관계
![image](https://github.com/user-attachments/assets/5b9aa6ec-ec98-4fe6-8b7f-e61b7199b962)
- 드라이버 로딩시 DriverManager라는 객체가 갖고 있는 메서드를 이용해서 드라이버를 로딩한다. 그래서 DriverManager 객체를 이용해서 Connection 인스턴스를 얻어내고, Connection 인스턴스를 통해서 Statement 객체를 얻어내고, Statement객체를 통해 ResultSet을 얻어낸다. 그래서 닫을때는 열때와 반대순서로 닫아주어야 한다.

## JDBC의 동작 흐름
![image](https://github.com/user-attachments/assets/4a1b5fb0-21cd-46b7-9856-7d67a9386765)
- JDBC는 Java 애플리케이션 내에서 JDBC API를 사용하여 데이터 베이스에 접근하는 단순한 구조이다.
- JDBC API를 사용하기위해서는 JDBC 드라이버를 먼저 로딩한 후에 데이터 베이스와 연결하게 된다.
- **JDBC 드라이버**
    - 데이터 베이스와 통신을 담당하는 인터페이스.
    - Oracle, MSSQL, MYSQL 등과 같은 데이터 베이스에 알맞는 JDBC 드라이버를 구현하여 제공
    - JDBC 드라이버의 구현체를 이용하여 특정 벤더의 데이터 베이스에 접근 할 수 있음.

## JBDC API 사용 흐름
- JDBC API 의 구성 요소들의 동작흐름은 다음과 같다. 
![image](https://github.com/user-attachments/assets/4741f87d-8791-4bea-a6ce-871974807175)
- JBDC 드라이버 로딩 : 사용하고자하는 JBDC드라이버를 로딩한다. JBDC 드라이버는 DriverManager 클래스를 통해 로딩된다.
- Connection 객체 생성 : JBDC 드라이버가 정상적으로 로딩 되면 DriverManager를 통해 데이터 베이스와 연결되는 세션(Session)인 Connection객체를 생성한다
- Statement 객체 생성 : Statement 객체는 작성된 SQL 쿼리문을 실행하기위한 객체로 정적 SQL쿼리문자열을 입력으로 가진다.
- Query 실행 : 생성된 Statement 객체를 이용하여 입력한 SQL 쿼리를 실행한다.
- ResultSet 객체로 부터 데이터 조회 : 실행된 SQl 쿼리문ㅇ에 대한 결과 데이터 셋이다.
- ResultSet, Statement, Connection 객체들의 Close: JDBC API를 통해 사용된 객체들은 생성된 객체들을 사용한 순서의 역순으로 Close한다.

## 커넥션 풀(Connnection Pool)
- JBDC API를 사용하여 데이터 베이스와 연결하기위해 Connection 객체를 생성하는 작업은 비용이 많이 드는 작업 중 하나다.
-  #### 커넥션 객체를 생성하는 과정
    - 애플리케이션에서 DB 드라이버를 통해 커넥션을 조회한다.
    - DB드라이버는 DB와 TCP/IP 커넥션을 연결한다. (3way handshake와 같은 연결 동작 발생)
    - DB 드라이버는 TCP/IP 커넥션이 연결되면 아이디와 패스워드, 기타 부가 정보ㅏㄴ 겨를 DB에 전달한다
    - DB는 아이디, 패스워드를  통해 내부 인증을 거친후 내부에 DB를 생성한다.
    - DB는 커넥션 생성이 완료되었다는 응답을 보낸다.
    - DB 드라이버는 커넥션 객체를 생성해서 클라이언트에 반환한다.
- 이 처럼 커넥션을 새로 만드는 것은 비용이 많이 들며 비효율적이다. 
- 이러한 문제를 해결하기위해 애플리케이션 로딩시점에 Conncetion 객체를 미리 생성하고 애플리케이션에서 데이터 베이스에 연결이 필요한 경우 미리 준비된 Connection 객체를 사용하여 애플리케이션의 성능을 향상하는 커넥션 풀(Connection Pool)이 등장하게 된다.
- **Conncetion 객체를 미리 생성하여 보관하고 애플리케이션이 필요할때 꺼내서 사용할 수 있도록 관리해주는 것이 Connection Pool이다.**

## 커넥션 풀 동작 구조 
![image](https://github.com/user-attachments/assets/c2078291-23a2-4db4-8eeb-6e0a168e3c5d)
- 애플리케이션을 시작하는 시점에 커넥션 풀은 필요한 만큼 커넥션을 미리 생성하여 보관한다.
- 서비스의 특징과 스펙에 따라 생성되는 Connection 객체의 수는 다르지만 일반적으로 기본 값으로 10개를 생성한다.
- 커넥션 풀에 들어가있는 Connection객체는 TCP/IP로 DB와 연결되어있는 상태이기때뭉네 즉시 SQL을 DB에 전달 할 수 있다. 
- 즉, DB드라이버를 통해 새로운 커넥션을 획득하는 것이 아닌 이미 생성되어있는 커넥션을 참조하여 사용하게 된다.
- 커넥션 풀에 있는 커넥션을 요청하면 커넥션 풀은 자신이 가지고있는 커넥션 객체 중 하나를 반환한다. 

- 따라서 DB드라이버를 통해 커낵션을 조회,연결,인증,SQL을 실행하는 시간등 커넥션 객체를 생성하기위한 과정을 생략할 수 있게 된다.
- Spring boot 2.0 이전 버전에서는 Apache 재단의 오픈소스인 Commons DBCP를 주로 사용하였지만, 스프링 부트 2.0 이후 HikariCP를 기본 DBCP로 채택하여 사용되고 있다.
 
## HikariCP
- HikariCP는 가벼운 용량과 빠른 속도를 가지는 우수한 성능의 JDBC Connection Pool Framework이다.
스프링 부트 2.0 이후부터는 커넥션 풀을 관리하기 위해 HikariCP를 사용하고 있다.
![image](https://github.com/user-attachments/assets/62481057-4a6f-4bd1-8436-9a025b25bc2e)
- HikariCP는 미리 정해놓은 크기만큼의 Connection을 Connection Pool에 담아 놓는다.
이후 요청이 들어오면 Thread가 Connection을 요청하고, Connection Pool에 있는 Connection을 연결해 준다.


참고링크 

 https://ittrue.tistory.com/250

 https://tecoble.techcourse.co.kr/post/2023-06-28-JDBC-DataSource/