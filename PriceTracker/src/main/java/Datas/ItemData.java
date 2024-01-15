/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datas;



public class ItemData {
    private String itemCode;
    private String item;
    private String unit;
    private String itemGroup;
    private String itemCategory;
    
    public ItemData(){}

    public ItemData(String itemCode, String item, String unit, String itemGroup, String itemCategory) {
        this.itemCode = itemCode;
        this.item = item;
        this.unit = unit;
        this.itemGroup = itemGroup;
        this.itemCategory = itemCategory;
    }

    // Getters and setters
    public String getItemCode(){
        return this.itemCode;
    }
    public String getItem(){
        return this.item;
    }
    public String getItemUnit(){
        return this.unit;
    }
    public String getItemCategory(){
        return this.itemGroup;
    }
    public String getItemSubCategory(){
        return this.itemCategory;
    }
    public void setItemName(String itemname){
        this.item = itemname;
    }
    public void setItemUnit(String newunit){
        this.unit = newunit;
    }
    
    public static void displayItemDetails(ItemData item){
        System.out.println(" Item Details Page");
        System.out.println("-----------------------------------");
        System.out.println("Selected item { "+item.getItem()+" ]");
        System.out.println("\n+---------------------------------------------------+---------------------------------------------------+");
        System.out.printf("| Code: %-44s| Unit: %-44s|\n", item.getItemCode(), item.getItemUnit());
        System.out.println("+---------------------------------------------------+---------------------------------------------------+");
        System.out.printf("| Category: %-40s| SubCategory: %-37s|\n", item.getItemCategory(), item.getItemSubCategory());
        System.out.println("+---------------------------------------------------+---------------------------------------------------+");

    }
    
}