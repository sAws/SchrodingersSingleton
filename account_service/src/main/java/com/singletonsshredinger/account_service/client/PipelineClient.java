package com.singletonsshredinger.account_service.client;

import com.singletonsshredinger.account_service.exceptions.AppException;
import com.singletonsshredinger.account_service.model.Pipeline;

import java.util.List;

public interface PipelineClient {
    List<Pipeline> getAllPipeLines() throws AppException;
}
