package inflearn.thejavatest.study;

import inflearn.thejavatest.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
