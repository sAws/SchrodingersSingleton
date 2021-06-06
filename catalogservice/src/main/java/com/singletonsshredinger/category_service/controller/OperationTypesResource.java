package com.singletonsshredinger.category_service.controller;

import com.singletonsshredinger.category_service.exceptions.AppException;
import com.singletonsshredinger.category_service.exceptions.BadIdentifierException;
import com.singletonsshredinger.category_service.services.OperationTypeService;
import com.singletonsshredinger.category_service.exceptions.EntityNotFoundException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/operation_types")
public class OperationTypesResource {

    @Inject
    protected OperationTypeService operationTypeService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getOperationTypes() {
        try {
            return Response.ok(operationTypeService.getOperationTypes(), MediaType.APPLICATION_JSON).build();
        } catch (AppException e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getOperationTypeByID(@PathParam("id") String id) {
        try {
            return Response.ok(operationTypeService.getOperationTypeByID(id), MediaType.APPLICATION_JSON).build();
        } catch (AppException e) {
            return Response.serverError().build();
        } catch (EntityNotFoundException e) {
            throw new NotFoundException();
        } catch (BadIdentifierException e) {
            throw new BadRequestException();
        }
    }
}