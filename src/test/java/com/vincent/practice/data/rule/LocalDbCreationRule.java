package com.vincent.practice.data.rule;

import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class LocalDbCreationRule implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    protected DynamoDBProxyServer server;

    public LocalDbCreationRule() {
        System.setProperty("sqlite4java.library.path", "native-libs");
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
      String port = "8000";
      this.server = ServerRunner.createServerFromCommandLineArgs(new String[]{"-inMemory", "-port", port});
      server.start();

      System.out.println("beforeAll>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
    
    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
      System.out.println("afterAll>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
      this.stopUnchecked(server);
    }

    protected void stopUnchecked(DynamoDBProxyServer dynamoDbServer) {
        try {
            dynamoDbServer.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
