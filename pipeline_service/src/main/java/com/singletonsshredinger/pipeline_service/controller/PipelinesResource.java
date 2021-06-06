package com.singletonsshredinger.pipeline_service.controller;

import com.singletonsshredinger.pipeline_service.exceptions.AppException;
import com.singletonsshredinger.pipeline_service.services.PipelineService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/pipelines")
public class PipelinesResource {

    @Inject
    protected PipelineService pipelineService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getPipelines(@QueryParam("entity") String entity) {
        try {
            if (entity != null) {
                return Response.ok(pipelineService.getPipelinesByEntity(entity), MediaType.APPLICATION_JSON).build();
            }
            return Response.ok(pipelineService.getPipelines(), MediaType.APPLICATION_JSON).build();
        } catch (AppException e) {
            return Response.serverError().build();
        }
    }
}