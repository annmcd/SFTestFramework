package com.test.cle.salesforce.serenity.stepdefinitions.common;

import com.sforce.soap.enterprise.EnterpriseConnection;

public class RecordAccessStruct {

    public EnterpriseConnection enterpriseConnection;
    public String yamlFileData;
    public String yamlFileCSVStructure;
    public String yamlFileSecuritySettings;
    public String yamlFileRecord;
    public String ownerUserName;
    public String accessorUserName;
    public String batchId;
    public String environment;
    public String region;
    public String userRole;

    public String getUserRole() {

        return userRole;
    }

    public void setUserRole(String userRole) {

        this.userRole = userRole;
    }

    public String getYamlFileRecord() {

        return yamlFileRecord;
    }

    public void setYamlFileRecord(String yamlFileRecord) {

        this.yamlFileRecord = yamlFileRecord;
    }

    public String getYamlFileData() {

        return yamlFileData;
    }

    public void setYamlFileData(String yamlFileData) {

        this.yamlFileData = yamlFileData;
    }

    public String getYamlFileCSVStructure() {

        return yamlFileCSVStructure;
    }

    public void setYamlFileCSVStructure(String yamlFileCSVStructure) {

        this.yamlFileCSVStructure = yamlFileCSVStructure;
    }

    public String getYamlFileSecuritySettings() {

        return yamlFileSecuritySettings;
    }

    public void setYamlFileSecuritySettings(String yamlFileSecuritySettings) {

        this.yamlFileSecuritySettings = yamlFileSecuritySettings;
    }

    public String getRegion() {

        return region;
    }

    public void setRegion(String region) {

        this.region = region;
    }

    public String getEnvironment() {

        return environment;
    }

    public void setEnvironment(String environment) {

        this.environment = environment;
    }

    public EnterpriseConnection getEnterpriseConnection() {

        return enterpriseConnection;
    }

    public void setEnterpriseConnection(EnterpriseConnection enterpriseConnection) {

        this.enterpriseConnection = enterpriseConnection;
    }

    public String getOwnerUserName() {

        return ownerUserName;
    }

    public void setOwnerUserName(String ownerUserName) {

        this.ownerUserName = ownerUserName;
    }

    public String getAccessorUserName() {

        return accessorUserName;
    }

    public void setAccessorUserName(String accessorUserName) {

        this.accessorUserName = accessorUserName;
    }

    public String getBatchId() {

        return batchId;
    }

    public void setBatchId(String batchId) {

        this.batchId = batchId;
    }
}
