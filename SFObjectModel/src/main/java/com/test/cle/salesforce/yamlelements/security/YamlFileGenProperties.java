package com.test.cle.salesforce.yamlelements.security;

import com.sforce.soap.enterprise.EnterpriseConnection;

public class YamlFileGenProperties {

    public String region;
    public String type;
    public String etlEntity;
    public String owner;
    public String yamlFileRecords;
    public String yamlFileCSVStructure;
    public String yamlTestAccountsFile;
    public String yamlGeneratedFile;
    public String userRole;
    public EnterpriseConnection enterpriseConnection;

    public String getUserRole() {

        return userRole;
    }

    public void setUserRole(String userRole) {

        this.userRole = userRole;
    }

    public String getYamlTestAccountsFile() {

        return yamlTestAccountsFile;
    }

    public void setYamlTestAccountsFile(String yamlTestAccountsFile) {

        this.yamlTestAccountsFile = yamlTestAccountsFile;
    }

    public String getYamlGeneratedFile() {

        return yamlGeneratedFile;
    }

    public void setYamlGeneratedFile(String yamlGeneratedFile) {

        this.yamlGeneratedFile = yamlGeneratedFile;
    }

    public String getYamlFileCSVStructure() {

        return yamlFileCSVStructure;
    }

    public void setYamlFileCSVStructure(String yamlFileCSVStructure) {

        this.yamlFileCSVStructure = yamlFileCSVStructure;
    }

    public EnterpriseConnection getEnterpriseConnection() {

        return enterpriseConnection;
    }

    public void setEnterpriseConnection(EnterpriseConnection enterpriseConnection) {

        this.enterpriseConnection = enterpriseConnection;
    }

    public String getYamlFileRecords() {

        return yamlFileRecords;
    }

    public void setYamlFileRecords(String yamlFileRecords) {

        this.yamlFileRecords = yamlFileRecords;
    }

    public String getRegion() {

        return region;
    }

    public void setRegion(String region) {

        this.region = region;
    }

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

    public String getOwner() {

        return owner;
    }

    public void setOwner(String owner) {

        this.owner = owner;
    }
}
