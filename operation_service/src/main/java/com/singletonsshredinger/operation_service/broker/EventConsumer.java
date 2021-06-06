package com.singletonsshredinger.operation_service.broker;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.singletonsshredinger.operation_service.client.PipelineClient;
import com.singletonsshredinger.operation_service.exceptions.AppException;
import com.singletonsshredinger.operation_service.model.ApplicationEventsMap;
import com.singletonsshredinger.operation_service.model.Event;
import com.singletonsshredinger.operation_service.model.Pipeline;
import com.singletonsshredinger.operation_service.services.OperationService;
import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class EventConsumer {
    private final Logger LOG = Logger.getLogger(EventConsumer.class);

    @Inject
    OperationService service;

    @Inject
    EventProducer producer;

    @Inject
    PipelineClient pipelineClient;


    @Incoming("test-in")
    public void receive(Record<Integer, String> record) {
        LOG.infof("Got a event: %s - %s", record.key(), record.value());
        process(record);
    }

    public void process(Record<Integer, String> record) {
        Event event = parseEvent(record.value());
        if (isMyEvent(event)) {
            doEventMethod(event);
        }
    }

    public Event parseEvent(String event) {
        try {
            return new Gson().fromJson(event, Event.class);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    public boolean isMyEvent(Event event) {
        if (event == null) {
            return false;
        }
        Map<String, ApplicationEventsMap.Out> map = getApplicationEventsList();
        String eventType = event.getType();
        // TODO what about comparing entity here?
        return map.containsKey(eventType);
    }

    public Map<String, ApplicationEventsMap.Out> getApplicationEventsList() {
        // TODO make it on service startup and cache
        List<Pipeline> allPipeLines;
        try {
            allPipeLines = pipelineClient.getAllPipeLines();
        } catch (AppException e) {
            e.printStackTrace();
            return null;
        }

        allPipeLines = allPipeLines.stream()
                .filter(p -> p.getEntity().equals("transfer"))
                .collect(Collectors.toList());

        Map<String, String> mapMethodType = new HashMap<>();
        allPipeLines.forEach(pipeline -> {
            for (Map.Entry<String, Pipeline.Step> entry : pipeline.getSteps().entrySet().stream()
                    .filter(e -> e.getValue().getService().equals("TransferService"))
                    .collect(Collectors.toList())) {
                entry.getValue().getEvents().getIn().forEach((inEvent -> {
                    mapMethodType.put(inEvent.getName(), inEvent.getMethod());
                }));
            }
        });
        // TODO this hardcode because не успел :-) так-то выше собраны все типы эвентов которые надо слушать и методы которые надо вызывать
        ApplicationEventsMap map = new ApplicationEventsMap();
        ApplicationEventsMap.Out out = new ApplicationEventsMap.Out();
        out.outEvent = "transfer_account_validation";
        out.method = "create_transfer";

        map.listen = new HashMap<>();
        map.listen.put("transfer_operation_created", out);
        return map.listen;
    }

    public void doEventMethod(Event event) {
        Map<String, ApplicationEventsMap.Out> map = getApplicationEventsList();
        ApplicationEventsMap.Out outMeta = map.get(event.getType());
        // TODO this is dumb code, it should be reflection
        switch (event.getType()) {
            case "transfer_account_validation":
                service.updateStatus(8);
                break;
            case "transfer_account_validated":
                service.updateStatus(9);
                break;
            case "transfer_done":
                service.finishOperation("123");
                break;
        }

        String outEventType = outMeta.outEvent;
        Event outEvent = new Event();
        outEvent.setEntity("transfer");
        outEvent.setType(outEventType);
        outEvent.setId(UUID.randomUUID().toString());
        outEvent.setData("{\"field\":\"my custom data\"}");

        sendOutEvent(outEvent);
    }

    public void sendOutEvent(Event event) {
        producer.sendEventToKafka(event);
    }
}