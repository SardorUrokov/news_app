package uz.pdp.news_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.news_app.entity.Post;
import uz.pdp.news_app.exceptions.RescuersNotFoundEx;
import uz.pdp.news_app.payload.ApiResponse;
import uz.pdp.news_app.payload.PostDTO;
import uz.pdp.news_app.repository.PostRepository;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    final PostRepository postRepository;

    public ApiResponse addNew(PostDTO postDTO) {
        Post post = new Post();
        post.setText(postDTO.getText());
        post.setTitle(postDTO.getTitle());
        postRepository.save(post); // save
        post.setUrl("http://localhost:80/api/post/" + post.getId()); //  add url+post.id for set url to post
        postRepository.save(post); // again save
        return new ApiResponse("Added", true);
    }

    public ApiResponse byId(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.map(post -> new ApiResponse("Found", true, post)).orElseThrow(() -> new RescuersNotFoundEx("Post", "id", id));
    }

    public ApiResponse all() {
        return new ApiResponse("Found", true, postRepository.findAll());
    }

    public ApiResponse editPost(Long id, PostDTO postDTO) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (!optionalPost.isPresent()) return new ApiResponse("Post not found", false);
        Post post = optionalPost.get();
        post.setTitle(postDTO.getTitle());
        post.setText(postDTO.getText());
        postRepository.save(post);
        return new ApiResponse("Edited", true);
    }

    public ApiResponse deleteById(Long id) {
        boolean b = postRepository.existsById(id);
        if (!b) return new ApiResponse("Not found", false);
        postRepository.deleteById(id);
        return new ApiResponse("Deleted!", true);
    }
}
