package uz.pdp.news_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.news_app.entity.Comments;

public interface CommentRepository extends JpaRepository<Comments,Long> {
}
