package  hotel.com.backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import  hotel.com.backend.Models.Users;
import  hotel.com.backend.Models.Enums.GenderType;

@Repository
public interface UserRepos extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);
}
