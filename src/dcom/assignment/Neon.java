/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dcom.assignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        return DriverManager.getConnection(url, user, password);
    }
    
   
    public ArrayList<ArrayList<Object>> read(String table) {
        ArrayList<ArrayList<Object>> data = new ArrayList<>();
        try (Connection conn = connect()) {
            String sql = "SELECT * FROM " + table;  // Note: validate table name in real usage to avoid SQL injection
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

    public void adddata(String table, ArrayList<Object> data){
        try (Connection conn = connect()) {
            StringBuilder sql = new StringBuilder("INSERT INTO " + table + " VALUES (");
            for (int i = 0; i < data.size(); i++) {
                sql.append("?");
                if (i < data.size() - 1) {
                    sql.append(", ");
                }
            }
            sql.append(")");
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            for (int i = 0; i < data.size(); i++) {
                pstmt.setObject(i + 1, data.get(i));
            }

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Inserted " + rowsAffected + " row(s) into " + table);
            System.out.print("Data inserted: ");
            for (Object obj : data) {
                System.out.print(obj + " | ");
            }
            System.out.println();
            
          } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

