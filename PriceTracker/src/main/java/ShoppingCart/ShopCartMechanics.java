/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ShoppingCart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/**
 *
 * @author Hafiz
 */
public class ShopCartMechanics {
    private static ArrayList<String> AllTables = new ArrayList<>();
    public static void AddItem(String nu,String code,String item,String unit){
        
        
        String jdbcurl = "jdbc:mysql://localhost:3306/shoppingcarts";
        String user = "root";
        String pass = "";
        
        System.out.println("Adding " + item + " to Shopping Cart...");
        try{
            
            String table_name = nu+"_SC";
            String values = "('"+ code + "','" + item + "','" + unit +"')";
            
            String sql = "DELETE from " +  table_name + " WHERE item_code = '" + code +"'";
            
            String sql2 = "INSERT INTO " + table_name + " (item_code,item,unit) VALUES " + values;
            
            Connection con = DriverManager.getConnection(jdbcurl,user,pass);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
            stmt.executeUpdate(sql2);
            
            
           

            con.close();
            stmt.close();

            System.out.println("Item Added!");
            
            
            
        }catch(SQLException e){
            System.out.println("Error Adding Item");
            e.printStackTrace();
        }
    }
    
    public static void RemoveItem(String nu,String code){
        String jdbcurl = "jdbc:mysql://localhost:3306/shoppingcarts";
        String user = "root";
        String pass = "";
        
        try{
            String table_name = nu+"_SC";

            
            String sql = "DELETE from " +  table_name + " WHERE item_code = '" + code +"'";
            
            Connection con = DriverManager.getConnection(jdbcurl,user,pass);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);

            con.close();
            stmt.close();

            System.out.println("Item updated in database!");
        }catch(SQLException e){
            System.out.println("Error removing item");
            e.printStackTrace();
        }catch(Exception e ){
            System.out.println("Item code not in shopping cart");
        }
        
    }
    public static void getAllUserTables(ArrayList<String> username){
        for(String x: username){
            String tablename = x+"_sc";
            AllTables.add(tablename);
        }
        
    }
    
    public static void ChangeItemName(String code,String newdet){
    
        String jdbcurl = "jdbc:mysql://localhost:3306/shoppingcarts";
        String user = "root";
        String pass = "";
        
        try{
            Connection con = DriverManager.getConnection(jdbcurl,user,pass);
            Statement stmt = con.createStatement();

            for(String x: AllTables){
                String sql = "UPDATE " + x + " SET item = '" + newdet + "' WHERE item_code = '" + code + "';";
                stmt.executeUpdate(sql);
            
            }

            con.close();
            stmt.close();

            System.out.println("Item updated in database!");
        }catch(SQLException e){
            System.out.println("Error removing item");
            e.printStackTrace();
        }catch(Exception e ){
            System.out.println("Item code not in shopping cart");
        }
            
        }
        
    public static void ChangeItemUnit(String code,String newdet){
    
        String jdbcurl = "jdbc:mysql://localhost:3306/shoppingcarts";
        String user = "root";
        String pass = "";
        
        try{
            Connection con = DriverManager.getConnection(jdbcurl,user,pass);
            Statement stmt = con.createStatement();

            for(String x: AllTables){
                String sql = "UPDATE " + x + " SET item = '" + newdet + "' WHERE unit = '" + code + "';";
                stmt.executeUpdate(sql);
            
            }

            con.close();
            stmt.close();

            System.out.println("Item updated in database!");
        }catch(SQLException e){
            System.out.println("Error removing item");
            e.printStackTrace();
        }catch(Exception e ){
            System.out.println("Item code not in shopping cart");
        }
            
        }
    }

