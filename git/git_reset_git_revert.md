
# git reset과 git revert 사용이유 
- git reset과 git revert는 commit 또는 Push헀던 내용을 이전 상태로 되돌리른 경우에 사용하는 명령어.
- 로컬의 commit 내용을 변경하고자 할때는 reset을 주로 사용. 원격저장소에 push한 결과를 되돌리고 싶을때는 revert
- 둘의 공통점은 과거로 돌아간다이지만 가장큰 차이점은 **과거로 되돌리겠다는 내용도 기록이되는가(커밋 이력에 남는가)
    - reset : 현재의 기록이 없어진채로 과거로 돌아갈 수 있음 . 이력을 남기지않음. 현재까지의 commit 이력을 남기지않고 원하는 시점으로 완전히 되돌아가고싶을떄 사용
    - revert : 과거로 돌아가겠다는 이력을 남겨두고 원하는 시점으로 돌아감, 즉 이전의 commit이력을 남겨두고 새로운 commit을 생성하면서 과거로 돌아가게 됨.

# git의 구성영역
![image](https://github.com/user-attachments/assets/a53f9d11-9837-46ee-b4a9-ffcde5ea06e9)
- git은 Wokring Directory, Repository, Index, Stash 4개의 영역으로 구성되어있음.
    1. Wokring Directory(작업영역) : 프로젝트 디렉토리이며, 개발자가 직접 코드를 수정하는 공간을 의미. .git을 제외한 모든 영역에 해당.
    2. Index(Staging Area) : Working Directory에서 Repository로 정보가 저장되기 전 준비 영역.
    3. Repository(저장소) : 파일이나 폴더를 변경 이력별로 저장해두는 곳. .git 디렉토리내에 존재함. Local, Remote, Repository로 구분됨.
    4. statch : 임시적으로 작업 사항을 저장해두고 나중에 꺼내올 수 있는 영역.


# git reset
```
git reset 커밋 ID
```
과거 커밋 지점으로 이동하고 이동도니 이후의 커밋은 삭제하는 명령어. git reset에는 3가지 종류가 있음.
    1. git reset -hard : 해당 커밋 ID의 상태로 이동하고 Workgin Direcetory와 Index영역 모두 초기화함.
    2. git reset -mixed : 해당 커밋 ID의 상태로 이동하고 index 영역은 초기화 되고 Working Directory는 변경되지않음.
    3. git reset -soft : 해당 커밋 ID 상태로 이동하고, index 영역과 Working Directory 모두 변경되지않고, commit된 파일들을 staging area로 돌려놓음.

# 특징
- 원격저장소에 push한 경우에 git reset을 사용할때는 주의가 필요함.
- 예를 들어, 커밋 A,B,C,D를 push한 후 git reset을 사용했다고 가정했을때. 

![image](https://github.com/user-attachments/assets/1f9fc65d-82cb-488d-8204-5a5ac99b45ec)

- 이떄 B커밋으로 reset --hard를 하게되면 C,D 커밋은 사라지게 되지만 원격 저장소에는 C,D가 남아있음. 

![image](https://github.com/user-attachments/assets/8267b7f2-0b24-4983-8f84-ea2ada650ce7)

- 로컬 저장소에는 C,D커밋이 사라졌지만 원격 저장소에는 C,D가 남아있다. 따라서 이 상태로 워격 저장소에 push하게 되면 충돌이 발생한다. 로컬 저장소에서 커밋 C,D가 사라짐으로써 원격 저장소와 커밋 히스토리가 불일치 하기때문.
- 나혼자 사용하는 브랜치라면 push --force 명령어로 원격 저장소에 올릴 수 있지만 다른 팀원이 있는 경우 충돌이 발생. 팀원과 공유하느 ㄴ브랜치에 커밋 히스토리를 바꾸게되면 다른 팀원이 push할때 충돌이 발생한다. 따라서 다른 사람과 공유하는 브랜치에서는 reset --hard로 커밋을 지우면 안된다. 
- 공유 브랜치에서 이전 커밋을 수정하고싶을 떄는 어떻게 해야할까? 이럴때 사용할 수 있는 것이 git revert

# git revert

```
git revery 커밋 ID
```
- 이전 커밋 내역을 남겨둔채 새로운 커밋을 생성한다. 따라서 다른 사람과 공유하느 브랜치에서 이전 커밋을 수정하고싶을때는 revert를 사용하는 게 좋다. 그래야 커밋 히스토리가 바뀌지않아 충돌이 발생하지않음. 
![image](https://github.com/user-attachments/assets/1f9fc65d-82cb-488d-8204-5a5ac99b45ec)

- 아래 그림처럼 C,D 커밋내역을 그대로 남겨주면서 revert커밋을 추가한다. 커밋 히스토리 변경 없이 해당 커밋 내용을 삭제한 것. 따라서 원격 저장소에 push해도 충돌이 일어나지않는다. 
![image](https://github.com/user-attachments/assets/10fcea7f-b876-4878-8f2a-5e7f5b46a9b6)
- 되돌릴 커밋이 여러개라면 범위를 주어서 여러개를 선택할 수도 있음.
```
git revery 2664ce8..15413dc
```


### 질문
#### 왜 로컬에서만 사용하는 브랜치와 다른 사람과 공유하는 브랜치에서 git reset과 git revert를 다르게 사용해야 할까요?
- git reset은 선택한 커밋 이후의 모든 커밋 이력을 지우면서 지정한 커밋으로 되돌립니다. 따라서 공유 브랜치에서 reset을 사용하면 다른 팀원들이 원격 저장소와 로컬 저장소 간에 커밋 이력이 불일치해 충돌이 발생할 가능성이 높습니다.
- 반면에, git revert는 되돌아가려는 커밋의 수정 사항을 반대로 적용한 새로운 커밋을 만들어 히스토리 상의 일관성을 유지하면서도 수정할 수 있습니다. 이 방법은 공유 브랜치에서도 안전하게 커밋 기록을 남기며 되돌리기를 수행할 수 있어 팀 작업에서 충돌을 방지합니다.

#### git reset과 git revert의 주요 차이점은 무엇이며, 어떤 상황에서 각각을 사용하는 것이 더 적합할까요?
- 주요 차이점은 커밋 이력의 변경 여부입니다. git reset은 지정된 커밋 이후의 이력을 모두 삭제해 히스토리를 변경하며, 원래 상태로 되돌아갑니다. 반면, git revert는 과거 커밋을 반대로 적용하여 되돌리는 새로운 커밋을 생성하므로, 기존 히스토리를 보존합니다.
- 적합한 상황으로, 개인 브랜치나 로컬에서만 작업할 때는 git reset을 사용해도 무방합니다. 하지만 협업하는 공유 브랜치에서는 커밋 히스토리를 유지해야 하므로 git revert를 사용하는 것이 더 적합합니다.

참고링크 

https://han-joon-hyeok.github.io/posts/git-reset-revert/
https://velog.io/@sonypark/git-reset-vs-git-revert-%EC%B0%A8%EC%9D%B4