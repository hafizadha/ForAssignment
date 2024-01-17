/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ShoppingCart;

import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class SCinfo {
    
    public static ArrayList<List<String>> shopcart ;
    public static void CreateSC(String nu){
        
        
        String jdbcurl = "jdbc:mysql://localhost:3306/shoppingcarts";
        String user = "root";
        String pass = "";
        
        try{
            String table_name = nu+"_SC";
            String sql = "CREATE TABLE " + table_name + " (item_code int(20),item varchar(200),unit varchar(25))" ;
            
            Connection con = DriverManager.getConnection(jdbcurl,user,pass);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);

            con.close();
            stmt.close();

            System.out.println("Shopping cart created!");
         
        }catch(SQLException e){
            System.out.println("Error creating shopping cart!");
            e.printStackTrace();
        }        
    }
    
    public static ArrayList<List<String>> ImportSC(String nu){
        String jdbcurl = "jdbc:mysql://localhost:3306/shoppingcarts";
        String user = "root";
        String pass = "";
        ArrayList<List<String>> scitems = new ArrayList<>();
        
        
        try{
            String table_name = nu+"_SC";
            
            String sql = "SELECT * from " + table_name;
            
            Connection con = DriverManager.getConnection(jdbcurl,user,pass);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
               ArrayList<String> inner = new ArrayList<>();
               inner.add(rs.getString(1));
               inner.add(rs.getString(2));
               inner.add(rs.getString(3));
               scitems.add(inner);
               
            }
            
            con.close();
            stmt.close();
            rs.close();

            System.out.println("Shopping cart imported!");
         
        }catch(SQLException e){
            System.out.println("Error importing shopping cart!");
            e.printStackTrace();
        }        
        
        return scitems; 

    }
}
