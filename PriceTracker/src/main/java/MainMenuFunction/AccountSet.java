/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainMenuFunction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;


public class AccountSet {
    public static void ChangeName(String ou,String nu){
        String user = "root";
        String pass = "";

        //Changing old username into new username in user information database
        try{
        
            String jdbcurl = "jdbc:mysql://localhost:3306/registeredusers"; //Directory to registereduser database
            String sql = "UPDATE ruserinfos " + "SET username = '" + nu + "' WHERE username = '" + ou + "';"; //SQL query to be executed

            
            Connection con = DriverManager.getConnection(jdbcurl,user,pass);
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.execute();
            
            con.close();
            pstmt.close();
                
        } catch(SQLException e){
            System.out.println("Error connecting to server...");
            e.printStackTrace();
        }
        
        String table_name = ou+"_sc";
        String new_table_name = nu+ "_sc";
        
        
        //Updating shopping cart name after changing username
        try{
            String jdbcurl2 = "jdbc:mysql://localhost:3306/shoppingcarts";
            String sql2 = "RENAME TABLE " + table_name + " TO " + new_table_name;
            
            Connection con = DriverManager.getConnection(jdbcurl2,user,pass);
            PreparedStatement pstmt = con.prepareStatement(sql2);
            pstmt.execute();
            
            con.close();
            pstmt.close();
            
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public static void ChangePass(String op,String np){
                
        String user = "root";
        String pass = "";
        
        
        //Changing old username into new username in user information database
        try{
        
            String jdbcurl = "jdbc:mysql://localhost:3306/registeredusers";
            String sql = "UPDATE ruserinfos " + "SET password = '" + np + "' WHERE password = '" + op + "';"; 

            
            Connection con = DriverManager.getConnection(jdbcurl,user,pass);
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.execute();
            
            con.close();
            pstmt.close();
                
        } catch(SQLException e){
            System.out.println("Error connecting to server...");
            e.printStackTrace();
        }
        
    }
    public static void ChangeEmail(String oe,String ne){
                
        String user = "root";
        String pass = "";
        
        
        //Changing old username into new username in user information database
        try{
        
            String jdbcurl = "jdbc:mysql://localhost:3306/registeredusers";
            String sql = "UPDATE ruserinfos " + "SET email = '" + oe + "' WHERE password = '" + ne + "';"; 

            
            Connection con = DriverManager.getConnection(jdbcurl,user,pass);
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.execute();
            
            con.close();
            pstmt.close();
                
        } catch(SQLException e){
            System.out.println("Error connecting to server...");
            e.printStackTrace();
        }
        
    }
}
