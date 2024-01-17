/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ShoppingCart;

import Datas.ItemData;
import Datas.PremiseData;
import Datas.SalesData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Hafiz
 */
public class FindShop {


    public static List<String> getAllStates(Map<String, PremiseData> prem){
        ArrayList<String> states = new ArrayList<>();

        for(PremiseData prdata: prem.values()){
            states.add(prdata.getState());

        }

        List<String> unistates = states.stream().distinct().sorted().collect(Collectors.toList());
        return unistates;
    }

    public static ArrayList<String> filterByState(String state,Map<String, PremiseData> prem){

        ArrayList<String> filtered = new ArrayList<>();
        
        for(PremiseData prdata: prem.values()){
            if(prdata.getState().equals(state)){
                filtered.add(prdata.getPremiseCode());
            }
        }

        return filtered;
    }

    
    public static Map<String, List<String>> getStoreItem(List<SalesData> salesDataList){
        Map<String, List<String>> storeItemMap = new HashMap<>();

        for (SalesData salesData : salesDataList) {
            String premiseCode = salesData.getPremiseCode() + ".0";

            // Check if the premise code is already in the map
            if (storeItemMap.containsKey(premiseCode)) {
                // If it's present, add the item code to the existing list
                List<String> itemList = storeItemMap.get(premiseCode);
                itemList.add(salesData.getItemCode());
            } else {
                // If it's not present, create a new list and add the item code
                List<String> itemList = new ArrayList<>();
                itemList.add(salesData.getItemCode());
                storeItemMap.put(premiseCode, itemList);
            }
        }
        
        return storeItemMap;
    }
    
    public static ArrayList<SalesData> filterByItemList(ArrayList<SalesData> salesDataList, List<String> cart, SalesData salesMethod){
        ArrayList<SalesData> relevantData = new ArrayList<>();
        
        for(String item: cart){
            relevantData.addAll(salesMethod.filterRelevantSalesData(salesDataList,item));
        }
        
        return relevantData;
    }
    
    public static ArrayList<ShopCartStore> getListCombinationMainStore(ArrayList<String> premiseByState, Map<String, List<String>> storeItemMap, ArrayList<String> cart,ArrayList<SalesData> relevantSalesDataForSc, ArrayList<ShopCartStore> listCombinationMainStore){
        
        for (String currentMainStore : premiseByState) {
            // filter item yang kedai tu jual
            ArrayList<String> listItemSell = (ArrayList<String>) storeItemMap.getOrDefault(currentMainStore, new ArrayList<>()); 
            //getOrDefault() method is used to return a default value if the key is not present in map

            ArrayList<String> listItemAvailable = new ArrayList<>();
            ArrayList<String> listItemNotAvailable = new ArrayList<>();
            double sumPriceOfAvailable = 0.0;
            
            if(listItemSell.containsAll(cart)){
                listItemAvailable = cart;
                for (String eachItem : cart) {
                    for(SalesData eachSalesData: relevantSalesDataForSc){
                        //calculate the total price of the data
                        if(currentMainStore.equals(eachSalesData.getPremiseCode() + ".0")&& eachSalesData.getItemCode().equals(eachItem)){
                            sumPriceOfAvailable += eachSalesData.getPrice();
                        }
                    }
                }
            }else{
                for (String eachItem : cart) {
                    if (listItemSell.contains(eachItem)) {
                        listItemAvailable.add(eachItem);
                        for(SalesData eachSalesData: relevantSalesDataForSc){
                            //calculate the total price of the data
                            if(currentMainStore.equals(eachSalesData.getPremiseCode() + ".0")&& eachSalesData.getItemCode().equals(eachItem)){
                                sumPriceOfAvailable += eachSalesData.getPrice();
                            }
                        }
                    } else {
                        listItemNotAvailable.add(eachItem);
                    }
                }

            }
          
            ShopCartStore mainStore = new ShopCartStore(currentMainStore, listItemAvailable, listItemNotAvailable, sumPriceOfAvailable);
            listCombinationMainStore.add(mainStore);
        }
        return listCombinationMainStore;
    }
    
    public static ArrayList<ShopCartStore> getListCombinationSubStore(ArrayList<String> premiseByState, Map<String, List<String>> storeItemMap, ArrayList<String> remainingItemsCopy,ArrayList<SalesData> relevantSalesDataForSc, ArrayList<ShopCartStore> listCombinationSubStore){
        for (String currentSubStore : premiseByState) {
            // filter item yang kedai tu jual
            ArrayList<String> listItemSell = (ArrayList<String>) storeItemMap.getOrDefault(currentSubStore, new ArrayList<>()); 
            //getOrDefault() method is used to return a default value if the key is not present in map

            ArrayList<String> listItemAvailable = new ArrayList<>();
            ArrayList<String> listItemNotAvailable = new ArrayList<>();
            double sumPriceOfItem = 0.0;
            
            if(listItemSell.containsAll(remainingItemsCopy)){
                listItemAvailable = remainingItemsCopy; 
                for (String eachItem : remainingItemsCopy) {
                    for(SalesData eachSalesData: relevantSalesDataForSc){
                        //calculate the total price of the data
                        if(currentSubStore.equals(eachSalesData.getPremiseCode() + ".0")&& eachSalesData.getItemCode().equals(eachItem)){
                            sumPriceOfItem += eachSalesData.getPrice();
                        }
                    }
                }
            }else{
                for (String eachItem : remainingItemsCopy) {
                    if (listItemSell.contains(eachItem)) {
                        listItemAvailable.add(eachItem);
                        for(SalesData eachSalesData: relevantSalesDataForSc){
                            //calculate the total price of the data
                            if(currentSubStore.equals(eachSalesData.getPremiseCode() + ".0")&& eachSalesData.getItemCode().equals(eachItem)){
                                sumPriceOfItem += eachSalesData.getPrice();
                            }
                        }
                    } else {
                        listItemNotAvailable.add(eachItem);
                    }
                }

            }
        
                       
            ShopCartStore subStore = new ShopCartStore(currentSubStore, listItemAvailable, listItemNotAvailable, sumPriceOfItem);
            listCombinationSubStore.add(subStore);
            

        }
        
        return listCombinationSubStore;
    }
    
    
    public static int findMaxItemSell(ArrayList<ShopCartStore> listCombinationStore, int maxTotalItem){
        //get the max value of the total available items
        for (int i = 1; i < listCombinationStore.size(); i++) {
            ShopCartStore nextStore = listCombinationStore.get(i);
            int nextTotalItem = nextStore.getListAvailableItem().size();

            if (nextTotalItem > maxTotalItem) {
                maxTotalItem = nextTotalItem;
//                System.out.println("Max: "+maxTotalItem);
            }
        }
        
        return maxTotalItem;
    }
    
    
    public static void displayFindShopResult(HashMap<String, PremiseData> premiseDataMap, HashMap<String, ItemData> itemDataMap, ShopCartStore optimalMainStore, int maxTotalItem, ArrayList<ShopCartStore> finalListOptimalSub, ArrayList<String> remainingItems, String selectedState) {
        int itemNum = 0, storeNum = 1;
        double totalOverall = 0.0;

        // Display the optimal main store
        itemNum = displayShoppingResult(optimalMainStore, premiseDataMap.get(optimalMainStore.getStoreCode()), itemDataMap, itemNum, storeNum);
        totalOverall += optimalMainStore.getTotalItemsPrice();
        storeNum++;

        // Display the optimal sub stores for the remaining items
        for (ShopCartStore subStore : finalListOptimalSub) {
            if (subStore.getListAvailableItem().isEmpty()) {
                for (String nodata : remainingItems) {
                    ItemData noItemData = itemDataMap.get(nodata);
                    displayNoStoreMessage(noItemData, selectedState);
                    
                }
                break;
            }
            itemNum = displayShoppingResult(subStore, premiseDataMap.get(subStore.getStoreCode()), itemDataMap, itemNum, storeNum);
            totalOverall += subStore.getTotalItemsPrice();
            storeNum++;
        }

        // Display total overall
        System.out.println("--------------------------------------------------------------------------------------\n");
        System.out.printf("[ Total overall for %d item(s) available: RM%.2f ]\n\n", itemNum, totalOverall);
        System.out.println("--------------------------------------------------------------------------------------");
    }

    private static int displayShoppingResult( ShopCartStore store, PremiseData selectedPremise, HashMap<String, ItemData> itemDataMap, int itemNum, int storeNum) {
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("Store [" + storeNum + "] : " + selectedPremise.getPremise());
        System.out.println("Address : " + selectedPremise.getAddress());
        System.out.println("\n+ List Available Items (" + store.getListAvailableItem().size() + ") +");

        for (String eachSelectedItem : store.getListAvailableItem()) {
            ItemData selectedItem = itemDataMap.get(eachSelectedItem);
            System.out.println("[" + (itemNum+1) + "] " + selectedItem.getItem() + " (" + selectedItem.getItemUnit() + ")");
            itemNum++;
        }

        System.out.printf("\n[ Total Price: RM%.2f ]\n", store.getTotalItemsPrice());
        System.out.println();
        
        return itemNum;
    }
    
    
    
    public static void displayNoStoreMessage(ItemData item, String state){
        System.out.println("--------------------------------------------------------------------------------------\n");
        System.out.println("\t+------------------------------------------------------------------------+");
        System.out.printf("\t| Sorry. No store in [ %-17s ] was found for this item:      |\n", state);
        System.out.printf("\t| %-71s|\n",item.getItem());
        System.out.println("\t+------------------------------------------------------------------------+\n");
    }
    
}