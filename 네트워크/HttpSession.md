# HttpSession
- 서블릿의 기능중 하나로, 서블릿 컨테이너가 제공하는 세션 관리 매커니즘.
- HttpSession을 사용하면 서블릿 컨테이너가 제공하는 세션을 활용하게된다.
- 세션은 HttpSession이라는 인터페이스 객체로 표현되며, HttpSession 객체는 HttpServletRequest의 getSession()이나 getSession(true) 메소드를 이용하여 생성할 수 있다.

## Spring에서의 Session
- Spring은 서블릿 기반의 웹 애플리케이션 프레이므 워크이기때문에 Spring에서 사용하는 세션은 기본적으로 서블릿에서 관리하되는 HttpSession객체를 의미한다.
- Spring은 HttpSession을 좀 더 객체 지향적인 방식으로 다룰 수 있게끔 다양한 방법을 제공.Spring은 HttpSession을 직접 다루거나, @SessionAttributes와 같은 어노테이션을 통해 세션을 관리할 수 있다.
- Spring Web MVC를 이용해서 구현한다 할지라도 Spring은 Servlet Container가 만든 HttpSession을 주입할 뿐, HttpSession을 생성하는 주체는 Servlet Container. 단, SpringSession을 사용한다면 Servley Container가 생성한 구현체가 아니라 Spring Session이 생성한 구현체를 사용.SpringSession은 Servlet Container가 아닌 Redis나 Mongodb 같은 데이터베이스에 세션을 저장/관리하기 위해 API를 제공한다.

## 서블릿에서 HttpSession을 사용하는 예시 
- Spring에서 HttpSession을 사용하려면 서블릿에서처럼 HttpSession 객체를 주입받아 사용할 수 있다.
```
@RequestMapping("/login")
public String login(HttpSession session, String username) {
    session.setAttribute("username", username);  // 서블릿과 동일
    return "redirect:/home";
}

```

## Spring의 세션 관리
- Spring에서 세션 관리는 서블릿의 HttpSession을 기반으로 하고 있지만, Spring은 이를 추상화하여 더 쉽게 사용할 수 있도록 한다.
    - @SessionAttributes: 특정 모델 속성을 세션에 저장하도록 지정할 수 있다.
```
@Controller
@SessionAttributes("user")
public class MyController {
    @ModelAttribute("user")
    public User setUser() {
        return new User(); // 사용자를 모델 속성으로 추가
    }
    
    @RequestMapping("/login")
    public String login(@ModelAttribute("user") User user) {
        return "home";
    }
}
```
- HttpSession 직접 사용: Spring에서 HttpSession을 그대로 사용하여 세션 정보를 처리할 수도 있다.
- 결론적으로, Spring에서 사용하는 세션은 서블릿의 HttpSession을 의미하며, Spring은 이를 객체지향적으로 다룰 수 있게끔 다양한 기능을 제공함. Spring 자체가 별도로 세션을 관리하는 다른 객체를 만들지는 않으며, 모든 세션 관리가 결국 서블릿의 세션에 의존한다.

### 질문
####  1: Spring에서 @SessionAttributes와 HttpSession의 차이점은 무엇인가요?
- @SessionAttributes는 특정 컨트롤러 범위에서만 유효한 세션 속성을 관리하며, 주로 모델 속성을 세션에 저장할 때 사용됩니다. 반면, HttpSession은 서블릿 컨테이너에서 생성된 세션 객체로, 애플리케이션 전체에서 세션 데이터를 유지하고 공유할 수 있습니다.

#### 2: Spring Session을 사용하면 어떤 이점이 있나요?

- Spring Session을 사용하면 Redis나 MongoDB 같은 외부 저장소를 통해 세션을 관리할 수 있습니다. 이를 통해 분산 시스템에서 세션 데이터를 쉽게 공유하고, 세션 클러스터링 없이도 확장성을 확보할 수 있습니다.

---

참고링크 

https://kellis.tistory.com/63 