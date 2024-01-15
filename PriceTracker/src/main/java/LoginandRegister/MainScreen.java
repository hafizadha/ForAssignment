/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LoginandRegister;

import LoginandRegister.RegisterSystem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import LoginandRegister.LoginSystem;
import ShoppingCart.ShopCartMechanics;
import java.util.ArrayList;

public class MainScreen {
    private static String UserURL ="jdbc:mysql://localhost:3306/registeredusers";
    private static String username = "root";
    private static String pass = "";
   
    
    
     public static void ImportRegUserSQL() {
        // Map to store data from the database
        ArrayList<UserInfos> UserInfosList = new ArrayList<>();

        try {
            
            Connection connection = DriverManager.getConnection(UserURL, username, pass);

            // SQL query to retrieve data
            String sql = "SELECT * FROM ruserinfos";

            // Create a prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Populate the HashMap with data from the database
            while (resultSet.next()) {
                String key = resultSet.getString(1);
                String value = resultSet.getString(2);
                String email = resultSet.getString(3);
                
                UserInfos user = new UserInfos(key,value,email);
                UserInfosList.add(user);
            }

            // End resources
            resultSet.close();
            preparedStatement.close();
            connection.close();
            
            System.out.println("Connected to Server\n");
            
            ArrayList<String> username = new ArrayList<>();
            
            for(UserInfos f : UserInfosList){
                username.add(f.getUsername());
            }
            
            RegisterSystem rs = new RegisterSystem();
            ShopCartMechanics.getAllUserTables(username);
            rs.SendToRS(UserInfosList);
            
            

        } catch (SQLException e) {
            System.out.println("Failed to connect to Server");
            System.out.println("Database failed to be imported...");
            e.printStackTrace();
        }
    }

    public static void DisMain(){
        System.out.println("\n==============================================================================================================");
        System.out.print("\t          ___      _            _____                _                 _   _  \n");
        System.out.print("\t         / _ \\_ __(_) ___ ___  /__   \\_ __ __ _  ___| | _____ _ __    | | | | \n");
        System.out.print("\t        / /_)/ '__| |/ __/ _ \\   / /\\/ '__/ _` |/ __| |/ / _ \\ '__|  / __) __)\n");
        System.out.print("\t       / ___/| |  | | (_|  __/  / /  | | | (_| | (__|   <  __/ |     \\__ \\__ \\\n");
        System.out.print("\t       \\/    |_|  |_|\\___\\___|  \\/   |_|  \\__,_|\\___|_|\\_\\___|_|     (   (   /\n");
        System.out.print("\t                                                                      |_| |_| ");
        System.out.println("\n==============================================================================================================\n");
        System.out.println("Welcome to Price Tracker! Please choose an option:\n\n[1] Login\n[2] Register\n[3] Close");
        System.out.println("-----------------------------");
        
    }
    
    public  boolean CheckValidity(String pass){
        boolean valid = true;
        int len = pass.length();
        
        if(len<=2 || len >=12){
            return false;
        }
        
        for(int i = 0;i<len;++i){
            int c = pass.charAt(i);
            
            if(c == ' '){
                valid = false;break;
            }  
        }
        return valid;
    }
}
    

