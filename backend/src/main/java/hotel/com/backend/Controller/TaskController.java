package hotel.com.backend.Controller;



import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hotel.com.backend.Config.SocketService;
import hotel.com.backend.Kafka.Producers.MessageProducer;
import hotel.com.backend.Kafka.Producers.TaskProducer;
import hotel.com.backend.Mapper.TaskMapper;
import hotel.com.backend.Models.Task;
import hotel.com.backend.Models.Request.PickTaskRequest;
import hotel.com.backend.Models.Request.TaskRequest;
import hotel.com.backend.Models.Response.TaskMessage;
import hotel.com.backend.Models.Response.TaskResponse;
import hotel.com.backend.Service.TaskService;
import jakarta.validation.Valid;

@RequestMapping("/api/tasks")
@RestController
public class TaskController {
    @Autowired
    TaskService taskService;
    @Autowired
    TaskMapper taskMapper;
     @Autowired
    SocketService socketService;
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    TaskProducer taskProducer;
    @Autowired
    MessageProducer messageProducer;

    @PostMapping("/task")
    public ResponseEntity<TaskResponse> add(@Valid @RequestBody TaskRequest req) {
        TaskResponse res = taskMapper.mapTaskResponse(taskService.add(req));
        taskProducer.sendNewTask(res);
        return new ResponseEntity<TaskResponse>(res, HttpStatus.CREATED);
    }
    @PutMapping("/task/{id}/complete")
    public ResponseEntity<TaskResponse> complete(@PathVariable Long id) {
        TaskResponse res = taskMapper.mapTaskResponse(taskService.completeTask(id));
        taskProducer.sendCompletedTask(res);
        TaskMessage message = new TaskMessage(id, "Complete");
        messageProducer.sendInProgressMessage(message);
        return new ResponseEntity<TaskResponse>(res, HttpStatus.OK);
    }
    @PutMapping("/task/{id}/cancel")
    public ResponseEntity<TaskResponse> cancel(@PathVariable Long id) {
        TaskResponse res = taskMapper.mapTaskResponse(taskService.cancelTask(id));
        taskProducer.sendCancelledTask(res);
        TaskMessage message = new TaskMessage(id, "Cancel");
        messageProducer.sendInProgressMessage(message);
        return new ResponseEntity<TaskResponse>(res, HttpStatus.OK);
    }
    @PutMapping("/task/{id}/handleTask")
    public ResponseEntity<TaskResponse> handleTask(@PathVariable Long id) {
        TaskResponse res = taskMapper.mapTaskResponse(taskService.takeChargeInTask(id));
        taskProducer.sendInProgressTask(res);
        TaskMessage message = new TaskMessage(id, "InProgress");
        messageProducer.sendNewMessage(message);
        return new ResponseEntity<TaskResponse>(res, HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<TaskResponse>> getAll() {
        List<Task> list = taskService.getTasks();
        List<TaskResponse> res = list.stream().map(task -> taskMapper.mapTaskResponse(task)).collect(Collectors.toList());
        return new ResponseEntity<List<TaskResponse>>(res, HttpStatus.OK);
    }
}
