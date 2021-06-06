package com.singletonsshredinger.account_service.dao;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.singletonsshredinger.account_service.model.Transfer;
import com.singletonsshredinger.account_service.utils.ApplicationProperties;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class TransferDAOImpl implements TransferDAO {

    @Inject
    protected MongoClient mongoClient;

    private List<Transfer> processCursor(MongoCursor<Document> cursor) {
        List<Transfer> list = new ArrayList<>();

        while (cursor.hasNext()) {
            Document document = cursor.next();
            Transfer transfer = new Transfer();
            transfer.setId(((ObjectId) document.get("_id")).toString());
            transfer.setName(document.getString("name"));
            transfer.setEntity(document.getString("entity"));
            transfer.setStatus(document.getInteger("status"));
            transfer.setType(document.getString("type"));

            Map<String, Transfer.Step> stepMap = new HashMap<>();
            Document steps = (Document) document.get("steps");
            for (Map.Entry<String, Object> entry : steps.entrySet()) {
                Transfer.Step newStep = new Transfer.Step();

                Document stepDoc = (Document) entry.getValue();
                Document events = (Document) stepDoc.get("events");

                List<Transfer.Step.Events.InEvent> inEvents = new ArrayList<>();
                List<Document> in = events.getList("in", Document.class);
                for (Document d : in) {
                    String name = d.getString("name");
                    String method = d.getString("method");
                    Transfer.Step.Events.InEvent inEvent = new Transfer.Step.Events.InEvent();
                    inEvent.setName(name);
                    inEvent.setMethod(method);

                    inEvents.add(inEvent);
                }

                List<Transfer.Step.Events.OutEvent> outEvents = new ArrayList<>();
                List<Document> out = events.getList("out", Document.class);
                for (Document d : out) {
                    String name = d.getString("name");
                    Integer status = d.getInteger("status");
                    Transfer.Step.Events.OutEvent outEvent = new Transfer.Step.Events.OutEvent();
                    outEvent.setName(name);
                    outEvent.setStatus(status);

                    outEvents.add(outEvent);
                }

                Transfer.Step.Events newEvents = new Transfer.Step.Events();
                newEvents.setIn(inEvents);
                newEvents.setOut(outEvents);
                newStep.setEvents(newEvents);
                newStep.setService(stepDoc.getString("service"));

                stepMap.put(entry.getKey(), newStep);
            }

            transfer.setSteps(stepMap);
            list.add(transfer);
        }

        return list;
    }

    private MongoCollection getCollection() {
        String database = ApplicationProperties.getInstance().getDatabase();
        String collection = ApplicationProperties.getInstance().getCollection();
        return mongoClient.getDatabase(database).getCollection(collection);
    }
}
