package com.test.cle.salesforce.serenity.api.objects;

import org.json.JSONObject;

public abstract class Record {

    protected static String apiName;

    protected JSONObject fields;

    public Record(String apiName) {
        this.fields = new JSONObject();
        this.apiName = apiName;
    }

    public JSONObject getFields() {
        return fields;
    }

    public static String getApiName() {
        return apiName;
    }

}
