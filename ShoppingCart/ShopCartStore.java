/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ShoppingCart;

import java.util.ArrayList;

public class ShopCartStore {
    private String storeCode;
    private ArrayList<String> listAvailableItem;
    private ArrayList<String> listNotAvailableItem;
    private double totalItemsPrice;
    
    public ShopCartStore(){}
    
    public ShopCartStore(String storeCode, ArrayList<String> listAvailableItem, ArrayList<String> listNotAvailableItem, double totalItemsPrice){
        this.storeCode = storeCode;
        this.listAvailableItem = listAvailableItem;
        this.listNotAvailableItem = listNotAvailableItem;
        this.totalItemsPrice = totalItemsPrice;
    }

    //acessor
    public String getStoreCode(){
        return this.storeCode;
    }
    public ArrayList<String> getListAvailableItem(){
        return this.listAvailableItem;
    }
    public ArrayList<String> getListNotAvailableItem(){
        return this.listNotAvailableItem;
    }
    public double getTotalItemsPrice(){
        return this.totalItemsPrice;
    } 
    
    //mutator
    public void setStoreCode(String storeCode){
        this.storeCode = storeCode;
    }
    public void setTotalItemsPrice(double totalItemsPrice){
        this.totalItemsPrice = totalItemsPrice;
    }
    
}