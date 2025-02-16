## ServeltContext
- ServletContext 클래스는 톰캣 컨테이너 실행 시 각 컨텍스트(웹 애플리케이션)마다 한 개의 ServletContext 객체를 생성함. 그리고 톰캣 컨테이너가 종료하면 ServletContext 객체 역시 소멸 됨.ServletContext 객체는 웹 애플리케이션이 실행되면서 애플리케이션 전체의 공통 자원이나 정보를 미리 바인딩해서 서블릿들이 공유하여 사용함. 
- 웹 애플리케이션의 컨텍스트. 웹 애플리케이션의 이름, 경로 및 초기화 파라미터를 포함한 웹 애플리케이션에 대한 정보를 포함한다. 또한 웹 애플리케이션의 모든 서블릿 및 JSP에 대한 공유 저장소로 사용할 수 있다. ServletContext 객체는 웹 애플리케이션이 시작될 때 웹 컨테이너에 의해 생성되고 웹 애플리케이션이 종료될 때 웹 컨테이너에 의해 파괴됩니다. ServletContext 객체는 getServletContext() 메서드를 사용하여 가져올 수 있습니다. 
- 즉,ServletContext는 웹 애플리케이션 전체에서 공유되는 정보를 관리하는 객체이다. ServletContext는 웹 애플리케이션의 전역 설정 및 데이터 공유를 위해 사용되는 핵심 객체. 서블릿 컨테이너가 웹 애플리케이션을 실행할 때 생성되며, 해당 애플리케이션 내 모든 서블릿과 JSP가 공유할 수 있는 전역적인 정보를 제공하는 역할을 한다.
- 톰캣이 실행되면서 생성된다. 서블릿 컨텍스트(ServletContext)란 하나의 서블릿이 서블릿 컨테이너와 통신하기 위해서 사용되어지는 메서드들을 가지고 있는 클래스가 바로 ServletContext다.
하나의 web application 내에 하나의 컨텍스트가 존재한다. web application내에 있는 모든 서블릿들을 관리하며 정보공유할 수 있게 도와 주는 역할을 담당하는 놈이 바로 ServletContext다. 쉽게 말하면 웹 애플리케이션의 등록 정보라고 볼 수 있다. 필터와 리스너 또한 등록하여 통신 간에 활용할 수 있다. 리스너는 서블릿 리스너, 세션 리스너 등 EventListener 구현체는 뭐든지 등록할 수 있다. 필터는 characterEncoding 등 Filter 구현체는 뭐든지 등록 할 수 있다.

## ServletContext의 주요 특징
- 애플리케이션 범위에서 공유됨
    - ServletContext는 특정 서블릿만이 아니라 웹 애플리케이션 전체에서 공유됨.
    - 모든 서블릿, JSP, 필터, 리스너가 접근 가능.
- 서블릿 컨테이너가 웹 애플리케이션 시작 시 생성하고 종료 시 소멸
    - ServletContext 객체는 웹 애플리케이션이 배포될 때 자동으로 생성됨.
    - 웹 애플리케이션이 종료되면 컨텍스트도 소멸됨.
- 애플리케이션의 설정 정보를 저장할 수 있음
    - 초기화 파라미터(web.xml 설정) 또는 코드에서 직접 값을 저장하여 활용 가능.
- 파일 경로 및 리소스 관리 기능 제공
    - 애플리케이션 내부 리소스(HTML, 이미지, 설정 파일 등)에 대한 경로를 조회할 수 있음.
- 전역 속성(attribute) 저장 가능
    - 서블릿 간 데이터를 공유하기 위해 속성을 저장할 수 있음

## ServletContext와 ServletConfig 차이
![Image](https://github.com/user-attachments/assets/79b3f9bf-027e-46e9-90c1-fafbe7f73e4b)

# servletContextListener
-  Java Servlet API에서 제공하는 인터페이스로, 웹 애플리케이션의 초기화 및 종료 시점에 특정 작업을 수행할 수 있도록 설계된 이벤트 리스너.  웹어플리케이션의 시작과 종료 이벤트를 핸들링할 수 있는 이벤트 리스너이다. ServletContextListener는 웹 애플리케이션의 생명주기를 감지하여 특정 작업을 수행하는 인터페이스이다. 즉, 웹 애플리케이션이 시작되거나 종료될 때 실행되는 이벤트를 처리하는 리스너 역할을 한다. 이를 통해 애플리케이션의 전역적인 리소스 초기화 및 정리를 처리할 수 있다. 서블릿 컨텍스트(ServletContext)의 생명주기를 감지하고 특정 동작을 수행할 수 있도록 해주는 인터페이스이다. 웹 애플리케이션이 시작되거나 종료될 때 실행할 작업을 정의하는 리스너라고 할 수 있다. 이를 활용하면 애플리케이션 초기화 작업(예: DB 연결, 설정 파일 로드)이나 종료 작업(예: 리소스 해제, 로그 기록)을 수행할 수 있다.

### 서블릿 리스너 동작 구조 
![image](https://github.com/user-attachments/assets/5d18c53e-486d-49fe-b59f-3f1f042dfff2)

## 주요 역할
- 웹 애플리케이션 초기화 시 작업 수행(웹 애플리케이션이 시작될 때(contextInitialized) 실행)
    - 서버가 ServletContext를 생성할 때 호출된다.즉,웹 애플리케이션이 배포되면서 서블릿 컨텍스트가 생성될 때 호출됨.
    - 데이터베이스 연결 풀 생성, 설정 파일 읽기, 캐시 초기화 등 애플리케이션 시작에 필요한 작업을 수행할 수 있다. DB 연결 풀을 생성하거나, 설정 정보를 로드하는 등의 초기화 작업 수행한다고 보면 된다.
- 웹 애플리케이션 종료 시 작업 수행(웹 애플리케이션이 종료될 때(contextDestroyed) 실행)
    - 서버가 ServletContext를 소멸시킬 때 호출됩니다.
    - 데이터베이스 연결 닫기, 임시 파일 삭제, 리소스 정리 등 애플리케이션 종료 작업을 처리할 수 있습니다.

## ServletContextListener와 ServletContext의 관계
- ServletContextListener는 ServletContext의 생명주기를 감지함.
- contextInitialized()에서 ServletContext를 활용하여 전역 데이터를 저장할 수 있음.
- contextDestroyed()에서 ServletContext를 활용하여 정리 작업 수행 가능.

## ServletContextListener의 생명주기
### 1. 웹 애플리케이션 시작
- 서버가 웹 애플리케이션을 실행하면서 ServletContextListener를 감지
- contextInitialized(ServletContextEvent event) 실행됨
- 초기화 작업 수행
    - DB 연결 풀 설정
    - 전역 데이터 로드
    - 로그 시스템 초기화
### 2. 웹 애플리케이션 실행 중
- 여러 서블릿과 JSP가 실행되며 ServletContext를 통해 공유 데이터를 관리할 수 있음.
### 3. 웹 애플리케이션 종료
- 서버가 웹 애플리케이션을 중지하거나 종료
- contextDestroyed(ServletContextEvent event) 실행됨
    - 정리 작업 수행
    - DB 연결 해제
    - 캐시 정리
    - 로그 저장

## ServletContextListener의 주요 메서드
- contextInitialized(ServletContextEvent event)
    - 애플리케이션이 시작될 때 실행됨. 초기화 작업 수행 (예: DB 연결, 설정 로드).
- contextDestroyed(ServletContextEvent event)
    - 애플리케이션이 종료될 때 실행됨. 자원 해제 및 정리 작업 수행 (예: DB 연결 해제, 캐시 정리).


### 질문
#### ServletContextListener를 사용하여 데이터베이스 연결 풀을 초기화하려면 어떤 단계가 필요한가요?
- 데이터베이스 연결 풀을 초기화하려면 먼저 ServletContextListener 인터페이스를 구현한 클래스를 작성하여 contextInitialized 메서드에서 연결 풀을 설정합니다. 그런 다음 생성된 연결 풀을 ServletContext에 속성으로 저장하여 애플리케이션 전역에서 사용할 수 있도록 합니다. 마지막으로, contextDestroyed 메서드에서 연결 풀을 종료하여 리소스를 반환합니다.

#### ServletContextListener를 web.xml 대신 어노테이션으로 등록하려면 어떤 어노테이션을 사용해야 하나요?
- ServletContextListener를 어노테이션으로 등록하려면 @WebListener 어노테이션을 사용해야 합니다. 이 어노테이션은 Servlet 3.0 이상에서 지원하며, 이를 통해 web.xml 파일을 수정하지 않고도 리스너 클래스를 자동으로 등록할 수 있습니다.

#### ServletContext와 ServletConfig의 차이점은 무엇인가요?
- ServletContext는 웹 애플리케이션 전역에서 공유되는 설정 정보와 자원을 관리하는 객체이며, 웹 애플리케이션 내 모든 서블릿과 JSP가 접근할 수 있습니다. 반면, ServletConfig는 특정 서블릿에 대한 설정 정보를 제공하는 객체로, 개별 서블릿마다 생성되며 해당 서블릿에서만 사용할 수 있습니다.

#### ServletContextListener를 활용하면 어떤 장점이 있나요?
- ServletContextListener를 활용하면 웹 애플리케이션이 시작될 때 필요한 리소스를 초기화하고, 종료될 때 정리하는 작업을 자동으로 수행할 수 있습니다. 예를 들어, 애플리케이션 시작 시 DB 연결 풀을 설정하거나 전역 캐시를 초기화할 수 있으며, 종료 시에는 DB 연결을 해제하거나 임시 파일을 정리할 수 있어 효율적인 리소스 관리가 가능합니다.

---

참고링크 

https://velog.io/@bonvoyage_/%EC%84%9C%EB%B8%94%EB%A6%BF-ServletContext

https://cyk0825.tistory.com/78

https://jordy-torvalds.tistory.com/entry/%EC%9E%90%EB%B0%94-EE-Servlet-ServletContext-%EC%8A%A4%ED%94%84%EB%A7%81-MVC-%EC%9E%90%EB%8F%99%EC%84%A4%EC%A0%95