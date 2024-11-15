# 3way-handshake
![image](https://github.com/user-attachments/assets/defb5275-8103-4b33-a5bb-bed8a8fc113d)

- TCP 3way-handshake과정은 클라이언트와 서버간의 신뢰할 수 있는 연결을 설정하는 과정.
- 각 세단계로 이루어져있으며, 각 단계에서 클라이언트와 서버는 서로에게 특정 메세지를 주고 받는다.
- 데이터를 전송하기전에 정확한 전송을 보장하기위해 사전에 세션을 수립하는 과정.
- 양쪽 모두 데이터를 전송할 준비가 되었다는 것을 보장하고 실제로 데이터 전달이 시작하기전에 한쪽에서 다른 쪽이 준비되었다는 것을 알 수 있다. 

## 3way-handshake과정
- 1. Client → Server (SYN)
    - 서버에 접속을 요청하는 SYN 패킷을 전송한다.
    - 송신자가 최초로 데이터를 전송할 때 Sequence Number를 임의의 랜덤 숫자로 지정하고, SYN 플래그 비트를 1로 설정한 새그먼트를 전송한다.
    - Client는 SYN을 보낸 후 SYN/ACK 응답을 기다리는 SYN_SENT 상태가 된다.
        - SYN : 연결설정, Sequence Number를 랜덤으로 설정하여 세션을 연결하는데 사용하며 초기에 Sequence Number를 전송하다.
        - SYN_SENT : SYN 요청을 한 상태 
        - LISTEN : 포트가 열린 상태로 연결 요청 대기중
        - COLOSE : 포트가 닫힌 상태 

- 2. Server → Client (SYN/ACK)
    - LISTEN 상태인 Server가 SYN을 받고, 클라이언트에게 요청을 수락(ACK)했으며 접속 요청 프로세스인 클라이언트도 포트를 열어달라(SYN)는 메시지를 전송한다.
    - ACK Number필드를 Sequence Number + 1로 지정하고 SYN과 ACK 플래그 비트를 1로 설정한 새그먼트 전송
    - SYN을 받은 서버는 SYN_RECEIVED 상태가 된다.
        - ACK : 응답확인, 패킷을 받았다는 것을 의미하며 Acknowledgement Number 필드가 유효한지 나타낸다.
        - SYN_RECEIVED : SYN 요청을 받고 상대방의 응답을 기다리는 중 
- 3. Client → Server (ACK)
    - 클라이언트는 서버의 응답을 받았다는 의미로, ACK Number필드를 Sequence Number + 1로 지정하고 서버로 ACK 플래그가 설정된 새그먼트를 전송한다.
    - ACK 요청을 보낸 클라이언트는 ESTABLISHED 상태가 된다.
    - ACK를 받은 서버는 ESTABLISHED 상태가 된다.
        - Fin : 연결 해제 , 세션 연결을 종료시킬떄 사용되며 더이상 전송할 데이터가 없음을 의미한다. 
        - ESTABLISHED : 포트 연결 상태 
 
# 4-Way Handshake
![image](https://github.com/user-attachments/assets/caf57083-053f-4569-a517-3166427d90b8)

- 3-Way Handshake가 세션을 수립하는 과정이었다면 4-Way Handshake는 세션을 종료하기 위해 수행되는 과정이며, 여기서 FIN 플래그를 사용한다.

## 3way-handshake과정
- 1. Client → Server (FIN)
    - close()가 호출되면 연결을 종료한다는 FIN 패킷을 보낸다.
    - FIN 패킷에는 ACK로 포함되어있다.
    - FIN 패킷을 보낸 후 FIN_WAIT_1 상태가 된다.
        - FIN_WAIT_1 : close() 호출 후 FIN 패킷을 보내고 응답을 대기하는 상태

- 2. Server → Client (ACK)
    - FIN 패킷을 받은 서버는 응답 패킷 ACK를 보낸다.
    - 응답 패킷 ACK를 보낸 후 CLOSE_WAIT 상태가 된다.
    - 아직 남은 데이터가 있다면 마저 전송을 마친 후에 close( )를 호출한다.
    - 클라이언트는 ACK 패킷을 받은 후 FIN_WAIT_2 상태가 된다.
        - FIN_WAIT_2 : 종료를 준비한다는 응답을 받고 종료 요청이 올떄까지 대기
- 3. Server → Client (FIN)
    - 데이터를 모두 보냈다면, 서버는 FIN 패킷을 클라이언트에게 보낸 후에, 승인 번호를 보내줄 때까지 기다리는 LAST_ACK 상태로 들어간다. 
        - LAST_ACK : 모든 데이터를 보내고 FIN 패킷을 전송한 후 상대방의 응답을 대기
- 4. Client → Server (ACK)
    - 클라이언트는 FIN 패킷을 받고, 확인했다는 ACK 응답을 보낸다.
    - ACK 응답을 보낸 후 클라이언트는 TIME_WAIT 상태가 된다.
    - TIME_WAIT 상태는 의도치 않은 에러로 인해 연결이 데드락으로 빠지는 것을 방지한다.
    - 만약 에러로 인해 종료가 지연되다가 타임이 초과되면 CLOSED 상태로 들어간다.
    - 서버는 ACK를 받은 이후 소켓을 닫는다 (Closed)
    - TIME_WAIT 시간이 끝나면 클라이언트도 닫는다 (Closed) - 기본 240초
        - TIME_WAIT : FIN패킷을 받은 후 다시 ACk응답을 한 이후의 상태, 의도치 않은 에러로 인해 연결이 데드락에 빠지는 것을 방지.
        - CLOSE_WAIT : 종료요청을 받고 진입하는 상태로 남은 데이터가 있다면 전송을 마친 후 close() 호출

### 질문 

#### TCP의 연결 설정 과정(3단계)과 연결 종료 과정(4단계)이 단계가 차이나는 이유
- Client가 데이터 전송을 마쳤다고 하더라도 Server는 아직 보낼 데이터가 남아 있을 수 있기 때문에 일단 FIN에 대한 ACK만 보내고, 데이터를 모두 전송한 후에 자신도 FIN 메세지를 보내기 때문이라고 볼 수 있다.
 
#### 만약 Server에서 FIN 플래그를 전송하기 전에 전송한 패킷이 Routing 지연이나 패킷 유실로 인한 재전송 등으로 인해 FIN 패킷보다 늦게 도착하는 상황이 발생하면 어떻게 해야하는가
위에서 4way handshake과정 마지막 부분에서 말한 TIME-WAIT을 말한 부분이 답이라고 보면되는데, TCP는 이러한 현상에 대비하여 Client는 Server로부터 FIN 플래그를 수신하더라도 일정시간동안 세션을 남겨놓고 잉여 패킷을 기다리는 과정을 거친다.
 
#### 초기 Sequence Number인 ISN을 0부터 시작하지 않고 난수를 생성해서 설정하는 이유
Connection을 맺을 때 사용하는 포트(Port)는 유한 범위 내에서 사용하고 시간이 지남에 따라 재사용된다. 따라서 두 통신 호스트가 과거에 사용된 포트 번호 쌍을 사용하는 가능성이 존재한다. 서버 측에서는 패킷의 SYN을 보고 패킷을 구분하게 되는데 난수가 아닌 순처적인 Number가 전송된다면 이전의 Connection으로부터 오는 패킷으로 인식할 수 있다. 이런 문제가 발생할 가능성을 줄이기 위해서 난수로 ISN을 설정한다.

#### TCP 3-Way Handshake에서 클라이언트가 서버에 SYN 패킷을 보낸 후 SYN_SENT 상태가 되는데, 이 상태가 필요한 이유는 무엇인가요?
- SYN_SENT 상태는 클라이언트가 서버로 연결 요청을 보낸 후, 서버로부터 SYN/ACK 응답을 받을 때까지 대기하는 상태입니다. 이 상태를 유지함으로써 클라이언트는 보낸 요청이 서버에서 수락되었는지 확인할 수 있으며, 응답이 없을 경우 재전송하거나 연결을 종료할 수 있는 기준을 제공합니다. 이는 연결 과정에서 데이터 손실을 방지하고, 신뢰성을 보장하기 위한 TCP 프로토콜의 핵심적인 메커니즘 중 하나입니다

#### TCP 4-Way Handshake에서 FIN 플래그와 ACK 플래그를 분리해서 전송하는 이유는 무엇인가요?
- TCP는 양방향 데이터 전송을 지원하므로, 한쪽에서 연결을 종료(FIN)하려 해도 상대방이 아직 전송해야 할 데이터가 있을 수 있습니다. FIN과 ACK 플래그를 분리하면 상대방은 데이터 전송을 마친 후 FIN 패킷을 전송할 수 있는 시간을 확보할 수 있습니다. 이는 데이터를 완전히 전달할 수 있는 기회를 제공하며, 비대칭적인 데이터 흐름에서도 안전하고 명확하게 연결 종료를 처리할 수 있도록 돕습니다.

참고링크 

https://jeongkyun-it.tistory.com/180
https://hyemsinabro.tistory.com/157
https://ghs4593.tistory.com/18