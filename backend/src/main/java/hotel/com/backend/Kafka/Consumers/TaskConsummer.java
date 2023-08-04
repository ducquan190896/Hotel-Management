package hotel.com.backend.Kafka.Consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import hotel.com.backend.Models.Response.TaskResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TaskConsummer {

    
    private final SimpMessagingTemplate simpMessagingTemplate;

    
    
    public TaskConsummer(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @KafkaListener(
        topics = "task-new",
        groupId = "group-task-1",
        containerFactory = "taskKafkaListenerContainerFactory"
    )
    public void consumeNewTask(TaskResponse task) {
        log.info("new task received: " + task.toString());
        simpMessagingTemplate.convertAndSend("/tasks/news", task);
    }

    @KafkaListener(
        topics = "task-inprogress",
        groupId = "group-task-1",
        containerFactory = "taskKafkaListenerContainerFactory"
    )
    public void consumeInprogressTask(TaskResponse task) {
        log.info("in-progress task received: " + task.toString());
        simpMessagingTemplate.convertAndSend("/tasks/inprogress", task);
    }

    @KafkaListener(
        topics = "task-completed",
        groupId = "group-task-1",
        containerFactory = "taskKafkaListenerContainerFactory"
    )
    public void consumeCompletedTask(TaskResponse task) {
        log.info("completed task received: " + task.toString());
        simpMessagingTemplate.convertAndSend("/tasks/completed", task);
    }

    @KafkaListener(
        topics = "task-cancelled",
        groupId = "group-task-1",
        containerFactory = "taskKafkaListenerContainerFactory"
    )
    public void consumeCancelledTask(TaskResponse task) {
        log.info("cancelled task received: " + task.toString());
        simpMessagingTemplate.convertAndSend("/tasks/cancelled", task);
    }
}
