
# 인터넷 네트워크, URI와 웹브라우저 요청 흐름

## 웹브라우저 요청 흐름
![image](https://github.com/user-attachments/assets/369451d2-6b2d-4173-8169-5c443828fd67)
- 인터넷창에 maps.google.com을 입력했을떄 일어나는 일은 8단계로 정리 할 수 있음.
    - 브라우저 주소창에 maps.google.com을 입력
    - 브라우저가 maps.google.com의 IP주소를 찾기위해 캐시에서 DNS기록을 확인
    - 만약 요청한 URL(maps.google.com)이 캐시에 없다면 ISP의 DNS서버가 DNS쿼리로 mapps.google.com을 호스팅하는 서버의 IP를 찾는다.
    - 브라우저가 해당 서버와 TCP연결을 시작한다
    - 브라우저가 웹서버에 HTTP 요청을 보낸다
    - 웹서버가 요청을 처리하고 응답을 보낸다
    - 서버가 HTTP 응답을 보낸다
    - 브라우저가 HTML 컨텐츠를 보여준다.

## 각 단계에 대한 설명

### 브라우저 주소창에 maps.google.com을 입력

### 브라우저가 maps.google.com의 IP주소를 찾기위해 캐시에서 DNS기록을 확인
![image](https://github.com/user-attachments/assets/9beeacb2-7fb2-43ae-91e2-89582d22ca70)
- DNS(Domain Name system)은 인터넷의 전화 번호부와 같다 DNS는 웹사이트의 IP 주소와 도메인 주소를 연결해주는 시스템이다. 인터넷의 모든 URL에는 고유한 IP 주소가 할당되어있으며 IP 주소는 엑세스 요청 웹사이트의 서버를 호스트하는 컴퓨터에 속한다. 예를 들어 www.google.com의 IP주소는 142.250.196.110이다. 따라서 원하는 경우 브라우저에서 https://142.250.196.110 를 입력하여 www.google.com에 접속할 수 있다.
- 할당된 도메인 영역에 대한 정보를 가지고있는 서보로 주소 도메인을 IP주소로 변환하는 역할을 한다.

- DNS의 주요목적은 사람들이 쉽게 사이트 주소를 찾을 수 있도록 도와주는 것. DNS가 자동으로 URL과 IP주소를 매핑해주기때문에 쉽게 원하는 사이트에 접속 할 수 있다.
- DNS기록을 찾기위해서 브라우저는 네개의 캐시를 확인한다
    - 첫번쨰 DNS쿼리는 우선 브라우저 캐시를 확인한다. 브라우저는 내가 이전에 방문한 웹사이트 DNS기록을 일정기간 동안 저장하고 있다
    - 두번째 브라우저는 OS 캐시를 확인한다. 브라우저 캐시에 원하는 DNS 레코드가 없다면 브라우저가 내 컴퓨터 OS에 시스템 호출(ex 윈도우에서 gethostname 호출)을 통해 DNS 기록을 가져온다.(OS도 DNS 레코드 캐시를 저장하고있다.)
    - 세번쨰 브라우저는 라우터 캐시를 확인한다. 만약 컴퓨터에서도 원하는 DNS레코드가 없다면 브라우저는 라우터에서 DNS기록을 저장한 캐시를 확인한다.
    - 마지막으로 ISP 캐시를 확인한다. 만약 위 모든 단계에서 DNS 기록을 찾지못한다면 브라우저는 ISP에서 DNS기록을 찾는다. ISP(Internet Service Provider)는 DNS서버를 가지고있는데 해당 서버에서 DNS기록 캐시를 검색할 수 있다.

### 만약 요청한 URL(maps.google.com)이 캐시에 없다면 ISP의 DNS서버가 DNS쿼리로 mapps.google.com을 호스팅하는 서버의 IP를 찾는다.
- 앞에서 언급했듯이 내 컴퓨터가 maps.google.com을 호스트하는 서버와 연결하려면 maps.google.com의 IP 주소가 필요하다. DNS 쿼리의 목적은 웹 사이트에 대한 올바른 IP 주소를 찾을 때까지 인터넷에서 여러 DNS 서버를 검색하는 것이다. 필요한 IP 주소를 찾거나, 찾을 수 없다는 오류 응답을 반환할 때까지 한 DNS 서버에서 다른 DNS 서버로 검색이 반복적으로 계속되기 때문에 이 유형의 검색을 재귀적 질의(Recursive Query)라고 한다.
- 이러한 상황에서, 우리는 ISP의 DNS 서버를 DNS 리커서(DNS Recursor)라고 부르는데, DNS 리커서는 인터넷의 다른 DNS 서버에 답변을 요청하여 의도된 도메인 이름의 적절한 IP 주소를 찾는 일을 담당한다. 다른 DNS 서버는 웹사이트 도메인 이름의 도메인 아키텍처를 기반으로 DNS 검색을 수행하므로 네임 서버(Name Server)라고 한다.
![image](https://github.com/user-attachments/assets/ddf8795b-6e8a-4d7e-a671-85ef40ed74da)
- 많은 웹 사이트 URL은 3차 도메인, 2차 도메인 및 최상위 도메인(TLD: Top Level Domain)으로 이뤄진다. 각 단계에는 DNS 룩업(lookup) 도중에 쿼리되는 고유한 네임 서버가 있다.
- DNS Lookup 이란 DNS 서버에서 인터넷 도메인 이름을 사용해 인터넷 주소 (ip)를 알아내는 과정이다.
![image](https://github.com/user-attachments/assets/84450f05-a3aa-4ddc-8c1e-7a28fb23ada0)
- 국내 도메인의 경우도 마찬가지이다. 아래와 같이 도메인은 수직적으로 나뉘어져 있다. 예를 들어, kr도메인에는 or영역을 표현하는 or.kr 도메인이 포함되어 있고, or.kr 도메인에는 kisa.or.kr 도메인이 포함되어 있다
-  maps.google.com의 경우 먼저 DNS 리커서가 루트 네임 서버(Root Name Server)에 연결한다. 루트 이름 서버는 리커서를 .com 도메인 네임 서버로 리디렉션한다. .com 네임 서버는 google.com 네임 서버로 리디렉션한다. google.com 네임 서버는 DNS 기록에서 maps.google.com과 일치하는 IP 주소를 찾아 DNS 리커서로 반환하고, 리커서는 이를 브라우저로 다시 보낸다.
- 위와 같은 요청(Request)은 내용 및 IP 주소(DNS 리커서의 IP 주소)와 같은 정보를 작은 데이터 패킷에 담겨 전송된다. 이 패킷은 올바른 DNS 서버에 도달하기 전에 클라이언트와 서버 사이의 여러 네트워킹 장비를 통해 이동한다. 이 장비는 라우팅 테이블을 사용하여 패킷이 목적지에 도달할 수 있는 가장 빠른 방법을 알아낸다. 만약 이동 도중에 패킷이 손실되면, 요청 실패 오류가 발생한다. 그렇지 않으면 올바른 DNS 서버에 도달하여 IP 주소를 가져온 후 브라우저로 돌아간다.

### 브라우저가 해당 서버와 TCP 연결을 시작한다.
- 브라우저가 올바른 IP 주소를 수신하면 IP 주소와 일치하는 서버와 연결해 정보를 전송한다. 브라우저는 인터넷 프로토콜(IP, Internet Protocol)을 사용하여 이러한 연결을 구축한다. 사용할 수 있는 여러가지 인터넷 프로토콜이 있지만, 일반적으로 HTTP 요청에서는 TCP(Transmission Control Protocol) 라는 전송 제어 프로토콜을 사용한다.
- 인터넷 프로토콜(IP, Internet Protocol)은 송신 호스트와 수신 호스트가 패킷 교환 네트워크(패킷 스위칭 네트워크, Packet Switching Network)에서 정보를 주고받는 데 사용하는 정보 위주의 규약(프로토콜, Protocol)이며, OSI 네트워크 계층에서 호스트의 주소지정과 패킷 분할 및 조립 기능을 담당한다. 줄여서 아이피(IP)라고도 한다.
- 내 컴퓨터(클라이언트)와 서버 간에 데이터 패킷을 전송하려면 TCP 연결을 해야 한다. 이 연결은 TCP/IP 3-way handshake라는 연결 과정을 통해 이뤄진다. 클라이언트와 서버가 SYN(synchronize: 연결 요청) 및 ACK(acknowledgement: 승인) 메시지를 교환하여 연결을 설정하는 3단계 프로세스이다.  
    - 클라이언트는 인터넷을 통해 서버에 SYN 패킷을 보내 새 연결이 가능한지 여부를 묻는다.
    - 서버에 새 연결을 수락할 수 있는 열린 포트가 있는 경우, SYN/ACK 패킷을 사용하여 SYN 패킷의 ACK(승인)으로 응답한다.
    - 클라이언트는 서버로부터 SYN/ACK 패킷을 수신하고 ACK 패킷을 전송하여 승인한다.

### 브라우저가 웹서버에 HTTP 요청을 보낸다.
- TCP 연결이 설정되면 데이터 전송이 시작된다. 브라우저는 maps.google.com 웹 페이지를 요청하는 GET 요청을 보낸다.
- 만약 자격 증명(credentials)을 입력하거나 form을 제출하는 경우 POST 요청을 사용할 수 있다. 이 요청에는 브라우저 식별(User-Agent 헤더), 수락할 요청 유형(Accept 헤더) 및 추가 요청을 위해 TCP 연결을 유지하라는 연결 헤더와 같은 추가 정보도 포함된다. 또한 브라우저가 이 도메인에 대해 저장한 쿠키에서 가져온 정보도 전달한다

### 서버가 요청을 처리하고 응답(response)을 보낸다.
- 서버에는 웹 서버(예: Apache, IIS)가 포함되어 있는데, 이는 브라우저로부터 요청을 수신하고, 해당 내용을 request handler에 전달하여 응답을 읽고 생성하는 역할을 한다. Request handler는 요청, 요청의 헤더 및 쿠키를 읽고 필요한 경우 서버의 정보를 업데이트하는 프로그램이다(NET, PHP, Ruby, ASP 등으로 작성됨). 그런 다음 response를 특정 포맷으로(JSON, XML, HTML)으로 작성한다.

### 서버가 HTTP 응답을 보낸다.
- 서버 응답에는 요청한 웹 페이지와 함께 상태 코드(status code), 압축 유형(Content-Encoding), 페이지 캐싱 방법(Cache-Control), 설정할 쿠키, 개인 정보 등이 포함 된다.
- 위의 응답을 보면 'Status Code' 헤더에 상태 코드가 숫자로 표시된다. 이것은 우리에게 response의 상태를 알려주기 때문에 매우 중요하다. 숫자 코드를 사용하여 HTTP 응답 결과를 다섯 가지 상태로 나타낸다.
    - 1xx (Information Response): 정보 메시지만을 나타낸다. 서버가 요청을 받았으며 서버에 연결된 클라이언트는 계속해서 작업을 하라는 뜻.
    - 2xx (Successful Response): 서버와의 요청이 성공함을 나타냄
    - 3xx (Redirection Message) : 요청 완료를 위해 추가 작업 조치가 필요함을 의미함. 위 사진의 301(Moved Permantly)는 요청한 리소스의 URI가 변경 되었음을 뜻한다.
    - 4xx (Client Error Response) : 클라이언트의 Request에 에러가 있음을 의미함.
    - 5xx (Server Error) : 서버 측의 오류로 request를 수행할 수 없음.
    - 따라서 오류가 발생한 경우 HTTP 응답을 확인하여 수신한 상태 코드의 유형을 확인할 수 있다.

### 브라우저가 HTML 컨텐츠를 보여준다.
- 브라우저는 응답받은 HTML을 화면에 단계별로 표시한다. 첫째, HTML 골격을 렌더링한다. 그런 다음 HTML 태그를 확인하고 이미지, CSS 스타일시트, 자바스크립트 파일 등과 같은 웹 페이지의 추가 요소에 대한 GET 요청을 보낸다. 정적 파일(Static File)은 브라우저에서 캐싱되므로 다음에 페이지를 방문할 때 다시 가져올 필요가 없다. 그리고 마지막으로, maps.google.com 페이지가 브라우저에 나타난다.



참고링크 

https://velog.io/@dyunge_100/WEB-%EC%9B%B9-%EC%84%9C%EB%B9%84%EC%8A%A4%EC%9D%98-%ED%86%B5%EC%8B%A0-%EA%B3%BC%EC%A0%95
https://velog.io/@khy226/%EB%B8%8C%EB%9D%BC%EC%9A%B0%EC%A0%80%EC%97%90-url%EC%9D%84-%EC%9E%85%EB%A0%A5%ED%95%98%EB%A9%B4-%EC%96%B4%EB%96%A4%EC%9D%BC%EC%9D%B4-%EB%B2%8C%EC%96%B4%EC%A7%88%EA%B9%8C