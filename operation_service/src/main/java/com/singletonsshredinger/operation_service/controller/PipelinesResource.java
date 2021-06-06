package com.singletonsshredinger.operation_service.controller;

import com.singletonsshredinger.operation_service.exceptions.AppException;
import com.singletonsshredinger.operation_service.services.OperationService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/pipelines")
public class PipelinesResource {

    @Inject
    protected OperationService operationService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getPipelines(@QueryParam("entity") String entity) {
        try {
            if (entity != null) {
                return Response.ok(operationService.getPipelinesByEntity(entity), MediaType.APPLICATION_JSON).build();
            }
            return Response.ok(operationService.getPipelines(), MediaType.APPLICATION_JSON).build();
        } catch (AppException e) {
            return Response.serverError().build();
        }
    }
}