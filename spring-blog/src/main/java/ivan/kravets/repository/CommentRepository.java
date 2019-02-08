package ivan.kravets.repository;

import ivan.kravets.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {


//    boolean existsById(Long id);

    Optional<CommentEntity> findById(Long id);
}
