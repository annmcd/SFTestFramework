package com.test.cle.salesforce.serenity.api.objects;

public class Lead extends Record {

    private static final String LEAD_API_NAME = "Lead";

    private static final String LAST_NAME = "LastName";
    private static final String VVID_NUMBER = "VVIDNumber__c";

    private static final String DEFAULT_LAST_NAME = "Smith";

    public Lead() {
        super(LEAD_API_NAME);
    }

    public Lead withVVIDNumber(String vvidNumber) {
        fields.put(VVID_NUMBER, vvidNumber);
        return this;
    }


    public Lead withLastName(String lastName) {
        fields.put(LAST_NAME, lastName);
        return this;
    }

    public Lead removeLastName() {
        fields.remove(LAST_NAME);
        return this;
    }

    public static Lead getDefaultMinimal() {
        Lead lead = new Lead()
                .withLastName(DEFAULT_LAST_NAME);
        return lead;
    }
}
