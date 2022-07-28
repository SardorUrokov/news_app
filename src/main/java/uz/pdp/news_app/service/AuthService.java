package uz.pdp.news_app.service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.news_app.AppConstants;
import uz.pdp.news_app.entity.User;
import uz.pdp.news_app.exceptions.RescuersNotFoundEx;
import uz.pdp.news_app.payload.ApiResponse;
import uz.pdp.news_app.payload.RegisterDTO;
import uz.pdp.news_app.repository.RoleRepository;
import uz.pdp.news_app.repository.UserRepository;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public ApiResponse registerUser(RegisterDTO registerDTO) {

        boolean b = userRepository.existsByUsername(registerDTO.getUserName());

        if (!registerDTO.getPassword().equals(registerDTO.getPrePassword()))
            return new ApiResponse("Password not equals!", false);

        if (b) return new ApiResponse("User already has been registered!", false);

        User user = new User();
        user.setFullName(registerDTO.getFullName());
        user.setUsername(registerDTO.getUserName());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setRoles(Collections.singleton(roleRepository.findByName(AppConstants.USER).orElseThrow(() -> new RescuersNotFoundEx("role", "name", AppConstants.USER))));
        user.setEnabled(true);

        userRepository.save(user);
        return new ApiResponse("Saved", true);
    }

    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}