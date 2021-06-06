package com.singletonsshredinger.operation_service.services;

import com.singletonsshredinger.operation_service.dao.PipelineDAO;
import com.singletonsshredinger.operation_service.exceptions.AppException;
import com.singletonsshredinger.operation_service.exceptions.BadIdentifierException;
import com.singletonsshredinger.operation_service.model.Pipeline;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class OperationServiceImpl implements OperationService {

    @Inject
    protected PipelineDAO pipelineDAO;

    @Override
    public List<Pipeline> getPipelines() throws AppException {
        return pipelineDAO.findAll();
    }

    @Override
    public List<Pipeline> getPipelinesByEntity(String entity) throws AppException, BadIdentifierException {
        return pipelineDAO.findByEntity(entity);

    }
}
