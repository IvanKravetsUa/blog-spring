package ivan.kravets.repository;

import ivan.kravets.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

//    boolean existsById(Long id);

    Optional<UserEntity> findById (Long id);
}
