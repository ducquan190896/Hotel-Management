package hotel.com.backend.Service;

import hotel.com.backend.Models.Department;

public interface DepartmentService {
    Department create(String name);
    Department update(Long id, String name);
}
