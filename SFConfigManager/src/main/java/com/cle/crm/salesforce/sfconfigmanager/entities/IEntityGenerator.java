package com.cle.crm.salesforce.sfconfigmanager.entities;


import com.test.cle.salesforce.yamlelements.security.EntityDef;

public interface IEntityGenerator {


    void doInit(String tabs);

    String doGenerateEntityYamlFile(String entityName);

    EntityDef doSerialise(String name);


}