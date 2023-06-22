package hotel.com.backend.Service;

import java.util.List;

import hotel.com.backend.Models.Task;
import hotel.com.backend.Models.Request.TaskRequest;

public interface TaskService {
    Task add(TaskRequest req);
    Task completeTask(Long id);
    Task cancelTask(Long Id);
    Task takeChargeInTask(Long id);
    List<Task> getTasks();
}
