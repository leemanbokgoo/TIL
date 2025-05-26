
## Spring Actuator와 prometheus, Grafana
- 구글의 쿠버네티스를 모니터링하기 위한 소프트웨어로 Borgmon이라는 것이 만들어졌는데 이를 비슷하게 오픈 소스로 공개한 것이 Prometheus으로 Prometheus는 오픈소스 모니터링 시스템이다. Spring Boot Application의 여러 메트릭을 수집하여 모니터링할 수 있는 환경을 구축할 수 있다.Spring Boot의 Actuator, Prometheus, Grafana 조합을 많이 사용하며 이를 통해 애플리케이션의 성능 및 상태에 대해 확인할 수 있다.

## Spring Actuator

![Image](https://github.com/user-attachments/assets/8d0d6b7c-8a32-4192-a418-a6078cc9e9cf)

- Spring Actuator는 Spring Boot 애플리케이션의 모니터링 및 관리 기능을 제공하는 라이브러리로, 개발자가 애플리케이션의 상태와 운영 정보를 쉽게 확인하고 관리할 수 있도록 도와준다. 특히, 애플리케이션의 상태와 성능 관련 데이터를 자동으로 수집하고 노출함으로써, 별도의 코드 작성 없이도 JVM 메모리 사용량, CPU 사용률, 쓰레드 상태, 데이터베이스 커넥션 상태 등을 손쉽게 모니터링할 수 있다.

- Actuator는 Prometheus와 같은 모니터링 도구가 필요한 메트릭을 '/actuator/prometheus' 엔드포인트에서 제공하여, 애플리케이션 내부에서 메트릭을 직접 관리할 필요 없이 Prometheus가 이를 수집해 갈 수 있도록 한다. 또한, Actuator는 여러 가지 기본적인 성능 메트릭을 내장하고 있어, 이를 Prometheus에서 바로 활용할 수 있다.
- 이처럼 Actuator는 Spring 애플리케이션과 Prometheus 간의 통합을 간단하게 처리하여, 시스템 성능 모니터링과 운영 관리의 복잡성을 크게 줄여준다.

### Spring Actuator 동작방식
- /actuator/health, /actuator/metrics, /actuator/prometheus 등의 엔드포인트를 통해 애플리케이션 상태와 메트릭 데이터를 JSON or Prometheus format으로 노출함
- Micrometer 라이브러리를 통해 메트릭 데이터를 수집하며, Prometheus가 이해할 수 있는 포맷으로 변환도 지원

- 동작 흐름
    - Actuator 엔드포인트 자동 등록한다.
        - Actuator를 의존성에 추가하면 Spring Boot는 기본적으로 여러 관리용 엔드포인트를 자동으로 등록한다. 예를 들어, /actuator/health, /actuator/metrics, /actuator/prometheus와 같은 URL이 생성된다.
    - Micrometer를 통한 메트릭 수집
        - 내부적으로는 Micrometer라는 라이브러리를 통해 JVM, GC, HTTP 요청 처리 시간, 스레드 수 등 다양한 시스템 및 애플리케이션 지표를 수집한다.Micrometer는 벤더 중립적인 메트릭 수집 라이브러리이며, Prometheus, Datadog 등 다양한 모니터링 툴과 연동 가능하다.
    - 요청이 들어오면 JSON 혹은 지정된 포맷으로 데이터 응답한다.
        - 사용자가 /actuator/health 같은 엔드포인트에 접근하면, 현재 애플리케이션의 헬스 상태를 JSON 형식으로 응답한다. /actuator/prometheus는 Prometheus 포맷의 메트릭을 응답해 Prometheus가 쉽게 수집할 수 있도록 지원한다.
    - 보안 및 커스터마이징 가능
        - Actuator 엔드포인트는 application.properties나 application.yml에서 어떤 정보를 노출할지 제어할 수 있으며, 보안 설정(Spring Security)도 쉽게 연동된다. 또한, 커스텀 메트릭이나 커스텀 헬스체크를 추가 구현하여 필요한 정보를 추가적으로 노출할 수 있다.


## 메트릭이란?
- 메트릭은 다양한 유형의 요청 시간, 데이터베이스 Connection, CPU 사용량 들을 숫자 데이터로 표현한 것이다. 사용자가 측정하고자 하는 값을 Time series 즉, 시간에 따른 변화를 기록하고 이를 통해 부하를 처리하는 방안에 대해 고민할 수 있다.


# 프로메테우스(prometheus)
- 시간이 지남에 따라 추이가 변하는 데이터를 메트릭(Metric)이라 부른다. CPU 사용률, 메모리 사용률, 트래픽 등이 메트릭(Metric)에 해당된다. 메트릭은 시간별로 데이터가 수집되기에 그 양이 많다. 그래서 외부에 메트릭을 저장하는 DB를 두는데, 대표적으로 프로메테우스(Prometheus)가 있다.

- Prometheus는 애플리케이션의 성능 모니터링과 경고 알림을 위해 널리 사용되는 오픈소스 시스템으로, 특히 시계열 데이터(Time Series Data) 수집과 분석에 특화된 툴이다. 시계열 데이터는 특정 시간에 기록된 메트릭 값을 기반으로 시간의 흐름에 따라 변화하는 데이터를 추적하고 저장하는 방식이다. 이러한 방식은 시스템 성능을 모니터링하고, 문제 발생 시 그 원인을 파악하는 데 유용하다.

![Image](https://github.com/user-attachments/assets/4b05b4ac-34d0-43af-8cb8-acb9549ec711)

- 클라우드 네이티브 환경과 분산 시스템에서 널리 사용되며, 안정적이고 확장 가능한 모니터링 솔루션을 제공한다. 다양한 플랫폼에서 손쉽게 통합할 수 있으며, 애플리케이션의 리소스 사용률, 요청 처리 시간, 데이터베이스 성능 등 중요한 메트릭을 실시간으로 수집하고 분석할 수 있다.
    - 메트릭 수집, 시각화, 알림 , 서비스 디스커버리 기능을 모두 제공하는 오픈 소스 모니터링 시스템.
- Prometheus의 알림(Alerting) 기능은, 지정된 조건에 맞춰 알림을 설정하여 CPU 사용률이 일정 수치를 초과하거나 메모리 사용량이 급증하는 상황을 즉시 파악하고 대응할 수 있다. 이러한 알림은 이메일, Slack 등 다양한 채널을 통해 전달되며, 운영자의 빠른 대응을 도와준다.
- Prometheus는 Grafana와 같은 시각화 도구와도 쉽게 통합되어, 직관적인 대시보드로 실시간 모니터링을 가능하게 한다. 
- 메트릭 수집은 주로 HTTP 엔드포인트를 통한 Pull 방식으로 이루어지며, 서비스 디스커버리 기능을 통해 동적인 환경에서도 자동으로 서비스를 탐지하고 모니터링할 수 있다. 또한, Exporter를 활용하여 Prometheus가 기본적으로 지원하지 않는 애플리케이션의 메트릭도 수집할 수 있다. 
- Prometheus는 자체 쿼리 언어인 PromQL을 제공하며, 이를 통해 다양한 방식으로 메트릭을 집계하고 필터링할 수 있다. 쿼리 결과는 Grafana와 같은 시각화 도구를 통해 그래프와 대시보드 형태로 표현하여 확인할 수 있다. 또한, 알림 규칙을 정의하고, 임계값을 초과하는 등의 문제가 발생하면 알림를 발송하여 운영자가 신속하게 문제를 인지하고 대응할 수 있도록 돕는다.
-

## 프로메테우스 특징
- Pull 방식의 메트릭 수집, 시계열 데이터 저장
    - 프로메테우스가 Target System에서 메트릭을 수집하기위해 풀링 방식을 사용한다. 프로메테우스가 주기적으로 Exporter로 부터 메트릭 읽어와서 수집하는 방식이다. 보통 모니터링 시스템의 에이전트 들은 에이전트가 모니터링 시스템으로 메트릭을 보내는 푸쉬 방식을 사용한다. 특히 푸쉬 방식은 서비스가 오토 스켈링등으로 가변적일 경우에 유리하다. 풀링 방식의 경우 모니터링 대상이 가변적으로 변경될 경우, 모니터링 대상의 IP 주소들을 알 수 가 없기 때문에 어려운 점이 있다.
    - 이러한 문제를 해결하기 위한 방안이 서비스 디스커버리다.
- 서비스 디스커버리
    -  특정 시스템이 현재 기동중인 서비스들의 목록과 IP 주소를 가지고 있으면 된다. 예를 들어 앞에서 VM들을 내부 DNS에 등록해놓고 새로운 VM이 생성될때에도 DNS에 등록을 하도록 하면, DNS에서 현재 기동중인 VM 목록을 얻어와서 그 목록의 IP들로 풀링을 하면 되는 구조이다.
    - 프로메테우스도 서비스 디스커버리 시스템과 통합을 하도록 되어 있다. DNS나, 서비스 디스커버리 전용 솔루션인 Hashicorp사의 Consul 또는 쿠버네티스를 통해서, 모니터링해야할 타겟 서비스의 목록을 가지고 올 수 있다.
- PromQL을 활용하여 저장된 시계열 쿼리 및 집계

## 프로메테우스 동작 방식
- Pull 방식으로 지정된 타겟(예: Spring Actuator의 /actuator/prometheus)에서 정기적으로 메트릭 데이터를 수집
- 수집한 데이터는 시계열(time-series) 형태로 저장되며, 쿼리 언어인 PromQL로 분석 가능
- 알람 룰(alert rule)을 설정하여 특정 조건이 충족되면 Alertmanager를 통해 알림 발송 가능
- 동작 흐름
    - 타겟 등록 (Service Discovery 또는 static 설정)
        - Prometheus는 설정 파일에 등록된 타겟의 메트릭 엔드포인트(예: /actuator/prometheus)를 기반으로 메트릭을 수집한다. 또는 Kubernetes와 같은 환경에서는 서비스 디스커버리를 통해 자동으로 타겟을 탐지할 수도 있다.
    - Pull 방식 수집 (Scraping)
        - Prometheus는 주기적으로 각 타겟의 메트릭 엔드포인트에 HTTP 요청을 보내고, 노출된 메트릭 데이터를 가져온다. 이 데이터는 Prometheus가 정의한 텍스트 기반 포맷으로 되어 있으며, 시간과 함께 저장된다.
    - 시계열 데이터 저장
        - 수집된 데이터는 내부 시계열 DB에 저장되며, 각 메트릭은 타임스탬프, 이름, 라벨(label) 정보를 기반으로 구분된다.
    - PromQL을 통한 조회 및 분석
        - 사용자는 Prometheus의 쿼리 언어인 PromQL을 통해 저장된 메트릭 데이터를 조회하거나 조건을 걸어 알람을 설정할 수 있다.
    - 알림 전송 (Alerting Rules)
        - Prometheus는 조건에 맞는 알람 룰(Alert Rule)을 평가하고, 필요 시 Alertmanager를 통해 슬랙, 이메일 등으로 알림을 전송한다.


## 프로메테우스의 핵심개념
- https://wlsdn3004.tistory.com/35

## 프로메테우스 구조
![Image](https://github.com/user-attachments/assets/82dd8be4-f0b2-4f3e-99c9-64e286b8c4fe)

### Prometheus Server
- 시계열 데이터를 생성하고 저장하는 핵심 서비스

### Retrieval
- 서비스 디스커버리로부터 모니터링 대상을 받아오고 Exporter로부터 주기적으로 그 대상의 메트릭을 수집하는 모듈

### TSDB
- 시계열 데이터를 저장하는 자체 스토리지 엔진
    - 외부에 DB를 두는 것이 아니면 프로메테우스는 로컬에 데이터를 저장해둔다.
- HTTP Server
    - 통신할 때 사용하는 HTTP Server

### Alertmanager
- 특정 메트릭이 임계치를 넘어가거나 경계에 잡혔을 때 이메일, 슬랙 등을 통해 알림 전송 가능

### Data Visualization
- 프로메테우스 자체 web UI가 있어 데이터 시각화가 가능
    - 시각화 기능은 약한 편이라 보통 Grafana라는 대시보드 툴로 대체해서 쓰는 편
- PromQL이라는 쿼리를 사용해서 데이터 조회

### Service Discovery
- 서비스 디스커버리란 MSA같은 분산 환경에서 서비스 클라이언트가 서비스를 호출할 때 서비스의 위치를 알아낼 수 있는 기능
- 프로메테우스는 서비스 디스커버리기능을 지원함 (쿠버네티스 등등)
- 쿠버네티스에 존재하는 모든 노드와 팟의 메트릭을 수집 가능

### Exporter
- 타겟 시스템에서 메트릭을 읽어서 프로메테우스가 풀링 할 수 있도록 하는 모니터링 에이전트
- 데이터를 가져오고 싶은 시스템에 설치하는 프로그램

### Pushgateway
- 프로메테우스가 메트릭을 푸시할 수 있게 지원
- 푸시된 메트릭을 프로메테우스에서 가져갈 수 있도록 중재자 역할 수행

## 프로메테우스 장점
- Pull 방식의 구조를 채택해서 모든 메트릭 정보를 중앙 서버로 보내지 않아도 된다. 대부분의 모니터링 구조는 push인데, 각 타겟 서버에서 부하가 걸릴 경우 push 방식은 fail point가 될 가능성이 있다.
- Kubernetes 환경에서 설치가 간단하고, grafana와의 연동을 통한 운영이 쉽다.
- 다양한 써드파티 프로그램과의 연동을 통해 운영이 쉽다. 다양한 metric exporter 제공하며 Linux, Window등의 OS metric 뿐 아니라 각종 Third-party의 exporter를 제공한다.
- 구조가 복잡하지 않고 간단하다.
- 모든 데이터를 수집하지 않고 일정 주기로 메트릭을 수집해 애플리케이션에 무리 없다.
- 장기간 데이터 유지와 확인이 가능하다. 데이터 저장소가 시계열 데이터 저장소로 구성되어있어, 많은 양의 정보를 빠르게 검색 가능핟.

## 프로메테우스 단점
- 각 Region에 프로메테우스를 배치 한 뒤, 이를 Master에 Aggregate하는 방식이 프로메테우스가 공식적으로 권장하는 다중화 방식이다. 클러스터링이 불가능함
- 싱글 호스트 아키텍처이기 때문에 저장용량이 부족하면 디스크 용량을 늘리는 것 밖에 방법이 없다. 스케일을 키우고 싶으면 프로메테우스를 여러개 구축해 계층형으로 연결해야 한다.
- 모든 데이터를 수집하지 않아서 대략적인 데이터 흐름을 보긴 좋지만 모든 데이터를 필요로 하는 목적에는 부적합
    - APM(Application Performance Monitoring)같이 모든 로그를 추적해야하는 상황에는 좋지 않음
    - 일정 풀링 주기를 기반으로 metric을 가져오기 때문에 풀링하는 순간의 스냅샷 정보만 알 수 있다. 스냅샷의 연속된 모음이기 때문에 근사값의 형태
- 프로메테우스 서버가 다운되거나, 설정 변경 등을 위해서 재시작을 할 경우 그간의 metric은 유실된다.

## 그라파나 (Grafana)
- 오픈소스 메트릭 데이터 시각화 도구로 시계열 메트릭 데이터를 시각화하여 대시보드를 제공해주는 오픈소스이다. 
- 다양한 데이터소스들의 데이터 시각화를 지원한다.
    - InfluxDB, OpenTSDB, AWS CloudWatch, Azure Monitor, ElasticSearch 등등
- 프로메테우스 자체의 시각화 도구가 빈약하기때문에 그라파나와 함께 쓰는 경우가 많다. 프로메테우스로 데이터를 숮비하고 그라파나로 해당 데이터를 보여주는 대시보드를 구성한다.
-  

## 그라파나 특징
- Grafana는 시계열 매트릭 데이터 수집에 강한 모습을 보이는 만큼, 활용하는 부분도 서버 리소스의 매트릭 정보나 로그 같은 데이터를 시각화는 데 많이 사용한다.
- 시각화한 그래프에서 특정 수치 이상으로 값이 치솟을 때(예를 들어 CPU 사용량 80% 이상) 알림을 전달받을 수 있는 기능도 제공한다.
- 이러한 기능은 인프라 운영 관점에서 굉장히 중요한 기능이라고 할 수 있다. 오픈소스 툴킷인 만큼, 커뮤니티도 많이 활성화 되어있는데, 일반 사용자들이 만들어놓은 대시보드를 import해서 사용할 수도 있고 import한 대시보드를 내 입맛에 맞게 커스터마이징 할 수도 있다.
- 다양한 플러그인이 있어 Grafana 내부적으로 기능 확장도 쉽게 가능하다.

## 그라파나 동작방식
- Prometheus를 데이터 소스로 연결하여 PromQL 쿼리를 통해 데이터를 조회
- 실시간 모니터링 가능한 다양한 그래프와 패널로 시각화
- 알람 조건도 정의 가능하며, 다양한 알림 채널(Slack, 이메일 등) 연동 가능

### 질문
#### Prometheus, Grafana, Spring Boot Actuator의 역할과 동작방식에 대해 설명하세요
- Prometheus, Grafana, Spring Boot Actuator는 애플리케이션의 모니터링 시스템을 구성할 때 자주 함께 사용되는 도구입니다.
- 먼저, Spring Boot Actuator는 애플리케이션의 상태와 각종 메트릭 정보를 노출하는 역할을 합니다. Micrometer 라이브러리를 통해 메트릭을 수집하고, 이를 Prometheus가 수집할 수 있도록 /actuator/prometheus와 같은 엔드포인트로 제공합니다.
- Prometheus는 이러한 메트릭 데이터를 수집하고 저장하는 시계열 데이터베이스입니다. Pull 방식으로 애플리케이션의 Actuator 엔드포인트에 주기적으로 접근하여 메트릭을 수집하며, 수집된 데이터에 대해 PromQL이라는 쿼리 언어로 조회 및 분석이 가능합니다.
- 마지막으로 Grafana는 Prometheus를 데이터 소스로 연결하여 메트릭 데이터를 시각화해주는 대시보드 도구입니다. 실시간 그래프나 차트로 메트릭을 시각화하고, 알람 조건을 설정하여 운영자가 이상 상황을 빠르게 감지할 수 있도록 돕습니다.
- 즉, Spring Actuator는 메트릭을 노출하고, Prometheus는 이를 수집 및 저장, Grafana는 시각화하는 역할을 하며, 이 세 가지를 연동하여 안정적인 모니터링 시스템을 구성할 수 있습니다.
    - Spring Boot Actuator에서 메트릭 노출
        - 애플리케이션에 Spring Boot Actuator와 Micrometer Prometheus Registry를 설정하면, /actuator/prometheus 엔드포인트에서 애플리케이션의 메트릭 정보를 Prometheus 포맷으로 노출하게 됩니다.
    - Prometheus가 주기적으로 메트릭 수집 (Pull)
        - Prometheus는 설정 파일에 등록된 타겟(예: http://my-service:8080/actuator/prometheus)을 기준으로, 주기적으로 해당 엔드포인트에 HTTP 요청을 보내고 메트릭을 수집합니다. 이 데이터는 시계열 형태로 내부 DB에 저장됩니다.
    - PromQL로 메트릭 분석 및 Alert 설정
        - Prometheus는 수집된 데이터를 PromQL이라는 자체 쿼리 언어로 분석할 수 있으며, 조건에 따라 Alertmanager를 통해 알림을 전송할 수 있습니다.
    - Grafana가 Prometheus를 데이터 소스로 사용
        - Grafana는 Prometheus를 데이터 소스로 설정하여, PromQL을 사용해 메트릭을 시각화합니다. 
        - 운영자는 다양한 그래프, 차트, 대시보드를 구성해 실시간 상태를 모니터링할 수 있습니다.


----

참고 링크 

https://junuuu.tistory.com/772

https://wlsdn3004.tistory.com/35

https://velog.io/@hsk2454/Prometheus%EB%9E%80

https://velog.io/@corone_hi/%EA%B7%B8%EB%9D%BC%ED%8C%8C%EB%82%98-%EC%98%A4%ED%94%88%EC%86%8C%EC%8A%A4-%EB%8D%B0%EC%9D%B4%ED%84%B0-%EB%AA%A8%EB%8B%88%ED%84%B0%EB%A7%81%ED%88%B4-BI-Tool

https://owin2828.github.io/devlog/2020/03/13/etc-5.html