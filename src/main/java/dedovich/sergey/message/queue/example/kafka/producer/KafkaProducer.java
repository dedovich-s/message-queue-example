package dedovich.sergey.message.queue.example.kafka.producer;

import dedovich.sergey.message.queue.example.kafka.model.DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, DTO> greetingKafkaTemplate;

    @Value(value = "${message.topic.name}")
    private String topicName;

    @Value(value = "${partitioned.topic.name}")
    private String partionedTopicName;

    @Value(value = "${filtered.topic.name}")
    private String filteredTopicName;

    @Value(value = "${DTO.topic.name}")
    private String dtoTopicName;

    public void sendMessage(String message) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
            }
        });
    }

    public void sendMessageToPartition(String message, int partition) {
        kafkaTemplate.send(partionedTopicName, partition, null, message);
    }

    public void sendMessageToFiltered(String message) {
        kafkaTemplate.send(filteredTopicName, message);
    }

    public void sendDtoMessage(DTO dto) {
        greetingKafkaTemplate.send(dtoTopicName, dto);
    }
}
