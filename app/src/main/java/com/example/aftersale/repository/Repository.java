package com.example.aftersale.repository;


import androidx.lifecycle.LiveData;

import com.example.aftersale.DataBase.UserDao;
import com.example.aftersale.Model.Create;
import com.example.aftersale.Model.Data;
import com.example.aftersale.Model.Suppliers;
import com.example.aftersale.Model.User;
import com.example.aftersale.Network.ApiService;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class Repository {
    private ApiService userApiService;
    private UserDao userDao;

    @Inject
    public Repository(ApiService pokemonApiService,UserDao userDao) {
        this.userApiService = pokemonApiService;
        this.userDao = userDao;
    }



    public Observable<User> getPokemons(String User_Name,String Password){
        return userApiService.getUser(User_Name,Password);
    }

    public Observable<ArrayList<Suppliers>> getSupplier(){
        return userApiService.getSupplier();
    }

    public Observable<Create> UploadData(String Supplier_Code, String Delivery_Date,String Shipping_Cost, String Purchase_Date,String Customer_Code, String AfterSale_Invoice,String Creation_Date, String User_ID){
        return userApiService.UploadData(Supplier_Code,Delivery_Date,Shipping_Cost,Purchase_Date,Customer_Code,AfterSale_Invoice,Creation_Date,User_ID);
    }
    public Observable<Create> updateData(String Supplier_Code,String Sending_Date){
        return userApiService.UploadData(Supplier_Code,Sending_Date);
    }

    public Observable<Create> validData(String order_no,String customer_no){
        return userApiService.ValidData(order_no,customer_no);
    }






    public void insertPokemon(Data pokemon){
        userDao.insertUser(pokemon);
    }

    public LiveData<List<Data>> getData(){
        return userDao.getUserData();
    }

    public void deletePokemon(String mail){
        userDao.deletePokemon(mail);
    }

}
