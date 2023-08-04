package hotel.com.backend.Kafka.Producers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import hotel.com.backend.Models.Response.TaskResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TaskProducer {
    @Qualifier("TaskProducerFactoryKafka")
    private final KafkaTemplate<String, TaskResponse> taskKafkaTemplate;

    public TaskProducer(KafkaTemplate<String, TaskResponse> taskKafkaTemplate) {
        this.taskKafkaTemplate = taskKafkaTemplate;
    }

    public TaskResponse sendNewTask(TaskResponse task) {
        log.info("new task sent " + task.toString());
        taskKafkaTemplate.send("task-new", task);
        return task;
    }

    public TaskResponse sendInProgressTask(TaskResponse task) {
        log.info("in-progress task sent " + task.toString());
        taskKafkaTemplate.send("task-inprogress", task);
        return task;
    }
    public TaskResponse sendCompletedTask(TaskResponse task) {
        log.info("completed task sent " + task.toString());
        taskKafkaTemplate.send("task-completed", task);
        return task;
    }

     public TaskResponse sendCancelledTask(TaskResponse task) {
        log.info("cancelled task sent " + task.toString());
        taskKafkaTemplate.send("task-cancelled", task);
        return task;
    }
}
