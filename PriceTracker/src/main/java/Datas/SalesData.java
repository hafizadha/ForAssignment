
package Datas;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SalesData {
    private String date;
    private String premiseCode;
    private String itemCode;
    private double price;
    
    public SalesData(){}

    public SalesData(String date, String premiseCode, String itemCode, double price) {
        this.date = date;
        this.premiseCode = premiseCode;
        this.itemCode = itemCode;
        this.price = price;
    }
    
    public SalesData(String premiseCode, String itemCode, double price){
        this.premiseCode = premiseCode;
        this.itemCode = itemCode;
        this.price = price;
    }

    // Getters and setters
    public String getDate(){
        return this.date;
    }
    public String getPremiseCode(){
        return this.premiseCode;
    }
    public String getItemCode(){
        return this.itemCode;
    }
    public double getPrice(){
        return this.price;
    }
    
    public static ArrayList<SalesData> filterRelevantSalesData(ArrayList<SalesData> salesList, String selectedItemCode){
        ArrayList<SalesData> relevantSalesData = new ArrayList<>();
        
        for (SalesData eachSalesData : salesList) {
            if (eachSalesData.getItemCode().equals(selectedItemCode)){
                relevantSalesData.add(eachSalesData);
            }
        }
        
        return relevantSalesData;
    }
    
    public static void sortRelevantSalesData(ArrayList<SalesData> relevantSalesData){
        relevantSalesData.sort(Comparator.comparingDouble(SalesData::getPrice));
    }
    
    public static ArrayList<SalesData> filterLatestPrice(List<SalesData> salesDataListNotSorted){
        // Assuming salesDataList is originally an immutable list (created with List.of())
        List<SalesData> salesDataListSorted = new ArrayList<>(salesDataListNotSorted);
        // Sorting salesDataList by date in ascending order
        Collections.sort(salesDataListSorted, Comparator.comparing(SalesData::getDate));
        
        Map<String, Double> latestPriceMap = new HashMap<>();

        // Iterate through the sorted salesDataList in reverse order
        for (int i = salesDataListSorted.size() - 1; i >= 0; i--) {
            SalesData eachRow = salesDataListSorted.get(i);
            String premiseItemKey = eachRow.getPremiseCode() + "|" + eachRow.getItemCode();

            // Skip duplicates and only keep the latest price for each premiseItemKey
            if (!latestPriceMap.containsKey(premiseItemKey)) {
                latestPriceMap.put(premiseItemKey, eachRow.getPrice());
            }
        }

        // Create the latestPriceDataList
        ArrayList<SalesData> latestPriceDataList = new ArrayList<>();

        for (String premiseItemKey : latestPriceMap.keySet()) {
            String[] codes = premiseItemKey.split("\\|");
            String eachPremiseCode = codes[0];
            String eachItemCode = codes[1];

            double latestPrice = latestPriceMap.get(premiseItemKey);
            SalesData salesData = new SalesData(eachPremiseCode, eachItemCode, latestPrice);
            latestPriceDataList.add(salesData);
        }
        
        
        return latestPriceDataList;
    }

  
    
}

