package hotel.com.backend.Kafka.Producers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import hotel.com.backend.Models.Response.TaskMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageProducer {
    
    @Qualifier("MessageProducerFactoryKafka")
    private final KafkaTemplate<String, TaskMessage> messageKafkaTemplate;

    public MessageProducer(KafkaTemplate<String, TaskMessage> messageKafkaTemplate) {
        this.messageKafkaTemplate = messageKafkaTemplate;
    }


    public TaskMessage sendInProgressMessage(TaskMessage message) {
        log.info("in-progress message sent: " + message.toString());
        messageKafkaTemplate.send("message-inprogress", message);
        return message;
    }

    public TaskMessage sendNewMessage(TaskMessage message) {
        log.info("new message sent: " + message.toString());
        messageKafkaTemplate.send("message-new", message);
        return message;
    }

}
