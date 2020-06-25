package com.test.cle.salesforce.yamlelements.security;

public class PSObjectDef {

    String type;
    String read;
    String edit;
    String create;
    String delete;
    String viewAll;
    String modifyAll;

    public String getType() {

        return type;
    }

    public void setType(String type) {

        this.type = type;
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

    public String getCreate() {

        return create;
    }

    public void setCreate(String create) {

        this.create = create;
    }

    public String getDelete() {

        return delete;
    }

    public void setDelete(String delete) {

        this.delete = delete;
    }

    public String getViewAll() {

        return viewAll;
    }

    public void setViewAll(String viewAll) {

        this.viewAll = viewAll;
    }

    public String getModifyAll() {

        return modifyAll;
    }

    public void setModifyAll(String modifyAll) {

        this.modifyAll = modifyAll;
    }
}
