package main.java.com.example.repo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DBUtil {
    private static final String CONNECTION_STRING = "mongodb://localhost:27017/";
    private static final String DATABASE_NAME = "course_management_system";
    private static MongoClient mongoClient;
    private static MongoDatabase database;

    static {
        try {
            mongoClient = MongoClients.create(CONNECTION_STRING);
            database = mongoClient.getDatabase(DATABASE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MongoDatabase getDatabase() {
        return database;
    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}