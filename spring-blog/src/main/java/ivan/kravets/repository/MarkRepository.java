package ivan.kravets.repository;




import ivan.kravets.entity.MarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarkRepository extends JpaRepository<MarkEntity, Long> {

    Optional<MarkEntity> findById(Long id);
}
