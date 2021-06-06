package com.singletonsshredinger.operation_service.model;

import java.util.Map;

public class ApplicationEventsMap {
    public Map<String, Out> listen;
    public static class Out {
        public String outEvent;
        public String method;
    }
}
