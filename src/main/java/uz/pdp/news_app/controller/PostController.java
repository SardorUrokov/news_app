package uz.pdp.news_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.news_app.service.PostService;
import uz.pdp.news_app.aop.CheckPermission;
import uz.pdp.news_app.payload.ApiResponse;
import uz.pdp.news_app.payload.PostDTO;
import uz.pdp.news_app.repository.PostRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {


    final PostRepository postRepository;

    final PostService postService;

    @CheckPermission("ADD_POST")
    @PostMapping
    public HttpEntity<?> addPost(@RequestBody PostDTO postDTO) {
        ApiResponse apiResponse = postService.addNew(postDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Long id) {
        ApiResponse apiResponse = postService.byId(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<?> allPost() {
        ApiResponse apiResponse = postService.all();
        return ResponseEntity.ok(apiResponse);
    }

    @CheckPermission("EDIT_POST")
    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Long id, @RequestBody PostDTO postDTO) {
        ApiResponse apiResponse = postService.editPost(id, postDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("DELETE_POST")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Long id) {
        ApiResponse apiResponse = postService.deleteById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 404).body(apiResponse);
    }

}
