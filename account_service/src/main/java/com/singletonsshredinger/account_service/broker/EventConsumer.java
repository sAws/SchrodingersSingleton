package com.singletonsshredinger.account_service.broker;

import com.google.gson.Gson;
import com.singletonsshredinger.account_service.client.PipelineClient;
import com.singletonsshredinger.account_service.model.ApplicationEventsMap;
import com.singletonsshredinger.account_service.model.Event;
import com.singletonsshredinger.account_service.services.AccountServiceImpl;
import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.*;

@ApplicationScoped
public class EventConsumer {
    private final Logger LOG = Logger.getLogger(EventConsumer.class);

    @Inject
    AccountServiceImpl service;

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
        if (event != null && isMyEvent(event)) {
            doEventMethod(event);
        }
    }

    public Event parseEvent(String event) {
        Event ev = null;
        try {
            ev = new Gson().fromJson(event, Event.class);
        } catch (com.google.gson.JsonSyntaxException e) {
            e.printStackTrace();
        }
        return ev;
    }

    public boolean isMyEvent(Event event) {
        Map<String, ApplicationEventsMap.Out> map = getApplicationEventsList();
        String eventType = event.getType();
        // TODO what about comparing entity here?
        return map.containsKey(eventType);
    }

    public Map<String, ApplicationEventsMap.Out> getApplicationEventsList() {
        // TODO make it on service startup and cache
//        List<Pipeline> allPipeLines;
//        try {
//            allPipeLines = pipelineClient.getAllPipeLines();
//        } catch (AppException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//        allPipeLines = allPipeLines.stream()
//                .filter(p -> p.getEntity().equals("transfer"))
//                .filter(p -> p.getSteps().values().stream().anyMatch(s -> s.getService().equals("TransferService")))
//                .collect(Collectors.toList());
//
//        Map<String, String> mapMethodType = new HashMap<>();
//        allPipeLines.forEach(pipeline -> {
//            for (Map.Entry<String, Pipeline.Step> entry : pipeline.getSteps().entrySet().stream()
//                    .filter(e -> e.getValue().getService().equals("TransferService"))
//                    .collect(Collectors.toList())) {
//                entry.getValue().getEvents().getIn().forEach((inEvent -> {
//                    mapMethodType.put(inEvent.getName(), inEvent.getMethod());
//                }));
//            }
//        });
        // TODO this hardcode because не успел :-) так-то выше собраны все типы эвентов которые надо слушать и методы которые надо вызывать
        ApplicationEventsMap map = new ApplicationEventsMap();
        ApplicationEventsMap.Out out = new ApplicationEventsMap.Out();
        out.outEvent = "transfer_account_validated";
        out.method = "validate";

        map.listen = new HashMap<>();
        map.listen.put("transfer_account_validation", out);
        return map.listen;
    }

    public void doEventMethod(Event event) {
        Map<String, ApplicationEventsMap.Out> map = getApplicationEventsList();
        ApplicationEventsMap.Out outMeta = map.get(event.getType());
        // TODO this is dumb code, it should be reflection
        if (outMeta.method.equals("validate")) {
            service.validate();
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