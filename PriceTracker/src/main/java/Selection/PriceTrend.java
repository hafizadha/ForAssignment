/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Selection;

import Datas.ItemData;
import Datas.SalesData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class PriceTrend {
    
    private String itemCode;
    
    public PriceTrend(){
        itemCode = null;
    }
    
    public PriceTrend(String ic){
        itemCode = ic;
    }

    public void getPriceTrend(HashMap<String, ItemData> itemDataMap,List<SalesData> salesDataList) {
                
        int count;
        double sum,average,minAverage=Double.MAX_VALUE,maxAverage=0;
        
        ItemData selectedItemData = itemDataMap.get(itemCode);
        String selectedItemName = selectedItemData.getItem();
        String selectedItemUnit = selectedItemData.getItemUnit();
        
        System.out.println("Price Trend Chart for " + selectedItemName + " " + selectedItemUnit);
        System.out.println("---------------------------------------------------");
        
        LocalDate startDate = LocalDate.of(2023, 8, 1); //set starting date
        LocalDate endDate = LocalDate.of(2023, 8, 29); //set ending date
        LocalDate currentDate = startDate;

        //find the minimum and maximum average of all the days 
        while (!currentDate.isAfter(endDate)) { //while current date is not after ending date

            count=0;
            sum=0;
            String date=currentDate.toString(); //convert date to String format

            for (SalesData eachSalesData : salesDataList) { //read everything in SalesDataList

                String readItemCode = eachSalesData.getItemCode(); 

                if (readItemCode.equals(itemCode)) {

                    String readDate = eachSalesData.getDate();

                    if(readDate.equals(date)){
                        count++;
                        double readPrice = eachSalesData.getPrice();
                        sum+=readPrice;
                    }
                }
            }

            if (count!=0) {

                average = sum/count;

                if(average<minAverage){
                    minAverage=average;
                }
                if(average>maxAverage){
                    maxAverage=average;
                }
            }
            currentDate=currentDate.plusDays(1); //plus one day to date        
        }


        System.out.print("    Date    |    Price    ");
        System.out.print("\n--------------------------");

        //calculate average and print barchart
        LocalDate startDate1 = LocalDate.of(2023, 8, 1); 
        LocalDate endDate1 = LocalDate.of(2023, 8, 29); 
        LocalDate currentDate1 = startDate1;
        
        List<List<String>> DateAndPrice = new ArrayList<>();
        
        
        
        

        while (!currentDate1.isAfter(endDate1)) { 

            count=0;
            sum=0;
            String date=currentDate1.toString(); 
            System.out.print("\n " + date + " |");

            for (SalesData eachSalesData : salesDataList) { 

                String readItemCode = eachSalesData.getItemCode(); 

                if (readItemCode.equals(itemCode)) {

                    String readDate = eachSalesData.getDate();

                    if(readDate.equals(date)){
                        count++;
                        double readPrice = eachSalesData.getPrice();
                        sum+=readPrice;
                    }
                }
            }

            if (count!=0) {
                List<String> inner = new ArrayList<>();
                average=sum/count;
                String avgs = String.valueOf(average);
                System.out.print(" ");
                
                inner.add(date);
                inner.add(avgs);
                DateAndPrice.add(inner);
                
                barChart(average,minAverage,maxAverage);
                
                System.out.printf("(%.2f)" , average);
            }
            currentDate1 = currentDate1.plusDays(1);        
        }

        System.out.println("\n\nScale:");
        System.out.println("$ = RM0.10");            
    }   
    
    public void barChart(double average, double minAverage, double maxAverage) {

        double scaleFactor=((average-minAverage)/0.10)+1; //calculate how many $ to be printed
        scaleFactor=Math.round(scaleFactor); //round up if decimal is more than or equal to 5
        
        for (int i=1; i<=scaleFactor; i++) {
            System.out.print("$");
        }
        
        double maxScaleFactor=((maxAverage-minAverage)/0.10)+1;       
        double difference=maxScaleFactor-scaleFactor; 
        
        for (int j=0; j<=difference+1; j++) {
            System.out.print(" ");
        }
    }
    
}