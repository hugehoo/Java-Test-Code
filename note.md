## 강의 : Junit5 소개

Junit 을 가장 많이 사용한다. 그다음이 mockito.

Q. 이 둘의 차이점은 무엇인가?

J5 는 그 자체로 여러 모듈화가 되어있다. Junit platform 에 Jupiter 와 vintage 로 이루어져있다. Jupiter 는 테스트엔진의 구현체 -> Junit5의 구현체 / Vintage 는
3,4를 지원하는 테스트엔진 구현체 주로 주피터 쪽을 살펴볼 것.

## 강의 : Junit5 시작하기

스프링부트 2.2 부터는 스프링부터 스타터 기본 junit 의존성이 5점대로 바뀌었다.

```java
class StudyTest {

    @Test
    @Disabled // test 무력화
    void create() {
        Study study = new Study();
        assertNotNull(study);
    }

    @Test
    void create1() {
        System.out.println("create 1");
    }

    @BeforeAll //  반드시 static 을 사용해야한ㄷ나. 리턴타입도 있으면 안된다. 그냥 static void 로 작성한다고 기억하라.
    static void beforeAll() {
        System.out.println("before All");
    }

    @AfterEach
    void afterEach() {
        System.out.println("after each");
    }

    @BeforeEach // 모든 테스트를 실행할 때, 각각의 테스트를 실행하기 이전과 이후에 한번씩 실행됨. ? (이후는 왜?)
    void beforeEach() {
        System.out.println("before each");
    }


    @AfterAll //  반드시 static 을 사용해야한ㄷ나. 리턴타입도 있으면 안된다. 그냥 static void 로 작성한다고 기억하라.
    static void afterAll() {
        System.out.println("after All");
    }
}
```

## 강의 : Junit5 테스트 이름 표시하기
test method 이름이 긴 경우, 가독성이 떨어진다. 
- `@DisplayNameGeneration` :  클래스와 메서드 모두에 사용가능. starategy 를 입력할 수 있다.
  - `@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)` : underscore 를 띄어쓰기로 치환해준다.


