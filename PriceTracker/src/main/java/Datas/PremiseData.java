
package Datas;

public class PremiseData {
    private String premiseCode;
    private String premise;
    private String address;
    private String premiseType;
    private String state;
    private String district;
    
    public PremiseData(){}

    public PremiseData(String premiseCode, String premise, String address, String premiseType, String state, String district) {
        this.premiseCode = premiseCode;
        this.premise = premise;
        this.address = address;
        this.premiseType = premiseType;
        this.state = state;
        this.district = district;
    }

    // Getters and setters
    public String getPremiseCode(){
        return this.premiseCode;
    }
    public String getPremise(){
        return this.premise;
    }
    public String getAddress(){
        return this.address;
    }
    public String getPremiseType(){
        return this.premiseType;
    }
    public String getState(){
        return this.state;
    }
    public String getDistrict(){
        return this.district;
    }
}
