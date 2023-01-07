package application.repository;

import application.domain.UserFav;
import application.domain.composite_keys.UserCartKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFavRepository extends JpaRepository<UserFav, UserCartKey> {

}
