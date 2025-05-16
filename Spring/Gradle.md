### 빌드 build
- 소스 코드 파일을 실행가능한 소프트웨어 산출물로 만드는 일련의 과정을 build라고 한다. 빌드 단계중 컴파일 그리고 링크가 포함되어있으며 컴파일과 링크 모두 빌드의 부분 집합이라 할 수 있다. 빌드 과정을 도와주는 도구를 빌드 툴이라고 하는데 Gradle이 빌드 툴 중 하나이다.

### 컴파일 complie
- 컴파일이란 개발자가 작성한 소스 코드를 Binary 코드의 오브젝트 파일로 변환하는 과정을 말한다. 즉, 컴퓨터가 이해할 수 있는 기계어로 변환하는 작업이다. 이러한 작업을 해주는 프로그램을 컴파일러라고 하면 자바의 경우 JVM에서 .java파일을 실행가능한 바이트 코드 형태의 클래스 파일이 자동적으로 새엇ㅇ한다.

### 링크 
- 프로젝트를 진행하다보면 여러 소스 파일이 생성되고 A라는 소스파일에서 B 소스파일에서 존재하는 메서드를 호출하는 경우가 많다. 이때 A와 B 소스 파일을 각각 컴파일만 하는 경우 A가 B에 존재하는 메서드를 찾지 못해 호출하지 못하게 된다. 따라서 A와 B를 연결해주는 작업이 필요한다. 이 작업을 링크라고 한다.
- 링크에는 정적 링크와 동적 링크가 있다. 
    - 정적 링크 : 컴파일된 소스 파일을 연결해서 실행가능한 파일을 만드는 것
    - 동적 링크 : 프로그램 실행 도중 프로그램 외부에 존재하는 코드를 찾아 연결하는 작업
- 자바의 경우 JVM이 프로그램 실행 도중 필요한 클래스를 찾아 classpath에 로드해주는데 이는 동적 링크의 예다.

## Gradle
- gradle은 오픈소스 빌드 자동화 툴로, 거의 모든 타입의 소프트웨어를 빌드할 수 있는 유연함을 가진다.

## Gradle 의 특징

### High Performance : build cache(빌드 캐시)
- Gradle은 실행시켜야 하는 task만 실행시키고 다른 불필요한 동작은 하지 않으며, build cache를 사용함으로써 이전 실행의 task output을 재사용한다. 심지어 서로 다른 기계에서도 build cache를 공유하여 성능을 높힐 수 있다.
- 작업(task)의 결과를 저장해두었다가 입력값이 동일하면 이전 결과를 재사용하는 것을 빌드 캐싱(Build Caching)이라고 하며, 크게 두 가지가 있다.
    - 로컬 캐시 : 사용자의 로컬 디스크에 저장
    - 원격 캐시 : 공유 서버 또는 파일 시스템에 저장되어 여러 빌드 환경에서 재사용 가능.
- 기본적으로 로컬 캐시는 활성화되어 있으며, 원격 캐시는 수동 설정이 필요하다.
- Gradle의 캐시는 기본적으로 ~/.gradle/caches/에 저장된다. Gradle은 각 Task에 대해 고유한 캐시 키를 생성하는데, 이 키는 입력 파일의 해시, Task 구현 클래스, Gradle 버전, 환경 변수 등을 기반으로 구성되며 이 키를 기준으로 캐시된 출력물을 찾아서 재사용 여부를 결정한다. 따라서 Task에 @Input, @Output 애노테이션을 명확히 지정하고, 입력이 바뀌지 않는 한 Gradle은 해당 Task를 실행하지 않고 캐시된 결과를 그대로 사용한다. 이런 방식으로 빌드 시간을 크게 단축할 수 있고, 특히 CI 환경이나 다수의 개발자가 협업하는 프로젝트에서 효과적이다.
- Task가 캐시되지 않는 이유는 예를 들어 @Input이나 @Output이 누락되었거나, Task가 상태 비저장(stateless)이 아니거나, doLast 블록 내에서 비결정적 작업(랜덤, 현재 시간 등)을 수행하면 캐시가 적용되지 않을 수 있다.
-  캐시 키 충돌이나 오염 방지는 어떻게 하냐면, Gradle은 해시 기반의 키 생성으로 충돌 가능성을 최소화하며, 각 Task의 구현체 정보까지 고려하기 때문에 Task 로직이 바뀌면 캐시도 무효화된다.

### JVM foundation
- Gradle은 JVM 에서 실행되기 때문에 JDK를 설치해야 한다. 또한 Java Standard API를 빌드 로직에 사용할 수 있으며 , 다양한 플랫폼에서 실행할 수 있다.

### Convetions
- Gradle은 Maven으로부터 의존 라이브러리 관리 기능을 차용했다. 따라서 컨벤션을 따라 Java 프로젝트와 같은 일반적인 유형의 프로젝트를 쉽게 빌드할 수 있으며, 필요하다면 컨벤션을 오버라이딩하거나 task를 추가해 컨벤션 기반의 빌드를 커스터마이징할 수 있다.

### Extensibility
- Gradle을 확장하면 고유의 task 타입을 제공하거나 모델을 빌드할 수 있다.

### IDE , Build Scan support
- Android Studio , IntelliJ IDEA , Eclipse 등의 IDE에서 Gradle을 임포트하여 사용할 수 있으며 , 빌드를 모니터링할 수 있는 Build Scan을 지원한다.

## Gradle의 Build Lifecycle 3단계
- Gradle은 아래의 3개의 단계로 빌드가 진행된다.
    - 초기화(Initialization) : 빌드 대상 프로젝트를 결정하고 각각에 대한 Project 객체를 생성. settings.gradle 파일에서 프로젝트 구성 (멀티프로젝트, 싱글프로젝트 구분)
    - 구성(Configuration) : 빌드 대상이 되는 모든 프로젝트의 빌드 스크립트를 실행. (프로젝트 객체 구성) configured Task 실행
    - 실행(Execution) : 구성 단계에서 생성하고 설정된 프로젝트의 태스크 중에 실행 대상 결정. gradle 명령행에서 지정한 태스크 이름 인자와 현재 디렉토리를 기반으로 태스크를 결정하여 선택된 Task들을 실행

### 초기화(Initialization) 단계 - settings.gradle 파일
- 초기화 단계에서는 빌드를 진행할 프로젝트를 결정하고 각각에 대한 프로젝트 인스턴스 객체를 생성한다. 이 과정은 settings.gradle 파일에서 진행된다.settings.gradle 파일은 루트 프로젝트 디렉터리에 있으며, 프로젝트 수준 저장소 설정을 정의하고, 앱을 빌드할 때 포함해야 하는 모듈을 Gradle에 알려준다.
- 초기화하는 과정에서는 setting.gradle을 읽어서 gradle이 빌드할 대상 프로젝트가 결정된다. 실제 프로젝트의 이름과 rootProject.name이 서로 같아야 한다.

### 구성(Configuration) 단계 
- 이 과정에서는 프로젝트에 포함된 모든 build script가 실행이 됨과 동시에 라이브러리를 가져오는 작업이 진행된다. 대표적으로 build.gradle이 실행되면서 해당 build script 파일에 설정된 라이브러리들을 읽어오는 것이다.

### 실행(Execution) 단계
- Configuration단계에서 준비가 된 내용을 바탕으로 실제 Task의 Actions를 실행한다.

##  Gradle 구성 요소
- 하나의 Gradle 프로젝트를 생성하면 기본적으로 아래의 파일들이 생성된다.
```
├── build.gradle
├── gradlew
├── gradlew.bat
├── settings.gradle
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
└── src
    ├── main
    │   └── ...
    └── test
    │   └── ...
```
- 주요 구성 요소는 build.gradle, gradlew (gradlew.bat), settings.gradle이다.

## 1. build.gradle
-  gradle로 빌드를 하면 (gradle wrapper로 빌드하면) build.gradle에 적힌대로 build된다. build.gradle파일은 plugins, repositories, dependencies, java, test의 항목으로 이루어져있다. java와 test는 하나의 task로써 java는 plugin에서 사용되고, test는 테스트시 사용된다.
    - 참고로 task는 하나의 작업들을 묶어놓은 블록같은 것이다. gradle에는 java task가 포함되어 있는데, gradle java 명령을 실행하면 java task에 적힌 명령이 실행된다.
 
### plugins
- plugin은 미리 구성한 features, compile된 Java 코드, JavaCompile 등의 Task, SourceSet 등의 domain object 가져오기, convention 지정 등 다양한 객체들을 추가할 수 있다. 
    - 새로운 DSL elements들을 추가하는 등 Gradle 모델 확장
    - 새로운 task 추가나 defaults 구성 등 지정된 convention에 따라 project를 구성
    - organizational repositories를 추가하거나 기준을 강제시키는 등 특정 configuration 적용

### repositories
- repositories는 아래 기술할 dependecies에 따라 사용하는 라이브러리들을 자동으로 다운로드하는 위치를 지정한다. 즉, gradle을 통해 빌드를 하게 되면, 해당 저장소 위치에서 필요한 라이브러리들을 다운로드한다.
 
### dependencies
- dependencies는 프로젝트에서 사용하는, 필요한 라이브러리를 지정한다. 이때 build 라이프 사이클의 적절한 시점에서 classpath에 추가해준다. 예를 들어 test시에만 사용할 라이브러리를 지정할 수 있고, compile time, runtime 각각에만 사용될 의존성들이 있을 수 있다. 개발자가 이를 build.gradle에 이를 명시하면 빌드 툴은 해당 시점에 맞는 의존성들을 추가한다.

![Image](https://github.com/user-attachments/assets/008beb23-2bba-44e7-ba53-c4924f14852c)

- 참고로 현재 compile은 지원이 중지됐는데, compile은 implementation으로 대체할 수 있기 때문이다.
- 이렇게 맞는 사이클에만 적절하게 추가하는 경우 다음과 같은 장점이 생긴다.
    - 컴파일이 빨라진다. (compile 할 때 사용할 일 없는 dependency를 classpath에서 뺀다면 빨라질 수 있다.)
    - 코드 작성 시, runtime classpath에 있어야하는 dependency를 실수로 사용하는 것을 미연에 방지할 수 있다.
    - classpath 목록을 깔끔하게 정리하는 것도 편해진다.이렇게 필요할 때만 의존성을 추가해준다면 여러 장점이 생긴다.

### test
- test는 gradle을 통해서 test를 수행할 때 사용한다. 예를 들어 터미널에서 gradle test를 입력하면, 어떤 작업을 통해 test를 수행할지 명시하는 것이다. 이 또한 task의 일종.


## 2. Gradle Wrapper (gradlew, gradlew.bat)
- Gradle Wrapper는 Gradle을 각 개발자나 CI 서버에 깔지 않고, 프로젝트에 함께 포함시켜 배포할 수 있는 방법을 제공해준다. gradle의 선언된 버전을 호출해 미리 다운로드해 빌드를 준비하고 gradle 프로젝트를 빠르게 실행시킬 수 있도록 돕는다.
- Wrapper를 사용하는 이유Gradle 빌드를 실행하는데 권장되는 방법은 Gradle Wrapper(gradlew)를 사용하는 것이다. Wrapper는 선언된 버전의 Gradle을 호출해 필요한 경우 미리 다운로드하는 스크립트 이다. 결과적으로 수동 설치 프로세스를 수행하지 않고도 Gradle 프로젝트를 신속하게 시작할 수 있게 된다.즉, Graddle Wrapper를 사용하면 이미 존재하는 프로젝트를 새로운 환경에서도 바로 빌드할 수 있게 되며 Java나 Gradle을 따로 설치할 필요가 없어진다. 환경에 종속되지 않게 되는 것.

## 3. Setting.gradle
- setting.gradle은 빌드 이름과 하위 프로젝트를 정의하며, 빌드 대상이 되는 프로젝트를 설정하는 스크립트.
    - rootProject.name = 'DBMigrator'
- 해당 프로젝트와 하위 프로젝트들 사이의 관계 및 프로젝트의 구성 정보를 기록하는 파일이다. 사용자 정보 및 실행환경 초기화 등으로 응용할 수 있다. 우선 작성하면, gradle은 해당 파일에 기술된대로 프로젝트를 구성하게 된다.
```
rootProject.name = 'fleamarket'

include 'marketbatch'
include 'marketcrawler'
```
- 이렇게 구성하면, fleamarket 프로젝트의 하위 프로젝트로 marketbatch, marketcrawler를 포함할 수 있다. 이후 최상위 프로젝트인 fleamarket 프로젝트의 build.gradle에 subprojects에 대한 설정을 추가한다면, 모든 하위 프로젝트에 해당 설정을 적용시킬 수 있다.


### 질문
####  Gradle의 빌드 캐시(Build Cache)는 어떤 원리로 작동하며, 사용 시 유의할 점은 무엇인가요?
- Gradle의 빌드 캐시는 이전에 수행한 Task의 출력 결과를 저장하고, 동일한 입력값이 주어지면 이를 재사용하여 빌드 시간을 단축하는 기능입니다. 이 캐시는 크게 로컬 캐시와 원격 캐시로 나뉘며, 기본적으로 로컬 캐시는 활성화되어 있고 원격 캐시는 설정이 필요합니다.
    - Gradle은 각 Task에 대해 고유한 캐시 키를 생성하는데, 이 키는 입력 파일의 해시, Task 구현 정보, Gradle 버전, 환경 변수 등을 바탕으로 계산됩니다. 입력값이 같으면 캐시된 출력 결과를 재사용하고, 입력이 바뀌면 새롭게 빌드가 수행됩니다.
- 사용 시 유의할 점은 다음과 같습니다
    - @Input, @Output 애노테이션을 명확하게 지정하지 않으면 캐싱이 적용되지 않을 수 있습니다.
    - Task 내부에서 랜덤값이나 현재 시간 등 비결정적 작업을 수행하면 캐시 무효화가 발생할 수 있습니다.
    - 캐시 충돌은 해시 기반 키와 Task 구현체까지 고려한 고유 키로 방지되며, 캐시 오염도 최소화됩니다.
- 이러한 캐시 기능은 특히 CI/CD 환경이나 팀 개발에서 빌드 성능 향상에 큰 효과를 줍니다.



#### Gradle의 빌드 라이프사이클 세 단계(초기화, 구성, 실행)에 대해 설명해 주세요.
- Gradle의 빌드는 크게 초기화(Initialization), 구성(Configuration), 실행(Execution) 세 단계로 나뉩니다
- 초기화 단계는 settings.gradle 파일을 기준으로 어떤 프로젝트들이 빌드에 포함될지 결정하며, 각 프로젝트에 대해 Project 객체를 생성합니다. 멀티 프로젝트 구성 시 이 단계에서 하위 프로젝트들을 등록하게 됩니다.
- 구성 단계는 각 프로젝트의 build.gradle 스크립트를 실행하며, 어떤 Task들이 존재하는지를 구성합니다. 이 단계에서 실제 Task가 실행되지는 않고, 실행 가능한 Task들의 설정만 이뤄집니다.
- 실행 단계는 명령어로 지정한 Task만 실행됩니다. 예를 들어 gradle build를 실행하면, 구성 단계에서 등록된 Task들 중 build Task와 그에 의존된 Task들만 실행됩니다.
- 이러한 구조는 빌드 성능 최적화에 중요한 역할을 하며, 필요한 Task만 실행되는 효율적인 빌드 환경을 제공합니다.

---

참고링크 

https://velog.io/@jaeygun/Spring-Boot-gradle%EC%9D%B4%EB%9E%80-gradle%EC%9D%98-%EB%8F%99%EC%9E%91-%EC%88%9C%EC%84%9C

https://dream-and-develop.tistory.com/358

https://taler.tistory.com/30

https://velog.io/@tjseocld/Gradle-%EC%9D%B4%EB%9E%80