package com.singletonsshredinger.transfer_service.broker;

import com.singletonsshredinger.transfer_service.model.Event;
import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.google.gson.Gson;

@ApplicationScoped
public class EventProducer {

    @Inject
    @Channel("test-out")
    Emitter<Record<String, String>> emitter;

    public void sendEventToKafka(Event event) {
        Gson gson = new Gson();
        String jsonEvent = gson.toJson(event);

        emitter.send(Record.of(event.getId(), jsonEvent));
    }
}