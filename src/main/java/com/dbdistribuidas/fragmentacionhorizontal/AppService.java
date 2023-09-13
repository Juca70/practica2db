/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dbdistribuidas.fragmentacionhorizontal;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juang
 */
public class AppService {

    private Connection conn;
    private DatabaseMetaData dbmd;
    private String dbName;
    private String tableName;

    public AppService(Connection conn) {
        this.conn = conn;
        try {
            dbmd = conn.getMetaData();
        } catch (SQLException ex) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Person> where(String condition) {
    List<Person> list = new ArrayList<>();
    String query = "SELECT * FROM persona " + condition;
        try (
                Statement st = conn.createStatement(); 
                ResultSet rs = st.executeQuery(query.trim())) {
            while (rs.next()) {
                Person p = new Person(
                        rs.getInt("No_Control"),
                        rs.getString("Nombre"),
                        rs.getString("Domicilio"),
                        rs.getString("Ciudad"),
                        rs.getInt("Edad"),
                        rs.getString("Oficio")
                );
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }

}
