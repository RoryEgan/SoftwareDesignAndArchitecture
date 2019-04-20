package com.cs4125.bikerentalapp.model.entity.userLevel;

import com.cs4125.bikerentalapp.model.entity.Visitor.Visitor;

public class PremiumUser implements IType {

    boolean returnedValue;

    @Override
    public String getType() {
        return "Silver";
    }

    @Override
    public void accept(Visitor v) {
        if(v.visitPremiumUser(this).equals("true"))
            returnedValue=true;
        else
            returnedValue=false;
    }

    public boolean returnBoolean(){
        return returnedValue;
    }
}
