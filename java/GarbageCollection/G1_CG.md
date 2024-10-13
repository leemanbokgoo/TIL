# 가비지 컬렉터(Garbage Collector) 종류와 G1 GC

가비지 컬렉터는 5가지 종류가 있다.( jdk7 기준)

Serial GC
ParallelGC
Parallel Old GC
Concurrent Mark & Sweep GC (CMS)
G1(Garbage First) GC

그중 이글에서는 G1를 알아보려고 한다. G1에 대해 공부하는 이유는 다음과 같다.

1. G1 GC는 CMS GC를 대체 하기위해 새롭게 등장했다.

2. 자바 9 버전부터 기본 가비지 컬렉터는 G1이다.




# GC란 ?
G1는 Garbage First의 약어로 Garbage만 있는 Region을 먼저 회수하기때문에 붙여진 이름이다. 빈 공간 확보를 더 빨리 한다는 것은 조기 승격이나 할당률이 급격이 늘어나는 것을 방지하여 Old 영역을 비교적 한가하게 만들 수 있다. G1은 앞서 말했듯이 **CMS GC의 주요 단점인 메모리 파편화, Full GC의 긴 stop-the-world, CPU 자원 과다사용 그리고 예측 불가능한 stop the world**를 대처하기위해 만들어졌다. G1은 특히 대용량 힙 메모리와 짧고 예측 가능한 멈춤 시간이 필요한 에플리케이션에 적합하게 설계되었다. 빠른 속도 처리를 지원하면서 **stop-the-world를 최소화하며 CMS GC보다 효율적**으로 동시에 어플리케이션과 가비지 컬렉터를 진행할 수 있다. 

(*Region이란 G1 GC에서 메모리를 효율적으로 관리하게 도입된 개념으로 G1 GC는 heap메모리를 고정된 크기의 작은 블럭들로 나누는데 그걸 Region이라고 부른다)


![image](https://github.com/user-attachments/assets/9c03b2f6-3a77-4b34-a5e1-43e43d7b4347)

G1 GC는 위의 그림처럼 여러개의 작은 영역으로 나누고 , 각 영역 별로 가비지 컬렉션을 수행한다. 이는 가비지 컬렉션의 효율성을 높이고 가비지 컬렉션으로 인한 stop-the-world를 최소화 한다.



# 특징
 
- 별도의 stop-the-world없이 메모리 공간을 압축하는 기능을 제공한다. 또한 전체 old영역 혹은 young 영역 통째로 압축(compation)할 필요없고 해당 영역의 일부 리전(Region)에 해대서만 압축하면 된다.
(*compation은 파편화된 메모리를 정리하여 사용 가능한 연속된 메모리 공간을 확보하는 과정을 의미한다.)
 
- G1 GC는 Remembered Set을 사용하여 각 영역간의 참조를 추적한다 -> 이를 통해 특정 영역만을 대상으로 가비지 컬렉션을 수행할 수 있으며 전체 heap을 스캔하는데 드는 비용을 줄일 수 있다. (*전체 힙을 스캔하는 것은 시간이 오래 걸리고 비효율적일 수 있기 때문에, G1GC는Region 간의 참조만을 추적하고 수집할 수 있도록 Remembered Set을 사용한다.)

- CMS의 비해 개선된 알고리즘을 사용하며 속도가 빠르다.

- 점진적인 가비지 컬렉션(한번에 처리하지않고 여러번 나누어 수행하는 방식)을 지원하여 어플리케이션의 응답 시간을 개선하고 가비지 컬렉션 작업을 더 예측 가능하게 만들어 실시간성이 중요한 어플리케이션에 유리하다.
 -> 가비지 컬렉션 작업을 더 예측 가능하게 만드는게 실시간성이 중요한 어플리케이션에서 유리할까?
- G1 GC의 주요 목표는 큰 heap을 가진 시스템에서도 일관된 가비지 컬렉션 성능을 제공하는것으로 대규모 자바 어플리케이션에서 선호되는 가비지 컬렉션 중 하나다.


# G1 GC의 Cycle

![image](https://github.com/user-attachments/assets/f2749596-cdc5-4435-9480-b34ae8df054e)


**G1은 두 phase를 번갈아 가며 GC 작업을 한다**. 위의 그림의 모든 원은 stop-the-world를 의미하며 원이 클수록 stop-the-world시간이오래걸린 것이다.  **파란 원은 Minor GC(young GC, Evacuation phase)**가 진행함에 따라 stop-the-world가 일어난 것이고 **주황색 원은 Major GC(Old GC, ConcurrentCycle)**이 진행하면서 객체를 Mark 단계에서 도달가능한 객체를 식별하고 , Sweep단계에서 도달할 수 없는 객체를 제거한다. 마지막으로 Compact(압축)단계에서 메모리를 압축하여 힙의 사용률을 최적화한다. **빨간 원은 Mixed GC**를 진행함에 따라 stop-the-world가 발생한 것이다.

 

만약 애플리케이션 메모리가 부족한 경우 G1GC는 다른 GC들처럼 Full GC를 수행한다.

(* Full GC는 힙의 모든 영역을 대상으로 하는 더 포괄적인 가비지 수집이다. 모든 영역을 청소하고 정리하는 작업이기 때문에 Major GC보다 더 큰 영향을 줄 수 있다.)
 

# Minor GC(Young 영역)
![image](https://github.com/user-attachments/assets/1a8fc51c-8b26-42a7-a2f8-e1f54d5a157d)

연속 되지않은 메모리 공간에 young 영역이 리전 단위로 메모리에 할당되어있다.


![image](https://github.com/user-attachments/assets/0e150ad6-f0e8-4974-8f28-c46d55627d1f)

Young 영역에 있는 유효객체를 Survivor 리전이나 Old 영역으로 복사하거나 이동한다. 이 단계에서 stop-the-world가 발생하고 Eden리전과 Survivor리전의 크기는 다음 Minor GC를 위해 다시 계산된다.


![image](https://github.com/user-attachments/assets/24f45b62-680d-49ab-844b-afb4da301fe5)
Minor GC를 마치고 나면 Eden 영역에서 Survivor 영역으로 이동하거나 Survivor 영역에서 Suvivor 영역으로 이동했다는 청록색 영역이 생긴다.Survivor 영역에서 Survivor 영역으로 이동했다는 뜻은 Survivor 0과 Survivor 1 사이의 이동을 생각하면 된다.

즉, 연속되지않는 young영역에 있는 live object를 survivor 영역이나 Old영역으로 이동시킨다. 이 과정에서 stop-the-world가 발생하며 모든 과정은 멀티 스레드로 동작한다.

![image](https://github.com/user-attachments/assets/dcb4a73a-0a33-45c7-bbca-d29d94cf4ec0)



**Initial Mark **단계는 Old 리전에 존재하는 객체들이 참조하는 Survivor 리전이 있는지 파악해서 Survivor 리전에 마킹하는 단계이다. Survivor 리전에 의존적이기 때문에 Survivor 리전은 깔끔한 상태여야 하고, Survivor 리전이 깔끔하려면 Minor GC가 전부 끝난 상태여야 한다. 따라서** Initial Mark는 Minor GC에 의존적이며, stop-the-world를 발생한다.**


**Root Region Scan**

Initial Mark 단계 다음으로 Root Region Scan 단계가 수행된다. Initial Mark 단계에서 마킹된 Survivor 리전에서 Old 리전에 대해 참조하고 있는 객체를 마킹한다. 멀티 스레드로 동작하며 다음 Minor GC가 발생하기 전에 동작을 완료한다



![image](https://github.com/user-attachments/assets/d548a567-8fa9-465f-9c2e-08e79f827342)

Concurrent Marking 단계에서는 Old 영역 내에 생존해 있는 모든 객체를 마킹한다. stop-the-world가 발생하지 않으므로 애플리케이션 스레드와 동시에 동작하고, Minor GC와 같이 진행되므로 종종 Minor GC에 의해 중단될 수 있다. 위 사진에서 X 표시한 리전은 모든 객체가 Garbage 상태인 영역이다

![image](https://github.com/user-attachments/assets/94ac29cd-f81e-44c6-b4e5-182def8cd1b5)

Remark 단계는 Concurrent Mark에서 X 표시한 영역을 바로 회수하며, STW가 발생한다. 또한, Concurrent Mark 단계에서 작업하던 Mark를 이어서 작업하여 완전히 끝내버린다. 이때 SATB(Snapshot-At-The-Beginning) 기법을 사용하기 때문에 CMS GC보다 더 빠르다. 참고로 SATB는 STW 이후 살아있는 객체에만 마킹하는 알고리즘이다.


![image](https://github.com/user-attachments/assets/873eec28-a0da-4106-ade0-b41f89215109)

Copying/Cleanup 단계에서는 STW가 발생하며, live object의 비율이 낮은 영역 순으로 순차적으로 수거해 나간다.

먼저 해당 영역에서 live object를 다른 영역으로 evacuation(move or copy)한 후, Garbage를 수집한다. G1 GC는 이렇게 Garbage의 수집을 우선(First)해서 계속하여 여유 공간을 신속하게 확보해 둔다.

 ![image](https://github.com/user-attachments/assets/1addda51-fa00-4278-9038-46d3769eb57c)


Major GC가 끝난 이후 live object가 새로운 Region으로 이동하고 메모리 Compaction이 일어나서 깔끔해진 것을 볼 수 있다.

즉, Major GC는 inital mark, root region scan, concurrent mark, remark, copy/cleanup 단계로 구분되어 있으며, 중간 중간 마킹을 수행하면서 Garbage만 존재하는 영역 및 live object가 적은 순으로 영역을 정리하여 가용 공간을 만들어 내는 것이 특징이다. 

 

#### Mixed GC
Mixed GC는 Young 영역과 Old 영역의 Garbage를 수집한다. 한 번에 Old 영역의 Garbage를 수집하는 것은 비용이 크므로 Mixed GC는 기본적으로 8회 수행된다.

Mixed GC는 Minor GC에서 수행하는 단계와 동일하지만, 추가로 Old 영역의 Garbage를 수집한다. 즉, Mixed GC는 Minor GC와 Old 영역의 GC를 혼합한 과정이라고 할 수 있다.

 




참고링크 

https://steady-coding.tistory.com/590
steady-coding.tistory.com
https://johngrib.github.io/wiki/java/gc/g1gc/#g1gc
johngrib.github.io
https://youn0111.tistory.com/67
youn0111.tistory.com
https://www.oracle.com/webfolder/technetwork/tutorials/obe/java/gc01/index.html
