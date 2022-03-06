package com.vector.services;

import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

public interface Command {
    Mono<? extends Object> execute(MessageCreateEvent event);
}
