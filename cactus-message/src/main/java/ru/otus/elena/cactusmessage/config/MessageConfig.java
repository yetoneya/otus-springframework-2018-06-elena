package ru.otus.elena.cactusmessage.config;

import java.io.File;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.BridgeFrom;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.NullChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import static org.springframework.integration.dsl.Pollers.trigger;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageChannel;
import ru.otus.elena.cactusmessage.service.ImageService;

@Configuration
@EnableIntegration
@IntegrationComponentScan
public class MessageConfig {

    @Autowired
    private ImageService imageService;

    @Bean
    public IntegrationFlow photos() {
        return f -> f
                .handle("imageService", "getCactusList")           
                .channel("directChannel");

    }

    @Bean
    public IntegrationFlow save() {
        return f -> f
                .handle("imageService", "saveCactus")
                .channel("directStr");
    }




    @Bean
    public MessageChannel directStr() {
        return MessageChannels.direct().datatype(String.class).get();
    }

    @Bean
    public MessageChannel directChannel() {
        return MessageChannels.direct().get();
    }


    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata defaultPoller() {
        return Pollers.fixedRate(500).maxMessagesPerPoll(1).get();
    }



}
