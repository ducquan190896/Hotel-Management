package hotel.com.backend.Service;

import java.util.List;

import hotel.com.backend.Models.Department;

public interface DepartmentService {
    Department create(String name);
    Department update(Long id, String name);
    List<Department> getAll();
}
