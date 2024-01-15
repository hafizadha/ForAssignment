package PriceTracker;

import ShoppingCart.*;
import MainMenuFunction.*;

import LoginandRegister.*;
import Datas.*;
import LoginandRegister.*;
import Selection.*;


import java.util.*;
import java.util.InputMismatchException;

public class PriceTracker {
    public static HashMap<String, ItemData> itemDataMap;
    public static HashMap<String, PremiseData> premiseDataMap;
    public static List<SalesData> salesDataList;
    
    public static void main(String[] args) throws InputMismatchException, Exception {
        
        Scanner MainScn = new Scanner(System.in);
        
        //Creating Objects from Java Classes
        MainScreen ms = new MainScreen();
        RegisterSystem RegS = new RegisterSystem();
        LoginSystem LogS = new LoginSystem();
        SCinfo sci = new SCinfo();
        EmailVerification ev = new EmailVerification();
        //Set active username and password ( Current user ) once successfully login
       
        
        boolean SystemOpen = true; //The whole system will run if the boolean is true.

        //Front Page
        boolean InMainScr = true;
        boolean display = true;
       
        ms.ImportRegUserSQL(); //Import registered user database (for login validation)
        
        
        do{ //Running the whole system
            UserInfos activeaccount;
            String activeuser = "";
            String activepass = "";
            String activeemail = "";
            while(InMainScr){ //While in front page (Login/Register page)
                if(display){
                MainScreen.DisMain();//.DisMain() Display options for user
                }

                int key = AskValidKey(3);

                //Login Page
                if(key == 1){ 
                    pageBreaker();
                    System.out.println(" Login page");
                    System.out.println("-----------------");
                    System.out.println("Enter username: ");
                    String userkey = MainScn.nextLine();
                    System.out.println("Enter password: ");
                    String passkey = MainScn.nextLine();

                    //LogS.UserValid method returns True if the username matches its password,otherwise, False
                    boolean isValid = LogS.UserValid(userkey,passkey); 

                    if(isValid == false){
                        System.out.println("Incorrect Username or Password");
                    }
                    while(isValid == false){
                        System.out.println("[1] Retry ");
                        System.out.println("[2] Login with E-mail");
                        System.out.println("[3] Back");
                        key = AskValidKey(3);

                        if(key==1){
                            System.out.println("Enter username: ");
                            userkey = MainScn.nextLine();
                            System.out.println("Enter password: ");
                            passkey = MainScn.nextLine();
                            isValid = LogS.UserValid(userkey,passkey);

                            if(isValid == false)
                                System.out.println("Incorrect Username or Password");
                            else{
                                InMainScr = false;break;
                            }
                        }
                        else if(key == 2){
                            System.out.println("Enter registered e-mail: ");
                            String userEmail = MainScn.nextLine();
                            
                            //CheckEmailExist() receives email from user and check wheter if its exist. It will return True if the email existed in the database
                            boolean emailexists =  LogS.CheckEmailExist(userEmail);
                            
                            
                            //If email doesn't exist, the system will ask the user to input an existing email use the same CheckEmailExist() again.
                            //If the user enter "-1", it will return back to the Main Screen
                            while(emailexists == false){
                                
                                System.out.println("[Email entered is not registered into an account!]");
                                System.out.println("Enter registered e-mail <Enter -1 to return back>: ");
                                
                                if(userEmail.equals("-1")){
                                    break;
                                }
                                userEmail = MainScn.nextLine();  
                                emailexists = LogS.CheckEmailExist(userEmail);
                            }
                            
                            
                            //If the email does exist, then the system will send a verification code to the user's email
                            if(emailexists){
                                int generatedCode = generateCode(); //this method will generate a random value between two constant value
                                ev.sendMail(userEmail,generatedCode);//This method will send the verification code to the user's email
                                
                                System.out.println("\nPlease enter the verification code:");
                                int inputCode = MainScn.nextInt();
                                if(inputCode == -1){//System will return back to Main Screen if the user inputs -1
                                    break;
                                }
                            
                                boolean validCode = ev.verifyCode(inputCode, generatedCode); 
                                //verify code input by user. The code entered must be the same as the generatred code
                            
                                //Ask again if the code is incorrect. Enter -1 to escape
                                while(validCode == false){
                                    System.out.println("Wrong verification code! Please try again:");
                                    inputCode = MainScn.nextInt();
                                    if(inputCode == -1){
                                    break;
                                    }
                                    validCode = ev.verifyCode(inputCode, generatedCode);
                                }
                                
                                
                                activeaccount = LogS.SetActiveUser(userEmail); //.SetActiveUser method retuns the specific user account containing the details if either the email or username matches (parameters)
                                activeuser = activeaccount.getUsername();
                                activepass = activeaccount.getPassword();
                                activeemail = activeaccount.getEmail();
                                InMainScr = false;break;
                            }
                        }
                        
                        else if(key == 3){
                            display = true;
                            System.out.println("\n");break;
                        }
                    }

                    //If password and username are valid, then break out of the InMainScr loop
                    if(isValid){
                        activeaccount = LogS.SetActiveUser(userkey);
                        activeuser = activeaccount.getUsername();
                        activepass = activeaccount.getPassword();
                        activeemail = activeaccount.getEmail();
                        InMainScr = false;break;
                    }
                }
            
            
            
                //Register Page
                else if(key == 2){
                    boolean inregpage = true;
                    pageBreaker();
                    System.out.println(" Register Page");
                    System.out.println("------------------------------------------------------------");
                    System.out.println("- Username and Password must be between 2-12 characters");
                    System.out.println("- Username and Password must not have blank space");
                    System.out.println("------------------------------------------------------------");
                    System.out.println("[1] OK");
                    System.out.println("[2] Back");
                    System.out.println("------------------------------------------------------------");
                    key = AskValidKey(2);


                    while(inregpage){       
                        if(key == 1){
                            
                            //First, ask for username
                            System.out.println("Enter Username <Enter -1 to return back>: ");
                            String newusername = MainScn.nextLine();
                            if(newusername.equals("-1")){
                                break;
                            }
                            boolean usernameexist = RegS.CheckUserExist(newusername);
                            
                            boolean usernamevalid = ms.CheckValidity(newusername); //ms.CheckValidity(input) to check wheter input meet specific condition(s), if yes, return True

                            //While the username is invalid, ask user to input a valid input
                            while(usernamevalid == false || usernameexist){ 
                                if(usernameexist){
                                    System.out.println("Username has already existed");
                                }
                                else if(usernamevalid == false){
                                    System.out.println("Invalid username!");
                                }
                                System.out.println("Please enter username ");
                                newusername = MainScn.nextLine();
                                
                                usernameexist = RegS.CheckUserExist(newusername);
                                usernamevalid = ms.CheckValidity(newusername);
                            }
                            
                            //Ask for password
                            System.out.println("Enter Password <Enter -1 to return back>: ");
                            String newpassword = MainScn.nextLine();
                            boolean passwordvalid = ms.CheckValidity(newpassword);

                            while(passwordvalid == false){
                                System.out.println("Invalid password! Please enter valid password: ");
                                newpassword = MainScn.nextLine();
                                passwordvalid = ms.CheckValidity(newpassword);
                            }

                            //Verification 
                            System.out.println("Verification required to register a new account. Please enter your e-mail address.");
                            String userEmail = MainScn.nextLine();
                            
                            boolean EmailExists = RegS.CheckEmailExist(userEmail);
                            
                            while(EmailExists){
                                System.out.println("Email has already registered into another account");
                                System.out.println("Please enter your e-mail address: ");
                                userEmail = MainScn.nextLine();
                                EmailExists = RegS.CheckEmailExist(userEmail);
                                
                            }
                            
                            
                            int generatedCode = generateCode(); //generate random verification to send to user
                            ev.sendMail(userEmail,generatedCode); //send verification code email by calling sendMail method in EmailVerification class
                            
                            System.out.println("\nPlease enter the verification code <Press -1 to return>:");
                            int inputCode = MainScn.nextInt();
                            if(inputCode == -1){
                                    break;
                                }
                            
                            boolean validCode = ev.verifyCode(inputCode, generatedCode); //verify code input by user
                            
                            while(validCode == false){
                                System.out.println("Wrong verification code! Please try again<Press -1 to return>:");
                                
                                inputCode = MainScn.nextInt();
                                if(inputCode == -1){
                                    break;
                                }
                                validCode = ev.verifyCode(inputCode, generatedCode);
                            }

                            RegS.SetUserInfos(newusername,newpassword,userEmail); //Send new username,password and email for registration
                            RegS.UpdateUserSQL(); //Update Registered User SQL

                            sci.CreateSC(newusername);
                            ms.ImportRegUserSQL();
                            break;
                            }
                        else if(key == 2){ //Go back to Main Screen
                            display = true;
                            System.out.println("");
                            break;  
                        }
                    }
                }

                else if(key == 3){ //Close the system if skey is Close ( or similar to ) or "3"
                    System.out.println("Program Closed.");
                    System.exit(0);
                }
                else{
                    display = false;
                    continue;
                }    
        }
        
        ItemData itemMethod = new ItemData();
        SalesData salesMethod = new SalesData();    
        
        //Start of main menu
        pageBreaker();
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nLoading data...\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        
        //Importing necessary databases (items,premises,pricecatcher and shopping cart);
        DataImport di = new DataImport();

        // Read the first dataset
        itemDataMap = di.readItemData("src\\main\\java\\resources\\lookup_item.csv");

        // Read the second dataset
        premiseDataMap = di.readPremiseData("src\\main\\java\\resources\\lookup_premise.csv");

        // Read the third dataset
        salesDataList = di.readSalesData("src\\main\\java\\resources\\pricecatcher_2023-08.csv");

        di.removeMissingValues(itemDataMap, salesDataList);

        
        ArrayList<SalesData> latestPriceDataList = salesMethod.filterLatestPrice(salesDataList);        
        
        pageBreaker();
        System.out.println(" Hello " + activeuser +", Welcome To Price Tracker!");
        
        boolean InMainMenu = true;
        
        ItemData selectedItemData = new ItemData();
        
        
        display = true;
        
        while(InMainMenu){
            boolean InSearch = false;
            boolean InCategories = false;
            boolean InSettings = false;
            boolean InShopCart = false;
            boolean inSelection = false; 
            
            if(display){
                pageBreaker();
                System.out.println(" Main Menu Page");
                System.out.println("------------------------------------------\n");
                DisplayMenu();
            }
            
            int menukey = AskValidKey(5);
            
            switch(menukey){
                case 1:InCategories=true;display = true;break;
                case 2:InSearch = true;display = true;break;
                case 3:InShopCart = true;display = true;break;
                case 4:InSettings = true;display = true;break;
                
                case 5:InMainMenu=false;break; //Exit
                //Invalid key by user    
                default:
                    display =false;
                    break;
            }
            

            /////BROWSE BY CATEGORIES////////////////////////////////////////////////////////////
            while(InCategories){
                BrowseCategories bc = new BrowseCategories();
                
                ArrayList<String> categories = new ArrayList<>();
                ArrayList<String> subCategories = new ArrayList<>();
                ArrayList<String> itemNameB = new ArrayList<>();
                
                //Item Main Category display
                boolean InItemGroup = true;
                while(InItemGroup){

                    int num1 =1, num2 = 1,num3 = 1;

                    //first we declare a hashset.A HashSet is a collection of items where every item is unique.
                    //Filter the unique values
                    HashSet<String> uniqueCategories = bc.getUniqueCategories(itemDataMap);

                    //display categories available 
                    pageBreaker();
                    System.out.println(" Categories: ");
                    System.out.println("-------------------------------------------------");
                    for(String category: uniqueCategories){
                        System.out.println("["+ num1 +"] "+ category);
                        categories.add(category);
                        num1++;
                    }
                    System.out.println("[" + num1 + "] Back to Main Menu");
                    System.out.println("-------------------------------------------------");
                    int user1 = AskValidKey(num1);
                    

                    if(user1==num1){
                        InCategories = false;break; //Exit to Main Menu
                    }

                    //filter the subcategory based on category
                    HashSet<String> uniqueSubCategories = bc.getUniqueSubCategories(itemDataMap, categories.get(user1-1));
                    //display the subcategory 
                    pageBreaker();
                    System.out.println(" Categories: "+categories.get(user1-1));
                    System.out.println(" SubCategories: ");
                    System.out.println("-------------------------------------------------");
                    for(String subCategory: uniqueSubCategories){
                        System.out.println("[" + num2 + "] "+ subCategory);
                        subCategories.add(subCategory);
                        num2++;
                    }
                    System.out.println("["+num2+"] Back");
                    System.out.println("-------------------------------------------------");
                    int user2 = AskValidKey(num2);

                    if(user2 == num2){
                        InItemGroup = false;break;
                    }
                    
                    //filter and display the items based on the subcategory
                    pageBreaker();
                    System.out.println(" Categories: "+categories.get(user1-1));
                    System.out.println(" SubCategories: ");
                    System.out.println(" Items: ");
                    System.out.println("-------------------------------------------------------------------------------------------------------------");
                    System.out.printf("%-6s%-95s%-10s\n","Code","Item","Unit");
                    System.out.println("-------------------------------------------------------------------------------------------------------------");
                    for(ItemData item: itemDataMap.values()){
                        if(item.getItemSubCategory().equals(subCategories.get(user2-1))){
                            System.out.printf("%-6s%-95s%-10s\n",item.getItemCode(),item.getItem(),item.getItemUnit());
                            itemNameB.add(item.getItemCode());
                        }
                    }
                    System.out.println("-------------------------------------------------------------------------------------------------------------");
                    System.out.println();
                    System.out.println("Enter item code: ");
                    String codekey = MainScn.nextLine();
                    boolean notFound = true;
                    
                    do{
                        for(String x: itemNameB){
                            if(codekey.equals(x)){
                                notFound = false;break;
                            }
                        }
                        if(notFound){
                            
                            System.out.println("Invalid item code!");
                            System.out.println("Enter item code: ");
                            codekey = MainScn.nextLine();
                        }
                        
                    } while(notFound == true);
                    
                    if(!notFound){
                        selectedItemData = itemDataMap.get(codekey);
                        inSelection = true;
                        InCategories = false;
                        break;
                    }
                    
                }
            }
            

            //While in Search Function
            while(InSearch){
                        SearchFunction sf = new SearchFunction();
                        pageBreaker();
                        System.out.println("Search");
                        System.out.println("------------------------------------------");
                        System.out.println("Search for item:");
                        
                        String searchkey = MainScn.nextLine();
                        System.out.println("\n\n");
                        
                        

                        for(ItemData obj:itemDataMap.values()){
                            String target = obj.getItem();
                            String code = obj.getItemCode();
                            String unit = obj.getItemUnit();
                            sf.Search(searchkey,target,code,unit);
                        }       

                        sf.PrintResult(searchkey);
                        
                        System.out.println("-------------------------------------------");
                        System.out.println("[1] Search again");
                        System.out.println("[2] Select item");
                        System.out.println("[3] Back to Main Menu");
                        System.out.println("-------------------------------------------");
                        int key = AskValidKey(3);
                        
                        switch(key){
                            case 1:System.out.println("\n\n");continue;
                            case 2:
                                System.out.println("Enter item code <Enter -1 to return>: ");
                                String codekey = MainScn.nextLine();
                                if(codekey.equals("-1")){
                                    break;
                                }
                                
                                System.out.println(codekey);
                                String getItem = sf.GetItem(codekey);
                                while(getItem == null){
                                    System.out.println("Invalid code!");
                                    System.out.println("Enter item code <Enter -1 to return>: ");
                                    codekey = MainScn.nextLine();
                                    if(codekey.equals("-1")){
                                        InSearch = false;break;
                                    }
                                    getItem = sf.GetItem(codekey);
                                }

                                selectedItemData = itemDataMap.get(codekey);
                                inSelection = true;
                                InSearch = false;
                                break;
                                
                                
                            case 3: InSearch = false;break;   
                        }
                    
                    
            }
            
            
            //Selection screen (Appear only after Search function and Browse by Categories)
            while(inSelection){
                
                boolean inModify = false;
                boolean inView = false;
                boolean inCheapSell = false;
                boolean inPriceTrend = false;
                boolean inShopCart = false;
                
                pageBreaker();
                
                System.out.println("Selected item [ "+selectedItemData.getItem()+" ("+selectedItemData.getItemUnit()+") ]");
                System.out.println("Selected code: " + selectedItemData.getItemCode());
                System.out.println("\n<Please select an action>");
                System.out.println("[1] View item details\n[2] Modify item details\n[3] View top 5 cheapest seller\n[4] View price trend\n[5] Add to shopping cart\n[6] Back to Main Menu\n");
                System.out.println("-------------------------------------------------");
                
                int selectkey = AskValidKey(6);
                
                switch(selectkey){
                    case 1:inView = true;break;
                    case 2:inModify = true;break;
                    case 3:inCheapSell = true;break;
                    case 4:inPriceTrend = true;break;
                    case 5:inShopCart = true;break;
                    case 6:inSelection = false;break;
                }
                
                while(inView){ 
                    pageBreaker();
                    itemMethod.displayItemDetails(selectedItemData);
                    System.out.println();
                    System.out.println("[1] Back");
                    System.out.println("-------------------------------------------");
                    
                    int ikey = AskValidKey(1);
                    
                    if(ikey ==1){
                        inCheapSell = false;break;
                    }
                    
                }
                
                while(inModify){
                    Modifier mod = new Modifier();
                    System.out.println("Choose detail to modify:");
                    System.out.println("------------------------------------------------");
                    System.out.println("[1] Name");
                    System.out.println("[2] Unit");
                    System.out.println("[3] Back");
                    int key = AskValidKey(3);
                    switch(key){
                        case 1:
                            System.out.println("Enter new name for item <Enter -1 to return back>: ");
                            String newname = MainScn.nextLine();
                            if(newname.equals("-1")){
                                break;
                            }
                            
                            mod.ModifyItem(selectedItemData.getItemCode(), newname, key);
                            selectedItemData.setItemName(newname);
                            ShopCartMechanics.ChangeItemName(selectedItemData.getItemCode(), newname);
                            break;
                        case 2:
                            System.out.println("Enter new value for unit<Enter -1 to return back>: ");
                            String newunit = MainScn.nextLine();
                            if(newunit.equals("-1")){
                                break;
                            }
                            mod.ModifyItem(selectedItemData.getItemCode(),newunit, key);
                            selectedItemData.setItemUnit(newunit);
                            ShopCartMechanics.ChangeItemUnit(selectedItemData.getItemCode(), newunit);
                            break;
                        case 3:inModify = false;break;
                    }
                }
                
                
                while(inCheapSell){
                    pageBreaker();
                    Display.displayTopFiveCheapest(selectedItemData, latestPriceDataList, premiseDataMap, MainScn);
                    System.out.println();
                    System.out.println("[1] Back");
                    System.out.println("-------------------------------------------");
                    int ikey = AskValidKey(1);
                    
                    if(ikey ==1){
                        inCheapSell = false;break;
                    }
                }
                
                while(inPriceTrend){
                    pageBreaker();
                    PriceTrend priTre = new PriceTrend(selectedItemData.getItemCode());
                    priTre.getPriceTrend((HashMap<String, ItemData>) itemDataMap, salesDataList);
                    System.out.println("-------------------------------------------\n");
                    System.out.println("[1] Back");         
                    System.out.println("-------------------------------------------");
                    int ikey = AskValidKey(1);
                    
                    if(ikey ==1){
                        inPriceTrend = false;break;
                    }
                    
                }               
                if(inShopCart){                    
                    ShopCartMechanics SCMech = new ShopCartMechanics();
                    
                    pageBreaker();
                    String code = selectedItemData.getItemCode();
                    String item = selectedItemData.getItem();
                    String unit = selectedItemData.getItemUnit();
                    
                    SCMech.AddItem(activeuser,code,item,unit);
                    
                }
            }
            
            while(InShopCart){
                ArrayList<String> itemsinSC = new ArrayList<>(); //List of item code for reference for Sales Data
                pageBreaker();
                System.out.println(" Shopping Cart Page");
                System.out.println("-------------------------\n");
                ArrayList<List<String>> shopcart = new ArrayList<>();
                shopcart.clear();
                
                shopcart = sci.ImportSC(activeuser);//Importing Shop Cart from SQL
                ShopCartMechanics SCMech = new ShopCartMechanics();
                
                System.out.println("\n");
                System.out.println("Shopping cart");
                System.out.println("-------------------------------------------------------------------------------");
                System.out.printf("%-6s%-95s%-10s\n","Code","Item","Unit");
                System.out.println("-------------------------------------------------------------------------------");
                
                //Display all items in Shop cart
                
                for(List<String> row : shopcart){
                    System.out.printf("%-6s%-65s%-10s\n",row.get(0),row.get(1),row.get(2)); //Displaying a row of item
                    itemsinSC.add(row.get(0)); //Adding the item code into the reference List
                    
                }
                

                
                System.out.println("--------------------------------------------------------------------------------");
                System.out.println("\n<Please select an action>");
                System.out.println("[1] Remove item\n[2] View top cheapest sellers for item(s)\n[3] Find shops to buy item(s)\n[4] Back");
                System.out.println("-------------------------------------------");
                int shopkey = AskValidKey(4);

                boolean InCheapPrem = false;
                boolean InFindShop = false;


                 switch(shopkey){
                     case 1:
                         pageBreaker();
                         System.out.println("Remove Item Page");
                         System.out.println("-------------------------\n");
                         System.out.println("Enter code item to remove: ");
                         String codekey = MainScn.nextLine();
                         System.out.println(codekey);
                         SCMech.RemoveItem(activeuser,codekey);break;
                     case 2: InCheapPrem = true;break;
                     case 3: InFindShop = true;break;
                     case 4:InShopCart = false;break;

                 }
                
                while(InCheapPrem){
                    pageBreaker();
                    Display.displayCheapestSellerSCItem(itemsinSC, salesMethod,latestPriceDataList);
                    System.out.println("\n[1] Back");
                    System.out.println("-------------------------------------------");
                    int ikey = AskValidKey(1);
                    
                    if(ikey ==1){
                        InCheapPrem = false;break;
                    }
                }

                while(InFindShop){
                    FindShop fs = new FindShop();
                    int statekey;
                    String selectedState;

                    List<String> UniqueStates = fs.getAllStates(premiseDataMap);

                    pageBreaker();
                    System.out.println(" Find Shop For items Page");
                    System.out.println("------------------------------------------\n");
                    //Display all States in Malaysia
                    System.out.println("Please select state");
                    System.out.println("------------------------------------------");

                    int num = 1;
                    for(String x:UniqueStates){
                        if(x.equals(null) || x.equals("")){
                            continue;
                        }
                        System.out.printf("[%d] %s\n",num,x);
                        num++;
                    }


                    System.out.printf("[%d] Back \n", num);
                    System.out.println("------------------------------------------");

                    statekey = AskValidKey(num);
                    
                    if(statekey == num){
                        InFindShop=false;break;
                    }

                    selectedState = UniqueStates.get(statekey);
                    System.out.println("\n");
                    System.out.println("--------------------------------------------------------------------------------------------------------------\n");
                    System.out.println("Selected: " + selectedState);
                    System.out.println();

                    //Filter the premiseData, extracting only rows which State column value match the input
                    ArrayList<String> premiseByState = FindShop.filterByState(selectedState, premiseDataMap);


                    // store the items that the store sells
                    Map<String, List<String>> storeItemMap = fs.getStoreItem(salesDataList);

                    //filter sales data by item
                    ArrayList<SalesData> relevantSalesDataForSc = fs.filterByItemList(latestPriceDataList, itemsinSC, salesMethod);

                    // find the list of store combination for the items
                    ArrayList<ShopCartStore> listCombinationMainStore = new ArrayList<>();
                    
                    listCombinationMainStore = fs.getListCombinationMainStore(premiseByState, storeItemMap, itemsinSC,relevantSalesDataForSc, listCombinationMainStore);

                    ShopCartStore optimalMainStore = listCombinationMainStore.get(0);
                    int maxTotalItem = optimalMainStore.getListAvailableItem().size();

                    //get the max value of the total available items
                    maxTotalItem = fs.findMaxItemSell(listCombinationMainStore, maxTotalItem);
                    

                    //if there are same store with max value, check for total price
                    double cheapestTotalPrice = Double.MAX_VALUE;
                    for (int i = 1; i < listCombinationMainStore.size(); i++) {
                        ShopCartStore nextStore = listCombinationMainStore.get(i);
                        int totalItem = nextStore.getListAvailableItem().size();

                        if (totalItem == maxTotalItem) {
                            
                            if(nextStore.getTotalItemsPrice()<cheapestTotalPrice){
                                optimalMainStore = nextStore;
                                cheapestTotalPrice = nextStore.getTotalItemsPrice();
                            }
                        }
                    }

                    ArrayList<String> remainingItems = optimalMainStore.getListNotAvailableItem();
                    ArrayList<ShopCartStore> finalListOptimalSub = new ArrayList<>();
                    int count = 0;

                    while(!remainingItems.isEmpty()){
                        //if no data was found after 10 loops it will break out of the loop
                        if(count == 10){
                            break;
                        }
                        
                        
                        
                        //copy the remaining items to avoid error
                        ArrayList<String> remainingItemsCopy = new ArrayList<>(remainingItems);
                        
                        ArrayList<ShopCartStore> listCombinationSubStore = new ArrayList<>();
                        listCombinationSubStore = fs.getListCombinationSubStore(premiseByState, storeItemMap, remainingItemsCopy,relevantSalesDataForSc, listCombinationSubStore);

                        ShopCartStore optimalSubStore = listCombinationSubStore.get(0);
                        int maxTotalItemSub = optimalSubStore.getListAvailableItem().size();

                        //get the max value of the total available items
                        maxTotalItemSub = fs.findMaxItemSell(listCombinationSubStore, maxTotalItemSub);
                        

                        //if there are same store with max value, check for total price
                        double cheapestTotalPriceSub = Double.MAX_VALUE;
                        for (int i = 1; i < listCombinationSubStore.size(); i++) {
                            ShopCartStore nextStore = listCombinationSubStore.get(i);
                            int totalItemSub = nextStore.getListAvailableItem().size();

                            if (totalItemSub == maxTotalItemSub) {
                                
                                if(nextStore.getTotalItemsPrice()<cheapestTotalPriceSub){
                                    optimalSubStore = nextStore;
                                    cheapestTotalPriceSub = nextStore.getTotalItemsPrice();
                                }
                            }
                        }

                        finalListOptimalSub.add(optimalSubStore);
                        remainingItems.removeAll(optimalSubStore.getListAvailableItem());
                        
                        count++;
                        
                    }
                    
                    // DIsplay Result
                    fs.displayFindShopResult( premiseDataMap, itemDataMap, optimalMainStore, maxTotalItem, finalListOptimalSub, remainingItems, selectedState);
                    
                    //reset the value of the object
                    for (ShopCartStore eachStore : listCombinationMainStore) {
                        eachStore.setStoreCode(null);
                        eachStore.getListAvailableItem().clear();
                        eachStore.getListNotAvailableItem().clear();
                        eachStore.setTotalItemsPrice(0.0);
                    }
                    
                    //reset the value of the object
                    for (ShopCartStore eachStore : finalListOptimalSub) {
                        eachStore.setStoreCode(null);
                        eachStore.getListAvailableItem().clear();
                        eachStore.getListNotAvailableItem().clear();
                        eachStore.setTotalItemsPrice(0.0);
                    }
                    
                    
                    System.out.println("\n[1] Back");
                    System.out.println("-------------------------------------------");
                    int ikey = AskValidKey(1);
                    
                    if(ikey ==1){
                        
                        InFindShop = false;break;
                    }
                }
            }

            
            //Account Settings (COMPLETED)
            while(InSettings){
                
                        AccountSet acc = new AccountSet();
                        pageBreaker();
                        //Displaying screen for Settings
                        System.out.println("Account Settings Page");
                        System.out.println("-------------------------------\n");
                        System.out.println("Account Details");
                        System.out.println("Username: " + activeuser);
                        System.out.println("Password: " + activepass);
                        System.out.println("Email: " + activeemail);
                        System.out.println("-------------------------------");
                        System.out.println("[1] Change username");
                        System.out.println("[2] Change password");
                        System.out.println("[3] Change email");
                        System.out.println("[4] Back to Main Menu");
                        System.out.println("-------------------------------");
     
                        int settingskey = AskValidKey(4);
                        
                        if(settingskey == 1){
                                boolean inChangeUser = true;
                                int settingskey1;
                                
                                while(inChangeUser){
                                    System.out.println("Enter new username<Enter -1 to return>: ");
                                    String changeuser = MainScn.nextLine();
                                    if(changeuser.equals("-1")){
                                        break;
                                    }
                                    
                                    //If username already exists in database, ask user to create a new non-existing username
                                    boolean usernameExists = LogS.CheckUserExist(changeuser);
                                    boolean usernamevalid = ms.CheckValidity(changeuser);

                                    if(usernameExists){
                                        System.out.println("Username is already taken!");
                                        System.out.println("-----------------------------------");
                                    }
                                    else if(usernamevalid == false){
                                        System.out.println("Invalid username!");
                                        System.out.println("-----------------------------------");
                                    }
                                    
                                    
                                    if(usernameExists || usernamevalid == false){

                                        System.out.println("[1] Retry");
                                        System.out.println("[2] Back");
                                        
                                        settingskey1 = AskValidKey(2);
                                        
                                        switch(settingskey1){
                                            case 1: //If user key is 1 (Retry)
                                                System.out.println("\n\n");
                                                continue;
                                            case 2:
                                                inChangeUser = false;
                                                break;
                                        }
                                    }
                                    
                                    if(usernameExists == false || usernamevalid){
                                        
                                        acc.ChangeName(activeuser,changeuser);
                                        activeuser = changeuser;
                                        ms.ImportRegUserSQL();
                                        
                                        break;
                                        
                                    }
                                }
                        }
                        
                                    
                        else if(settingskey == 2){
                            boolean inChangePass = true;
                            
                            while(inChangePass){
                                System.out.println("Enter new password <Enter -1 to return>: ");
                                String changepass = MainScn.nextLine();
                                if(changepass.equals("-1")){
                                    break;
                                }
                                
                                boolean passvalid = ms.CheckValidity(changepass);
                                
                                if(passvalid == false){
                                        System.out.println("Invalid password!");
                                        System.out.println("-----------------------------------");
                                }
                                 if(passvalid == false){
                                        int settingskey2;
                                        System.out.println("[1] Retry");
                                        System.out.println("[2] Back");
                                        
                                        settingskey2 = AskValidKey(2);
                                        
                                        switch(settingskey2){
                                            case 1: //If user key is 1 (Retry)
                                                System.out.println("\n\n");
                                                continue;
                                            case 2:
                                                inChangePass = false;
                                                break;
                                        }
                                 }
                                 if(passvalid){
                                    acc.ChangePass(activepass,changepass);
                                    activepass = changepass;
                                    break;}
                                 
                            }
                        }
                        
                        else if(settingskey == 3){
                            boolean InChangeEmail = true;
                            while(InChangeEmail){

                                System.out.println("Enter new email<Press -1 to return>:");
                                String changeemail = MainScn.nextLine();
                                if(changeemail.equals("-1")){
                                    break;
                                }

                                boolean emailexists = LogS.CheckEmailExist(changeemail);
                                if(emailexists){
                                    System.out.println("The email has already registered into another account");
                                }
                                while(emailexists){
                                    System.out.println("");
                                    int settingskey2;
                                    System.out.println("[1] Retry");
                                    System.out.println("[2] Back");
                                    settingskey2 = AskValidKey(2);
                                    
                                    switch(settingskey2){
                                            case 1: //If user key is 1 (Retry)
                                                System.out.println("\n\n");
                                                continue;
                                            case 2:
                                                InChangeEmail = false;
                                                break;
                                    }
                                }
                            
                            if(!emailexists){
                                int generatedCode = generateCode();
                                ev.sendMail(changeemail,generatedCode);
                                
                                System.out.println("\nPlease enter the verification code<Enter 0 to return back>:");
                                int inputCode = MainScn.nextInt();
                                if(inputCode == 0){
                                    break;
                                }
                            
                                boolean validCode = ev.verifyCode(inputCode, generatedCode); //verify code input by user

                                while(validCode == false){
                                        System.out.println("Wrong verification code! Please try again<Enter -1 to return back>:");
                                        inputCode = MainScn.nextInt();
                                        if(inputCode == -1){
                                            break;
                                        }
                                        validCode = ev.verifyCode(inputCode, generatedCode); 
                                }     
                                acc.ChangeEmail(activeemail,changeemail);
                                activeemail = changeemail;   
                            }
                            }
                        }
                        
                        else if(settingskey == 4){
                            InSettings = false;break;
                        }
                        
            }
                       
        }   
        
        pageBreaker();
        System.out.println("Successfully Logout!");
        InMainScr = true; //Returning back to Main Menu screen loop (skipping Login/Register page loop)
            
        } while(SystemOpen); //SystemOpen Loop to be able to loop back into Main menu screen
        
    
    }
    
        
    
    public static void DisplayMenu() {
        System.out.println("<Please select an action>");
        System.out.println("[1] Browse by Categories");
        System.out.println("[2] Search for a Product");
        System.out.println("[3] View Shopping Cart");
        System.out.println("[4] Account Settings");
        System.out.println("[5] Logout");
        System.out.println("---------------------------------------------");
    }
    
    public static int AskValidKey(int maxnum){
        Scanner scn = new Scanner(System.in);
        int inpkey = 0;
        boolean isValid;     
        
        do{
            System.out.println("Enter key: ");
        try{
            inpkey = scn.nextInt();
            scn.nextLine();
            isValid = CheckValidKey(inpkey,maxnum);
            
            if(isValid == false){
                System.out.println("INVALID KEY!");
            }
            
        }catch(InputMismatchException e){
            System.out.println("INVALID KEY! ");
            isValid =false;
            scn.nextLine();
        }
        
        }while(isValid == false);
        
        return inpkey;
    }
    
    public static boolean CheckValidKey(int key,int maxnum){
        boolean isValid = false;
        
        if(key<=0 || key>maxnum){
            isValid = false;
        }
        else{
            isValid = true;}
        
        return isValid; 
    }
    

    public static int generateCode(){
        Random r = new Random();
        int code=r.nextInt(8999+1)+1000;
        return code;
    }
    
    public static void pageBreaker(){
        System.out.println("\n==============================================================================================================\n");
    }
}