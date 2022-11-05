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
        study.setOwner(member.orElseThrow(() -> new IllegalArgumentException("Member not exist")));
        return repository.save(study);
    }
}
