package com.test.cle.salesforce.serenity.api.objects;

public class CampaignEventSlot extends Record {

    private static final String CAMPAIGN_EVENT_SLOT_API_NAME = "CampaignEventSlot__c";

    private static final String NAME = "Name";
    private static final String CAMPAIGN = "Campaign__c";

    public CampaignEventSlot() {
        super(CAMPAIGN_EVENT_SLOT_API_NAME);
    }

    public CampaignEventSlot withName(String name) {
        fields.put(NAME, name);
        return this;
    }

    public CampaignEventSlot withCampaign(String campaign) {
        fields.put(CAMPAIGN, campaign);
        return this;
    }

}
