package inflearn.thejavatest.study;

import inflearn.thejavatest.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

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

}