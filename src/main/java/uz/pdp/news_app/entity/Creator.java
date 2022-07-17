package uz.pdp.news_app.entity;

import lombok.*;
import uz.pdp.news_app.entity.enums.Roles;
import uz.pdp.news_app.entity.template.AbsNameEntity;

import javax.persistence.*;

@Entity(name = "creator")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class Creator extends AbsNameEntity {

    @Enumerated(EnumType.STRING)
    private Roles role = Roles.USER;

    private String email, phone;
    private boolean active = true;

}
