package com.singletonsshredinger.category_service.model;

import java.util.List;


public class OperationType {
    private String id;
    private String entity;
    private String name;
    private List<String> acls = null;
    private List<String> categories = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAcls() {
        return acls;
    }

    public void setAcls(List<String> acls) {
        this.acls = acls;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

}