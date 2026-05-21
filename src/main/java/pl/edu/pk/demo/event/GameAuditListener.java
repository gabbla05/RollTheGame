package pl.edu.pk.demo.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class GameAuditListener {

    @EventListener
    public void handleGameCreatedEvent(GameCreatedEvent event) {
        System.out.println(">>> ASYNCHRONICZNY EVENT: Ktoś właśnie dodał nową grę! ID: " + event.gameId() + ", Tytuł: " + event.title());
    }
}