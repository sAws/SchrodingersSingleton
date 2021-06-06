package com.singletonsshredinger.category_service.services;

import com.singletonsshredinger.category_service.dao.OperationTypeDAO;
import com.singletonsshredinger.category_service.exceptions.AppException;
import com.singletonsshredinger.category_service.exceptions.BadIdentifierException;
import com.singletonsshredinger.category_service.exceptions.EntityNotFoundException;
import com.singletonsshredinger.category_service.model.OperationType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class OperationTypeServiceImpl implements OperationTypeService {

    @Inject
    protected OperationTypeDAO operationTypeDAO;

    @Override
    public List<OperationType> getOperationTypes() throws AppException {
        return operationTypeDAO.findAll();
    }

    @Override
    public OperationType getOperationTypeByID(String id) throws AppException, BadIdentifierException, EntityNotFoundException {
        return operationTypeDAO.findById(id);

    }
}
