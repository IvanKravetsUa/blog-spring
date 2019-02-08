package ivan.kravets.repository;

import ivan.kravets.entity.UserDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, Long> {

    boolean existsById(Long id);

    Optional<UserDetailsEntity> findById(Long id);
}
