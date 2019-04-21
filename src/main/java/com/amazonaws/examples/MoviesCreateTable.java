package com.amazonaws.examples;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.Arrays;

public class MoviesCreateTable {
    public static void main(String[] args) throws Exception{
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        DynamoDB dynamoDB = new DynamoDB(client);
        String tableName = "Movies";

        try {
            System.out.printf("Attempting to create table %s. Please wait...\n", tableName);
            Table table = dynamoDB.createTable(tableName,
                    Arrays.asList(new KeySchemaElement("year", KeyType.HASH),
                            new KeySchemaElement("title", KeyType.RANGE)),
                    Arrays.asList(new AttributeDefinition("year", ScalarAttributeType.N),
                            new AttributeDefinition("title", ScalarAttributeType.S)),
                    new ProvisionedThroughput(10L, 10L));

            table.waitForActive();
            System.out.println("Success. Table status: " + table.getDescription().getTableStatus());
        } catch (Exception e) {
            System.err.println("Unable to create table:");
            System.err.println(e.getMessage());
        }
    }
}
