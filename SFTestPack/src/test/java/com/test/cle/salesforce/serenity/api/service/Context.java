package com.test.cle.salesforce.serenity.api.service;

import java.util.List;

public class Context {

    private static String recentRecordId;
    private static String campaignID;
    private static String campaignEventSlotID;

    public static void storeLatestRecordId(String recentRecord) {
        recentRecordId = recentRecord;
    }

    public static String getLatestRecordId() {
        return recentRecordId;
    }

    public static void storeCampaignId(String campaign) {
        campaignID = campaign;
    }

    public static String getCampaignId() {
        return campaignID;
    }

    public static void storeCampaignEventSlotId(String campaignEventSlot) {
        campaignEventSlotID = campaignEventSlot;
    }

    public static String getCampaignEventSlotId() {
        return campaignEventSlotID;
    }
}
