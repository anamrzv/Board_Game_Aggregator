package application.configuration;

import application.domain.User;
import application.domain.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private Set<UserRole> userRole;

    public static CustomUserDetails fromUserEntityToCustomUserDetails(User userEntity) {
        CustomUserDetails c = new CustomUserDetails();
        c.username = userEntity.getUsername();
        c.password = userEntity.getPassword();
        Set<UserRole> roles = new HashSet<>();
        roles.add(userEntity.getUserRole());
        c.userRole = roles;
        return c;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRole;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    @Override
    public boolean isEnabled() {
        return true;
    }
}
