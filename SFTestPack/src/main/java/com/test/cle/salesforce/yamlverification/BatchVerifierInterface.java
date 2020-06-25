package com.test.cle.salesforce.yamlverification;

import java.io.IOException;

public interface BatchVerifierInterface {

    boolean fileExists(String yamlfile);

    boolean serialialize(String yamlfile, String entityType) throws IOException;
}
