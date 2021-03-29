package com.example.aftersale.Model;

import com.google.gson.annotations.SerializedName;

public class Suppliers {
    @SerializedName("supplier_Code")
    private int supplier_Code;
    @SerializedName("supplier_Name")
    private String supplier_Name;
    @SerializedName("Email")
    private String Email;

    public int getSupplier_Code() {
        return supplier_Code;
    }

    public void setSupplier_Code(int supplier_Code) {
        this.supplier_Code = supplier_Code;
    }

    public String getSupplier_Name() {
        return supplier_Name;
    }

    public void setSupplier_Name(String supplier_Name) {
        this.supplier_Name = supplier_Name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
