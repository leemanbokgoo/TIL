## 에러 Exception 예외처리 

### 프로그래밍 오류의 종류
- 컴파일 에러 : 컴파일시 발생하는 에러 
- 런타임 에러 : 실행 시에 발생하는 에러
- 논리적 에러 : 실행은 되지만 의도와는 다르게 동작하는 것.

여기서 중요하게 봐야할 것은 런타임 에러이다. 런타임에러를 방지하기위해서 우리는 에러 처리를 한다.

## 예외 클래스 

![스크린샷 2024-10-15 오후 9 51 25](https://github.com/user-attachments/assets/f2fafb0b-6031-4a37-8b85-b1657830c384)


- 오류 : 시스템이 종류되어야할 수준의 상황과 같이 수습할 수 없는 문제, 개발자가 미리 예측하여 방지하기 어려움
- 에러 : 개발자가 구현한 로직에서 발생한 실수나 사용자의 영향에 의해 발생 -> 오류와 달리 미리 방지 가능. 예외처리 필요

이렇게 대부분의 예외는 개발자가 구현한 로직에서 발생한 실수나 사용자의 영향에서 발생한다. 그래서 예외는 에러와 달리 문제가 발생하더라도 예외처리를 통해 통제할 수 있다. (대표적인 예 try-catch )

### 


## 자바 예외 처리 전략
### 자바 예외 처리의 기본 원칙
- 어떤 예외도 무시하지말라
- 적절한 수준에서 예외를 처리해야한다.
- 구체적인 예외를 캐치하라

```
try {
        // risky operations
    } catch (FileNotFoundException e) {
        // handle FileNotFoundException
    } catch (IOException e) {
        // handle IOException
    }
```






참고 링크 

https://toneyparky.tistory.com/40