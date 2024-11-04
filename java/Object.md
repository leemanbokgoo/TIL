# Object

![image](https://github.com/user-attachments/assets/81c58aa3-4239-4c01-b4df-0c0f4b9c93d1)

- 자바의 최상위 클래스 
- 자바 클래스를 선언할떄 exntends 키워드로 다른 클래스를 상속하지않으면 암시적으로 java.lang.Object 클래스를 상속하게 된다.

![image](https://github.com/user-attachments/assets/cda8ecea-0778-4302-8cb6-88b6be2de848)


### Object 클래스 메서드

### toString()
- 기본적으로 Object 클래스의 toString()메소드는 해당 객체에 대한 정보와 주소(hash code)를 문자열로 반환한다. ex)Object@12ndar
- 객체의 이름이나 주소값이 아닌 객체 고유의 정보를 출력하고 싶을떄 toString 메서드를 정의하여 반환값을 다르게 설정해주면 됨. 보통 밑의 코드처럼 오버라이딩함.

```
public String toString(){
     return String.format("이름 : %s, 나이 : %d세", this.name, this.age);
    }
```


### equals 
```
 동일성 : 비교 대상이 실제로 "똑같은" 대상이여야함.
 동등성 비교대상이 같은 값이라고 우리가 정의 한 것 
```
- equals : 두 객체가 논리적으로 동일한지 확인하는 메서드, 객체의 참조가 같은지 (동일성)
- 기본타입에서 두값이 같은지 비교하기위해서는 비교 연산자 (==)을 사용.하지만 객체의 경우 **참조타입**임으로 비교 연산자를 사용하게되면 객체의 주소값을 비교. -> 두 객체에 대한 주소값이 아니라 두 객체가 가지고 있는 변수값이 같은지 여부를 비교하기위해서는 equals()메소드를 오버라이딩해서 사용해야함.
- 즉, 자바에서 동일성을 확인하기위해서는 == 연산자를, 동등성을 확인하기위해서는 equals() 메서드를 오버라이딩해서 사용.
```
public class Person {		
	private int age;
	
	public Person(int age) {
		this.age = age;
	}

	@Override
	public boolean equals(Object obj) {		// equals() 오버라이드
		if (obj instanceof Person) {		// 형변환 체크
			Person person = (Person) obj;	// 형변환(다운캐스팅)
			return age == person.age;
		} else {
			return false;			
		}
	}
    
	@Override
	public int hashCode() {			// 보통 equals()와 함께 오버라이드함
		return Objects.hash(age);
	}
}
```

### hashCode 메서드

- hashCode() : 객체를 위한 해시코드(정수 값)를 반환하는 메소드.이는 해시기반 컬렉션(hashMap,hashSet등)에서 객체를 찾기위한 방법. 
**논리적으로 같은 객체는 같은 해시코드를 가져야한다.**
-  두 객체가 equals()로 비교했을 때 동일하다면, 그 두 객체의 hashCode()도 동일. 즉, hashCode()는 equals()와 마찬가지로 두 인스턴스의 동등성을 정의하기 위해 사용됨.

#### hashCode와 HashMap
- HashMap같은 Hash관련 컬렉션은 hashCode() 메서드가 아주 중요하게 사용.
- hashMap은 키로 매핑된 값을 조회하려 할때 다음과 같은 과정을 거침
![image](https://github.com/user-attachments/assets/d39eead8-2440-43e5-b560-65d23a626aab)
- hashMap은 키를 저장하는게 아니라 hashCode()로 연산한 결과를 저장.( hashCode() 연산한 값을 capacity(용량)로 나눈 값의 나머지를 저장)
- 만약 키가 2인 값을 조회한다고 가정할때 우선 키로 들어온 2를 hashCode() 메서드로 연산한 후 그 값과 일치하는 데이터를 조회. 만약 매칭되는 결과가 하나만 있다면 그 키에 매칭되는 데이터를 반환.
- 하지만 이 hashCode() 연산을 통해 나온 키 값이 중복될 수 있다는 문제가 있음. 위에서 설명하였듯 hashCode() 연산의 결과를 16같이 작은 수로 나눈 나머지를 사용하는데, 이렇게 되면 0 ~ 15 사이에 값이 나오므로 충분히 겹치는 값이 존재할 수 있다.
- 중복되는 키가 두개 조회되면 이때 equals()메서드를 사용해서 두 데이터 비교 -> 상대적으로 가벼운 hashCode연산을 통해 먼저 후보군을 줄이고 equals()를 사용하는 것.


---
참고링크 


https://velog.io/@edgar6bf/Java%EA%B0%9D%EC%B2%B4%EC%A7%80%ED%96%A5-Object-Class

https://inpa.tistory.com/entry/JAVA-%E2%98%95-Object-%ED%81%B4%EB%9E%98%EC%8A%A4%EC%99%80-%EC%83%81%EC%9C%84-%EB%A9%94%EC%84%9C%EB%93%9C-%EC%9E%AC%EC%A0%95%EC%9D%98-%ED%99%9C%EC%9A%A9-%EC%B4%9D%EC%A0%95%EB%A6%AC

https://velog.io/@edgar6bf/Java%EA%B0%9D%EC%B2%B4%EC%A7%80%ED%96%A5-Object-Class
https://kadosholy.tistory.com/107 [KADOSHoly:티스토리]
https://velog.io/@edgar6bf/Java%EA%B0%9D%EC%B2%B4%EC%A7%80%ED%96%A5-Object-Class