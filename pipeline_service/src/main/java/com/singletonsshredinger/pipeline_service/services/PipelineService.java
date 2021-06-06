package com.singletonsshredinger.pipeline_service.services;

import com.singletonsshredinger.pipeline_service.exceptions.AppException;
import com.singletonsshredinger.pipeline_service.exceptions.BadIdentifierException;
import com.singletonsshredinger.pipeline_service.model.Pipeline;

import java.util.List;

public interface PipelineService {
    List<Pipeline> getPipelines() throws AppException;

    List<Pipeline> getPipelinesByEntity(String id) throws AppException, BadIdentifierException;

}
