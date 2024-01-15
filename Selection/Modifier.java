/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Selection;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
public class Modifier {
    
        public void ModifyItem(String itemcode,String newdetail,int para){
            boolean modifyName = false;
            boolean modifyUnit = false;
            if(para == 1){
                modifyName = true;
            }
            else if(para == 2){
                modifyUnit = true;
            }
            
            String code,name,unit,group,category;
            
            
            
            try{
                String filepath = "C:\\Users\\Hafiz\\Documents\\NetBeansProjects\\PriceTracker\\src\\main\\java\\resources\\lookup_item.csv";
                File itemData = new File(filepath);
                //Read existing lookup_item csv file
                CSVReader csvReader = new CSVReader(new FileReader(filepath)); 
                List<String[]> data = csvReader.readAll();
                
                if(modifyName){
                    for(String[] x: data){
                        String[] row = x;
                        if(row[0].equals(itemcode)){
                            row[1] = newdetail;break;
                        }
                    }
                }
                else if(modifyUnit){
                    for(String[] x: data){
                        String[] row = x;
                        if(row[0].equals(itemcode)){
                            row[2] = newdetail;break;
                        }
                    }
                }
                
                csvReader.close();
                CSVWriter writer = new CSVWriter(new FileWriter(filepath));
                writer.writeAll(data);
                writer.flush();
                writer.close();
                
                
                System.out.println("Item Modified!");
                if(modifyName){
                   
                    System.out.println("Name changed to " + newdetail);
                }
                else{
                    System.out.println("Unit changed to " + newdetail);
                }
                System.out.println("----------------------------------------------------");
            }
            catch(CsvException e){
                e.printStackTrace();
            }    
            catch(IOException e){
                e.printStackTrace();
            }
    }
}
