package com.test.cle.salesforce.yamlelements.security;

public class RecordAccessObjectDef {

    public String job;
    public String MaxAccessLevel;
    public boolean HasAllAccess;
    public boolean HasDeleteAccess;
    public boolean HasEditAccess;
    public boolean HasReadAccess;
    public boolean HasTransferAccess;

    public String getJob() {

        return job;
    }

    public void setJob(String job) {

        this.job = job;
    }

    public String getMaxAccessLevel() {

        return MaxAccessLevel;
    }

    public void setMaxAccessLevel(String maxAccessLevel) {

        MaxAccessLevel = maxAccessLevel;
    }

    public boolean isHasAllAccess() {

        return HasAllAccess;
    }

    public void setHasAllAccess(boolean hasAllAccess) {

        HasAllAccess = hasAllAccess;
    }

    public boolean isHasDeleteAccess() {

        return HasDeleteAccess;
    }

    public void setHasDeleteAccess(boolean hasDeleteAccess) {

        HasDeleteAccess = hasDeleteAccess;
    }

    public boolean isHasEditAccess() {

        return HasEditAccess;
    }

    public void setHasEditAccess(boolean hasEditAccess) {

        HasEditAccess = hasEditAccess;
    }

    public boolean isHasReadAccess() {

        return HasReadAccess;
    }

    public void setHasReadAccess(boolean hasReadAccess) {

        HasReadAccess = hasReadAccess;
    }

    public boolean isHasTransferAccess() {

        return HasTransferAccess;
    }

    public void setHasTransferAccess(boolean hasTransferAccess) {

        HasTransferAccess = hasTransferAccess;
    }
}
