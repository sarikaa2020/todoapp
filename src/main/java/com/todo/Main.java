package com.todo;

import java.sql.Connection;                  
import java.sql.SQLException;
import com.todo.util.DatabaseConnection;      

public class Main {
    public static void main(String[] args) {
        DatabaseConnection dbConnection = new DatabaseConnection();
        try {
            // Call the static method from DatabaseConnection
            Connection connection = dbConnection.getDBConnection();
            System.out.println("Database connected successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }
}
