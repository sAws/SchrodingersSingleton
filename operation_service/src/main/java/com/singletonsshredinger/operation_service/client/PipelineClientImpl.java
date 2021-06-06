package com.singletonsshredinger.operation_service.client;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.singletonsshredinger.operation_service.exceptions.AppException;
import com.singletonsshredinger.operation_service.model.Pipeline;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class PipelineClientImpl implements PipelineClient {
    // TODO Service Discovering: Consul, Eureka
    private final String URL = "http://localhost:8080";

    public List<Pipeline> getAllPipeLines() throws AppException {
        List<Pipeline> pipelines;

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(URL + "/api/pipelines");
        HttpEntity entity;
        CloseableHttpResponse response;
        try {
            response = httpclient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException("error execution query");
        }

        try {
            entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            Type listType = new TypeToken<ArrayList<Pipeline>>() {
            }.getType();
            pipelines = new Gson().fromJson(result, listType);
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException("error parsing entity");
        } finally {
            try {
                response.close();
            } catch (IOException ignored) {

            }
        }
        return pipelines;
    }
}