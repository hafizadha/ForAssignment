/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainMenuFunction;
import Datas.ItemData;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author Hafiz
 */
public class BrowseCategories {
    public static HashSet<String> getUniqueCategories(Map<String, ItemData> itemDataMap) {
        HashSet<String> uniqueCategories = new HashSet<>();

        for (ItemData itemData : itemDataMap.values()) {
            uniqueCategories.add(itemData.getItemCategory());
        }

        return uniqueCategories;
    }
    
    public static HashSet<String> getUniqueSubCategories(Map<String, ItemData> itemDataMap, String selectedCategories) {
        HashSet<String> uniqueSubCategories = new HashSet<>();

        for (ItemData testing : itemDataMap.values()) {
            if(testing.getItemCategory().equals(selectedCategories)){
                uniqueSubCategories.add(testing.getItemSubCategory());
            }
        }

        return uniqueSubCategories;
    }
}

