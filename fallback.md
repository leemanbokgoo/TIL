# fallback
- 어떤 기능이 약해지거나 제대로 동작하지않을떄 이에 대처하는 기능 또는 동작을 말한다. fallback은 API 호출 시 발생하는 예외처리를 위한 클래스를 말한다. 실패에 대해서 후처리 위해 설정해두는 methdo이다.

### fallback을 호출할때
- fallback으로 지정한 메소드는 다음에 경우 원본대신 실행(정상동작하지않을 경우 실행)

### Circuit Open
- 메소드를 호출 하지만 디버그로 찍어보면 메소드 바디안에 들어오지않을 경우. 즉, 누군가 가로채서 바로 Exception으로 보내버릴때 
- A가 B,C,D,를 호출 하는데 B가 장애가 발생한 경우 B를 차단해버르므로 장애가 전파되지는 차단기 역할

### Any Exception (HystrixBadRequestException 제외)

### Thread Pool/Queue/Semaphore 가 가득찼을때
- Thread Pool : 작업 처리에 사용되는 Thread를 제한된 개수만큼 정해놓고 작업 큐에 들어오는 작업을 하나씩 Thread가 맡아서 처리하는 것
- Semaphore : 현재 공유자원에 접근할 수 있는 쓰레드, 프로세스의 수를 나타내는 값을 두어 상호배제를 달성하는 기법(카운트)
- 참고) https://worthpreading.tistory.com/90
### Timeout
### 실제 명령어의 수행 여부

참고링크 

https://velog.io/@sweet_sumin/Fallback