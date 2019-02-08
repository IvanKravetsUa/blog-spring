package ivan.kravets.repository;

import ivan.kravets.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {

    boolean existsByName(String name);

//    boolean existsById(Long id);

    Optional<TagEntity> findById(Long id);
}
