package com.singletonsshredinger.category_service.dao;

import com.singletonsshredinger.category_service.exceptions.AppException;
import com.singletonsshredinger.category_service.exceptions.BadIdentifierException;
import com.singletonsshredinger.category_service.exceptions.EntityNotFoundException;
import com.singletonsshredinger.category_service.model.OperationType;

import java.util.List;

public interface OperationTypeDAO {
    OperationType findById(String id) throws AppException, BadIdentifierException, EntityNotFoundException;
    List<OperationType> findAll() throws AppException;
}
