package uz.pdp.news_app.entity;

import lombok.*;
import uz.pdp.news_app.entity.template.AbsEntity;

import javax.persistence.Entity;

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
    private Integer views;


//    private Creator creator;
}
