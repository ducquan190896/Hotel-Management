package hotel.com.backend.Service.Implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hotel.com.backend.Exception.BadResultException;
import hotel.com.backend.Exception.EntityExistingException;
import hotel.com.backend.Exception.EntityNotFoundException;
import hotel.com.backend.Models.Department;
import hotel.com.backend.Models.Users;
import hotel.com.backend.Models.Enums.Role;
import hotel.com.backend.Repository.DepartmentRepos;
import hotel.com.backend.Service.DepartmentService;
import hotel.com.backend.Service.UserService;

@Service
public class DepartmentServiceIml  implements DepartmentService{
    @Autowired
    UserService userService;
    @Autowired
    DepartmentRepos departmentRepos;

    @Override
    public Department create(String name) {
       Users authUsers = userService.getAuthUser();
       if(!authUsers.getRoles().contains(Role.MANAGER) || !authUsers.getRoles().contains(Role.ADMIN)) {
        throw new BadResultException("are not authorized to add new department");
       }

       Optional<Department> entity = departmentRepos.findByName(name);
       if(entity.isPresent()) {
        throw new EntityExistingException("the department exist");
       }
       Department department = new Department(name);
       return departmentRepos.save(department);

    }

    @Override
    public Department update(Long id, String name) {
        Users authUsers = userService.getAuthUser();
        if(!authUsers.getRoles().contains(Role.MANAGER) || !authUsers.getRoles().contains(Role.ADMIN)) {
            throw new BadResultException("are not authorized to update department");
        }

        Optional<Department> entity1 = departmentRepos.findById(id);
        if(!entity1.isPresent()) {
            throw new EntityNotFoundException("the department not found");
        }
        Department department = entity1.get();

        Optional<Department> entity = departmentRepos.findByName(name);
        if(entity.isPresent()) {
            throw new EntityExistingException("the department exist");
        }

        department.setName(name);
        return departmentRepos.save(department);
    }


}
