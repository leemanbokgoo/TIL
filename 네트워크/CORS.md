## CORS
- CORS(Cross-Origin Resource Sharing)는 다른 출처(도메인, 프로토콜, 포트)에서 요청된 리소스에 대한 접근을 제한하는 보안 정책이다. 기본적으로 웹 브라우저는 동일 출처 정책(Same-Origin Policy)을 적용하여, 보안상의 이유로 다른 출처에서 요청하는 리소스를 차단한다. 그러나, CORS를 설정하면 특정 도메인에서 서버의 리소스에 접근할 수 있도록 허용할 수 있다. 이를 위해 서버는 Access-Control-Allow-Origin과 같은 CORS 관련 HTTP 헤더를 응답에 포함시켜야 하며, 클라이언트는 사전 요청(Preflight Request)을 통해 서버의 허용 여부를 확인할 수 있다. CORS는 웹 애플리케이션에서 보안과 유연성을 조절하는 중요한 메커니즘
- 즉, 도메인이 다른 서버끼리 리소스를 주고 받을 때 보안을 위해 설정된 정책. CORS를 설정한다는 건 ‘출처가 다른 서버 간의 리소스 공유’를 허용한다는 것.

## Origin

![Image](https://github.com/user-attachments/assets/a6b702a8-3133-45b9-8674-a011afa552dc)
- 서버의 위치를 의미하는 https://google.com과 같은 URL들은 마치 하나의 문자열 같아 보여도, 사실은 여러 개의 구성 요소로 이루어져있다.
- 이때 출처는 Protocol과 Host, 그리고 위 그림에는 나와있지 않지만 :80, :443과 같은 포트 번호까지 모두 합친 것을 의미한다. 즉, 서버의 위치를 찾아가기 위해 필요한 가장 기본적인 것들을 합쳐놓은 것이다. 또한 출처 내의 포트 번호는 생략이 가능한데, 이는 각 웹에서 사용하는 HTTP, HTTPS 프로토콜의 기본 포트 번호가 정해져있기 때문이다. (port 80)
- 그러나 만약 https://google.com:443과 같이 출처에 포트 번호가 명시적으로 포함되어 있다면 이 포트 번호까지 모두 일치해야 같은 출처라고 인정된다.
하지만 이 케이스에 대한 명확한 정의가 표준으로 정해진 것은 아니기 때문에, 더 정확히 이야기하자면 어떤 경우에는 같은 출처, 또 어떤 경우에는 다른 출처로 판단될 수도 있다.

## Cross-Origin (다른 출처) 판단 기준
![Image](https://github.com/user-attachments/assets/ab9f1a43-84b5-4d52-a9df-b4db3125e313)

- 두 개의 출처가 서로 같다고 판단하는 로직은, 두 URL의 구성 요소 중 Scheme(프로토콜), Host(도메인), Port, 이 3가지만 동일하면 된다.
- 따라서 일반적으로는 same-origin이란 scheme(프로토콜), host(도메인), 포트가 같다는 말이며, 이 3가지 중 하나라도 다르면 cross-origin이다.
- 여기서 중요한 사실 한 가지는 이렇게 출처를 비교하는 로직이 서버에 구현된 스펙이 아니라 브라우저에 구현되어 있는 스펙이라는 것이다.
- 만약 우리가 CORS 정책을 위반하는 리소스 요청을 하더라도 해당 서버가 같은 출처에서 보낸 요청만 받겠다는 로직을 가지고 있는 경우가 아니라면 서버는 정상적으로 응답을 하고, 이후 브라우저가 이 응답을 분석해서 CORS 정책 위반이라고 판단되면 그 응답을 사용하지 않고 그냥 버리는 순서인 것이다.

### 출처 비교와 차단은 브라우저가 한다
- 서버는 CORS를 위반하더라도 정상적으로 응답을 해주고, 응답의 파기 여부는 브라우저가 결정한다 즉, CORS는 브라우저의 구현 스펙에 포함되는 정책이기 때문에, 브라우저를 통하지 않고 서버 간 통신을 할 때는 이 정책이 적용되지 않는다. 출처를 비교하는 로직은 서버에 구현된 스펙이 아닌 브라우저에 구현된 스펙이다.
- 또한 CORS 정책을 위반하는 리소스 요청 때문에 에러가 발생했다고 해도 서버 쪽 로그에는 정상적으로 응답을 했다는 로그만 남기 때문에, CORS가 돌아가는 방식을 정확히 모르면 에러 트레이싱에 난항을 겪을 수도 있다.

## CORS 에러 해결방법
### Access-Control-Allow-Origin 세팅하기
- 서버에서 Access-Control-Allow-Origin 헤더를 설정해서 요청을 수락할 출처를 명시적으로 지정할 수 있다. 이 헤더를 세팅하면 출처가 다르더라도 https://myshop.com의 리소스 요청을 허용하게 된다.
- *를 설정하면 출처에 상관없이 리소스에 접근할 수 있는 와일드카드이기 때문에 보안에 취약해져요. 그래서 'Access-Control-Allow-Origin': https://myshop.com과 같이 직접 허용할 출처를 세팅하는 방법이 더 좋다.

### 프락시 서버 사용하기
- 웹 애플리케이션이 리소스를 직접적으로 요청하는 대신, 프락시 서버를 사용하여 웹 애플리케이션에서 리소스로의 요청을 전달하는 방법도 있다. 이 방법을 사용하면, 웹 애플리케이션이 리소스와 동일한 출처에서 요청을 보내는 것처럼 보이므로 CORS 에러를 방지할 수 있다.
- 예를 들어, http://example.com라는 주소의 웹 애플리케이션이 http://api.example.com라는 리소스에서 데이터를 요청하는 상황을 가정했을 때 웹 애플리케이션은 직접적으로 리소스에 요청하는 대신, http://example-proxy.com라는 프락시 서버에 요청을 보낼 수 있다. 그러면 프락시 서버가 http://api.example.com으로 요청을 전달하고, 응답을 다시 웹 애플리케이션에 반환하는 거죠. 이렇게 하면 요청이 http://example-proxy.com보내진 것처럼 보이므로, CORS 에러를 피할 수 있다.

### 질문
#### CORS는 서버에서 제어하는 정책인가, 브라우저에서 제어하는 정책인가?
- CORS는 브라우저에서 제어하는 보안 정책입니다. 서버는 CORS 위반 요청에 대해서도 정상적으로 응답을 반환하지만, 브라우저가 이를 확인한 후 허용되지 않은 경우 응답을 차단하고 사용할 수 없도록 합니다. 따라서 CORS 정책은 서버에 구현된 것이 아니라 브라우저에 구현된 보안 스펙이며, 브라우저를 거치지 않는 서버 간 통신에서는 CORS 제한이 적용되지 않습니다.

#### Access-Control-Allow-Origin을 *로 설정하면 어떤 문제가 발생할 수 있나요?
- Access-Control-Allow-Origin: *을 설정하면 모든 출처에서 리소스를 요청할 수 있도록 허용하기 때문에 보안에 취약해질 수 있습니다. 악성 웹사이트에서도 서버의 데이터를 요청할 수 있으며, 인증이 필요한 API의 경우 CSRF(Cross-Site Request Forgery)와 같은 공격에 노출될 위험이 커집니다. 따라서, 보안 강화를 위해 특정한 도메인(Access-Control-Allow-Origin: https://myshop.com)을 명시적으로 지정하는 것이 바람직합니다.

---

참고링크 

https://velog.io/@effirin/CORS%EB%9E%80-%EB%AC%B4%EC%97%87%EC%9D%B8%EA%B0%80

https://docs.tosspayments.com/resources/glossary/cors

https://inpa.tistory.com/entry/WEB-%F0%9F%93%9A-CORS-%F0%9F%92%AF-%EC%A0%95%EB%A6%AC-%ED%95%B4%EA%B2%B0-%EB%B0%A9%EB%B2%95-%F0%9F%91%8F