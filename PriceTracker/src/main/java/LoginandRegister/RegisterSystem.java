package LoginandRegister;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;

public class RegisterSystem {
    private static String UserURL ="jdbc:mysql://localhost:3306/registeredusers";
    private static String username = "root";
    private static String pass = "";
    
    private static String newuser;
    private static String newpass;
    private static String newemail;
    static ArrayList<UserInfos> UI;
    
    
    public static void SendToRS(ArrayList<UserInfos> f){
        UI = f;
    }
    
    public static void SetUserInfos(String u,String p,String e){
        newuser = u;
        newpass = p;
        newemail = e;
    }

    
    public static void UpdateUserSQL(){
        try { 
            Connection connect = DriverManager.getConnection(UserURL,username,pass); 
            
            String sql = "insert into ruserinfos (username,password,email) values (?,?,?)";
            
            PreparedStatement ps =connect.prepareStatement(sql);
            
            ps.setString(1, newuser);
            ps.setString(2,newpass);
            ps.setString(3, newemail);
            ps.executeUpdate();
            
            connect.close(); 
            
            System.out.println("Registration Successful!");
        } catch (Exception e) { 
            System.out.println("Failed to update..."); 
            e.printStackTrace();
        } 
    }  
    
    public boolean CheckUserExist(String username){
        boolean UserExist = false;
        
        for(UserInfos u:UI){
            if(username.equals(u.getUsername())){
                UserExist = true;break;
            }
        }
        
        return UserExist;
    }
    
    public static boolean CheckEmailExist(String email){
        boolean EmailExist = false;
        
        for(UserInfos u:UI){
            if(email.equals(u.getEmail())){
                EmailExist = true;break;
            }
        }
        
        return EmailExist;
    }        
}

