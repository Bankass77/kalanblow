package ml.kalanblow.gestiondesinscriptions.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic eleveTopic() {
        return new NewTopic("gestions-des-eleves", 1, (short) 1);  // Nom du topic, nombre de partitions, réplicas
    }
}
