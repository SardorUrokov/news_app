package uz.pdp.news_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.news_app.entity.template.AbsEntity;

import javax.persistence.*;
import java.util.*;

@Data
@Entity(name = "users")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
//@EntityListeners(AuditingEntityListener.class)
public class User extends AbsEntity implements UserDetails {

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        for (Role role : this.roles) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(role.getName()));
        }
        return grantedAuthorityList;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    private boolean enabled;

//    private boolean accountNonExpired = true;
//
//    private boolean accountNonLocked = true;
//
//    private boolean credentialsNonExpired = true;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<Permissions> permissions = this.role.getPermissions();
//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        for (Permissions permission : permissions) {
//            grantedAuthorities.add(new SimpleGrantedAuthority(permission.name()));
//        }
//        return grantedAuthorities;
//    }

    public User(String fullName, String username, String password, Set<Role> roles, boolean enabled) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.enabled = enabled;
    }

}
