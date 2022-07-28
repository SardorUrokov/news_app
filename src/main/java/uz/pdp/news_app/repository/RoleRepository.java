package uz.pdp.news_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.news_app.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

    boolean existsByName(String name);
}
