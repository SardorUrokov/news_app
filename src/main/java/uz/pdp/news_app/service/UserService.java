package uz.pdp.news_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.news_app.entity.Role;
import uz.pdp.news_app.entity.User;
import uz.pdp.news_app.exceptions.RescuersNotFoundEx;
import uz.pdp.news_app.payload.ApiResponse;
import uz.pdp.news_app.payload.RegisterDTO;
import uz.pdp.news_app.payload.UserDTO;
import uz.pdp.news_app.repository.RoleRepository;
import uz.pdp.news_app.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {


    final UserRepository userRepository;
    final RoleRepository roleRepository;
    final PasswordEncoder passwordEncoder;
    final RoleService roleService;

    public ApiResponse addUser(UserDTO userDTO) {
        boolean b = userRepository.existsByUsername(userDTO.getUserName());
        if (b) return new ApiResponse("User already exist", false);

        ApiResponse response = roleService.getById(userDTO.getRoleId().longValue());
        if (!response.isSuccess())
            return response;

        User user = new User();
        user.setFullName(userDTO.getFullName());
        user.setUsername(userDTO.getUserName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles(Collections.singleton((Role)response.getObject()));
        user.setEnabled(userDTO.getEnabled());

        userRepository.save(user);
        return new ApiResponse("Saved", true);
    }

    public ApiResponse edit(Long id, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) return new ApiResponse("User not found", false);
        boolean b = userRepository.existsByUsername(userDTO.getUserName());

        if (b) return new ApiResponse("Username already exist", false);

        ApiResponse response = roleService.getById(userDTO.getRoleId().longValue());
        if (!response.isSuccess())
            return response;

        User user = optionalUser.get();
        user.setFullName(userDTO.getFullName());
        user.setUsername(userDTO.getUserName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles(Collections.singleton((Role)response.getObject()));

        user.setEnabled(userDTO.getEnabled());

        userRepository.save(user);
        return new ApiResponse("Saved", true);
    }

    public ApiResponse editMyProfile(RegisterDTO registerDTO) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userRepository.existsByUsernameAndIdNot(registerDTO.getUserName(), user.getId()))
            return new ApiResponse("Username already exists!", false);

        if (!registerDTO.getPassword().equals(registerDTO.getPrePassword()))
            return new ApiResponse("Passwords are not compatible!", false);


        user.setFullName(registerDTO.getFullName());
        user.setUsername(registerDTO.getUserName());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        userRepository.save(user);
        return new ApiResponse("User saved!", true);
    }

    public ApiResponse delete(Long id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isEmpty()) return new ApiResponse("User not found", false);
        userRepository.deleteById(id);
        return new ApiResponse("Deleted", true);
    }

    public ApiResponse getById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(user -> new ApiResponse("User by id!", true, user)).orElseThrow(() -> new RescuersNotFoundEx("user", "id", id));
    }

    public ApiResponse getAll() {
        return new ApiResponse("List User", true, userRepository.findAll());
    }
}
