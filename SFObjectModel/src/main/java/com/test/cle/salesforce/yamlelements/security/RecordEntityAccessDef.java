package com.test.cle.salesforce.yamlelements.security;

import java.util.List;

public class RecordEntityAccessDef {

    public String type;
    public String etlEntity;
    // public String parentRelationship;
    List<RecordAccessObjectDef> accessSettings;

    public String getType() {

        return type;
    }

    public void setType(String type) {

        this.type = type;
    }

    public String getEtlEntity() {

        return etlEntity;
    }

    public void setEtlEntity(String etlEntity) {

        this.etlEntity = etlEntity;
    }

    public List<RecordAccessObjectDef> getAccessSettings() {

        return accessSettings;
    }

    public void setAccessSettings(List<RecordAccessObjectDef> accessSettings) {

        this.accessSettings = accessSettings;
    }
}
