package com.singletonsshredinger.category_service.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.singletonsshredinger.category_service.exceptions.AppException;
import com.singletonsshredinger.category_service.exceptions.EntityNotFoundException;
import com.singletonsshredinger.category_service.exceptions.BadIdentifierException;
import com.singletonsshredinger.category_service.model.OperationType;
import com.singletonsshredinger.category_service.utils.ApplicationProperties;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class OperationTypeDAOImpl implements OperationTypeDAO {

    @Inject
    protected MongoClient mongoClient;

    @Override
    public OperationType findById(String id) throws AppException, BadIdentifierException, EntityNotFoundException {
        BasicDBObject query = new BasicDBObject();
        try {
            query.put("_id", new ObjectId(id));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new BadIdentifierException("bad id");
        }
        List<OperationType> list;
        try (MongoCursor<Document> cursor = getCollection().find(query).iterator()) {
            list = this.processCursor(cursor);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("error to find documents", e);
        }
        if (list.size() > 1) {
            throw new AppException("found more than 1 document");
        } else if (list.isEmpty()) {
            throw new EntityNotFoundException("no documents found");
        }
        return list.get(0);
    }

    @Override
    public List<OperationType> findAll() throws AppException {
        try (MongoCursor<Document> cursor = getCollection().find().iterator()) {
            return this.processCursor(cursor);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("error to find documents", e);
        }
    }

    private List<OperationType> processCursor(MongoCursor<Document> cursor) {
        List<OperationType> list = new ArrayList<>();

        while (cursor.hasNext()) {
            Document document = cursor.next();
            OperationType operationType = new OperationType();
            operationType.setId(((ObjectId) document.get("_id")).toString());
            operationType.setName(document.getString("name"));
            operationType.setEntity(document.getString("entity"));
            operationType.setCategories(document.getList("categories", String.class));
            operationType.setAcls(document.getList("acls", String.class));
            list.add(operationType);
        }

        return list;
    }

    private MongoCollection getCollection(){
        String database = ApplicationProperties.getInstance().getDatabase();
        String collection = ApplicationProperties.getInstance().getCollection();
        return mongoClient.getDatabase(database).getCollection(collection);
    }
}
