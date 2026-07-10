package com.v2nhung.bank.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authorization.event.AuthorizationEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthorizationEvents {

    @EventListener
    public void onFailure(AuthorizationEvent event) {
        log.error("Authorization failed for the user : {} due to : {}",
                event.getAuthentication().get().getName(), event.getAuthorizationResult());
    }
}
