package com.singletonsshredinger.category_service.services;

import com.singletonsshredinger.category_service.exceptions.AppException;
import com.singletonsshredinger.category_service.exceptions.BadIdentifierException;
import com.singletonsshredinger.category_service.exceptions.EntityNotFoundException;
import com.singletonsshredinger.category_service.model.OperationType;

import java.util.List;

public interface OperationTypeService {
    List<OperationType> getOperationTypes() throws AppException;
    OperationType getOperationTypeByID(String id) throws AppException, BadIdentifierException, EntityNotFoundException;

}
