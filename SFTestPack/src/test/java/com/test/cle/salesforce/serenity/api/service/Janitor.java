package com.test.cle.salesforce.serenity.api.service;

import java.util.ArrayList;
import java.util.List;

public class Janitor {


    private static Janitor instance;

    private Janitor(){}

    public static synchronized Janitor getInstance(){
        if(instance == null){
            instance = new Janitor();
        }
        return instance;
    }

    private List<String> recordsToDelete = new ArrayList<>();

    public void resetCleanupList() {
        recordsToDelete = new ArrayList<>();
    }

    public void addRecordForCleanup(String recordID) {
        recordsToDelete.add(recordID);
    }

    public void cleanup() {
        for (int idx = recordsToDelete.size()-1 ; idx>=0; idx--) {
            UiApiConnector connector = UiApiConnector.getInstance();
            connector.deleteRecord(recordsToDelete.get(idx));
        }
        resetCleanupList();
    }

}
