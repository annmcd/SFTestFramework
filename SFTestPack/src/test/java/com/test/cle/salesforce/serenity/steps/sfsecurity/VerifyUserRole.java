package com.test.cle.salesforce.serenity.steps.sfsecurity;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.soap.enterprise.sobject.UserRole;
import com.sforce.ws.ConnectionException;
import com.test.cle.salesforce.testutils.Constants;
import com.test.cle.salesforce.yamlelements.security.Role;
import com.test.cle.salesforce.yamlelements.security.UserRoleDef;
import net.thucydides.core.annotations.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.List;

public class VerifyUserRole {
    static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    public static StringBuffer doVerifyUserRole(EnterpriseConnection enterpriseConnection, String yamlFile)
            throws ConnectionException {
        StringBuffer failures=new StringBuffer ();
        Yaml yaml = new Yaml(new Constructor(UserRoleDef.class));
        UserRoleDef entity = null;
        try {
            try (InputStream inputStream = new FileInputStream(new File(yamlFile))) {

                entity = yaml.load(inputStream);
            }
        } catch (IOException e) {
            log.error(e);
        }

        List<Role> cleUserRole = entity.getRole();

        for (Role userRole : cleUserRole) {
            String roleName = userRole.getName();
            String yamlContactAccess = userRole.getContactAccessForAccountOwner().trim();
            String yamlCaseAccess = userRole.getCaseAccessForAccountOwner().trim();
            String yamlOppAccess = userRole.getOpportunityAccessForAccountOwner().trim();

            String soql =
                    "select name, ContactAccessForAccountOwner, CaseAccessForAccountOwner , "
                            + "OpportunityAccessForAccountOwner from userRole where name = "
                            + "'"
                            + roleName
                            + "'";
            QueryResult qr = enterpriseConnection.query(soql);
            if (qr.getSize() > 0) {
                for (SObject sfObj : qr.getRecords()) {

                    UserRole sfUserRole = (UserRole) sfObj;
                    String sfContactAccess = sfUserRole.getContactAccessForAccountOwner();
                    String sfCaseAccess = sfUserRole.getCaseAccessForAccountOwner();
                    String sfOpportunityAccess = sfUserRole.getOpportunityAccessForAccountOwner();

                    if(!yamlContactAccess.equalsIgnoreCase (sfContactAccess)){

                        failures.append ("Role: 'Contact Access' Expected: " + yamlContactAccess + " Actual: " + sfContactAccess + Constants.CR);
                    }
                    if(!yamlCaseAccess.equalsIgnoreCase (sfCaseAccess)){

                        failures.append ("Role: 'Case Access' Expected: " + yamlCaseAccess + " Actual: " + sfCaseAccess + Constants.CR);
                    }
                    if(!yamlOppAccess.equalsIgnoreCase (sfOpportunityAccess)){

                        failures.append ("Role: 'Opportunity Access' Expected: " + yamlOppAccess + " Actual: " + sfOpportunityAccess+ Constants.CR);
                    }

                    break;
                }
            }else{

                failures.append(Constants.ROLE_NOT_FOUND +soql );
            }
        }
        return failures;
    }


    @Step
    public static void assertFailures(StringBuffer buff) {

        if(buff.length ()>0){

            Assert.assertTrue (buff.toString (),buff.length ()==0);
            buff.delete(0, buff.length());
        }
    }

}
