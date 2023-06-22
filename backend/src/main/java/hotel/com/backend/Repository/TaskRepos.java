package hotel.com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hotel.com.backend.Models.Department;
import hotel.com.backend.Models.Task;
import hotel.com.backend.Models.Users;

@Repository
public interface TaskRepos extends JpaRepository<Task, Long> {
    List<Task> findByDepartment(Department department);
    List<Task> findByCreator(Users creator);
    List<Task> findByCompletor(Users completor);
}
