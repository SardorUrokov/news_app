package uz.pdp.news_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.news_app.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);
    boolean existsByUsernameAndIdNot(String username, UUID id);

    Optional<User> findByUsername(String username);

}
