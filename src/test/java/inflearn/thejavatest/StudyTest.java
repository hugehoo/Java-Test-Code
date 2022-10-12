package inflearn.thejavatest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    @Test
//    @Disabled // test 무력화
    void create_study() {
        Study study = new Study(10);
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

    @BeforeEach
        // 모든 테스트를 실행할 때, 각각의 테스트를 실행하기 이전과 이후에 한번씩 실행됨. ? (이후는 왜?)
    void beforeEach() {
        System.out.println("before each");
    }


    @AfterAll //  반드시 static 을 사용해야한ㄷ나. 리턴타입도 있으면 안된다. 그냥 static void 로 작성한다고 기억하라.
    static void afterAll() {
        System.out.println("after All");
    }

    @Test
    void create_new_study() {
        Study study = new Study(10);

//        assertNotNull(study);
//        assertEquals(StudyStatus.DRAFT, study.getStatus(),
//                "스터디를 처음 만들면 상태값이 DRAFT");

        // 문자열을 그대로 쓰면, 테스트가 성공하든 아니든 모두 연산하지만
        // () -> "msg" 처럼 람다식을 사용하면 실패할 때만 문자식을 연산한다.
        // expected 가 먼저, actual 을 후자에 -> 뒤바뀌어도 사실 테스트는 제대로 동작한다. 그래도 제대로 알고 사용하자.


        // 중간에 실패하던 말던, assert 문을 모두 실행한다.
        // 무엇이 실패했는지 한눈에 알 수 있다.
        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus()),
                () -> assertTrue(study.getLimit() > 0, "스터디 참석 인원은 0보다 커야함")

        );
    }

    @Test
    @DisplayName("예외 처리 확인")
    void throws_Exception_When_Limit_Negative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Study(-10));
        assertEquals("0 미만 금지", exception.getMessage());
    }

    @Test
    @DisplayName("타임 아웃 테스트")
    void timeout_Test() {
        assertTimeout(Duration.ofMillis(200), () -> {
            new Study(10);
            Thread.sleep(190);
        });
    }

    @Test
    @EnabledOnOs(OS.MAC)
    @EnabledIfEnvironmentVariable(named="TEST_ENV", matches = "LOCAL")
    void test_environment_variable() {
        String test_env = System.getenv("TEST_ENV");
        System.out.println(test_env);
        assumeTrue("LOCAL".equalsIgnoreCase(test_env));
        assumingThat("LOCAL".equalsIgnoreCase(test_env), () -> {
            Study study = new Study(199);
            assertTrue(study.getLimit() > 10);
        });
    }

    @Test
    @DisabledOnOs(OS.MAC)
    void test_depends_OS() {
        System.out.println("not working");
    }
}