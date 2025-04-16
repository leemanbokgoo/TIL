# Cascade (영속성 전이)
- cascade 옵션이란 @OneToMany 나 @ManyToOne에 옵션으로 줄 수 있는 값이다.Entity의 상태 변화를 전파시키는 옵션이다. 만약 Entity의 상태 변화가 있으면 연관되어 있는(ex. @OneToMany, @ManyToOne) Entity에도 상태 변화를 전이시키는 옵션이다. 기본적으로는 아무 것도 전이시키지 않는다. 
- Cascade(영속성 전이)는 엔티티의 상태 변화가 연관된 엔티티에도 전이되도록 설정하는 기능이다. 즉, 어떤 엔티티를 persist, remove, merge 등의 작업을 했을 때, 그 엔티티에 연관된 다른 엔티티들도 자동으로 같은 작업을 하도록 한다. 예를 들어, 부모 엔티티를 저장할 때 자식 엔티티도 함께 저장하고 싶다면 Cascade를 사용해서 한 번의 persist로 모두 저장되게 할 수 있다. 또한 부모 엔티티를 삭제하면 자식 엔티티 까지 함께 삭제된다. 데이터 베이스의 데이터 무결성을 지켜주는 속성이라고 할 수 있다.
- JPA Cascade를 활용하면 ‘어떤 엔티티와 다른 엔티티가 밀접한 연관성이 있을 때’에 대한 관리가 매우 수월해진다. 즉, A라는 엔티티에 어떤 작업을 수행했을 때, 그 작업이 연관된 B라는 엔티티도 이루어져야 한다면 JPA Cascade를 유용하게 사용할 수 있다는 의미이다.
- 영속성 전이는 @OneToOne, @OneToMany, @, @ManyToMany 관계에서 사용할 수 있다.
- cascade는 부모 - 자식 관계에서 부모이거나, 연관관계의 주인이 아닌 반대편에 설정해주면된다.
    - 하나의 게시글을 작성할 때 하나의 이미지를 첨부하여 작성한다고 가정하여 Post 엔티티와 Image 엔티티가 있다고 해보자. 그렇다면 게시글 Post가 부모가 될 것이다. 이 경우에는 Post에 cascade를 설정하면 된다.
    - 일대다는 "일"이 연관관계의 주인이 되고, "다" 쪽에 외래키(FK)가 있다. 하지만 이는 비정상적 구조이므로 @ManyToOne 양방향 매핑을 이용한 경우로 예시를 들겠다. 다대일은 외래키(FK)를 갖고 있는 "다"쪽이 연관관계의 주인이 된다. 하나의 팀에는 여러 멤버가 속할 수 있다고 가정하여 Team 엔티티와 Member 엔티티가 있다고 해보자. Member가 "다" 이므로 연관관계의 주인이 되고, Team은 "일"이므로 반대편이 된다. 이 경우에 연관관계 주인의 반대편인 Team에 cascade를 설정해주면된다.

## Cascade 종류
-  사용하는 옵션은 ALL,PERSIST, REMOVE 이다.
    - ALL: 모든 Cascade를 적용
    - PERSIST: 엔티티를 영속화할 때, 연관된 엔티티도 함께 유지 (부모와 자식 영속 상태 )
    - MERGE: 엔티티 상태를 병합(Merge)할 때, 연관된 엔티티도 모두 병합
    - REMOVE: 엔티티를 제거할 때, 연관된 엔티티도 모두 제거
    - DETACH: 부모 엔티티를 detach() 수행하면, 연관 엔티티도 detach()상태가 되어 변경 사항 반영 X
    - REFRESH: 상위 엔티티를 새로고침(Refresh)할 때, 연관된 엔티티도 모두 새로고침

## Cascade 장단점
- Cascade를 사용하면 부모 엔티티를 저장할 때 자식 엔티티도 함께 저장할 수 있어서 코드를 간략화하고 편리하게 사용할 수 있다. 또한, 저장, 삭제, 수정 등을 한 번에 처리할 수 있기 때문에 효율적이다.
- 하지만, Cascade를 사용하면 자식 엔티티를 저장할 때 부모 엔티티가 먼저 저장되어 있어야 하기 때문에 이를 고려하여 코드를 작성해야 한다. 또한, 자식 엔티티를 일괄적으로 처리하기 때문에 필요하지 않은 자식 엔티티까지 저장되는 경우가 생길 수 있다. 이를 방지하기 위해서는 적절한 cascade 옵션을 선택하여 사용해야 한다.

## 고아 객체
- 고아객체는 부모 엔티티와 연관관계가 끊어진 자식 엔티티를 의미한다. JPA 에서는 이러한 고아객체를 자동으로 삭제할 수 있다. 부모 엔티티에서 orphanRemoval 옵션을 true 로 주어서 자동으로 삭제하도록한다.
- 해당 엔티티를 참조하는 곳이 하나이고, 특정 엔티티가 개인 소유하는 경우에만 사용한다. 그렇지 않은 경우에 해당 엔티티를 참조하는 다른 곳에서 조회가 실패되어 문제가 발생할 수 있다.
- @OneToOne, @OneToMany 만 사용가능한다.
- 참조하는 곳이 하나일 때 사용해야 합니다.

### 영속성 전이 주의사항
- 영속성 전이는 연관관계 매핑하는것과 아무 관련이 없다.
    - Entity를 영속화 할 때 연관된 Entity도 함께 영속화하는 편리함을 제공할 뿐이다.
- 다른 Entity가 Child와 연관이 있으면 해당 CASCADE를 사용하면 안된다.
    - Parent와 Child의 LifeCycle이 동일할때 (등록,삭제 등) 사용하기
    - 단일 Entity에 완전히 종속적일 때 사용하면 괜찮다.
- CASCADE 를 설정하면 연관관계에 있는 객체간의 작업이 서로에게 영향을 끼치게 된다. 그렇기 때문에 단일 엔티티에 완전히 종속적이거나 라이프사이클이 같은 객체들의 관계에 대해서만 사용해야한다.
- @ManyToOne(cascade=CascadeType.ALL) 또는 @ManyToOne(cascade=CascadeType.REMOVE) 일 때, Many쪽 (자식 엔티티)를 제거할 때 주의 하여야 한다.
    - 연관된 부모도 삭제하기 때문이다.
    - 연관된 부모를 삭제하게 된다면 그 부모를 참조하고 있던 자식 엔티티들이 고아객체가 되어버린다.

## JPA Cascade가 위험한 이유
- Cascade의 개념 자체는 어렵지 않다. 단순히 EntityManager의 어떤 작업이 수행되면 해당하는 작업을 연관된 엔티티들에 전파하겠다는 의미다.
- 다만 Cascade가 위험하다고 하는 이유는 연관관계 매핑과 함께 Cascade를 잘못 사용하게 되면 의도치 못한 결과를 불러올 수 있기 때문이다.

### 1. 참조 무결성 제약조건 위반 가능성
- CascadeType.REMOVE 혹은 CascadeType.ALL 옵션을 사용하면 엔티티 삭제 시 연관된 엔티티들이 전부 삭제가 되기 때문에 의도치 않게 참조 무결성 제약조건을 위반할 수도 있다.
- 참조 무결성 제약조건이란, 관계형 데이터베이스(RDB)에서 릴레이션(relation)은 참조할 수 없는 외래 키(foreign key)를 가져서는 안 된다는 조건을 의미한다. 이를 위반하는 경우 데이터의 모순이 발생하게 된다.
- 예를 들어 아래와 같이 Comment와 Post가 다대일로 매핑이 된 상태이고 Comment 엔티티의 필드 post에 CascadeType.REMOVE를 지정한 상태라고 가정해본다.

```
// Comment.java
@Entity
public class Comment {

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "post_id")
    private Post post;
    // ...생략
}
그렇다면 다음과 같은 문제가 발생할 수 있습니다.

@Test
void remove_bad_case() {
    Post post = new Post();
    Comment comment1 = new Comment();
    Comment comment2 = new Comment();

    comment1.setPost(post);
    comment2.setPost(post);

    postRepository.save(post);
    commentRepository.save(comment1);
    commentRepository.save(comment2);

    commentRepository.delete(comment1); // 관련된 엔티티(post)도 삭제

    assertThatThrownBy(() -> entityManager.flush())
            .isInstanceOf(PersistenceException.class);
    // comment2 가 참조하는 post 엔티티가 삭제되었으므로 외래키 관련 예외 발생
    // 만약 외래키 제약조건이 없다면 예외가 발생하지 않음
}

```
- comment1을 삭제했을 뿐이지만, 연관된 엔티티인 post까지 삭제되었고 기존에 post를 참조하고 있던 comment2는 참조하고 있는 값이 사라져 참조 무결성 제약조건 위반 예외가 발생한다.
- flush 시점에 참조 무결성 제약조건 위반 예외가 발생하는 이유는 ‘영속성 컨텍스트’에서는 외래키 제약조건이 존재하지 않기 때문이다. 데이터베이스에 직접 쿼리를 날리고 나서야 예외가 발생하게 된다.
- 실제 운영 환경에서는 개발 편의성을 위해 외래키 제약조건을 사용하지 않는 경우도 많은데, 그런 경우에는 예외도 발생하지 않아서 데이터 정합성에 문제가 생길 가능성이 크다.

### 2. 양방향 연관관계 매핑 시 충돌 가능성
- 양방향 연관관계 매핑을 하는 경우에도 유의해야 한다. Comment, Post가 다대일 관계로 구성되어 있고 연관관계의 주인은 Comment라고 가정하겠다. 이 경우 아래와 같이 Post의 comments 필드에 CascadeType.PERSIST를 지정하는 경우 문제가 생길 수 있다.

```
// Post.java
@Entity
public class Post {

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST)
    private List<Comment> comments = new ArrayList<>();
```
- 테스트 코드를 통해 문제 상황을 보여드리면 다음과 같다.
```
@Test
void bidirectional_bad_case() {
    Post post = new Post();
    Comment comment1 = new Comment();
    Comment comment2 = new Comment();

    post.addComment(comment1);
    post.addComment(comment2);

    commentRepository.delete(comment1);
    postRepository.save(post);

    assertThat(commentRepository.existsById(comment1.getId())).isTrue();
  }

```
- 이상하게 commentRepository.delete(comment1) 을 호출했음에도 삭제가 되지 않는 모습을 확인할 수 있다. delete 를 호출해 comment1이 삭제된 상태에서 post를 save 하니 다시 comment1 값이 복원된 것이다.
- 이처럼 영속화에 대한 관리 지점이 두 곳이면 데이터값을 예측할 수 없는 문제가 발생한다. ‘영속성 전이(cascade)는 관리하는 부모가 단 하나일 때 사용해야 한다’ 라는 주장이 나온 배경도 비슷한 맥락이다.
- 이를 해결하기 위해서는 다음과 같이 post에서도 comment를 제거하도록 편의 메소드를 구현해야한다.

```
// Post.java
public void deleteComment(Comment comment) {
    comments.remove(comment);
}
@Test
void bidirectional_good_case() {
    Post post = new Post();
    Comment comment1 = new Comment();
    Comment comment2 = new Comment();

    post.addComment(comment1);
    post.addComment(comment2);

    post.deleteComment(comment1);
    commentRepository.delete(comment1);
    postRepository.save(post);

    assertThat(commentRepository.existsById(comment1.getId())).isFalse();
  }
```
- 이는 비단 CascadeType.PERSIST만의 문제는 아니며 다른 CascadeType을 사용하면서 양방향 연관관계 매핑을 적용하는 경우에는 항상 염두에 둬야 하는 부분이다.

## Cascade의 사용 시기
- JPA Cascade는 엔티티 간 관계가 명확할 때 사용하는 것을 권장한다. 단순히 편해서 비즈니스 로직이 깔끔해진다고 해서 JPA Cascade를 사용하는 것은 추후 큰 문제를 야기할 수도 있다. 특히 Cascade 오용으로 인한 문제가 발생하는 경우, 앞서 설명한 예시처럼 예외가 발생하지 않는 경우도 있기 때문에 발견되지 못한 채로 운영 환경에 오랫동안 잠식할 확률이 높다.
- ‘게시물’과 ‘댓글’의 관계처럼 부모 - 자식 구조가 명확하다면 Cascade를 사용하는 것은 괜찮은 선택이다. 하지만 하나의 자식에 여러 부모가 대응되는 경우(‘수강 중인 수업’과 ‘학생’의 관계 등)에는 사용하지 않는 것이 좋다. 여러 부모가 대응되는 순간 예상하지 못한 충돌 문제가 발생할 확률이 높아지고, 이로 인해 비즈니스 로직을 작성하는 개발자는 항상 JPA Cascade의 부수효과를 경계해야 하기 때문이다.
- JPA Cascade는 개발 편의성을 높일 수 있는 기술이다. 하지만 이를 오용하는 경우 데이터 정합성을 깨뜨릴 수 있을뿐더러 최악의 경우에는 데이터 손실까지 발생하게 된다.

- 부모 엔티티와 자식 엔티티 간의 라이프사이클이 같은 경우
    - 부모 엔티티와 자식 엔티티의 상태 변화가 서로에게 영향을 주는 경우, cascade 옵션을 사용하여 상태 변화를 전파할 수 있다. 이를 통해 데이터의 일관성을 유지하고 코드의 중복을 줄일 수 있다.
- 엔티티 간의 종속성이 큰 경우
    - 엔티티 간의 종속성이 크다면, 한 엔티티의 상태 변화가 다른 엔티티에 영향을 미칠 가능성이 높다. 이러한 경우, cascade 옵션을 사용하여 상태 변화를 자동으로 전파하도록 설정할 수 있다.
     - 좋아요 개수의 경우 게시물에서만 사용되지 다른 곳에서 사용되지 않는다. 다른 말로, 게시물이 존재하기 때문에 좋아요 기능 또한 존재한다. 즉, 게시물이 삭제되면 좋아요 기록 또한 같이 삭제되어야 한다. 
- 하지만 너무 많은 엔티티에 영향을 미치는 경우, 성능 저하의 원인이 될 수 있다. 따라서 cascade 옵션은 신중하게 사용해야 하며, 꼭 필요한 경우에만 설정하는 것이 좋다.

### Cascade와 @OneToMany
- 결론부터 말하자면 @OneToMany에만 Cascade가 사용 가능하다. 이 말은 @OneToMany를 사용하는 필드에 모두 Cascade를 사용하라는 의미가 아닌다. 일단 Cascade를 쓰기 위해서는 최소한의 조건으로 @OneToMany이어야 한다는 의미이다.
- @OneToMany에만 사용해야 하는 이유는 다음과 같다.
    - 자식 엔티티에는 좋아요를 누른 사용자가 있다. 여기서 게시물이 삭제되면 해당 게시물과 관련된 좋아요 기록은 필요가 없어진다. 따라서 부모가 삭제되었을 때, 부모를 참조하는 자식들의 연쇄적인 삭제가 발생해야 한다.

### 질문
#### Cascade를 설정하면 어떤 문제가 발생할 수 있나요?
- Cascade를 무분별하게 설정하면 의도치 않은 데이터 삭제나 저장이 발생할 수 있습니다. 예를 들어 CascadeType.REMOVE를 설정한 상태에서 부모 엔티티를 삭제하면 연관된 자식 엔티티도 같이 삭제되는데, 이 자식 엔티티가 다른 엔티티와도 연관돼 있다면 데이터 정합성 문제가 생길 수 있습니다.
또한, 연관된 엔티티가 많을 경우 CascadeType.PERSIST나 MERGE를 사용하면 예상보다 많은 SQL이 실행되며 퍼포먼스 이슈도 발생할 수 있습니다. 따라서 cascade는 연관관계와 도메인 요구사항을 충분히 고려한 후 설정하는 것이 좋습니다.

#### CascadeType.ALL을 항상 사용하는 것이 좋지 않은 이유는 무엇인가요?
- CascadeType.ALL은 PERSIST, MERGE, REMOVE, REFRESH, DETACH 전부를 포함하는 설정인데, 모든 상황에서 전이되는 것이 바람직하지 않을 수 있습니다.
예를 들어 REMOVE가 포함되어 있어서 부모 엔티티를 삭제하면 자식 엔티티도 함께 삭제되는데, 이 자식이 실제로는 재사용되거나 다른 곳에서 참조되고 있다면 큰 문제가 발생합니다.
또한, 의도하지 않은 merge나 detach가 발생할 수 있어 트랜잭션 제어가 어려워질 수 있기 때문에, 상황에 맞는 전이 타입만 명시적으로 지정하는 것이 더 안전하고 명확한 설계입니다.


----

참고링크 

https://jammdev.tistory.com/178

https://tecoble.techcourse.co.kr/post/2023-08-14-JPA-Cascade/

https://server-technology.tistory.com/252

https://0soo.tistory.com/133

https://hstory0208.tistory.com/entry/JPA-%EC%98%81%EC%86%8D%EC%84%B1-%EC%A0%84%EC%9D%B4cascade%EC%99%80-%EA%B3%A0%EC%95%84%EA%B0%9D%EC%B2%B4orphalRemoval%EB%9E%80

https://hstory0208.tistory.com/entry/JPA-%EC%98%81%EC%86%8D%EC%84%B1-%EC%A0%84%EC%9D%B4cascade%EC%99%80-%EA%B3%A0%EC%95%84%EA%B0%9D%EC%B2%B4orphalRemoval%EB%9E%80