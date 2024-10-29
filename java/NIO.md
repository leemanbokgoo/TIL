
# NIO (new I/O)
![image](https://github.com/user-attachments/assets/fed9730d-1d7e-433f-b61a-3e52a6ff4dbc)
- 위의 그림은 java에서 IO를 처리하는 전체적인 구조를 보여주는 그림
- 커널 버퍼에서 JVM내의 Buffer로 한번 더 데이터를 옮겨주는 과정이 생기면서 발생하는 문제점 -> JVM 내부 버퍼로 복사 시 발생하는 CPU연산, CG관리, IO 요청에 대한 스레드 블록이 발생하는 현상 때문에 효율이 좋지못함.
- NIO는 자바 7 부터 나온 패키지로 기존의 I/O 패키지를 개선하기위해 나왔다. 자바 NIO 사이의 일관성 없는 클래스 설계를 바로 잡고 비동기 채널등의 네트워크 지원을 대폭 강화한 NIO 2. API가 추가되었음.
- 기존 입출력 방식인 Stream 기반 IO와는 다른 방식의 입출력 처리를 제공. 논 블로킹(non-blocking) IO를 지원하며 채널(Channel)과 버퍼(Buffer)를 통해 데이터를 처리

## NIO 도입 배경
- 기존 스트림 기반 IO의 한계 : 
    - 스트림 기반 IO는 데이터를 읽거나 쓸때 해당 작업이 완료될때까지 블로킹(blocking)되는 특성이 있음. 이는 멀티스레드 환경에서 자원의 효율적 사용에 제약을 주며 대규모 데이터 처리나 네트워크 프로그래밍에서 성능저하의 원인이 됨.
- NIO는 이러한 문제를 해결하기위해 비동기 방식의 입출력 처리를 가능하게 함.
    - 즉, 입출력 작업이 블로킹 되지않고 데이터를 처리하는 동안 다른 작업을 동시에 수행할 수 있게 됨 -> 어플리케이션의 성능을 크게 향상시킴.

## 채널과 버퍼
- NIO의 핵심 구성 요소인 채널과 버퍼는 데이터의 입출력을 효율적으로 관리.
- 채널은 데이터의 소스나 목적지와 연결되는 통로 역할을 하며, 버퍼는 데이터를 임시 저장하는 메모리 공간. 
- 이를 통해 개발자는 보다 세밀하게 데이터의 입출력을 제어할 수 있습니다.

## NIO의 핵심 구성 요소와 작동 원리
- NIO의 핵심 구성 요소는 채널(Channel), 버퍼(Buffer), 셀렉터(Selector). 
    - 채널은 데이터의 읽기와 쓰기를 담당하는 통로로, 파일, 소켓 등 다양한 데이터 소스와 연결될 수 있음. 
    - 버퍼는 채널을 통해 전송되는 데이터를 저장하는 메모리 공간으로, 데이터의 임시 저장 및 전송 단위로 사용됨.
    - 셀렉터는 비동기 입출력을 관리하는 컴포넌트로, 여러 채널의 입출력 이벤트를 동시에 감시하고 처리할 수 있다. 이를 통해 하나의 스레드로 여러 네트워크 연결을 효율적으로 관리할 수 있게 됨. 

- NIO의 작동 원리는 논블로킹 입출력과 이벤트 기반의 입출력 처리에 기반한다. 개발자는 셀렉터를 사용하여 여러 채널의 상태를 모니터링하고, 준비된 입출력 작업을 선택적으로 처리할 수 있다. 이는 멀티스레드 환경에서 자원의 효율적 사용과 성능 최적화를 가능하게 한다.
- NIO를 사용함으로써, 개발자는 네트워크 프로그래밍이나 대규모 파일 처리와 같은 고성능 애플리케이션 개발에 있어 유연성과 성능을 동시에 확보할 수 있다.


## 자바와 IO와 NIO의 차이 
![image](https://github.com/user-attachments/assets/96adb12f-308d-4017-80e5-9f6a67854c0d)

#### 스트림 VS 채널
- IO는 스트림 기반이다, 스트림은 입력 스트림과 출력 스트림으로 구분되어있기때문에 데이터를 읽기 위해서는 입력 스트림을 생성해야 하고 데이터를 출력하기 위해서는 출력 스트림 생성해야한다. NIO는 채널 기반으로 채널 스트림과 달리 양방향으로 입력과 출력이 가능하다. 그렇기 때문에 입력과 출력을 별도의 채널을 만들 필요가 없음

#### NonBuffer VS Buffer
- IO에서는 출력 스트림이 1바이트를 쓰면 입력 스트림이 1바이트를 읽는다. 이것보다는 버퍼를 사용해서 복수개의 바이트를 한꺼번에 입력받고 출력하는 것이 빠른 성능 낸다. 그래서 IO는 버퍼를 제공 해 주는 보조 스트림인 BufferedInputStream, BufferedOutpuStream을 연결해서 사용하기도 한다. NIO는 기본적으로 버퍼를 사용해서 입출력을 하므로 IO보다는 성능이 좋다. 채널은 버퍼에 저장된 데이터를 출력하고 입력된 데이터를 버퍼에 저장한다. 
- IO는 스트림에서 읽은 데이터를 즉시 처리 -> 입력된 전체 데이터를 별도로 저장하지않으면, 입력 된 데이터의 위치를 자유롭게 이용할 수 없다. NIO는 읽은 데이터를 무조건 버퍼에 저장하기때문에 버퍼내에서 데이터의 위치를 이동해가면서 필요한 부분만 읽고 쓸수 있다.

#### 블로킹 VS Non-블로킹
- IO는 블로킹이 된다. 입ㄹ력 스트림의 read() 출력 스트림의 write() 메소드를 호출 하면 블로킹이 된다. IO 스레드가 블로킹이 되면 다른 일을 할수 없음 -> 블로킹을 빠져나오기위해 입터럽트도 할 수 없고 블로킹을 빠져나오는 방법은 스트림을 닫는 것 뿐
- NIO는 블로킹과 non-블로킹 특징을 모두 가지고있다. IO 블로킹과의 차이점은 NIO 블로킹은 스레드를 인터럽트함으로써 빠져나올 수가 있다는 것. NIO의 Non-블로킹은 입출력 작업 준비가 완료된 채널만 선택해서 작업도 스레드가 처리하기때문에 작업 스레드가 블로킹 되지않는다. NIO non-블로킹의 핵심 객체는 멀티 플렉서인 selector다. 셀럭터는 복수 개의 채널 중에서 이벤트가 준비 완료된 채널을 선택하는 ㅂ아법을 제공해준다. 

### IO와 NIO 중 어떤 것을 선택해야하는 지 
- IO를 사용해야 하는 순간:
    - 단순한 파일 읽기/쓰기: 파일 I/O 작업이 간단하고 직렬적으로 이루어지는 경우, IO가 더 적합.
    - 적은 수의 클라이언트: 동시에 처리해야 하는 클라이언트 수가 적고, 각 클라이언트와의 연결이 오래 지속되지 않는 경우 IO를 사용하는 것이 간편.
    - 높은 수준의 추상화 필요: 코드의 간결함과 단순함이 중요한 경우, IO가 더 직관적이므로 쉽게 이해하고 사용할 수 있다.
- NIO를 사용해야 하는 순간:
    - 많은 연결 처리: 동시에 많은 클라이언트와 연결을 유지해야 하는 서버 애플리케이션의 경우, NIO의 비동기 및 논블로킹 처리 능력이 유리
    - 대량의 데이터 전송: 대량의 데이터를 효율적으로 처리해야 하는 경우, NIO는 버퍼를 활용하여 성능을 극대화할 수 있다.
    - 비동기 처리 필요: 작업이 블로킹되지 않아야 하거나, 스레드 수를 줄이고 효율적으로 재사용하고자 하는 경우 NIO가 적합
    - 복잡한 I/O 작업: 파일 시스템과 네트워크에서 더 정교한 입출력 제어가 필요할 때, NIO의 다양한 기능을 활용할 수 있다.
- 결론적으로, IO는 단순하고 직렬적인 작업에 적합하고, NIO는 많은 연결이나 대량의 데이터 처리에 최적화된 선택


### 질문 다섯개

#### Q. IO와 NIO의 차이점은 무엇인가요?
- A. IO와 NIO의 주요 차이점은 데이터 처리 방식과 비동기 지원 여부에 있습니다. IO는 블로킹 방식으로 동작하여 데이터 처리가 완료될 때까지 다음 작업을 수행할 수 없지만, NIO는 논블로킹 방식으로 데이터를 처리할 수 있어 하나의 스레드가 여러 작업을 동시에 처리할 수 있습니다. 또한, IO는 스트림 기반인 반면, NIO는 버퍼 기반으로 데이터를 읽고 쓰며, 채널을 사용하여 더 효율적으로 파일이나 네트워크에 접근할 수 있습니다. 이를 통해 NIO는 고성능 네트워크 애플리케이션에서 보다 유리하게 사용됩니다.

#### Q. NIO의 채널(Channel)과 버퍼(Buffer)의 역할은 무엇인가요? 문장으로
- A. NIO에서 채널(Channel)은 데이터 전송의 경로를 제공하며, 파일이나 네트워크 소켓 등과의 연결을 관리하는 역할을 합니다. 반면, 버퍼(Buffer)는 데이터의 임시 저장소로 사용되어 채널을 통해 읽거나 쓸 데이터를 저장하고, 데이터의 효율적인 처리와 전송을 가능하게 합니다. 즉, 채널은 데이터를 읽고 쓰는 통로 역할을 하고, 버퍼는 그 데이터를 담아두는 공간으로 기능하여 NIO의 성능을 극대화합니다.

#### Q. NIO를 사용할 때의 성능상 이점은 무엇인가요? 
- NIO를 사용할 때의 성능상 이점은 비동기 처리와 논블로킹 I/O를 통해 여러 작업을 동시에 처리할 수 있다는 점입니다. 이를 통해 스레드의 수를 줄이고, CPU 자원을 효율적으로 활용할 수 있으며, 대량의 클라이언트 요청을 더 빠르게 처리할 수 있습니다. 또한, NIO의 버퍼링 기법을 통해 데이터 전송의 효율성을 높여, 파일이나 네트워크와의 I/O 작업에서 더 높은 성능을 발휘할 수 있습니다. 이러한 특징 덕분에 NIO는 고성능 네트워크 애플리케이션 및 대량의 데이터 처리가 필요한 경우에 유리합니다.

#### Q. NIO를 사용하여 넌블로킹 I/O를 구현하는 방법은 무엇인가요?
- NIO를 사용하여 논블로킹 I/O를 구현하려면, 먼저 Selector 객체를 생성하여 여러 SocketChannel을 등록하고, 각 채널을 논블로킹 모드로 설정해야 합니다. 이후, Selector의 select() 메서드를 호출하여 준비된 채널을 확인하고, 각 채널에서 read() 또는 write() 메서드를 호출하여 데이터를 비동기적으로 읽거나 쓸 수 있습니다. 이러한 방식으로 단일 스레드에서 여러 채널의 I/O 작업을 동시에 처리할 수 있어 효율적인 네트워크 통신을 구현할 수 있습니다.

#### Q. NIO를 사용할 때의 주의점은 무엇인가요?
- NIO를 사용할 때 주의해야 할 점은 여러 가지가 있습니다. 먼저, 버퍼를 적절하게 관리해야 하며, 크기가 너무 작으면 성능 저하가, 너무 크면 메모리 낭비가 발생할 수 있습니다. 또한, SocketChannel이나 ServerSocketChannel을 논블로킹 모드로 설정하기 위해 configureBlocking(false) 메서드를 호출하는 것이 중요합니다. Selector를 사용할 때는 select() 메서드의 반환값을 확인하여 준비된 채널이 없을 경우 적절한 대기 시간을 설정해야 합니다. 멀티스레드 환경에서는 NIO의 버퍼에 동시에 접근할 수 있어 데이터 일관성이 깨질 수 있으므로 동기화 메커니즘을 사용해야 합니다. 마지막으로, 채널, 선택기, 버퍼 등 NIO 리소스를 사용한 후에는 반드시 닫아야 하며, 이를 통해 메모리 누수나 리소스 고갈을 방지할 수 있습니다.

참고링크 

https://velog.io/@tkadks123/Java-NIO%EC%97%90-%EB%8C%80%ED%95%B4-%EC%95%8C%EC%95%84%EB%B3%B4%EC%9E%90-12

https://brunch.co.kr/@myner/47