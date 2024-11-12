
# CI/CD
- CI/CD는 애플리케이션 개발 단계부터 배포때까지의 모든 단계를 자동화를 통해서 좀 더 효율적이고 빠르게 사용자에게 빈번히 배포할 수 있는 것을 말한다.
- CI/CD 개념만을 두고 보자면 자동화와 직접적으로 관련이 있지는 않다. 하지만 그럼에도 자동화라는 키워드는 CI/CD라는 단어에 거의 항상 따라붙는다. 

# CI
- 지속적인 통합이라는 뜻이다. 애플리케이션의 버그 수정이나 새로운 코드 변경이 추가적으로 빌드 및 테스트 되면서 공유된느 레파지토리에 통합(merge)되는 것을 의미한다.
- 코드 변경사항을 주기적으로 빈번하게 머지해야한다.
    - 동일한 소스 파일외에서 두명의 개발자가 개발하다가 나중에 merge를 하게되면 충돌이 일어난다. 이를 해결하기위해 많은 시간을 잡아먹게됨.
    - 기능을 매일 최대한 작은 단위로 나눠서 개발하고 통합해나가는 것이 중요하다.
- **통합을 위한 단계 ( 빌드,테스트,머지)의 자동화**
    - 새로추가된 변경사항이나 시스템의 버그를 초기하지않았는지 자동으로 테스트가 되어야한다. 
    - 빌드를 통과하거나 테스트에 문제가 생기면 빨간불이 뜨고 아니면 초록불이 뜬다.
- 개발자를 위해 빌드와 테스트를 자동화하는 과정, CI는 변경 사항을 자동으로 테스트해 애플리케이션에 문제가 없다는 것을 보장함.
- 그리고 코드를 정기적으로 빌드하고 테스트하므로 여려명이 동시에 작업하는 경우 충돌을 방지하고 모니터링 할 수 있습니다. 


## 장점
- 개발생산성 향상
- 문제점을 빠르게 발견
- 버그 수정 용이
- 코드퀄리티 향샹

# CD
- 지속적 제공, 지속적 배포라는 뜻이다. CI를 통해서 주기적으로 merge된 코드들이 자동으로 Build되고 Test 됬다면 배포단계에서 release할 준비 단계를 거치고 문제가 업슨지 수저할만한 것들이 없는지 개발자가 검증하는 팀이 검증을 함 -> 개발자나 검증팀이 검증하고 배포가 결정나서 배포를 수동적으로 진행하는 것이 Continuous Delivery 지속적인 제공이다.
- 혹은 배포할 준비가 되자마자 **자동화**를 통하여 배포를 진행하는 것이 지속적인 배포이다.

## 장점
- 개발자는 배포보다는 개발에 더욱 신경을 쓸 수 있도록 도와준다
- 개발자가 원클릭으로 수작업 없이 빌드,테스트,배포까지의 자동화를 할 수 있다. 


# CI/CD 파이프라인
- 개발자가 배포할때마다 일일히 빌드하고 배포하는 과정을 진행하는 것은 한두번이면 충분하곘지만 이러한과정이 수없이 진행된다면 일일히 이 과정을 수행하는 것이 번잡스럽고 지루할 것이다. 그래서 이 수없이 진행되는 배포과정을 자동화시키는 방법을 구축하게 되는데 그것을 CI/CD 파이프 라인 이라고 함.
![image](https://github.com/user-attachments/assets/dbc7e2b0-58d5-4a68-aa86-c717b6d819f9)

- 위의 그림은 배포 과정을 도식화 한것. 개발자가 코드를 원격 저장소에 올리면 그 코드가 빌드 및 테스트와 릴리즈를 거쳐 배포서버로 전달된다. 배포서버에 도달한 빌드된 코드는 애플리케이션 서버로 최종 배포가 완료되고 그 결과물을 유저가 직접 확인하게 된다.
- **여기서 자동화를 꾀하는 부분은 보통 코드가 빌드되면서 최종적으로 배포가 되는 단계까지이다. 이 부분을 지속적인 통합 및 배포를 위하여 일련의 자동화 단계로 만드는데, 이것을 파이프라인 구축이라고 표현한다**


## 질문
#### 질문 1: CI/CD 파이프라인을 자동화하면 개발자가 더 신경 써야 하는 부분은 무엇인가요?
- CI/CD 파이프라인이 자동화되면 빌드, 테스트, 배포 과정에서 발생할 수 있는 오류를 자동으로 식별하고 처리할 수 있습니다. 그러나 개발자는 여전히 테스트 케이스의 품질과 배포 과정에서의 롤백 전략을 신경 써야 합니다. CI/CD가 자동화된다고 해서 모든 문제가 해결되는 것은 아니며, 테스트 코드나 배포 전략이 잘못될 경우 파이프라인이 잘 돌아가더라도 문제가 발생할 수 있습니다. 따라서, 테스트 커버리지를 높이고, 배포 실패 시 신속히 롤백할 수 있는 메커니즘을 구현하는 것이 중요합니다.

#### 질문 2: CI/CD에서 지속적 제공(Continuous Delivery)와 지속적 배포(Continuous Deployment)의 차이점은 무엇인가요?
- **지속적 제공(Continuous Delivery)**와 **지속적 배포(Continuous Deployment)**의 차이점은 배포 단계에서의 인간 개입 여부입니다.지속적 제공에서는 코드가 자동으로 빌드되고 테스트되며, 배포 준비가 완료된 상태에서 개발자나 검증 팀의 수동 검토 및 승인이 이루어집니다. 검토 후, 배포가 진행됩니다.지속적 배포는 자동화가 한 단계 더 진행되어, 코드가 빌드되고 테스트되면 자동으로 배포가 이루어집니다. 즉, 코드가 배포되기 전에 인간의 개입 없이 시스템이 자동으로 배포까지 처리합니다. 따라서 지속적 배포는 더 고도화된 자동화 수준을 의미하며, 자동화된 배포를 통해 빠르고 빈번한 업데이트가 가능합니다.










참고 링크 

https://www.youtube.com/watch?v=0Emq5FypiMM&list=WL&index=8
https://velog.io/@leejungho9/CICD-%EB%9E%80