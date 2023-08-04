package hotel.com.backend.Kafka.Consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import hotel.com.backend.Models.Response.TaskMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageConsumer {

    private final SimpMessagingTemplate simpMessagingTemplate;   

    public MessageConsumer(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @KafkaListener(
        topics = "message-inprogress",
        groupId = "group-task-2",
        containerFactory = "messageKafkaListenerContainerFactory"
    )
    public void consumeInProgressMessage(TaskMessage message) {
        log.info("inprogress message received: " + message.toString());
        simpMessagingTemplate.convertAndSend("/tasks/inprogress", message);
    }

     @KafkaListener(
        topics = "message-new",
        groupId = "group-task-2",
        containerFactory = "messageKafkaListenerContainerFactory"
    )
    public void consumeNewMessage(TaskMessage message) {
        log.info("new message received: " + message.toString());
        simpMessagingTemplate.convertAndSend("/tasks/news", message);
    }
}
