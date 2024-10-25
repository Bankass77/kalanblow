package ml.kalanblow.gestiondesinscriptions.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;

@EnableKafka
@Configuration
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.topic.gestions-des-eleves}")
    private String topic;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message, final String string) {
        kafkaTemplate.send(topic, message);
        System.out.println("Message envoyé au topic : " + message);
    }
}
