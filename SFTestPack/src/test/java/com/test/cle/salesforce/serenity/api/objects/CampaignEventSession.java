package com.test.cle.salesforce.serenity.api.objects;

public class CampaignEventSession extends Record{

    private static final String CAMPAIGN_EVENT_SESSION_API_NAME = "CampaignEventSession__c";

    private static final String NAME = "Name";
    private static final String CAMPAIGN_EVENT_SLOT = "CampaignEventSlot__c";
    private static final String CAPACITY = "Capacity__c";
    private static final String DETAILS = "Details__c";
    private static final String ROOM_NAME = "RoomName__c";

    public CampaignEventSession() {
        super(CAMPAIGN_EVENT_SESSION_API_NAME);
    }

    public CampaignEventSession withName(String name) {
        fields.put(NAME, name);
        return this;
    }

    public CampaignEventSession withCampaignEventSlot(String slotID) {
        fields.put(CAMPAIGN_EVENT_SLOT, slotID);
        return this;
    }

}
