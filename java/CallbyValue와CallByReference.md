

## Call by value와 call by reference
- 자바에서 메서드 호출 시 파라미터를 전달하는 방법에 대해 알아보자. 함수의 매개변수에서 값을 복사하느냐 주소값을 참조하느냐에 따라 반환결과가 달라지기때문에 중요하다고 한다. 다만 최근에는 Call by Reference는 트렌드에 뒤쳐진 기술로 선호도가 굉장히 낮아져 더이상 최신언어에는 사용되지않는다고 하니 이런 게 있다는 것만 알아두도록 하자. 

## Call by value
기본 자료형
- 메소드를 호출할떄 값을 넘겨주기때문에 pass by value라고도 불린다. 모든 메서드 호출은 값에 의한 호출로 이루어진다. 이것은 메서드가 호출될때 인수로 전달되는 값의 복사본이 메서드로 전달된다는 의미이다. 기본 자료형의 경우 메서드에 전달 할때 해당 변수의 값을 복사하여 전달한다. 메서드 내부에서 그 값을 변경해도 원본 변수에는  영향을 미치지않는다.

public class Main {
    public static void main(String[] args) {
        int x = 10;
        modifyPrimitive(x); // x의 값은 그대로 유지
        System.out.println(x); // 출력: 10
    }

    public static void modifyPrimitive(int value) {
        value = 20; // value의 복사본을 수정
    }
}
 

## 기본 자료형
하지만 반대로 객체나 배열 같은 참조타입을 메서드에 전달할때도 참조의 값이 복사된다. 여기서 값은 객체 메모리의 주소를 의미한다. 메서드에 참조된 객체 속성을 수정하면 원본 객체에도 영향을 미친다. 

class Person {
    String name;
}

public class Main {
    public static void main(String[] args) {
        Person person = new Person();
        person.name = "Alice";
        modifyReference(person);
        System.out.println(person.name); // 출력: Bob
    }

    public static void modifyReference(Person p) {
        p.name = "Bob"; // 참조된 객체의 속성 변경
    }
}
 

하지만 참조자체를 다른 객체로 변경하면 원본 참조는 변경되지않는다.

class Person {
    String name;
}

public class Main {
    public static void main(String[] args) {
        Person person = new Person();
        person.name = "Alice";
        changeReference(person);
        System.out.println(person.name); // 출력: Alice (변경되지 않음)
    }

    public static void changeReference(Person p) {
        p = new Person(); // 새로운 객체로 참조를 변경
        p.name = "Bob";
    }
}
 

이러한 동작이 자바에서 call by reference처럼 보일 수 있지만, 실제로는 메서드 호출 시 전달된 값(참조 주소)의 복사본을 사용하는 call by value이다.

## call by reference
함수나 메서드 호출 시, 인수로 전달되는 변수의 주소를 전달하는 방식이다 이는 변수의 실제 메모리 위치를 함수에 넘기는 것이기 때문에, 함수 안에서 해당 변수를 직접 수정할 수 있다 Call by Reference는 변수를 복사하는 대신에, 해당 변수 자체에 접근하기 때문에 함수 내부에서의 변화가 호출한 쪽에도 영향을 미친다.
