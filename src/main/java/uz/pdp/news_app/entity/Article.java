package uz.pdp.news_app.entity;

import lombok.*;
import uz.pdp.news_app.entity.template.AbsEntity;
import uz.pdp.news_app.entity.enums.Category;

import javax.persistence.*;
import java.util.List;

@Entity(name = "article")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class Article extends AbsEntity {

    private String title;
    private String articleText;
    private Integer views = 0;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "article")
    private List<Comment> comments;

    @ManyToOne
    private User creator;
}
