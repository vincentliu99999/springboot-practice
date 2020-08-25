package com.vincent.practice;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
@ConfigurationProperties
public class DynamoDBConfig {

	@Value("${local-db-use}")
	private boolean useLocalDB;
	
	@Bean
	@Scope("prototype")
	public DynamoDbClient buildDDB() throws URISyntaxException{
		if(useLocalDB)
			return DynamoDbClient.builder().endpointOverride(new URI("http://localhost:8000")).build();
		else
			return DynamoDbClient.builder().region(Region.US_EAST_1).build();
	}
}
