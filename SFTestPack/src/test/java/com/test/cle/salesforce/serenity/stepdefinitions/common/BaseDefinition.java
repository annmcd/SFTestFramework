package com.test.cle.salesforce.serenity.stepdefinitions.common;

import org.apache.log4j.Logger;
import java.lang.invoke.MethodHandles;

public class BaseDefinition {

    public static final Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private static String environment = System.getProperty("target_salesforce_environment");

    public static String getEnvironment() {

        return environment;
    }

    public static void setEnvironment(String environment) {

        BaseDefinition.environment = environment;
    }
}
