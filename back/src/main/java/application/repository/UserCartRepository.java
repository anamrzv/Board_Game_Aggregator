package application.repository;

import application.domain.UserCart;
import application.domain.composite_keys.UserCartKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCartRepository extends JpaRepository<UserCart, UserCartKey> {
}
