package com.example.aftersale.Model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;
    @SerializedName("User_ID")
    private String User_ID;
    @SerializedName("username")
    private String username;
    @SerializedName("User_Description")
    private String User_Description;
    @SerializedName("Group_Name")
    private String Group_Name;
    @SerializedName("User_status")
    private String User_status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_Description() {
        return User_Description;
    }

    public void setUser_Description(String user_Description) {
        User_Description = user_Description;
    }

    public String getGroup_Name() {
        return Group_Name;
    }

    public void setGroup_Name(String group_Name) {
        Group_Name = group_Name;
    }

    public String getUser_status() {
        return User_status;
    }

    public void setUser_status(String user_status) {
        User_status = user_status;
    }

    public String getUser_Department() {
        return User_Department;
    }

    public void setUser_Department(String user_Department) {
        User_Department = user_Department;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getGroupID() {
        return GroupID;
    }

    public void setGroupID(String groupID) {
        GroupID = groupID;
    }

    public String getComplexID() {
        return ComplexID;
    }

    public void setComplexID(String complexID) {
        ComplexID = complexID;
    }

    public String getGroupsize() {
        return groupsize;
    }

    public void setGroupsize(String groupsize) {
        this.groupsize = groupsize;
    }

    @SerializedName("User_Department")
    private String User_Department;
    @SerializedName("company")
    private String company;
    @SerializedName("GroupID")
    private String GroupID;
    @SerializedName("ComplexID")
    private String ComplexID;
    @SerializedName("groupsize")
    private String groupsize;

}
