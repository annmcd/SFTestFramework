package com.test.cle.salesforce.yamlelements.security;

public class ProfileFieldDef {

    String type;
    String fieldName;
    String read;
    String edit;

    public String getType() {

        return type;
    }

    public void setType(String type) {

        this.type = type;
    }

    public String getFieldName() {

        return fieldName;
    }

    public void setFieldName(String fieldName) {

        this.fieldName = fieldName;
    }

    public String getRead() {

        return read;
    }

    public void setRead(String read) {

        this.read = read;
    }

    public String getEdit() {

        return edit;
    }

    public void setEdit(String edit) {

        this.edit = edit;
    }
}
