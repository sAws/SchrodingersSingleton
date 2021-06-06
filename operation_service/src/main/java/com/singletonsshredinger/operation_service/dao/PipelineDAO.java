package com.singletonsshredinger.operation_service.dao;

import com.singletonsshredinger.operation_service.exceptions.AppException;
import com.singletonsshredinger.operation_service.model.Pipeline;

import java.util.List;

public interface PipelineDAO {
    List<Pipeline> findByEntity(String entity) throws AppException;

    List<Pipeline> findAll() throws AppException;
}
