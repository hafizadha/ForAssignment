/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Selection;

import Datas.ItemData;
import Datas.PremiseData;
import Datas.SalesData;
import static PriceTracker.PriceTracker.itemDataMap;
import static PriceTracker.PriceTracker.premiseDataMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

/**
 *
 * @author Hafiz
 */
public class Display {
    
    
    
    public static void displayTopFiveCheapest(ItemData selectedItemData, ArrayList<SalesData> latestPriceDataList, HashMap<String,PremiseData> premiseDataMap, Scanner sc){
        
        SalesData salesMethod = new SalesData();

        // Filter the relevant sales data
        ArrayList<SalesData> relevantSalesData = salesMethod.filterRelevantSalesData(latestPriceDataList,selectedItemData.getItemCode());

//        if(!relevantSalesData.isEmpty()){
        try{
            // Sort the relevant sales data based on price in ascending order
            salesMethod.sortRelevantSalesData(relevantSalesData);

//            // user can enter area of the store they want
//            String state = getUserState(sc);

            // display the five cheapest seller for the selected item
            displayTopFiveDetails(selectedItemData, relevantSalesData, premiseDataMap);
            
        }catch(Exception e){
            displayNoDataMessage(selectedItemData);
        }
    }
    
    public static void displayTopFiveDetails(ItemData item, ArrayList<SalesData> relevantSalesData, HashMap<String,PremiseData> premiseMap){
        
        // Limit to the top 5 records
        int limit = 5;
        HashSet<String> displayedPremises = new HashSet<>();
       
        System.out.println("+------------------------------------------------------------------------------------------------------------+");
        System.out.println("|                                      TOP FIVE CHEAPEST SELLER                                              |");
        System.out.println("+------------------------------------------------------------------------------------------------------------+");
        System.out.printf("|Item: %-102s|\n",item.getItem());
        System.out.printf("|Unit: %-102s|\n",item.getItemUnit());
        System.out.println("+------------------------------------------------------------------------------------------------------------+");

        String[] positions = {"[1st]","[2nd]","[3rd]","[4th]","[5th]"};

        // display the top five arraylist fo the ascending order
        for (int i = 0, j = 0; i < limit; i++,j++) {
            SalesData eachSalesData = relevantSalesData.get(j);
            
            // Check if the premise code has already been displayed
            if (displayedPremises.contains(eachSalesData.getPremiseCode())) {
                i--;
                continue;
            }
            
            // get premise data for the spesific price
            PremiseData eachPremiseData = premiseMap.get(eachSalesData.getPremiseCode() + ".0");
            
//            // check the state of the premise to only display the store in that state
//            if(!eachPremiseData.getState().equals(state)){
//                i--;
//                continue;
//            }

            System.out.printf("\n| %-5s",positions[i]);
            System.out.printf("\n| Price:  RM%.2f",eachSalesData.getPrice());
            System.out.printf("\n| Seller: %-52s",eachPremiseData.getPremise());
            System.out.printf("\n| Address: %-50s",eachPremiseData.getAddress());
            System.out.printf("\n| State: %-50s",eachPremiseData.getState());
//            System.out.printf("\n| Date: %10s",eachSalesData.getDate());
            System.out.println("\n\n+-----------------------------------------------------------------------------------------------------------+");
            
            // Add premise code to the set of displayed premises
            displayedPremises.add(eachSalesData.getPremiseCode());
        }
    }
    
    public static void displayNoDataMessage(ItemData item){
        System.out.println("Sorry. No data was found for this item: ");
        System.out.printf(" %-54s\n",item.getItem());
    }
    
    public static void displayCheapestSellerSCItem(ArrayList<String> itemsInCart, SalesData salesMethod, ArrayList<SalesData> latestPriceDataList){
        //display items in cart with cheapest seller
        System.out.println("Cheapest Seller For Items in Cart");
        System.out.println("------------------------------------------\n");
        for(String cart :  itemsInCart){            
            ArrayList<SalesData> relevantSalesData = salesMethod.filterRelevantSalesData(latestPriceDataList,cart);
            salesMethod.sortRelevantSalesData(relevantSalesData);
            
            ItemData addedItemData = itemDataMap.get(cart);
            String addedItemName = addedItemData.getItem();
            String addedItemUnit = addedItemData.getItemUnit();
            
            SalesData cheapestPriceData = relevantSalesData.get(0);
            double cheapestPrice = cheapestPriceData.getPrice();
            
            PremiseData cheapestSellerData = premiseDataMap.get(cheapestPriceData.getPremiseCode()+".0");
            String cheapestSellerName = cheapestSellerData.getPremise();
            String cheapestSellerAddress = cheapestSellerData.getAddress();

            System.out.println("+------------------------------------------------------------------------------------------------------------+");
            System.out.printf("| Item: %-101s|\n",addedItemName);
            System.out.printf("| Unit: %-101s|\n", addedItemUnit);
            System.out.printf("| Price: RM%-98.2f|\n", cheapestPrice);
            System.out.printf("| Seller: %-99s|\n", cheapestSellerName);
            System.out.printf("| Address: %-98s|\n", cheapestSellerAddress);
            System.out.println("+------------------------------------------------------------------------------------------------------------+\n");
            
        }
    }
}
