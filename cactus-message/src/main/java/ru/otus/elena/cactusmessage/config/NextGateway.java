package ru.otus.elena.cactusmessage.config;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.elena.cactusmessage.domain.CactusDto;

@MessagingGateway
public interface NextGateway {

    @SuppressWarnings("UnresolvedMessageChannel")
    @Gateway(requestChannel = "next.input", replyChannel = "rendez")
    CactusDto nextCactus();
}
