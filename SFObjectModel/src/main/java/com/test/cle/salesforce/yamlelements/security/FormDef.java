package com.test.cle.salesforce.yamlelements.security;

import java.util.List;

public class FormDef {

    String name;
    String type;
    List<FormField> formFields;
    List<CustomFormField> customFormFields;


    public List<CustomFormField> getCustomFormFields() {

        return customFormFields;
    }

    public void setCustomFormFields(List<CustomFormField> customFormFields) {

        this.customFormFields = customFormFields;
    }


    public List<FormField> getFormFields() {

        return formFields;
    }

    public void setFormFields(List<FormField> formFields) {

        this.formFields = formFields;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {

        this.type = type;
    }
}
