package com.test.cle.salesforce.yamlelements.security;

import java.util.Optional;

public class CustomFormField {


    String controlType;
    Optional<String> optionValues; // picklist,text,textarea,currency,email,number,checkbox
    boolean mandatory;

    Optional<String> maxLength;
    Optional<String> id;
    String developerName;
    public String inputTerm;


    public String getDeveloperName() {

        return developerName;
    }

    public void setDeveloperName(String developerName) {

        this.developerName = developerName;
    }

    public void setInputTerm(String inputTerm) {

        this.inputTerm = inputTerm;
    }

    public Optional<String> getInputTerm() {

        return Optional.ofNullable(inputTerm);

    }

    public Optional<String> getId() {

        return id;
    }

    public void setId(Optional<String> id) {

        this.id = id;
    }

    public Optional<String> getOptionValues() {

        return optionValues;
    }

    public void setOptionValues(Optional<String> optionValues) {

        this.optionValues = optionValues;
    }

    public Optional<String> getMaxLength() {

        return maxLength;
    }

    public void setMaxLength(Optional<String> maxLength) {

        this.maxLength = maxLength;
    }


    public String getControlType() {

        return controlType;
    }

    public void setControlType(String controlType) {

        this.controlType = controlType;
    }

    public boolean isMandatory() {

        return mandatory;
    }

    public void setMandatory(boolean mandatory) {

        this.mandatory = mandatory;
    }


}
