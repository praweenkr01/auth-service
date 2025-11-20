package com.shopitto.authservice.respository;

//public interface UserRepository {
//}
//package com.ecom.authservice.repository;

//import com.ecom.authservice.entity.User;
import com.shopitto.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
