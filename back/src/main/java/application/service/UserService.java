package application.service;

import application.domain.User;
import application.domain.UserRole;
import application.repository.UserRepository;
import application.util.PasswordHasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Random;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private static final String pepper = "7Hv@*!1rdSQ9";
    private static final Random RANDOM = new SecureRandom();

    @Transactional
    public void saveUser(String login, String password, String mail, boolean wantsMailing) {
        byte[] salt = new byte[6];
        RANDOM.nextBytes(salt);
        String saltString = new String(salt, StandardCharsets.UTF_8);
        UserRole general_user = new UserRole(1);
        User user = new User(login, PasswordHasher.encryptStringSHA(pepper + password + saltString), saltString, mail, wantsMailing, general_user, null, null, null, null);
        userRepository.save(user);
    }

    @Transactional
    public User findByLogin(String login) {
        return userRepository.getByUsername(login);
    }

    @Transactional
    public User findByLoginAndPasswordAndUserRole(String login, String password, UserRole userRole) {
        User user = userRepository.getByUsernameAndUserRole(login, userRole);
        if (user != null) {
            String saltString = user.getSalt();
            String toBeCheckedPassword = PasswordHasher.encryptStringSHA(pepper + password + saltString);
            return userRepository.getByUsernameAndPassword(login, toBeCheckedPassword);
        }
        return null;
    }
}
