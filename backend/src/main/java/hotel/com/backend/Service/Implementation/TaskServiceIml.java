package hotel.com.backend.Service.Implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import hotel.com.backend.Exception.BadResultException;
import hotel.com.backend.Exception.EntityNotFoundException;
import hotel.com.backend.Models.Department;
import hotel.com.backend.Models.Task;
import hotel.com.backend.Models.Users;
import hotel.com.backend.Models.Enums.Role;
import hotel.com.backend.Models.Request.TaskRequest;
import hotel.com.backend.Repository.DepartmentRepos;
import hotel.com.backend.Repository.TaskRepos;
import hotel.com.backend.Repository.UserRepos;
import hotel.com.backend.Service.TaskService;
import hotel.com.backend.Service.UserService;

@Service
public class TaskServiceIml implements TaskService{

    @Autowired
    TaskRepos taskRepos;
    @Autowired
    DepartmentRepos departmentRepos;
    @Autowired
    UserService userService;
    @Autowired
    UserRepos userRepos;
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public Task add(TaskRequest req) {
        Users authUser = userService.getAuthUser();
        Department department = getDepartment(req.getDepartment());
        Task task = new Task(req.getName(), req.getLocation(), req.getDescription(), department, authUser);
        if(req.isUrgent() == true) {
            task.setUrgent(true);
        }
        task = taskRepos.save(task);
        authUser.getTasksCreated().add(task);
        department.getTasks().add(task);
        departmentRepos.save(department);
        userRepos.save(authUser);
        simpMessagingTemplate.convertAndSend("/tasks", task);
        return task;
    }

    @Override
    public Task cancelTask(Long Id) {
        Users authUser = userService.getAuthUser();
        if(!authUser.getRoles().contains(Role.MANAGER) || !authUser.getRoles().contains(Role.ADMIN)) {
        throw new BadResultException("are not authorized to add new department");
       }
       Task task = checkTask(Id);
       task.setCancelled(true);
       task.setInProgress(false);
       task.setCompleted(false);
       task = taskRepos.save(task);
       simpMessagingTemplate.convertAndSend("/tasks", task);
       return task;
    }

    @Override
    public Task completeTask(Long id) {
        Users authUser = userService.getAuthUser();
        Task task = checkTask(id);
        if(!task.getCompletor().equals(authUser)) {
           throw new BadResultException("completor does not match the auth user"); 
        }
        task.setCompleted(true);
        task.setInProgress(false);
        task.setCancelled(false);
        task = taskRepos.save(task);
        simpMessagingTemplate.convertAndSend("/tasks", task);
        return task;
    }

    @Override
    public List<Task> getTasks() {
        List<Task> tasks = taskRepos.findAll();
        tasks.sort((a, b) -> b.getDateUpdated().compareTo(a.getDateUpdated()));
        return tasks;
    }

    @Override
    public Task takeChargeInTask(Long id) {
        Users authUser = userService.getAuthUser();
        Task task = checkTask(id);
        task.setCompletor(authUser);
        task.setInProgress(true);
        task.setCompleted(false);
        task.setCancelled(false);
        task = taskRepos.save(task);
        simpMessagingTemplate.convertAndSend("/tasks", task);
        return task;
    }
    
    private Department getDepartment(String name) {
        Optional<Department> entity = departmentRepos.findByName(name);
        if(!entity.isPresent()) {
            throw new EntityNotFoundException("the department not found");
        }
        return entity.get();
    }

    private Task checkTask(Long id) {
        Optional<Task> entity = taskRepos.findById(id);
        if(!entity.isPresent()) {
            throw new EntityNotFoundException("the task not found");
        }
        return entity.get();
    }
}
