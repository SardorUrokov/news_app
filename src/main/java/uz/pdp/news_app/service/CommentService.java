package uz.pdp.news_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.news_app.entity.Comments;
import uz.pdp.news_app.entity.Post;
import uz.pdp.news_app.entity.User;
import uz.pdp.news_app.payload.ApiResponse;
import uz.pdp.news_app.payload.CommentDTO;
import uz.pdp.news_app.repository.CommentRepository;
import uz.pdp.news_app.repository.PostRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    final CommentRepository commentRepository;

    final PostRepository postRepository;

    public ApiResponse add(CommentDTO commentDTO) {
        Optional<Post> optionalPost = postRepository.findById(commentDTO.getPostId());
        if (!optionalPost.isPresent()) return new ApiResponse("Post not found", false);

        Comments comments = new Comments();
        comments.setText(commentDTO.getText());
        comments.setPost(optionalPost.get());
        commentRepository.save(comments);
        return new ApiResponse("Saved", true);
    }

    public ApiResponse edit(CommentDTO commentDTO, Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Comments> optionalComment = commentRepository.findById(id);

        if (!optionalComment.isPresent()) return new ApiResponse("Comment not found!", false);

        if (!optionalComment.get().getCreatedBy().getUsername().equals(user.getUsername()))
            return new ApiResponse("you can edit only your comments!", false);

        Optional<Post> postOptional = postRepository.findById(commentDTO.getPostId());
        if (!postOptional.isPresent()) return new ApiResponse("Post not found!", false);

        Comments comment = optionalComment.get();
        comment.setText(commentDTO.getText());
        comment.setPost(postOptional.get());
        commentRepository.save(comment);
        return new ApiResponse("Comment added!", true);
    }


    public ApiResponse deleteMyComment(Long id){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Comments> optionalComment = commentRepository.findById(id);

        if (optionalComment.isEmpty())
            return new ApiResponse("Comment not found!", false);

        if (!optionalComment.get().getCreatedBy().getUsername().equals(user.getUsername()))
            return new ApiResponse("You can delete only your comment", false);

        commentRepository.deleteById(id);
        return new ApiResponse("Deleted!", true);
    }

    public ApiResponse delete(Long id){

        Optional<Comments> optionalComment = commentRepository.findById(id);

        if (!optionalComment.isPresent())
            return new ApiResponse("Comment not found!", false);

        commentRepository.deleteById(id);
        return new ApiResponse("Deleted!", true);
    }
    public ApiResponse getById(Long id) {
        Optional<Comments> commentOptional = commentRepository.findById(id);
        return commentOptional.map(post -> new ApiResponse("Comment deleted", true, post)).orElseGet(() -> new ApiResponse("Comment not found", false));
    }

    public ApiResponse getAll() {
        return new ApiResponse("Comment List", true, commentRepository.findAll());
    }
}
