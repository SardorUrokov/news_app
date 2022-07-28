package uz.pdp.news_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.news_app.service.UserService;
import uz.pdp.news_app.aop.CheckPermission;
import uz.pdp.news_app.payload.ApiResponse;
import uz.pdp.news_app.payload.RegisterDTO;
import uz.pdp.news_app.payload.UserDTO;
import uz.pdp.news_app.repository.UserRepository;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    final UserRepository userRepository;
    final UserService userService;

    @CheckPermission("ADD_USER")
    @PostMapping("/add")
    public HttpEntity<?> add(@Valid @RequestBody UserDTO userDTO) {
        ApiResponse apiResponse = userService.addUser(userDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("EDIT_USER")
    @PutMapping("/{id}")
    public HttpEntity<?> editUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        ApiResponse apiResponse = userService.edit(id, userDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("EDIT_MY_PROFILE")
    @PutMapping
    public ResponseEntity<?> editMyProfile(@Valid @RequestBody RegisterDTO registerDTO) {
        ApiResponse apiResponse = userService.editMyProfile(registerDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    @CheckPermission("DELETE_USER")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteById(@PathVariable Long id) {
        ApiResponse apiResponse = userService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 404).body(apiResponse);
    }

    @CheckPermission("GET_USER")
    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Long id) {
        ApiResponse apiResponse = userService.getById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);

    }

    @CheckPermission("GET_USERS")
    @GetMapping
    public HttpEntity<?> all() {
        ApiResponse apiResponse = userService.getAll();
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);

    }

}
