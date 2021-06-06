package com.singletonsshredinger.transfer_service.controller;

import com.singletonsshredinger.transfer_service.broker.EventProducer;
import com.singletonsshredinger.transfer_service.client.PipelineClient;
import com.singletonsshredinger.transfer_service.exceptions.AppException;
import com.singletonsshredinger.transfer_service.model.Pipeline;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/metric")
public class MetricResource {

    @Inject
    EventProducer producer;

    @Inject
    PipelineClient pipelineClient;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getMetrics(@QueryParam("year") Integer year, @QueryParam("title") String title) {
        try {
            List<Pipeline> allPipeLines = pipelineClient.getAllPipeLines();
        } catch (AppException e) {
            e.printStackTrace();
        }
        return Response.ok("TBD", MediaType.APPLICATION_JSON).build();
    }
}