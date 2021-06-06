package com.singletonsshredinger.transfer_service.client;

import com.singletonsshredinger.transfer_service.exceptions.AppException;
import com.singletonsshredinger.transfer_service.model.Pipeline;

import java.util.List;

public interface PipelineClient {
    List<Pipeline> getAllPipeLines() throws AppException;
}
