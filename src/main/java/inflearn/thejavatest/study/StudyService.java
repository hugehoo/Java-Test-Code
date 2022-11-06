package inflearn.thejavatest.study;

import inflearn.thejavatest.domain.Member;
import inflearn.thejavatest.domain.Study;
import inflearn.thejavatest.member.MemberService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class StudyService {
    private final MemberService memberService;
    private final StudyRepository repository;

    public Study createNewStudy(Long memberId, Study study) {
        Optional<Member> member = memberService.findById(memberId);
//        study.setOwnerId(member.orElseThrow(() -> new IllegalArgumentException("Member not exist")));
        Study newStudy = repository.save(study);
        memberService.notify(newStudy);
        memberService.notify(member.get());
        return newStudy;
    }

    public Study openStudy(Study study) {
        study.open();
        Study openedStudy = repository.save(study);
        memberService.notify(openedStudy);
        return openedStudy;
    }
}
