
package LoginandRegister;


import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Session;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;

public class EmailVerification {
    
    //use JavaMail API to send email to user
    public static void sendMail(String receipient,int generatedCode) throws Exception{
        

        System.out.println("A verification code will be sent to your e-mail. Please wait...");
        
        Properties properties = new Properties(); //set properties for Gmail SMTP server (server information and authentication settings)
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); 
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        
        String sendFrom = "mindsweeperPT@gmail.com";
        String password = "rfgj twtj uots puyr"; //this is a password for JavaMail to access the SMTP server, the actual password for the gmail is "mindsweeper123"
        
        Session session = Session.getInstance(properties, new Authenticator(){ //create session object to authenticate our email and password
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(sendFrom, password);
            }   
        });
        
        Message message = emailContent(session, sendFrom, receipient, generatedCode);
        
        Transport.send(message); //Send email with message
        
        System.out.println("\nE-mail sent successfully!");
        System.out.println("Please check your spam if the e-mail does not appear.");
    }
    
    //set email content 
    private static Message emailContent(Session session, String sendFrom, String receipient, int generatedCode){
        try {
            Message message = new MimeMessage(session); //create message object
            message.setFrom(new InternetAddress(sendFrom)); //since it is an email address so need to use InternetAddress class by JavaMail 
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(receipient)); 
            message.setSubject("Verification Code From Price Tracker");
            message.setText("Dear user, your verification code is " + generatedCode + ". \nThank you for using Price Tracker by Mindsweeper.");
            return message;
        } catch (Exception ex) {
            Logger.getLogger(EmailVerification.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    //verify code input by user
    public static boolean verifyCode(int inputCode, int generatedCode){
        if(inputCode==generatedCode){
            return true;
        }else
            return false;
    }
    
}
