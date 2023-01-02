package application.repository;

import application.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User getByUsername(String username);

    User getByUsernameAndPassword(String username, String password);
}
