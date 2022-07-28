package uz.pdp.news_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.news_app.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
