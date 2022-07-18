package uz.pdp.news_app.entity;

import lombok.*;
import uz.pdp.news_app.entity.enums.Roles;
import uz.pdp.news_app.entity.template.AbsNameEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class User extends AbsNameEntity {

    @Enumerated(EnumType.STRING)
    private Roles role = Roles.USER;

    private String email, phone;
    private boolean active = true;
/*
    private String password;
    private boolean accountNonExpired = true; //accountni vaqti o'tmaganmi?
    private boolean accountNonLocked = true; //bloklanmaganmi?
    private boolean credentialsNonExpired = true; //parol o'znikimi
    private boolean enabled = true; //tizimga kimdir kirganda undan foydalanish huquqi

    //bu tizimdan foydalanuvchini yo permissionlari bo'ladi yoki rollari ro'yxati
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        for (Role role : this.roles) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(role.getName()));
        }
        return grantedAuthorityList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    //ixtiyoriy uniq bo'ladigan ustunni berish kk login
    @Override
    public String getUsername() {
        return this.phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
*/
}
