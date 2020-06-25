package com.test.cle.salesforce.serenity.api.objects;

public class Campaign extends Record {

    private static final String CAMPAIGN_API_NAME = "Campaign";

    private static final String TYPE = "Type";
    private static final String NAME = "Name";
    private static final String IS_ACTIVE = "IsActive";
    private static final String WBI_NUMBER = "WBINumber__c";
    private static final String EVENT_CAPACITY = "EventCapacity__c";
    private static final String FADB_TRANSACTION = "FADbTransaction__c";
    private static final String EVENT_COMMON_NAME = "EventCommonName__c";

    private static final String DEFAULT_WBI_NUMBER = "P00000000";
    private static final String DEFAULT_FADB_TRANSACTION = "1904.917";

    public Campaign() {
        super(CAMPAIGN_API_NAME);
    }

    public Campaign withIsActive(boolean isActive) {
        fields.put(IS_ACTIVE, isActive);
        return this;
    }

    public Campaign withType(String type) {
        fields.put(TYPE, type);
        return this;
    }

    public Campaign withName(String name) {
        fields.put(NAME, name);
        return this;
    }

    public Campaign withCapacity(int capacity) {
        fields.put(EVENT_CAPACITY, capacity);
        return this;
    }

    public Campaign withEventCommonName(String eventCommonName) {
        fields.put(EVENT_COMMON_NAME, eventCommonName);
        return this;
    }

    public Campaign withFADbTransaction(String fadbTransaction) {
        fields.put(FADB_TRANSACTION, fadbTransaction);
        return this;
    }

    public Campaign withWBINumber(String wbiNumber) {
        fields.put(WBI_NUMBER, wbiNumber);
        return this;
    }

    public static Campaign getDefaultMinimal() {
        Campaign campaign = new Campaign()
                .withFADbTransaction(DEFAULT_FADB_TRANSACTION)
                .withWBINumber(DEFAULT_WBI_NUMBER);
        return campaign;
    }
}
