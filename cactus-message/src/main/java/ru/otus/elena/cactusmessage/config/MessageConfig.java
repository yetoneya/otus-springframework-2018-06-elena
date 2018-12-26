package ru.otus.elena.cactusmessage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageChannel;
import ru.otus.elena.cactusmessage.domain.Cactus;
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
                .split()
                .filter(imageService, "isImage")
                .aggregate()
                .channel("directChannel");
    }

    @Bean
    public IntegrationFlow save() {
        return f -> f
                .handle("imageService", "toCactus")
                .<Cactus, Boolean>route(cactus -> cactus != null, mapping -> mapping
                .subFlowMapping(true, sf -> sf
                .handle("imageService", "saveCactus")
                .channel("directStr"))
                .subFlowMapping(false, sf ->sf.<String,String>transform(imageService, "fromNull")
                .channel("directStr")));
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
