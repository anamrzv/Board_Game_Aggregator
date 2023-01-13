package application.service;

import application.domain.User;
import application.domain.UserCart;
import application.domain.UserFav;
import application.domain.UserRole;
import application.domain.composite_keys.UserCartKey;
import application.repository.UserCartRepository;
import application.repository.UserFavRepository;
import application.repository.UserRepository;
import application.repository.UserRoleRepository;
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
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserFavRepository userFavRepository;
    @Autowired
    private UserCartRepository userCartRepository;

    private static final String pepper = "7Hv@*!1rdSQ9";
    private static final Random RANDOM = new SecureRandom();

    @Transactional
    public void saveUser(String login, String password, String mail, boolean wantsMailing) {
        byte[] salt = new byte[6];
        RANDOM.nextBytes(salt);
        String saltString = new String(salt, StandardCharsets.UTF_8);
        UserRole general_user = userRoleRepository.getById(1);
        User user = new User(login, PasswordHasher.encryptStringSHA(pepper + password + saltString), saltString, mail, wantsMailing, general_user, null, null, null, null);
        userRepository.save(user);
    }

    @Transactional
    public UserRole getRole(Integer id) {
        return userRoleRepository.getById(id);
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
            System.out.println(toBeCheckedPassword);
            return userRepository.getByUsernameAndPassword(login, toBeCheckedPassword);
        }
        return null;
    }


    @Transactional
    public void updateUser(User user) { userRepository.save(user); }

    @Transactional
    public void addFavToUser(UserFav fav) { userFavRepository.save(fav); }

    @Transactional
    public UserFav getFavByKey(UserCartKey key) { return userFavRepository.findById(key).orElse(null); }

    @Transactional
    public UserCart getCartByKey(UserCartKey key) { return userCartRepository.findById(key).orElse(null); }

}
