package com.test.cle.salesforce.yamlelements.security;

import java.util.List;

public class RecordElements {

    String lookupName;

    List<RecordColumnHeader> columnHeaders;
    List<RecordLookupWhereCondition> whereConditions;

    public String getLookupName() {

        return lookupName.trim();
    }

    public void setLookupName(String lookupName) {

        this.lookupName = lookupName;
    }

    public List<RecordColumnHeader> getColumnHeaders() {

        return columnHeaders;
    }

    public void setColumnHeaders(List<RecordColumnHeader> columnHeaders) {

        this.columnHeaders = columnHeaders;
    }

    public List<RecordLookupWhereCondition> getWhereConditions() {

        return whereConditions;
    }

    public void setWhereConditions(List<RecordLookupWhereCondition> whereConditions) {

        this.whereConditions = whereConditions;
    }
}
