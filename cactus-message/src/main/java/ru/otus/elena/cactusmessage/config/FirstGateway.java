package ru.otus.elena.cactusmessage.config;

import java.util.List;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.elena.cactusmessage.domain.CactusDto;

@MessagingGateway
public interface FirstGateway {

    @SuppressWarnings("UnresolvedMessageChannel")
    @Gateway(requestChannel = "photos.input", replyChannel= "directChannel")
    List<CactusDto> photos(String selection);
}
