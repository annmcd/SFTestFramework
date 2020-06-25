package com.test.cle.salesforce.serenity.steps.entityconfigsteps;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.soap.enterprise.sobject.UserRole;
import com.sforce.ws.ConnectionException;
import com.test.cle.salesforce.serenity.stepdefinitions.common.BaseDefinition;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RoleHierarchySteps extends BaseDefinition {

    private static String getParentRoleId(String roleName, EnterpriseConnection enterpriseConnection)
            throws ConnectionException {

        String query = "Select parentRoleId from userRole where Name = '" + roleName + "'";
        com.sforce.soap.enterprise.QueryResult qr = enterpriseConnection.query(query);
        String parentRoleId = "";

        if (qr.getSize() > 0) {

            for (SObject obj : qr.getRecords()) {
                UserRole role = (UserRole) obj;
                parentRoleId = role.getParentRoleId();
            }
        } else {
            log.debug("Role not found " + query);
        }
        return parentRoleId;
    }

    private static String getRoleName(String id, EnterpriseConnection enterpriseConnection)
            throws ConnectionException {

        String query = "Select name from userRole where Id = '" + id + "'";
        QueryResult qr = enterpriseConnection.query(query);
        String roleName = null;

        if (qr.getSize() > 0) {

            for (SObject obj : qr.getRecords()) {
                UserRole role = (UserRole) obj;
                roleName = role.getName();
            }
        } else {
            log.debug("Role not found " + query);
        }
        return roleName;
    }

    public static String processRoles(
            List<String> roleNames, EnterpriseConnection enterpriseConnection)
            throws ConnectionException {

        ArrayList<String> sfHierarchy = new ArrayList<String>();

        for (Iterator<String> iterator = roleNames.iterator(); iterator.hasNext(); ) {
            String roleName = iterator.next();
            log.debug("RoleName found = " + roleName);
            String parentRoleId = getParentRoleId(roleName, enterpriseConnection);
            log.debug("parentRoelId " + parentRoleId);
            if (parentRoleId != null) {
                String parentRole = getRoleName(parentRoleId, enterpriseConnection);
                sfHierarchy.add(parentRole);
            }
        }
        return sfHierarchy.toString();


    }
}
