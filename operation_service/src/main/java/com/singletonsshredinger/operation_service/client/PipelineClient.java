package com.singletonsshredinger.operation_service.client;

import com.singletonsshredinger.operation_service.exceptions.AppException;
import com.singletonsshredinger.operation_service.model.Pipeline;

import java.util.List;

public interface PipelineClient {
    List<Pipeline> getAllPipeLines() throws AppException;
}
