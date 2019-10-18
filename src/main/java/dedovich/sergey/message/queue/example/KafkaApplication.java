package dedovich.sergey.message.queue.example;

import dedovich.sergey.message.queue.example.kafka.model.DTO;
import dedovich.sergey.message.queue.example.kafka.producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
@EnableScheduling
@SpringBootApplication
public class KafkaApplication {
    private final KafkaProducer kafkaProducer;

    @Scheduled(fixedDelay = 5000)
    public void produce() {
        kafkaProducer.sendDtoMessage(new DTO("Payload"));
        kafkaProducer.sendMessage("Simple message");
        kafkaProducer.sendMessageToFiltered("FILTER_ME");
        kafkaProducer.sendMessageToPartition("Message for partition", 0);
    }

    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class);
    }
}
