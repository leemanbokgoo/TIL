
- 자바에서는 Log4j → Logback → Log4j2 시간 순으로 로깅 라이브러리가 개발되었다.

# logback
- logback은 java에서 logging을 하기 위한 라이브러리이며 slf4j의 구현체이다.
- log4j 보다는 늦고 log4j 보다는 전에 개발 되었으며 log4j 보다 향상 된 성능과 필터링을 제공한다.
- log 설정 변경 시 서버를 재시작할 필요가 없는 자동 리로드가 가능하다.
- slf4j의 구현체로써 SpringBoot의 기본 log로 사용되고 있으며 spring-boot-starter-web안에 spring-boot-starter-logging의 logback이 기본적으로 포함되어 있어서 별다른 dependency 추가 없이 사용할 수 있다.


# log4j2
- log4j와 logback의 단점을 보완하여 개발되었다.
- log4j2는 가장 최근에 나온 logging 라이브러리이며 마찬가지로 slf4j의 구현체이고 제일 빠르다.
- 멀티쓰레드 환경에서 압도적으로 처리량이 많고 대기 시간이 짧다. 그렇기에 대용량 트래픽을 처리하는 환경에서 주로 사용한다.
- 람다식을 활용할 수 있으며 고급 필터링 옵션을 제공한다.
- logback과 동일하게 log 설정 변경 시 서버를 재시작할 필요가 없는 자동 리로드가 가능하며 필터링을 제공한다.
- log level은 log4j와 동일하다.
- Log4j 2의 가장 눈에 띄는 기능들 가운데 하나는 비동기 성능이다. Log4j 2는 LMAX 디스럽터를 활용하는데, 이 라이브러리는 커널 락의 필요성을 줄이며 12배만큼 로깅 성능을 제공.동일 환경에서 Log4j 2는 1초에 18,000,000개 이상의 메시지를 기록할 수 있는 반면 Logback과 Log4j 1 등은 초당 2,000,000개 미만의 메시지를 기록할 수 있다고 한다. 파일뿐만 아니라 HTTP, DB, Kafka에 로그를 남길 수 있으며 비동기적인 로거를 지원합니다.
- 로깅 성능이 중요시될 때 Log4j2 사용을 고려합니다.
- spring-boot-starter-web 모듈에 Logback을 사용하는 spring-boot-starter-logging 모듈이 포함되어있습니다.
- Log4j2 사용을 위해서는 spring-boot-starter-logging 모듈을 exclude하고 spring-boot-starter-logging-log4j2 의존성을 주입해야 합니다.
- Logback과 달리 멀티 쓰레드 환경에서 비동기 로거(Async Logger)의 경우 Log4j 1.x 및 Logback보다 성능이 우수합니다.
- 2021년 말, 최대 보안 이슈인 Apache Log4j2의 보안 취약점이 발견되었으며 글을 쓰는 현재는 최신 버전으로 업데이트 했을 경우 해당 문제를 해결할 수 있습니다.

## 비교 

![image](https://github.com/user-attachments/assets/779a7d34-c0ff-47c8-9c99-e3a5ed44c11e)


-  로깅할 양이 많고 로깅에 성능이 중요하다면 Logback보단 Log4j2를 사용하는 것이 좋다. 또한 Logback, Log4j2 둘 다SLF4j를 구현하고 있기 때문에 구현을 교체하기에 편리하다.
- 대용량 트래픽 환경에서 Logback 대신 Log4j2를 사용해야 하는 이유는 쓰레드의 갯수가 늘어날 수록 Log4j2의 Async Logger의 처리 성능은 크게 올라가기때문이다.. 
- 반대로 멀티 쓰레드 상황에서도 다른 로깅 프레임워크의 성능은 크게 개선되지 않는다. CPU 코어를 충분히 활용하지 못하기 때문이다.  다만 주의해야 할 점이 있는데, 비동기 로거를 사용할 경우 버퍼에 로그를 우선 저장하기 때문에 파일로 Flush되지 않은 로그는 서버 종료 이벤트로 인해 유실될 수 있다. 선택 가능한 해결책은 두 가지인데, 둘다 완벽한 해결책은 아니다. 다만 가능하다면 두 방법 모두 적용하는 것이 좋다.  
    -  유실되면 안되는 중요한 로그일 경우 sync logger를 사용하고, 그렇지 않은 경우에는 async logger를 혼합하여 적용한다. 
    -  로그 유실을 최소화하기 위해 Graceful Shutdown을 구현한다. 


### 질문
#### Log4j2의 Async Logger를 사용할 때 로그 유실 가능성을 최소화하려면 어떻게 해야 하나요?
- 중요한 로그는 Sync Logger를 사용하여 동기적으로 기록합니다.Graceful Shutdown을 구현하여 서버 종료 시 버퍼에 남아 있는 로그가 안전하게 기록되도록 합니다.

#### Logback과 Log4j2 중 어떤 상황에서 어떤 로깅 라이브러리를 선택해야 하나요?
- Logback은 일반적인 애플리케이션에서 충분한 성능을 제공하며 Spring Boot 기본 설정과의 호환성이 뛰어나 간편하게 사용할 수 있습니다.
- Log4j2는 대용량 트래픽 환경이나 멀티 쓰레드 환경에서 비동기 로거를 통해 성능 향상이 중요한 경우에 적합합니다. Async Logger를 활용하면 쓰레드 증가에 따라 처리 성능이 크게 향상됩니다.

참고링크 : 

https://morethantoday.tistory.com/84?category=853479

https://0soo.tistory.com/241

https://hamryt.tistory.com/m/9

https://ksabs.tistory.com/252

https://blog.naver.com/sqlpro/223132863513