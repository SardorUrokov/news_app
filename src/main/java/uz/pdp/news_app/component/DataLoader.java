package uz.pdp.news_app.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.news_app.AppConstants;
import uz.pdp.news_app.entity.Role;
import uz.pdp.news_app.entity.User;
import uz.pdp.news_app.enums.Permissions;
import uz.pdp.news_app.repository.RoleRepository;
import uz.pdp.news_app.repository.UserRepository;

import java.util.Arrays;

import static uz.pdp.news_app.enums.Permissions.*;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    @Value("${spring.sql.init.mode}") // spring.sql.init.mode= ( always or never )

    private String modeType;
    final UserRepository userRepository;
    final RoleRepository roleRepository;
    final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (modeType.equals("always")) {

            Permissions[] values = Permissions.values();

            Role admin = roleRepository.save(new Role(
                    AppConstants.ADMIN,
                    Arrays.asList(values), "Admin all permissions" // Get all permissions for admin
            ));

            Role user = roleRepository.save(new Role(
                    AppConstants.USER,
                    Arrays.asList(ADD_COMMENT, EDIT_COMMENT, DELETE_MY_COMMENT, EDIT_MY_COMMENT), "User" // limited permissions for user
            ));

            userRepository.save(new User(
                    "Admin", "Admin", passwordEncoder.encode("123"), admin, true
            )); // Admin role, password=123

            userRepository.save(new User(
                    "User", "User", passwordEncoder.encode("123"), user, true
            )); // User role, password=123
        }
    }
}