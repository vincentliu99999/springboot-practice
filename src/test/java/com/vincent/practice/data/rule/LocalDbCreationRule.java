package com.vincent.practice.data.rule;

import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class LocalDbCreationRule implements BeforeEachCallback, AfterEachCallback {

    protected DynamoDBProxyServer server;

    public LocalDbCreationRule() {
        // System.setProperty("sqlite4java.library.path", "native-libs");
        System.getProperties().setProperty("sqlite4java.library.path", "src/test/resources/libs");
    }

    protected void stopUnchecked(DynamoDBProxyServer dynamoDbServer) {
        try {
            dynamoDbServer.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
      // this.stopUnchecked(server);
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
      String port = "8000";
        this.server = ServerRunner.createServerFromCommandLineArgs(new String[]{"-inMemory", "-port", port});
        server.start();
    }

}
