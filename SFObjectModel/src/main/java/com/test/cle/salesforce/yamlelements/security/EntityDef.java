package com.test.cle.salesforce.yamlelements.security;

import java.util.List;

public class EntityDef {


    String name;

    List<DePicklist> dePicklists;
    List<Removal> removals;
    List<MandatoryAttr> mandatory;
    List<DataType> dataTypes;
    List<EnLabel> labels;
    List<DeLabel> deLabels;
    List<EncryptedField> encryptedFields;
    List<EnPicklist> picklists;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DePicklist> getDePicklists() {
        return dePicklists;
    }

    public void setDePicklists(List<DePicklist> dePicklists) {
        this.dePicklists = dePicklists;
    }

    public List<Removal> getRemovals() {
        return removals;
    }

    public void setRemovals(List<Removal> removals) {
        this.removals = removals;
    }

    public List<MandatoryAttr> getMandatory() {
        return mandatory;
    }

    public void setMandatory(List<MandatoryAttr> mandatory) {
        this.mandatory = mandatory;
    }

    public List<DataType> getDataTypes() {
        return dataTypes;
    }

    public void setDataTypes(List<DataType> dataTypes) {
        this.dataTypes = dataTypes;
    }

    public List<EnLabel> getLabels() {
        return labels;
    }

    public void setLabels(List<EnLabel> labels) {
        this.labels = labels;
    }

    public List<DeLabel> getDeLabels() {
        return deLabels;
    }

    public void setDeLabels(List<DeLabel> deLabels) {
        this.deLabels = deLabels;
    }

    public List<EncryptedField> getEncryptedFields() {
        return encryptedFields;
    }

    public void setEncryptedFields(List<EncryptedField> encryptedFields) {
        this.encryptedFields = encryptedFields;
    }

    public List<EnPicklist> getPicklists() {
        return picklists;
    }

    public void setPicklists(List<EnPicklist> picklists) {
        this.picklists = picklists;
    }

    public List<RestrictedPickList> getRestrictedPickLists() {
        return restrictedPickLists;
    }

    public void setRestrictedPickLists(List<RestrictedPickList> restrictedPickLists) {
        this.restrictedPickLists = restrictedPickLists;
    }

    List<RestrictedPickList> restrictedPickLists;


}
