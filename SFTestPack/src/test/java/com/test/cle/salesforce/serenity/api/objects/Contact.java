package com.test.cle.salesforce.serenity.api.objects;

public class Contact extends Record {

    private static final String CONTACT_API_NAME = "Contact";

    private static final String LAST_NAME = "LastName";
    private static final String SALUTATION = "Salutation";
    private static final String VVID_NUMBER = "VVIDNumber__c";

    private static final String DEFAULT_SALUTATION = "Mr";
    private static final String DEFAULT_LAST_NAME = "Smith";

    public Contact() {
        super(CONTACT_API_NAME);
    }

    public Contact withLastName(String lastName) {
        fields.put(LAST_NAME, lastName);
        return this;
    }

    public Contact removeLastName() {
        fields.remove(LAST_NAME);
        return this;
    }

    public Contact withSalutation(String salutation) {
        fields.put(SALUTATION, salutation);
        return this;
    }

    public Contact removeSalutation() {
        fields.remove(SALUTATION);
        return this;
    }

    public Contact withVVIDNumber(String vvidNumber) {
        fields.put(VVID_NUMBER, vvidNumber);
        return this;
    }

    public static Contact getDefaultMinimal() {
        Contact contact = new Contact()
                .withLastName(DEFAULT_LAST_NAME)
                .withSalutation(DEFAULT_SALUTATION);
        return contact;
    }
}
