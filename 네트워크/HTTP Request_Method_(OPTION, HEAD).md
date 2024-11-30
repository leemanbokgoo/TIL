# HTTP Request Method (OPTION, HEAD)
- 웹 서버는 클라이언트에게 제공할 여러가지 컨텐츠를 준비하고 있고, 클라이언트가 요청하면 즉시 해당되는 컨텐츠를 제공하게 된다. 이러한 컨텐츠 기반에서 직접적인 액션을 취하는 Method가 GET, POST, PUT, PATCH, DELETE 이다.
- OPTIONS와 HEAD, TRACE는 웹 서버가 컨텐츠를 서비스하기 위한 환경적인 요소를 확인하기 위한 Method라고 보면 된다. 웹 서버에서 어떤 문제가 있을 시 문제를 진단하기 위해 사용된다.
    - HEAD : GET과 동일하지만 메시지 부분(body 부분)을 제외하고, 상태 줄과 헤더만 반환
    - OPTIONS : 대상 리소스에 대한 통신 가능 옵션(메서드)을 설명(주로 CORS에서 사용)
    - CONNECT : 대상 자원으로 식별되는 서버에 대한 터널을 설정
    - TRACE : 대상 리소스에 대한 경로를 따라 메시지 루프백 테스트를 수행

# HTTP 메서드 - HEAD (HTTP 헤더 요청)
![image](https://github.com/user-attachments/assets/d657bd2a-3de9-4e2a-a107-6ecc2c8fcf86)
- GET과 동일하지만 서버에서 Body를 Return 하지 않음
- 응답의 상태 코드만 확인할때와 같이 Resource를 받지 않고 오직 찾기만 원할때 사용 (일종의 검사 용도)
- 서버의 응답 헤더를 봄으로써 Resource가 수정 되었는지 확인 가능
- HEAD 요청 방식은 GET 요청 방식과 비슷하지만, 실제 응답 본문(Body)을 포함하지 않는다. GET은 당연히 컨텐츠를 클라이언트에게 전달하지만, HEAD는 클라이언트가 요청한 URI에 해당되는 응답 헤더만 전달한다.
- 위 사진과 같이 DATA가 클라이언트에게 전달되지 않고 상태코드(Status)만 전달되는 것을 볼 수 있다.
- HEAD는 흔히 클라이언트가 요청한 URI의 컨텐츠가 웹 서버에 존재하는지 여부를 확인할 수 있다.
- 클라이언트는 응답 헤더만 전달받게 되는데 상태코드(Status)가 200 OK 면 컨텐츠가 웹 서버에 존재하는 것이고, 404는 웹 서버에 존재 하지 않는 것을 의미한다.


# HTTP 메서드 - OPTION (HTTP 옵션 요청)
![image](https://github.com/user-attachments/assets/e24fc39e-e540-49e3-b990-6675bd240501)
- 예비 요청(Preflight)에 사용되는 HTTP 메소드
- 예비 요청이란 본 요청을 하기 전에 안전한지 미리 검사하는 것이라고 보면 된다
- 서버의 지원 가능한 HTTP 메서드와 출처를 응답 받아 CORS 정책Visit Website을 검사하기 위한 요청이다.
- OPTIONS 요청은 웹 서버에서 지원하는 HTTP 요청 방식을 확인하고자 할때 사용하는 Method이다.
- 클라이언트가 OPTIONS라는 요청 Method와 URI를 웹 서버로 전달하면, 웹 서버는 서버가 지원하는 HTTP 요청 방식(GET, POST, PUT 등)을 응답 헤더에 포함하여 클라이언트에게 응답한다.
- 이 요청은 실제 데이터를 요청하지 않고, 서버가 어떤 메서드를 지원하고 어떤 권한을 가지고 있는지를 확인하는 데 사용된다.
- 보안 정책에서 허용된 메서드 및 권한을 확인할 때 유용하며, Cross-Origin Resource Sharing(CORS)와 관련하여 웹 애플리케이션 간의 통신을 지원하기 위해 주로 사용된다.


### 질문
#### HEAD 메서드를 사용하여 웹 서버의 리소스 존재 여부를 확인할 때, 어떤 상황에서 HEAD 메서드가 유용할까요?
- 클라이언트는 실제 데이터를 다운로드하지 않고 리소스가 존재하는지만 확인하려는 경우에 유용합니다. 예를 들어, 파일이 존재하는지 또는 특정 웹 페이지가 유효한 상태인지 테스트할 때 사용됩니다.


#### OPTIONS 메서드는 CORS 정책에서 어떻게 사용되며, 브라우저는 언제 이 메서드를 자동으로 실행하나요?
- OPTIONS 메서드는 Cross-Origin Resource Sharing(CORS)에서 서버의 허용 메서드와 권한을 확인하는 예비 요청(Preflight Request)으로 사용됩니다. 브라우저는 비-단순 요청(예: 사용자 정의 헤더 포함, PUT 메서드 사용) 시 본 요청을 보내기 전에 자동으로 OPTIONS 요청을 실행하여 보안 검사를 수행합니다.

----

출처 

https://developer.mozilla.org/ko/docs/Web/HTTP/Methods/OPTIONS

https://developer.mozilla.org/ko/docs/Web/HTTP/Methods/HEAD

https://inpa.tistory.com/entry/WEB-%F0%9F%8C%90-HTTP-%EB%A9%94%EC%84%9C%EB%93%9C-%EC%A2%85%EB%A5%98-%ED%86%B5%EC%8B%A0-%EA%B3%BC%EC%A0%95-%F0%9F%92%AF-%EC%B4%9D%EC%A0%95%EB%A6%AC

https://velog.io/@wujin/HTTP-Method-OPTIONS-HEAD-TRACE
