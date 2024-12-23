# socket
![image](https://github.com/user-attachments/assets/d51f544d-4322-4522-8648-a306a28eb0eb)

![image](https://github.com/user-attachments/assets/bf6bd19f-0b1b-4d6f-81b2-ca003b07d7b7)

- 두 프로그램이 서로 데이터를 주고받을 수 있도록 양쪽(두 프로그램 모두)에 생성되는 통신단자이다.
- TCP/IP 4계층에서 전송 계층에 놓임.
- 전송계층 위에서 전송 계층의 포로토콜 제어를 위한 코드 제공 
- 즉 , **소켓은 엔드포인트이다. 통신의 양끝단**
- 프로그램이 네트워크에서 데이터를 주고받을 수 있도록 네트워크 환경에 연결 할 수 있게 만들어진 **연결부**(창구역할) , 일반적으로 TCP/IP 프로토콜을 이용함.
- **그러므로 프로세스가 데이터를 보내거나 받기위해서는 반드시 소켓을 열어서 소켓에 데이터를 써보내거나 소켓으로부터 데이터를 읽어들여야한다.**
- TCP/IP를 이용하는 창구 역할
- 두 프로그램이 네트워크를 통해 서로 통신을 수행할 수 있도록 양쪽에 생성되는 링크의 단자
⇒ 서로 다른 프로세스끼리 데이터 전달이 가능하다
- 소켓통신이란 서버와 클라이언트 양방향 연결이 이루어지는 통신으로 클라이언트도 서버로 부터 요청을 보낼 수 있고 서버도 클라이언트로 부터 요청을 보낼 수 있는 통신.
- 클라이언트와 서버 양쪽에서 서로에게 데이터를 전달하는 방식의 양방향 통신
- 보통 스트리밍이나 실시간 채팅 등 실시간으로 데이터를 주고 받아야하는 경우 Connection을 자주 맺고 끊는 HTTP 통신보다 소켓 통신이 적합하다. 소켓 통신은 계속 해서 Connection을 들고있기때문에 HTTP보다 많은 리소스가 소모된다. 


## 소켓의 동작 흐름 
![image](https://github.com/user-attachments/assets/9c68b769-6ac2-471f-91f4-b363f82e55f2)

- 클라이언트의 역할 : 소캣 생성, 연결 요청, 데이터 송수신, 소켓 닫기
- 서버의 역할 : 소캣 생성, 결합, 주시, 받아들이기, 데이터 송수신, 소켓 닫기

### 클라이언트의 흐름
#### 1. 클라이언트 소캣의 생성
- 연결 대상에 대한 정보가 들어있지않은 socket(껍대기 소캣)을 생성.
- 이때 소켓 종류를 선택해야하는데 TCP 소켓을 위해선 stream 타입을, UDP 소캣을 위해선 데이터그램 타입으로 지정 가능.
#### 2. 연결 요청 (Connnetion)
- 서버에 설정된 IP,Port로 연결시도
- 연결하고싶은 대상에게 연결 보내는데 **IP주소와 포트 번호**로 연결하고싶은 대상을 특정한다. (서버 소켓단의 IP와 포트번호)
- 요청을 보내고 끝이 아니라 요청에 대한 결과가 돌아와야만 Connect 실행이 끝난다.
#### 3. 데이터의 송수신 (Send, Receive)
- accept()로 클라이언트의 socket descriptor 반환
- 연결 요청과 같이 요청을 보낸다고 끝나는 게 아니라 요청에 대한 결과(신호)가 들어와야 실행이 끝남.
- 송신할때는 데이터를 보내는 것이기떄문에 데이터를 언제 얼마나 보낼 것인지 알수있지만 수신할때는 상대방이 언제 얼만큼의 데이터를 보낼 것인지 알수가 없다는 서로의 차이점이 존재.
- 그렇기때문에 수신하는 API는 별도의 Thread에서 진행하게 됨.
#### 4. 소켓 닫기 
- 더이상의 송수신이 없다고 판단되면 소켓을 닫음.

### 서버의 흐름
- 어떤 데이터를 서버에서 보내려고 할떄 수신 측에서 무작정 데이터를 수신하는게 아니라 포트번호를 식별하여 알맞게 들어온 프로세스만을 수신하여야함.
#### 1. 서버 소캣 생성
    - 클라이언트 소켓과 마찬가지로 연결대상에 대한 정보가 들어있지않은 껍대기 소켓을 생성한다.
#### 2. 바인딩 (IP, Port 번호 설정)
![image](https://github.com/user-attachments/assets/78606bcf-2e5c-478b-a301-3d14a9f7ce14)
- 우리는 컴퓨터를 이용할때 많은 서비스를 사용한다 -> 즉 , 수많은 프로세스가 동시에 돌아가고있다.

- 만약 서버 소켓이 받은 데이터를 다시 보내주어야할떄 **프로세스들의 포트 번호가 동일하다면 혼란이 생길수있음**
- 따라서 서버 소켓이 고유한 포트 번호를 만들수있도록 소켓과 포트 번호를 결합해주는 작업이 필요.
- 위의 그림같이 소켓이 사용하는 포트 번호가 다른 소켓의 포트번호와 중복된다면, 모든 소켓이 10000이라는 포트번호를 사용하게 된다면 네트워크를 통해 10000포트로 데이터가 수신될때 어떤 소켓이 처리해야하는지 알 수 없는 문제가 생김. -> 운영체제에서는 소켓들이 중복된 포트 번호를 사용하지않도록 내부적으로 포트번호와 소켓 연결 정보를 관리한다.
- **하나의 프로세스는 동일한 포트번호를 가진 여러개의 소켓을 결합할수있다** 즉, 호스트가 하나의 Port로 여러개의 Socket을 만들어 다른 호스트들과 데이터를 주고 받을 수 있다 -> 하나의 채팅앱을 사용하더라도 동시에 많은 사람들과 채팅을 주고받을 수 있다.
- 특정 포트가 소캣을 여러개 열어서 첫번째 소켓으로는 엄마, 두번째로는 아빠 이런식으로 카톡 할 수 있게 되는 원리라고 생각하면 됨.
#### 3. 클라이언트 연결 요청 대기 
- listen()으로 클라이언트 요청에 대기열을 만들어 몇개의 클라이언트를 대기 시킬건지 결정
- 서버 소켓에서 포트번호와 바인딩 작업을 마치고 나면 클라이언트로부터의 연결 요청을 받아들일 준비가 된것
- 클라이언트가 연결 요청을 할 때까지 기다리다가 연결 요청이 오면 대기 상태를 종료하고 리턴한다.
#### 4. 클라이언트 연결 수립
- 실질적인 연결이 시작되는 단계
- aceept()로 클라이언트와 연결
- 서버 소켓은 연결 요청을 받아들임과 동시에 새로운 소켓을 생성한다.
- 서버 소켓의 메인 역활은 클라이언트 연결 요청을 기다리는 것 -> 따라서 클라이언트 소켓으로부터 연결요청을 받으면 새로운 소켓을 열고 이것과 클라이언트 소켓을 맵핑하여 넘겨줌
#### 5. 데이터 송수신(Send, Receive)
- 클라이언트와 동일
#### 6. 소켓 닫기
- 클라이언트와 동일
- 하지만 서버 소켓은 자신이 생성한 소켓들을 관리해야함. 

## 소켓의 종류 
### 스트림 소켓
- TCP를 사용하는 연결 지향 방식의 소켓
- 송수신자의 연결을 보장하여 신뢰성이 있는 데이터 송수신이 가능
- 데이터의 순서 보장
- 소량데이터보다 대량 데이터 전송에 적합
- 대부분의 네트워크 애플리케이션은 스트림 소켓을 사용. 신뢰성있는 데이터 전송을 제공, 데이터는 순서대로 전송되고 손실이 발생하면 재전송 됨.
- 점대점 연결
#### 서버
- 소켓 생성
- 바인딩
- listen(연결되지않은 소켓을 대기모드로 전환)
- 클라이언트 요청 수락 후 통신을 위한 실질적인 소켓 생성(처음에 생성한 소켓은 새로운 클라이언트 요청을 대기하기위해 쓰임)
- 데이터 송수신
- 소켓 닫음
#### 클라이언트
- 소켓 생성
- 서버가 설정한 IP,Port로 연결
- accept()로 클라이언트의 socket descriptor 반환
- 데이터 송수신
- 소켓 닫음

### 데이터그램 소켓
- UPD를 사용하는 비연결형 소켓,비 신뢰성 데이터 전송
- 데이터의 순서와 신뢰성을 보장하기 어려움
- 점대점뿐 만 아니라 일대다 연결도 가능
- acccept()과정없이 소켓 생성 후 바로 데이터 송수신
- 데이터는 개별적으로 데이터 그램으로 전송되며 손서 보장이나 재전송의 기능이 없다. 실시간 전송이 중요한 경우( ex 스트리밍, 온라인게임) 사용됨.


## 소켓과 HTTP의 차이 
### HTTP 통신
- 클라이언트의 요청이 있을때만 서버가 응답
- JSON,HTML,Image등 다양한 데이터를 주고받을 수 있다.
- 서버가 응답한 후 연결을 바로 종료하는 단방향 통신이지만 Kepp Alive 옵션을 주어 일정 시간동안 커넥션을 유지할 수있다
- 실시간 연결이 아닌 데이터 전달이 필요한 경우에만 요청을 보내는 상황에 유리
### 소켓통신
- 클라이언트와 서버가 특정 포트를 통해 양방향 통신을 하는 방식
- 데이터 전달 후 연결이 끊어지는 것이 아니라 계속 해서 연결을 유지 -> HTTP에 비해 더 많은 리소스 소모
- 클라이언트와 서버가 실시간으로 계속 하여 데이터를 주고 받아야하는 경우에 유리
- 실시간 동영상 스트리밍이나 온라인 게임등에 사용.


### 질문
#### 소켓 통신에서 클라이언트와 서버가 데이터를 송수신할 때 신뢰성과 데이터 순서가 중요한 이유는 무엇인가요?
- 소켓 통신에서 신뢰성과 데이터 순서가 중요한 이유는 데이터의 정확성과 일관성을 보장하기 위해서입니다. 예를 들어, 파일 전송이나 금융 거래와 같은 애플리케이션에서는 데이터의 누락이나 순서가 뒤바뀌는 문제가 발생하면 큰 손실이 초래될 수 있습니다. 스트림 소켓(TCP 기반)은 이러한 요구사항을 충족하기 위해 데이터 전송 시 확인 응답(ACK)을 보내고, 패킷 순서를 보장함으로써 안정적인 통신을 제공합니다.

#### 서버 소켓과 클라이언트 소켓의 주요 차이점은 무엇인가요?
- 서버 소켓은 주로 클라이언트의 연결 요청을 기다리고 이를 수락하는 역할을 하며, 클라이언트와의 연결이 이루어지면 새로운 소켓을 생성해 통신을 처리합니다. 반면, 클라이언트 소켓은 서버에 연결 요청을 보내고 서버와 연결된 이후 데이터를 송수신하는 데 초점이 맞춰져 있습니다. 따라서 서버 소켓은 "요청 대기"라는 추가 단계가 있으며, 여러 클라이언트 요청을 처리하기 위해 다수의 소켓을 생성하고 관리합니다.


참고링크 

https://velog.io/@rhdmstj17/%EC%86%8C%EC%BC%93%EA%B3%BC-%EC%9B%B9%EC%86%8C%EC%BC%93-%ED%95%9C-%EB%B2%88%EC%97%90-%EC%A0%95%EB%A6%AC-1

https://on1ystar.github.io/socket%20programming/2021/03/16/socket-1/