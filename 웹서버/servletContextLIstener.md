- 서블릿 리스너 : 웹 컨테이너가 관리하는 라이프 사이클 사이에 발생하는 입네트를 감지하여 해당 이벤트가 발생 시 해당 이벤트에 대한 일련의 로직을 처리하는 인터페이스 git

### 서블릿 리스너 동작 구조 
![image](https://github.com/user-attachments/assets/5d18c53e-486d-49fe-b59f-3f1f042dfff2)

# servletContextListener
- 웹 컨테이너는 웹 어플리케이션(컨텍스트)이 시작·종료되는 시점에 특정 클래스의 메서드를 실행할 수 있는 기능을 제공한다.이 기능을 통해 웹 어플리케이션 실행시 필요한 초기화 작업 또는 종료된 후 사용된 자원을 반환하는 작업 등을 수행 할 수 있다. 
-  Java Servlet API에서 제공하는 인터페이스로, 웹 애플리케이션의 초기화 및 종료 시점에 특정 작업을 수행할 수 있도록 설계된 이벤트 리스너. 이를 통해 애플리케이션의 전역적인 리소스 초기화 및 정리를 처리할 수 있다.
-  웹 어플리케이션 시작·종료시 특정한 기능을 실행하기위해서는 아래의 코드를 작성하면 된다.
    - 1. javax.servlet.ServletContextListener 인터페이스를 구현한 클래스를 작성
    - 2. web.xml 파일에 1번에서 작성한 클래스를 등록
- javax.servlet.ServletContextListener: 웹 어플리케이션이 시작·종료될 때 호출할 메서드를 정의한 인터페이스
- javax.servlet.ServletContextListener 인터페이스는 웹 어플리케이션이 시작되거나 종료될 때 호출할 메서드를 정의할 인터페이스로서, 다음과 같은 두 개의 메서드를 정의하고 있다.
    - public void contextInitialized(ServletContext sce) : 웹 어플리케이션을 초기화할 때 호출한다.
    - public void contextDestroyed(ServletContext sce) : 웹 어플리케이션을 종료할 때 호출한다.
- 웹 어플리케이션이 시작되거나 종료될 때 ServletContextListener 인터페이스를 구현한 클래스를 실행하려면 web.xml 파일에 <listener> 태그와 <listener-class> 태그를 사용해서 완전한 클래스 이름을 명시해주면 된다

## 주요 역할
- 웹 애플리케이션 초기화 시 작업 수행
    - 서버가 ServletContext를 생성할 때 호출됩니다.
    - 데이터베이스 연결 풀 생성, 설정 파일 읽기, 캐시 초기화 등 애플리케이션 시작에 필요한 작업을 수행할 수 있습니다.
- 웹 애플리케이션 종료 시 작업 수행
    - 서버가 ServletContext를 소멸시킬 때 호출됩니다.
    - 데이터베이스 연결 닫기, 임시 파일 삭제, 리소스 정리 등 애플리케이션 종료 작업을 처리할 수 있습니다.

### 질문
#### ServletContextListener를 사용하여 데이터베이스 연결 풀을 초기화하려면 어떤 단계가 필요한가요?
- 데이터베이스 연결 풀을 초기화하려면 먼저 ServletContextListener 인터페이스를 구현한 클래스를 작성하여 contextInitialized 메서드에서 연결 풀을 설정합니다. 그런 다음 생성된 연결 풀을 ServletContext에 속성으로 저장하여 애플리케이션 전역에서 사용할 수 있도록 합니다. 마지막으로, contextDestroyed 메서드에서 연결 풀을 종료하여 리소스를 반환합니다.

#### ServletContextListener를 web.xml 대신 어노테이션으로 등록하려면 어떤 어노테이션을 사용해야 하나요?
- ServletContextListener를 어노테이션으로 등록하려면 @WebListener 어노테이션을 사용해야 합니다. 이 어노테이션은 Servlet 3.0 이상에서 지원하며, 이를 통해 web.xml 파일을 수정하지 않고도 리스너 클래스를 자동으로 등록할 수 있습니다.


