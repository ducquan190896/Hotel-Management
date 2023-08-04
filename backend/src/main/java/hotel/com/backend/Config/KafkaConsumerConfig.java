package hotel.com.backend.Config;
import java.util.HashMap;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import hotel.com.backend.Models.Response.TaskMessage;
import hotel.com.backend.Models.Response.TaskResponse;

import java.util.HashMap;
import java.util.Map;


@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    private static final String BOOTSTRAP_SERVERS = "localhost:9092,localhost:9093,localhost:9094";
    private static final String consumerGroup = "group-task-1";
    private static final String consumerGroup2 = "group-task-2";

   @Bean
   public ConsumerFactory<String, TaskResponse> taskConsumerFactory() {
       Map<String, Object> configs = new HashMap<>();

       configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
       configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
       configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
       configs.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup);
       configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

       return new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), new JsonDeserializer<>(TaskResponse.class));
   }

   @Bean
   public ConcurrentKafkaListenerContainerFactory<String, TaskResponse> taskKafkaListenerContainerFactory() {
       ConcurrentKafkaListenerContainerFactory<String, TaskResponse> factory = new ConcurrentKafkaListenerContainerFactory<>();
       factory.setConsumerFactory(taskConsumerFactory());

       return factory;
   }

   @Bean
   public ConsumerFactory<String, TaskMessage> messageConsumerFactory() {
       Map<String, Object> configs = new HashMap<>();

       configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
       configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
       configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
       configs.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup2);
       configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

       return new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), new JsonDeserializer<>(TaskMessage.class));
   }

   @Bean
   public ConcurrentKafkaListenerContainerFactory<String, TaskMessage> messageKafkaListenerContainerFactory() {
       ConcurrentKafkaListenerContainerFactory<String, TaskMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
       factory.setConsumerFactory(messageConsumerFactory());

       return factory;
   }
}
