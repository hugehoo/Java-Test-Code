package inflearn.thejavatest.member;

import inflearn.thejavatest.domain.Member;
import inflearn.thejavatest.domain.Study;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface MemberService {

    Optional<Member> findById(Long memberId);

    void validate(Long id);

    void notify(Study study);

    void notify(Member member);
}
