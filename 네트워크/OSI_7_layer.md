
# OSI 7 계층 
![image](https://github.com/user-attachments/assets/ccb51cd4-91ca-4696-a603-0ad36ba32494)
![image](https://github.com/user-attachments/assets/0d63f2ce-7b99-4c6c-8f37-5107e3dbf628)
- 네트워크 통신을 7개의 계층으로 나누어 설명함. 각 계층은 특정한 기능을 담당하며 서로 독립적으로 작동. 국제 표준화 기구 (ISO)에서 정의한 네트워크 표준 모델
- 1계층(물리계층)부터 7계층(응용계층)으로 구성되어있다.
- 그림과 같이 각 계층을 지날떄마다 각 계층에서 header가 붙게되고 수신은 역순으로 헤더를 분석하게 된다.

## 1계층 물리 계층 (Physical Layer)
- 주로 전기적, 기계적 기능적인 특성을 이용해서 통신 케이블로 데이터를 전송하는 물리적인 장비.
- 단지 데이터 전기 신호(0,1)로 변환해서 주고 받는 기능만 할 뿐
- 이 계층에서 사용되는 통신 단위 : 비트(Bit)이며 이것은 1과 0으로 나타내어지는 즉 전기적으로 On,Off 상태
- 단지 데이터만 전달 할 뿐 전송하려는(또는 받으려는) **데이터가 무엇인지, 어떤 에러가 있는 지 등에는 전혀 신경쓰지않음.**
- 장비 : 통신 케이블, 리피터, 허브 등

## 2계층 데이터 링크 계층 (DataLink Layer)
- 물리 계층을 통해 송수신되는 정보의 오류와 흐름을 관리하여 안전한 통신의 흐름을 관리 -> **안전한 정보의 전달을 수행**할수있도록 도와주는 역할을 한다. 
- 통신에서의 오류도 찾아주고 재존송하는 기능도 가지고있다. 
- 프레임에 물리적 주소(MAC address)를 부여하고 에러 검출, 재존송, 흐름제어를 수행
- 이 계층에서 전송되는 단위 : 프레임 (frame)
- 장비 : 브릿지, 스위치, 이더넷등(여기서 MAC주소를 사용) 
- 브릿지나 스위치를 통해 맥주소를 가지고 물리계층에서 받은 정보를 전달함.
- 데이터 링크 계층은 포인트 투 포인트 간 신뢰성있는 전송을 보장하기위한 계층으로 CRC 기반의 오류제어나 흐름 제어가 필요하다. 네트워크 위의 개체들간 데이터를 전달하고 물리 계층에서 발생할 수 있는 오류를 찾아내고 수정하는데 필요한 기능적 , 절차적 수단을 제공.

- 주소 값은 물리적으로 할당 받음 -> 네트워크 카드가 만들어질때부터 Mac address가 정해져있다는 뜻.
- 주소 체계는 계층이 없는 단일 구조 , 데이터 링크의 잘 알려진 예는 이더넷.
- 이외에도 HDLC나 ADCCP같은 포인트 투 포인트 프로토콜이나 패킷 스위칭 네트워크나 LLC, ALOHA 같은 근거리 네트워크용 프로토콜이 있다.
- 네트워크 브릿지나 스위치 등이 이계층에서 동작하며 직접 이어진 곳에만 연결 할수 있음.


## 3계층 네트워크 계층 (Network Layer)
- **데이터를 목적지까지 가장 안전하고 빠르게 전달하는 기능(라우팅)**이 이 계층에서 가장 중요한 기능.
- 라우터 (Router)를 통해 경로를 선택하고 주소(IP)를 정하고 경로(Route)에 따라 패킷전달 
- 논리적인 주소 구조 (IP), 곧 네트워크 관리자가 직접 주소를 할당하는 구조를 가지며 계층적이다.
- 서브네트의 최상위 계층으로 경로를 설정하고, 청구 정보를 관리한다. 개방형 시스템들의 사이에서 네트워크 연결을 설정, 유지, 해제하는 기능을 부여하고, 전송 계층 사이에 네트워크 서비스 데이터 유닛(NSDU : Network Service Data Unit)을 교환하는 기능을 제공한다.
- 이 계층에서 전송되는 단이 : 패킷 (Packet)
- 장비 : 라우터

## 4계층 전송 계층 (Transport Layer)
- 통신을 활성화하기위한 계층으로 보통 TCP프로토콜을 이용하여 port를 열어서 응용 프로그램들이 전송할 수 있게한다. 
- 만약 데이터가 온다면 4계층에서 해당 데이터를 하나로 합쳐서 5계층으로 보낸다. 이 계층 까지는 물리적인 계층에 속한다.
- 전송 계층은 특정 연결의 유효성을 제어하고 일부 프로토콜은 상태 개념이 있고 연결기반이다. -> 전송 계층이 패킷들의 전송이 유효한지 확인하고 전송 실패한 패킷들을 다시 전송해준다(TCP)
- port 번호, 전송 방식(TCP,UDP) 결정 -> TCP 헤더 붙음
    - TCP : 신뢰성 , 연결 지향적
    - UDP : 비신뢰성, 비연결성, 실시간
- 두 지점간의 신뢰성 있는 데이터를 주고받게 해주는 역할
- 신호를 분산하고 다시 합치는 과정을 통해 에러와 경로를 제어

## 5계층 세션계층 (Session Layer)
- **데이터가 통신하기위한 논리적인 연결**을 뜻한다. 통신을 하기위한 대문이라고 보면 됨.
- 하지만 4계층에서도 연결을 맺고 종료할 수 있기 때문에 이 계층에서 통신이 꼭 끊어진다고 보긴 어렵다. 그러므로 세션 계층은 4 계층과 무관하게 응용 프로그램 관점에서 봐야 한다. 
- 세션 설정, 유지, 종료, 전송 중단시 복구 등의 기능이 있다.
- 세션 계층(Session layer)은 양 끝단의 응용 프로세스가 통신을 관리하기 위한 방법을 제공.
- 동시 송수신 방식(duplex), 반이중 방식(half-duplex), 전이중 방식(Full Duplex)의 통신과 함께, 체크 포인팅과 유휴, 종료, 다시 시작 과정 등을 수행함.
- **이 계층은 TCP/IP 세션을 만들고 없애는 책임을 진다.**


- 주 지점 간의 프로세스 및 통신한느 호스트간의 연결 유지
- TCP/IP 세션 채결, port번호를 기반으로 통신 세션 구성
- API, Socket

## 6계층 표현계층 ( Persentation Layer)
- 전송하는 데이터의 표현방식을 결정(ex 데이터 변환, 압축, 암호화 등)
- 파일 인코딩, 명령어를 포장,압축,암호화
- JPEG, MPEG, GIF, ASCII 등

## 7 계층 응용계층( Application Layer)
- 최종 목적지로, 응용 프로세스와 직접 관계하여 일반적인 응용 서비스를 수행(ex chrome 등)
- 해당 통신 패킷들은 밑에 나열한 프로토콜에 의해 모두 처리가 되며 모든 통신의 양끝단은 HTTP와 같은 프로토콜이지 크롬같은 응용 프로그램, 브라우저가 아니다.
- HTTP , FTP, SMTP, POP3, IMAP, Telent 등과 같은 프로토콜이 있다.

# TCP/IP 4계층이란?
![image](https://github.com/user-attachments/assets/98db6090-73bd-4fb7-bfb3-3c4e8d7a1bdb)
![image](https://github.com/user-attachments/assets/42814517-c696-4d29-ba24-89e1391f1e3f)

- OSI 계층보다 먼저 나온 규격으로 TCP/IP 4계층은 현재 인터넷에서 사용되는 프로토콜로, 좀 더 실무적이면서 프로토콜 중심으로 단순화된 모델
- TCP/IP 4계층의 구조는 네트워크 연결 계층(Network Access Layer), 인터넷 계층(Internet Layer), 전송 계층(Transport Layer), 애플리케이션 계층(Application Layer)으로 구성되어 있다. 이런 식의 구분을 통해 각 기능들은 서로 간의 간섭을 최소화 할 수 있어, 유지와 보수에 있어 편리하다는 이점이 있다.

## 응용 계층 (Application Layer)
- 사용자와 가장 가까운 계층으로 사용자 <-> 소프트웨어간의 소통을 담당하는 계층.
- 주로 응용 프로그램들끼리 데이터를 교환하기위한 계층
    - 데이터 단위 : Data 데이터, 메세지 (Message)
    - ex) 파일 전송, 이메일, FTP, HTTP, DNS, SMTP 등

## 전송 계층( Transport Layer)
- **통신 노드간의 데이터 전송 및 흐름에 있어 신뢰성을 보장한다.**
- 전송 계층에 사용되는 대표적인 프로토콜로는 TCP, UDP가 있다.
    - 데이터 단위 : 세그먼트 
    - 전송 주소 : Port
    - ex) TCP, UDP 등

## 인터넷 계층 (Internet Layer)
- 네트워크 상에서 데이터의 전송을 담당하는 계층으로 서로 다른 네트워크 간의 통신을 가능하게 하는 역할을 수행 -> **연결성 제공**
- 단말을 구분하기위해 논리적인 주소로 IP를 할당하게되고 이 IP주소로 네트워크 상의 컴퓨터를 식별하여 주소를 지정할 수 있도록 해줌.
- 네트워크 끼리 연결하고 데이터를 전송하는 기기인 '라우터'라고 하며, 라우터에 의한 네트워크간의 전송을 **라우팅**이라고 한다. 이 라우터가 내부의 라우팅 테이블(Routing table)을 통해 경로 정보를 등록하여 데이터 전송을 위한 최적의 경로를 찾는데 이렇게 출발지와 목적지간의 데이터 전송과정을 가리켜 End-to-End 통신이라고 부름
    - 데이터 단위 : 패킷(Packet)
    - 전송 주소 : IP
    - 예시 : IP,ARP, ICMAP, RARP

## 네트워크 연결 계층 (Network Access Layer)
- 물리적인 데이터의 전송을 담당하는 계층으로 여기서는 인터넷 계층과 달리 같은 네트워크 안에서 데이터가 전송된다 .
- 노드간의 신뢰성 있는 데이터 전송을 담당하며 논리적인 주소가 아닌 물리적인 주소인 MAC을 참조해 장비간 전송을 하고 기본적인 에러 검출과 패킷의 Frame화를 담당한다.
    - 데이터 단위 : 프레임(Frame)
    - 전송 주소 : MAC
    - 예시 : MAC, LAN, 패킷망 등에 사용되는 것(대표적으로 Ethernet)


    
참고 링크 

https://blog.pollra.com/f-lab-mogacko-day-10-step-2/

https://shlee0882.tistory.com/110

https://velog.io/@orijoon98/%EB%84%A4%ED%8A%B8%EC%9B%8C%ED%81%AC-OSI-7-%EA%B3%84%EC%B8%B5

https://velog.io/@dyunge_100/Network-TCPIP-4%EA%B3%84%EC%B8%B5%EC%97%90-%EB%8C%80%ED%95%98%EC%97%AC