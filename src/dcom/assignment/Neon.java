/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dcom.assignment;

import java.sql.*;
import java.util.*;

/**
 *
 * @author Loh Kai Xuan
 */
public class Neon {
    String url = "jdbc:postgresql://ep-noisy-credit-a8eab2hc-pooler.eastus2.azure.neon.tech:5432/neondb?sslmode=require";
    String user = "neondb_owner"; // NOT your email
    String password = "npg_uSqdFMEL7eX1"; // The password from Neon dashboard

    public Connection connect() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver not found. Include it in your library path." + e.getMessage());
            throw new SQLException("PostgreSQL JDBC Driver not found.", e);
        }
        return DriverManager.getConnection(url, user, password);
    }

    public ArrayList<ArrayList<Object>> read(String table) {
        ArrayList<ArrayList<Object>> data = new ArrayList<>();
        try (Connection conn = connect()) {
            String sql = "SELECT * FROM " + table; // Note: validate table name in real usage to avoid SQL injection
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();

            while (rs.next()) {
                ArrayList<Object> row = new ArrayList<>();
                for (int i = 1; i <= colCount; i++) {
                    row.add(rs.getObject(i));
                }
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public String addData(String table, ArrayList<String> columns, ArrayList<Object> values) {
        if (columns.size() != values.size()) {
            return "Error: Column count and value count do not match!";
        }

        StringBuilder colBuilder = new StringBuilder();
        StringBuilder valBuilder = new StringBuilder();

        for (int i = 0; i < columns.size(); i++) {
            colBuilder.append(columns.get(i));
            valBuilder.append("?");
            if (i < columns.size() - 1) {
                colBuilder.append(", ");
                valBuilder.append(", ");
            }
        }

        String sql = "INSERT INTO " + table + " (" + colBuilder + ") VALUES (" + valBuilder + ")";

        try (Connection conn = connect()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < values.size(); i++) {
                pstmt.setObject(i + 1, values.get(i));
            }
            int rowsAffected = pstmt.executeUpdate();

            // Build a string of inserted data
            StringBuilder insertedData = new StringBuilder();
            insertedData.append("Inserted ").append(rowsAffected).append(" row(s) into ").append(table).append(".\n");
            insertedData.append("Data: { ");
            for (int i = 0; i < columns.size(); i++) {
                insertedData.append(columns.get(i)).append(": ").append(values.get(i));
                if (i < columns.size() - 1)
                    insertedData.append(", ");
            }
            insertedData.append(" }");
            return insertedData.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error inserting data: " + e.getMessage();
        }
    }

    public String updateData(String table, ArrayList<String> columns, ArrayList<Object> values, String conditionColumn,
            Object conditionValue) {
        if (columns.size() != values.size()) {
            return "Error: Column count and value count do not match!";
        }

        StringBuilder setBuilder = new StringBuilder();
        for (int i = 0; i < columns.size(); i++) {
            setBuilder.append(columns.get(i)).append(" = ?");
            if (i < columns.size() - 1) {
                setBuilder.append(", ");
            }
        }

        String sql = "UPDATE " + table + " SET " + setBuilder + " WHERE " + conditionColumn + " = ?";

        try (Connection conn = connect()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < values.size(); i++) {
                pstmt.setObject(i + 1, values.get(i));
            }
            pstmt.setObject(values.size() + 1, conditionValue);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Updated " + rowsAffected + " row(s) in " + table);
            return "Updated " + rowsAffected + " row(s) in " + table;
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error updating data: " + e.getMessage();
        }
    }

    public String deleteData(String table, String conditionColumn, Object conditionValue) {
        String sql = "DELETE FROM " + table + " WHERE " + conditionColumn + " = ?";

        try (Connection conn = connect()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, conditionValue);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Deleted " + rowsAffected + " row(s) from " + table);
            return "Deleted " + rowsAffected + " row(s) from " + table;
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error deleting data: " + e.getMessage();
        }
    }

}
