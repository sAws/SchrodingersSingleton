package com.singletonsshredinger.transfer_service.model;

import java.util.List;
import java.util.Map;


public class Transfer {
    private String id;
    private String name;
    private String entity;
    private Integer status;
    private String type;
    private Map<String, Step> steps;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Step> getSteps() {
        return steps;
    }

    public void setSteps(Map<String, Step> steps) {
        this.steps = steps;
    }

    public static class Step {
        private Events events;
        private String service;

        public Events getEvents() {
            return events;
        }

        public void setEvents(Events events) {
            this.events = events;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public static class Events {
            private List<InEvent> in;
            private List<OutEvent> out;

            public List<InEvent> getIn() {
                return in;
            }

            public void setIn(List<InEvent> in) {
                this.in = in;
            }

            public List<OutEvent> getOut() {
                return out;
            }

            public void setOut(List<OutEvent> out) {
                this.out = out;
            }

            public static class InEvent {
                private String name;
                private String method;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getMethod() {
                    return method;
                }

                public void setMethod(String method) {
                    this.method = method;
                }
            }

            public static class OutEvent {
                private String name;
                private Integer status;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public Integer getStatus() {
                    return status;
                }

                public void setStatus(Integer status) {
                    this.status = status;
                }
            }
        }
    }
}

