/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainMenuFunction;

import java.util.ArrayList;
import java.util.List;

public class SearchFunction {
    private List<List<String>> Filtereditems;
    private int maxsimilarwords; 
    
    public SearchFunction(){
        maxsimilarwords =0;
        Filtereditems = new ArrayList<>();
    }

    //the Maximum of equal/similar words between input string and target string for deeper filtering
   //E.g Ayam Masak Merah and Ayam Masak Lemak. Total similar words in both strings: 2
    
    public void Search(String inp,String target,String code,String unit){
        String t = target.toLowerCase();
        inp = inp.toLowerCase();
        
        String[] a = inp.split(" ");
        String[] b = t.split(" ");
        boolean isMatch = false;

        
        int similarwords = 0;
        for(int i =0;i<a.length;++i){
            for(int j =0;j<b.length;j++){
                boolean wordMatch = StringMatch(a[i],b[j]);
                if(wordMatch){
                    ++similarwords;
                    isMatch = true;
                }
            }
        }

        if(similarwords>maxsimilarwords){
            maxsimilarwords = similarwords;
        }  
        

        if(isMatch){
            List<String> inner = new ArrayList<String>();
            inner.add(String.valueOf(similarwords));
         inner.add(code);
            inner.add(target);
            inner.add(unit);
            
            Filtereditems.add(inner);
        }
    }
    
    public void PrintResult(String key){

        System.out.printf("Possible search for %s\n",key);
        System.out.println("-------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-6s%-95s%-10s\n","Code","Item","Unit");
        System.out.println("-------------------------------------------------------------------------------------------------------------");
        
        String s = String.valueOf(maxsimilarwords);
        String s2 = String.valueOf(maxsimilarwords-1);
        
        
        
        
        System.out.println("Most accurate result(s): ");
        for(List<String> row: Filtereditems){
            if(row.get(0).equals(s)){
                System.out.printf("%-6s%-95s%-10s\n",row.get(1),row.get(2),row.get(3));
            }
        }
        System.out.println("\n-------------------------------------------------------------------------------------------------------------");
        System.out.println("Other possible result: ");
        for(List<String> row: Filtereditems){
            if(row.get(0).equals(s2)){
                System.out.printf("%-6s%-95s%-10s\n",row.get(1),row.get(2),row.get(3));
            }
        }
        
        
        
        
        
        
    }
    
    public String GetItem(String code){
        String item;
        for(List<String> row:Filtereditems){
            if(row.get(1).equals(code)){
                item = row.get(2);
                return item;
            }  
        }
        
        return null;
    }
    
    
    public boolean StringMatch(String x,String y){
        boolean isMatch = false;
        int Longer;
        
        if(x.length()>y.length()){
            Longer = x.length();
        }
        else
            Longer = y.length();
            
        int Editdis = calcLeven(x,y);
        
        
        double similarityrate = (Longer - Editdis) /(double) Longer;
        if(similarityrate >= 0.75){
            isMatch =true;
        }
        return isMatch;
    }
    
    
    
    //Application of fuzzy search (Allow typos / partial search)
    public int calcLeven(String x,String y){ 
        //String x: Input string 
        //String y: Target string
        
        int editDistance; 
        
        //Convert all characters to lowercase to minimize differences
        x.toLowerCase();         y.toLowerCase();
        
        
        //If x is empty, then the edit distance is equal to y ( target string )
        if(x.isEmpty()){
            return editDistance = y.length();
        }
        
        
        int[][] levenmatrix = new int[x.length()+1][y.length()+1];
        
        for(int i=0;i<x.length()+1;i++){
            for(int j=0;j<y.length()+1;j++){
                if(Math.min(i, j) == 0){
                    levenmatrix[i][j] = Math.max(i, j);
                }
                else{
                    int ins = levenmatrix[i-1][j] + 1;
                    int del = levenmatrix[i][j-1] +1;
                    
                    int conditionval;
                    if(x.charAt(i-1) != y.charAt(j-1)){
                        conditionval = 1;
                    }
                    else{
                        conditionval = 0;
                    }
                    
                    int replace = levenmatrix[i-1][j-1] + conditionval;
                    
                    levenmatrix[i][j] = Math.min(ins,Math.min(del, replace));
                    
                }    
            }
        }

        
       
                
        editDistance = levenmatrix[x.length()][y.length()];
        return editDistance;
    }
    
    
}