/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LoginandRegister;


public class LoginSystem extends RegisterSystem {
    
    public static boolean UserValid(String username,String password){
        
        boolean ValidUser = false;
        for(UserInfos u:UI){
            if(username.equals(u.getUsername()) && password.equals(u.getPassword())){
                ValidUser = true;break;
            }
        }
    return ValidUser;    
    }
    
    public static boolean CheckUserAndEmail(String username,String email){
        boolean matching = false;
        
        for(UserInfos u: UI){
            if(username.equals(u.getUsername()) && email.equals(u.getEmail())){
                matching = true;break;
            }
        }
        
        return matching;
    }
    
    public UserInfos SetActiveUser(String key){
        UserInfos active = new UserInfos();
        for(UserInfos u: UI){
            if(key.equals(u.getUsername()) || key.equals(u.getEmail())){
                active = u;break;
            }
        }
        
        System.out.println(active.getEmail());
        System.out.println(active.getUsername());
        return active;
        
    }
    


}
