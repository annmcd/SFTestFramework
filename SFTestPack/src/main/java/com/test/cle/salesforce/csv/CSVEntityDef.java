package com.test.cle.salesforce.csv;

import java.util.List;

public class CSVEntityDef {

    public String etlEntity;
    public String type;
    public String csv;
    public String header;
    public List<CSVRegion> regionList;
    public List<CSVUserRole> userRoleList;

    public List<CSVUserRole> getUserRoleList() {

        return userRoleList;
    }

    public void setUserRoleList(List<CSVUserRole> userRoleList) {

        this.userRoleList = userRoleList;
    }

    public List<CSVRegion> getRegionList() {

        return regionList;
    }

    public void setRegionList(List<CSVRegion> regionList) {

        this.regionList = regionList;
    }

    public String getEtlEntity() {

        return etlEntity;
    }

    public void setEtlEntity(String etlEntity) {

        this.etlEntity = etlEntity;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {

        this.type = type;
    }

    public String getCsv() {

        return csv;
    }

    public void setCsv(String csv) {

        this.csv = csv;
    }

    public String getHeader() {

        return header;
    }

    public void setHeader(String header) {

        this.header = header;
    }
}
