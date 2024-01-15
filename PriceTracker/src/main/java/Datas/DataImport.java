/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datas;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class DataImport {

    public static HashMap<String, ItemData> readItemData(String filePath){
        HashMap<String, ItemData> itemDataMap = new HashMap<>();

        try {
            CSVReader csvReader = new CSVReader(new FileReader(filePath)); 
            List<String[]> data = csvReader.readAll();

            // Assuming the first row is a header, skip it
            for (int i = 2; i < data.size(); i++) {
                String[] record = data.get(i);
                String itemCode = record[0];
                ItemData itemData = new ItemData(record[0],record[1], record[2], record[3], record[4]);
                itemDataMap.put(itemCode, itemData);
            }
        } catch(CsvException e){
            System.out.println("Error reading CSV file. Please check the file format.");
        } catch(IOException e){
            System.out.println("Problem with file input.");
        }

        return itemDataMap;
    }

    public static HashMap<String, PremiseData> readPremiseData(String filePath){
        HashMap<String, PremiseData> premiseDataMap = new HashMap<>();

        try{
            CSVReader csvReader = new CSVReader(new FileReader(filePath));
            List<String[]> data = csvReader.readAll();

            // Assuming the first row is a header, skip it
            for (int i = 2; i < data.size(); i++) {
                String[] record = data.get(i);
                String premiseCode = record[0];
                PremiseData premiseData = new PremiseData(record[0], record[1], record[2], record[3], record[4], record[5]);

                premiseDataMap.put(premiseCode, premiseData);
            }
        } catch(CsvException e){
            System.out.println("Error reading CSV file. Please check the file format.");
        } catch(IOException e){
            System.out.println("Problem with file input.");
        }
        
        return premiseDataMap;
    }

    public static List<SalesData> readSalesData(String filePath){
          
        List<SalesData> salesDataList = null;

        try {
            CSVReader csvReader = new CSVReader(new FileReader(filePath));
            salesDataList = csvReader.readAll().stream()
                    .skip(1) // Assuming the first row is a header, skip it
                    .map(record -> new SalesData(record[0], record[1], record[2], Double.parseDouble(record[3])))
                    .toList();
        } catch(CsvException e){
            System.out.println("Error reading CSV file. Please check the file format.");
        } catch(IOException e){
            System.out.println("Problem with file input.");
        }

        return salesDataList;
    }

    public static void removeMissingValues(HashMap<String, ItemData> itemdatamap, List<SalesData> sales) {
        HashSet<String> itemCodesCopy = new HashSet<>(itemdatamap.keySet()); //create copy to avoid error

        for (String itemCode : itemCodesCopy) {
            boolean itemFound = false;

            for (SalesData salesData : sales) {
                if (salesData.getItemCode().equals(itemCode)) {
                    itemFound = true;
                    break;
                }
            }

            if (!itemFound) {
                // If ItemCode is not found in sales, remove the corresponding ItemData from itemdatamap
                itemdatamap.remove(itemCode);
            }
        }
    }
    
    public static void ModifyItem(HashMap<String, ItemData> itemdatamap,String code,String itemname){
        for(ItemData id : itemdatamap.values()){
            if(id.getItemCategory().equals(code)){
                id.setItemName(itemname);
            }
        }
    }
    
}
