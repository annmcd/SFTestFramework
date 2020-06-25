
# Introduction
Complete Integration Test BDD Framework for CLE, using the power of the latest release of Serenity and supported versions of JDK, maven and cucumber plugins.

# Pipeline
A declarative jenkins groovy pipeline is provided. Its maven driven allowing support for
* Branch
* Target Environment
* Test Suite
* Serenity Report
* Build Stamped with Env and Branch Identifiers


# Runtime PreRequisite
* Installed JDK 1.8_x
* Maven 3.6.3
* Proxy Settings and SSL Enabled



# Build and Test
***Maven verify lifecycle, includes all suites and  features not tagget with @ignore***

*mvn clean verify*



***Direct to a specific proxy***
*mvn clean verify Dhttps.proxyHost=ilserverproxy -Dhttps.proxyPort=8443*

***disable jira update (defaulted in pom.xml)***
*mvn clean verify -Dserenity.skip.jira.updates=true  -Dserenity.jira.workflowactive=false*

***Run all test suites not marked for ignore in test environment***

mvn clean verify -Dtarget_saleforce_environment=test -Dcontext=test

***Run a specific test suite in the test environment (Default is all suites>***

mvn clean verify -Dserenity-include1=relative path to test suite

***Disable serenity ascii banners in the log output***

mvn clean verify -Dtarget_salesforce_environment=test -Dcontext=test -Dserenity.console.headings=minimal

***Choose your own logging directory for log4j output***

-Dlog4JDir=c:/mylogdirectory

# Contribute
Certainly, create a feature and a pull request and I will review your changes?

# Note:
1. SerenityBDD supports only one log4j File Appender. Two are being used by the logger to
separately show test failures as a whole rather than individual assertion failures, ignore the exception

#Disable Proxy (Non GWLE Host)
Set use_proxy=false in <env>_configuration.properties

# Having SSL Issues Debug it?
*mvn verify -Dhttps.proxyHost=ilserverproxy -Dhttps.proxyPort=8443  -Djavax.trustStore="C:\Program Files\Java\jdk1.8.0_161\jre\lib\security\cacerts" -Djavax.net.ssl.trustStorePassword=changeit  -Djavax.netdebug=SSL:handshake*


# Trust Store not up to date?
**Download the current x509 cert(.crt/cer) for Jira and Central Maven Sites and update your local trust store**

*keytool -importcert -alias atlassian -file  C:\workspace4\SFTestPack\src\test\resources\atlassian.cer -keystore <mykeystorefile>*

# Deployed Tooling API
Download the Tooling WSDL from Salesforce production org (Do not choose the strongly typed WSDL)
Build https://github.com/forcedotcom/wsc (you will use the generated Uber Jar as a code generator for the tooling client jar)
Issue java -classpath %CLASSPATH%;force-wsc-48.1.0-uber.jar;  com.sforce.ws.tools.wsdlc Tooling_WSDL.xml  tooling-client-48.1.jar
 

mvn deploy:deploy-file -DgroupId=com.force.soap  -DartifactId=force-tooling-api  -Dversion=48.1 -Dpackaging=jar -Dfile=tooling-client-48.1.jar -Durl=https://cle-crm.pkgs.visualstudio.com/_packaging/salesforce/maven/v1 -DgeneratePom=true -DrepositoryId=salesforce

# Generate Yaml File for Entity

_GenerateEntityYaml "EntityName1,EntityName2"_

Yaml files are created by the SFConfigManager Project in src/test/resources with a .1suffix.yaml

Copy/Replace the yaml file to src/Test/resource/entityDefs in the SFTestPack Project

# ToDo
Note: currently CLE workstations do not all ssl handshakes with the algorithm
provided by the Atlassian.net cert. This needs to be configured on a server host. Enable Jira Integration

