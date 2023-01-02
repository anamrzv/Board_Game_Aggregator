package application.configuration;

import application.domain.User;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userService.findByLogin(username);
        if (userEntity == null) throw new UsernameNotFoundException("User was not found");
        else return CustomUserDetails.fromUserEntityToCustomUserDetails(userEntity);
    }
}