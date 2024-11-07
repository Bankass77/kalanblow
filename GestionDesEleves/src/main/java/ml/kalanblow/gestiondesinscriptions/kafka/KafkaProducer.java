package ml.kalanblow.gestiondesinscriptions.kafka;

import java.net.SocketTimeoutException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;

@EnableKafka
@Configuration
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.topic.gestions-des-eleves}")
    private String topic;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @RetryableTopic(
            backoff = @Backoff(value = 3000L),
            attempts = "5",
            autoCreateTopics = "false",
            include = SocketTimeoutException.class, exclude = NullPointerException.class)
    public void sendMessage(String message, final String string) {
        kafkaTemplate.send(topic, message);
        System.out.println("Message envoyé au topic : " + message);
    }
}
