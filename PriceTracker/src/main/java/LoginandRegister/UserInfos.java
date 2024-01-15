/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LoginandRegister;

/**
 *
 * @author Hafiz
 */
public class UserInfos {
    
    private String username;
    private String password;
    private String email;
    
    UserInfos(){
        username = null;
        password = null;
        email = null;
    }
    
    UserInfos(String user,String pass,String em){
        this.username = user;
        this.password = pass;
        this.email = em;
    }
    
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getEmail(){
        return email;
    }
    
    public void setInfos(String u,String p,String e){
        this.username = u;
        this.password = p;
        this.email = e;
    }
    
}
