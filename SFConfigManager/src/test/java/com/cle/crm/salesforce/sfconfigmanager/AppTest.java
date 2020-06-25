package com.cle.crm.salesforce.sfconfigmanager;

import com.cle.crm.salesforce.sfconfigmanager.entities.EntityGeneratorImpl;
import com.test.cle.salesforce.yamlelements.security.EntityDef;
import com.test.cle.salesforce.yamlelements.security.DataType;
import com.test.cle.salesforce.yamlelements.security.EnLabel;
import com.test.cle.salesforce.yamlelements.security.DeLabel;
import java.util.List;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;


public class AppTest {


    //disabled tests as generator client and test are not resourcing spreadsheet from same directory
    //issue to be resolved, with relative path
   @Test
    public void yamlFileExists() {
        EntityGeneratorImpl impl = new EntityGeneratorImpl();
        impl.doInit("account");
        String yamlFile = impl.doGenerateEntityYamlFile("account");
        assertTrue("Expect this file to exist ", Files.exists(Paths.get(yamlFile)));
    }

    @Test
    public void yamlFileSerialisable() {
        EntityGeneratorImpl impl = new EntityGeneratorImpl();
        impl.doInit("account");
        String yamlFile = impl.doGenerateEntityYamlFile("account");
        EntityDef def = impl.doSerialise(yamlFile);
        assertNotNull("Serialisable OK? ", def);
    }

    @Test
    public void yamlAllEntitiesHaveDatatype() {
        EntityGeneratorImpl impl = new EntityGeneratorImpl();
        impl.doInit("account");
        String yamlFile = impl.doGenerateEntityYamlFile("account");
        EntityDef def = impl.doSerialise(yamlFile);
        List<DataType> dataTypes = def.getDataTypes();

        for (DataType dataType : dataTypes) {
            String name = dataType.getValue();
            assertTrue ("DataType Name Value Check?",name != null && !name.isEmpty());
        }
    }

    @Test
    public void yamlAllEntitiesHaveEnLabel() {
        EntityGeneratorImpl impl = new EntityGeneratorImpl();
        impl.doInit("account");
        String yamlFile = impl.doGenerateEntityYamlFile("account");
        EntityDef def = impl.doSerialise(yamlFile);
        List<EnLabel> enLabels = def.getLabels();

        for (EnLabel enLabel : enLabels) {
            String label = enLabel.getValue();
            assertTrue ("EnLabel Name Value Check?",label != null && !label.isEmpty());
        }
    }

    @Test
    public void yamlAllEntitiesHaveDeLabel() {
        EntityGeneratorImpl impl = new EntityGeneratorImpl();
        impl.doInit("account");
        String yamlFile = impl.doGenerateEntityYamlFile("account");
        EntityDef def = impl.doSerialise(yamlFile);
        List<DeLabel> deLabels = def.getDeLabels();

        for (DeLabel deLabel : deLabels) {
            String label = deLabel.getValue();
            assertTrue ("DeLabel Name Value Check?",label != null && !label.isEmpty());
        }
    }
}