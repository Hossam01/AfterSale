package com.example.aftersale.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
@Entity(tableName = "user")
public class Data {
    @PrimaryKey(autoGenerate = true)
    private int uid;
    @ColumnInfo(name = "mail")
    @SerializedName("mail")
    private String mail;
    @ColumnInfo(name = "imageName")
    @SerializedName("imageName")
    private String imageName;

    public Data( String mail, String imageName) {
        this.mail = mail;
        this.imageName = imageName;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
