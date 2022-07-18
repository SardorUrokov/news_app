package uz.pdp.news_app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import uz.pdp.news_app.entity.template.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends AbsEntity {

    private String text;

    @JsonIgnore
    @ManyToOne
    private Article article;
}
