package inflearn.thejavatest;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    @Test
//    @Disabled // test 무력화
    void create_study() {
        Study study = new Study();
        assertNotNull(study);
    }

    @Test
    @DisplayName("테스트 만들기 ✅")
    void create1() {
        System.out.println("create 1");
    }

    @BeforeAll //  반드시 static 을 사용해야한ㄷ나. 리턴타입도 있으면 안된다. 그냥 static void 로 작성한다고 기억하라.
    static void before_all() {
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