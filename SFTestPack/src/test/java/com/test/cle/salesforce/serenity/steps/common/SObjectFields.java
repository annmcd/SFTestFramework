package com.test.cle.salesforce.serenity.steps.common;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.sobject.FieldDefinition;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.soap.partner.DescribeSObjectResult;
import com.sforce.soap.partner.Field;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.ws.ConnectionException;
import org.apache.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

public class SObjectFields {

    static PartnerConnection partnerConnection;
    static EnterpriseConnection enterpriseConnection;

    static Logger log = Logger.getLogger (MethodHandles.lookup ().lookupClass ().getName ());

    public static void setConnection(PartnerConnection conn) {
        partnerConnection = conn;
    }


    public static void setEnterpriseConnection(EnterpriseConnection conn) {
        enterpriseConnection= conn;
    }
    public static Field[] getSalesforceFieldInfo(String type) {
        Field[] fields = null;
        try {

            DescribeSObjectResult describeSObjectResult =
                    partnerConnection.describeSObject (type);

            if (describeSObjectResult != null) {
                fields = describeSObjectResult.getFields ();
            }
        } catch (Exception e) {
            log.error (e);
        }
        return fields;
    }


    public static List<FieldDefinition> getSalesforceFieldDefinitionInfo(String entity) {


        String soql = "Select EntityDefinition.QualifiedApiName, QualifiedApiName," +
                " DataType, Label, Description FROM FieldDefinition  WHERE EntityDefinition.QualifiedApiName " +
                "IN(" + "'" + entity + "')  and QualifiedApiName like '%__c'";


        com.sforce.soap.enterprise.QueryResult qresult=null;
        try {
         qresult  = enterpriseConnection.query (soql);
        } catch (ConnectionException e) {
            log.error (e);
        }
        ArrayList<FieldDefinition> fieldDefinitions = new ArrayList<> ();
        if (qresult.getSize () > 0) {
           SObject[]  records=  qresult.getRecords ();
            for (SObject obj: records) {
                FieldDefinition fieldDefinition = (FieldDefinition) obj;
                fieldDefinitions.add (fieldDefinition);

            }
        }
        return fieldDefinitions;
    }

}
