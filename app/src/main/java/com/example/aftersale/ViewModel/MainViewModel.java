package com.example.aftersale.ViewModel;

import android.util.Log;
import android.widget.Toast;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aftersale.Model.Create;
import com.example.aftersale.Model.Data;
import com.example.aftersale.Model.Suppliers;
import com.example.aftersale.Model.User;
import com.example.aftersale.repository.Repository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends ViewModel {
    private Repository repository;
    public MutableLiveData<ArrayList<Suppliers>> supplierList = new MutableLiveData<ArrayList<Suppliers>>();
    public MutableLiveData<String> supplierListError = new MutableLiveData<>();
    public MutableLiveData<String> createDone = new MutableLiveData<>();
    public MutableLiveData<String> valid = new MutableLiveData<>();
    private LiveData<List<Data>> favoritePokemonList = null;


    @ViewModelInject
    public MainViewModel(Repository repository) {
        this.repository = repository;
        favoritePokemonList = repository.getData();

    }

    public MutableLiveData<ArrayList<Suppliers>> getSupplierList() {
        return supplierList;
    }

    public MutableLiveData<String> getSupplierListError() {
        return supplierListError;
    }





    public void addData(Data user)
    {
        repository.insertPokemon(user);
    }



    public LiveData<List<Data>> getFavoritePokemonList() {
        return favoritePokemonList;
    }



    public void fetchSupplierData(){
        repository.getSupplier()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((ArrayList<Suppliers> user)-> {
                        supplierList.setValue(user);
                        },throwable -> {
                            Log.d("Error",throwable.getMessage());
                         supplierListError.setValue(throwable.getMessage());
                        }
                );
    }

    public void uploadData(String Supplier_Code, String Delivery_Date,String Shipping_Cost, String Purchase_Date,String Customer_Code, String AfterSale_Invoice,String Creation_Date, String User_ID){
        repository.UploadData(Supplier_Code,Delivery_Date,Shipping_Cost,Purchase_Date,Customer_Code,AfterSale_Invoice,Creation_Date,User_ID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((Create user)-> {
                    createDone.setValue(user.getMessage());
                    },throwable -> {
                    Log.d("Error",throwable.getMessage());
                    supplierListError.setValue(throwable.getMessage());
                }
                );

    }

    public void updateData(String Supplier_Code, String Sending_Date){
        repository.updateData(Supplier_Code , Sending_Date)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((Create user)-> {
                            createDone.setValue(user.getMessage());
                        },throwable -> {
                            Log.d("Error",throwable.getMessage());
                            supplierListError.setValue(throwable.getMessage());
                        }
                );

    }


    public void validData(String order_no, String customer_no){
        repository.validData(order_no , customer_no)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((Create user)-> {
                           valid.setValue(user.getMessage());
                        },throwable -> {
                            Log.d("Error",throwable.getMessage());
                            supplierListError.setValue(throwable.getMessage());
                        }
                );

    }



    public void delete(String mail){
        repository.deletePokemon(mail);
    }
}
