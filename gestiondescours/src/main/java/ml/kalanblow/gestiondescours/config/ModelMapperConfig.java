package ml.kalanblow.gestiondescours.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Ignorer les propriétés nulles
        modelMapper.getConfiguration().setPropertyCondition(context -> context.getSource() != null);

        // Utiliser une stratégie de correspondance stricte
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper;
    }
}