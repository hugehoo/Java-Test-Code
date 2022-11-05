package inflearn.thejavatest.study;

import inflearn.thejavatest.domain.Member;
import inflearn.thejavatest.domain.Study;
import inflearn.thejavatest.member.MemberService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyServiceTest {
    public static final String EMAIL = "hoo@email.com";

//    @Mock
//    MemberService memberService;

//    @Mock // 단순 어노테이션만 있다고 해서 테스트가 진행되진 않는다.  -> @ExtendWith(MockitoExtension.class) 를 등록해줘야한다.
//    StudyRepository studyRepository;

    @Test
    void createStudyService(
            @Mock MemberService memberService,
            @Mock StudyRepository studyRepository
    ) { // mock 객체를 파라미터로 받아, 메서드 전용 mock 객체만 생성할 수도 있다.

//        MemberService service = mock(MemberService.class);
//        StudyRepository repository = mock(StudyRepository.class);
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }


    @Test
    void createNewStudyService(
            @Mock MemberService memberService,
            @Mock StudyRepository studyRepository
    ) { // mock 객체를 파라미터로 받아, 메서드 전용 mock 객체만 생성할 수도 있다.
        StudyService studyService = new StudyService(memberService, studyRepository);

        Optional<Member> optional = memberService.findById(1L);
        memberService.validate(2L); // return type void 이면 아무 일도 일어나지 않는다.

        Member member = new Member();
        member.setId(1L);
        member.setEmail(EMAIL);

        // memberService 에서 findById(1L) 라는 행동을 하면,
        // thenReturn() 이하의 값을 리턴하길 원한다~ 라고 시나리오를 작성
        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        Optional<Member> member1L = memberService.findById(1L);
        assertEquals(1L, member1L.get().getId());

        // 어떤 파라미터를 쓰던 간에(argumentMatcher) 항상 Optional.of(member) 를 리턴한다.
        when(memberService.findById(any())).thenReturn(Optional.of(member));
        assertEquals(EMAIL, memberService.findById(1L).get().getEmail());

        doThrow(new IllegalArgumentException()).when(memberService).validate(1L);
        assertThrows(IllegalArgumentException.class, () -> {
            memberService.validate(1L);
        });
        memberService.validate(2L);

        // createNewStudy() 내부에서 memberService.findById(1L) 가 동작한다.
        // 우리는 위에서 해당 행동의 stubbing 을 미리 만들어두었다.
        assertNotNull(studyService);
    }


    @Test
    void 연속적인_Stubbing_만들기(
            @Mock MemberService memberService,
            @Mock StudyRepository studyRepository
    ) { // mock 객체를 파라미터로 받아, 메서드 전용 mock 객체만 생성할 수도 있다.
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail(EMAIL);

        when(memberService.findById(any()))
                .thenReturn(Optional.of(member))
                .thenThrow(new RuntimeException())
                .thenReturn(Optional.empty());

        Optional<Member> byId = memberService.findById(1L);
        assertEquals(EMAIL, byId.get().getEmail());

        assertThrows(RuntimeException.class, () -> {
            memberService.findById(2L);
        });

        assertEquals(Optional.empty(), memberService.findById(3L));
    }
}

// mock 을 쓰는게 의미가 있나 모르겠다.
// 우리의 실제 데이터는 정제되지 않은, 누락된 데이터가 더 많은 실 데이터일 것인데,
// 모든게 갖춰져 있다고 가정하는 mock 데이터가 의미있나?
// 아니면 mock 객체 자체도 데이터를 누락해서 생성하여 테스트하나?
