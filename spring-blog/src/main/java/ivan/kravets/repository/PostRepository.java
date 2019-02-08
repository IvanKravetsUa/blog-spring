package ivan.kravets.repository;

import ivan.kravets.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

//    boolean existsByTitleIgnoreCase(String title);
//
//   boolean existsById(Long id);

    Optional<PostEntity> findById(Long id);
}
