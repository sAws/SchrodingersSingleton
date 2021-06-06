package com.singletonsshredinger.operation_service.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.singletonsshredinger.operation_service.exceptions.AppException;
import com.singletonsshredinger.operation_service.model.Pipeline;
import com.singletonsshredinger.operation_service.utils.ApplicationProperties;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class PipelineDAOImpl implements PipelineDAO {

    @Inject
    protected MongoClient mongoClient;

    @Override
    public List<Pipeline> findByEntity(String entity) throws AppException {
        BasicDBObject query = new BasicDBObject();
        query.put("entity", entity);

        List<Pipeline> list;
        try (MongoCursor<Document> cursor = getCollection().find(query).iterator()) {
            list = this.processCursor(cursor);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("error to find documents", e);
        }
        return list;
    }

    @Override
    public List<Pipeline> findAll() throws AppException {
        try (MongoCursor<Document> cursor = getCollection().find().iterator()) {
            return this.processCursor(cursor);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("error to find documents", e);
        }
    }

    private List<Pipeline> processCursor(MongoCursor<Document> cursor) {
        List<Pipeline> list = new ArrayList<>();

        while (cursor.hasNext()) {
            Document document = cursor.next();
            Pipeline pipeline = new Pipeline();
            pipeline.setId(((ObjectId) document.get("_id")).toString());
            pipeline.setName(document.getString("name"));
            pipeline.setEntity(document.getString("entity"));
            pipeline.setStatus(document.getInteger("status"));
            pipeline.setType(document.getString("type"));

            Map<String, Pipeline.Step> stepMap = new HashMap<>();
            Document steps = (Document) document.get("steps");
            for (Map.Entry<String, Object> entry : steps.entrySet()) {
                Pipeline.Step newStep = new Pipeline.Step();

                Document stepDoc = (Document) entry.getValue();
                Document events = (Document) stepDoc.get("events");

                List<Pipeline.Step.Events.InEvent> inEvents = new ArrayList<>();
                List<Document> in = events.getList("in", Document.class);
                for (Document d : in) {
                    String name = d.getString("name");
                    String method = d.getString("method");
                    Pipeline.Step.Events.InEvent inEvent = new Pipeline.Step.Events.InEvent();
                    inEvent.setName(name);
                    inEvent.setMethod(method);

                    inEvents.add(inEvent);
                }

                List<Pipeline.Step.Events.OutEvent> outEvents = new ArrayList<>();
                List<Document> out = events.getList("out", Document.class);
                for (Document d : out) {
                    String name = d.getString("name");
                    Integer status = d.getInteger("status");
                    Pipeline.Step.Events.OutEvent outEvent = new Pipeline.Step.Events.OutEvent();
                    outEvent.setName(name);
                    outEvent.setStatus(status);

                    outEvents.add(outEvent);
                }

                Pipeline.Step.Events newEvents = new Pipeline.Step.Events();
                newEvents.setIn(inEvents);
                newEvents.setOut(outEvents);
                newStep.setEvents(newEvents);
                newStep.setService(stepDoc.getString("service"));

                stepMap.put(entry.getKey(), newStep);
            }

            pipeline.setSteps(stepMap);
            list.add(pipeline);
        }

        return list;
    }

    private MongoCollection getCollection() {
        String database = ApplicationProperties.getInstance().getDatabase();
        String collection = ApplicationProperties.getInstance().getCollection();
        return mongoClient.getDatabase(database).getCollection(collection);
    }
}
