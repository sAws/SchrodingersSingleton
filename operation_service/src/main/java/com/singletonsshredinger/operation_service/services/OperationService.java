package com.singletonsshredinger.operation_service.services;

import com.singletonsshredinger.operation_service.exceptions.AppException;
import com.singletonsshredinger.operation_service.exceptions.BadIdentifierException;
import com.singletonsshredinger.operation_service.model.Pipeline;

import java.util.List;

public interface OperationService {
    void updateStatus(Integer status);
    void finishOperation(String id);
}

