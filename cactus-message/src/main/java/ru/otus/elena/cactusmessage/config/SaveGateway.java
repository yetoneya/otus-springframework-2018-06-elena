
package ru.otus.elena.cactusmessage.config;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.elena.cactusmessage.domain.CactusDto;

@MessagingGateway
public interface SaveGateway {
    
    @SuppressWarnings("UnresolvedMessageChannel")
    @Gateway(requestChannel = "save.input", replyChannel= "directStr")
    String saveCactus(CactusDto cactus);
}
