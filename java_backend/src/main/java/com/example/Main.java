package com.example;

import static spark.Spark.*;

import com.google.gson.Gson;

import java.sql.*;
import java.util.*;

public class Main {

    static Connection getConnection() {
        for (int i = 0; i < 10; i++) {
            try {
                return DriverManager.getConnection(
                        "jdbc:mysql://db:3306/testdb",
                        "root",
                        "password"
                );
            } catch (Exception e) {
                System.out.println("MySQL not ready yet... " + e);
                try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
            }
        }
        throw new RuntimeException("Cannot connect to MySQL");
    }

    public static void main(String[] args) {

        port(8081);

        get("/", (req, res) -> "Java Spark backend is working");

        get("/users", (req, res) -> {
            res.type("application/json");

            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");

            List<Map<String, Object>> list = new ArrayList<>();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", rs.getInt("id"));
                row.put("name", rs.getString("name"));
                list.add(row);
            }

            rs.close();
            stmt.close();
            conn.close();

            return new Gson().toJson(list);
        });
    }
}
