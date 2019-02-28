package ivan.kravets.repository;

import ivan.kravets.entity.UserDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInformationRepository extends JpaRepository<UserDetailsEntity, Long> {

    boolean existsById(Long id);

    Optional<UserDetailsEntity> findById(Long id);
}
