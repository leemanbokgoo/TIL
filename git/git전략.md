
# git 전략
- 브랜치 전략이란 여러 개발자가 하나의 저장소를 사용하는 환경에서 저장소를 효과적으로 활용하기위한 work-flow 
- 브랜치 생성에 규칙을 만들어서 협업을 유연하게 하는 방법론을 말한다.

#### 만일 브랜치 전략이 없다면?
- 아래와 같은 문제가 생기기 쉽다. 
    - 어떤 브랜치가 최신 브랜치인지 
    - 어디다 push를 보내야하는지
    - 핫 픽스는 어떤 브랜치를 기준으로 해야하는지
    - 배포시 어떤 브랜치를 배포해야하는지

## git-flow
![image](https://github.com/user-attachments/assets/92f623da-b607-42c7-9ea7-45643f85dac0)
- git-flow 참고 과정 : https://techblog.woowahan.com/2553/
- gitflow에는 5가지 브랜치가 존재
    - master : 기준이 되는 브랜치로 제품을 배포하는 브랜치
    - develop : 개발 브랜치로 개발자들이 이 브랜치를 기준으로 각자 작업한 기능들을 merge
    - feature : 단위기능을 개발하는 브랜치로 기능 개발이 완료되면 develop 브랜치에 merge
    - release : 배포를 위해 master 브랜치로 보내기전에 먼저 QA(품질검사)를 하기위한 브랜치 
    - hotfix : master 브랜치로 배포했는데 버그가 생겼을때 긴급 수정하기위한 브랜치 
- master과 develop이 항시 유지 되는 메인 브랜치고 나머지는 필요에 의해서 운영하는 브랜치
- 브랜치를 merge할때 항상 -no-ff 옵션을 붙여 branch에 대한 기록이 사라지는 것을 방지하는 걸 원칙으로 한다.

# git flow gmfma
- 앞서 말한 5가지 기본 구조중 가장 많이 사용되는 가지는 master과 develop가 되며 정상적인 프로젝트를 진행하기위해서는 둘 모두를 운용해야한다.
- 나머지 feature, release, hofix branch는 사용하지않는다면 지우더라도 오류가 발생하지않기때문에 깔끔한 프로젝트 진행을 원한다면 지워뒀다가 해당 가지를 활용해야할 상황이 왔을때 만들어줘도 괜찮다
- 대부분의 작업은 Develop에서 취합하고 테스트를 통해 확실하게 변동사항이 없을 경우에 master로의 병합을 진행.
- master가 아닌 가지들은 master의 변동사항을 꾸준히 주시해야한다.

## github-flow
- git-flow가 github에서는 사용하기 복잡하다고 나온 브랜치 전략이다
- hotfix나 feature 브랜치를 구분하지않는다.
- **자동화**개념이 들어가있다는 큰 특징이 존재하며 자동화가 적용되어있지않은 곳만 수동으로 진행하면 된다.
- git-flow 비해 흐름이 단순해짐에 따라 그 규칙도 단순해졌다.
- 기본적으로 master-branch에 대한 규칙만 정확하게 정립되어있다면 나머지 가지들에 대해서는 특별한 관여를 하지않으며 PULL REQUEST기능을 사용하도록 건장한다.

## 특징
- relase branch가 명확하게 구분되지않은 시스템에서의 사용이 유용하다.
- Github 자체의 서비스 특성상 배포의 개념이 없는 시스템으로 되어있기때문에 이 FLOW가 유용하다
- 웹서비스들에 배포의 개념이 없어지고 있는 추세이기때문에 앞으로 Git flow에 비해 사용하기 더 수월할 것이다
- hotfix와 가장 작은 기능을 구분하지않는다. 모든 구분사항들도 결국 개발자가 전부 수정하는 일들 중 하나이기때문이다. 대신 구분하는 것은 우선순위가 어떤 것이 높은지에 대한 것.


# github-flow 흐름
- 브랜치 생성 -> 개발&커밋&푸쉬 -> PR(pull request)생성 -> 리뷰 & 토의 -> 테스트 -> 최종 Merge 
- github-flow의 핵심은 master로 Merge가 일어나면 자동으로 배포가 되도록 설정해놓는다 (CI/CD)

참고링크 ㅣ
https://inpa.tistory.com/entry/GIT-%E2%9A%A1%EF%B8%8F-github-flow-git-flow-%F0%9F%93%88-%EB%B8%8C%EB%9E%9C%EC%B9%98-%EC%A0%84%EB%9E%B5