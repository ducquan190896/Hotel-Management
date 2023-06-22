package hotel.com.backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hotel.com.backend.Models.Department;

@Repository
public interface DepartmentRepos extends JpaRepository<Department, Long> {
    Optional<Department> findByName(String name);
}
