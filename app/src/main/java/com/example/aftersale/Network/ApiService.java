package com.example.aftersale.Network;

import com.example.aftersale.Model.Create;
import com.example.aftersale.Model.Suppliers;
import com.example.aftersale.Model.User;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("PPCModules/LoginPPC.php")
    Observable<User> getUser(@Field("User_Name") String User_Name, @Field("Password") String Password);

    @GET("aftersale/Supplier.php")
    Observable<ArrayList<Suppliers>> getSupplier();

    @FormUrlEncoded
    @POST("aftersale/AfterSale_Invoice_Data.php")
    Observable<Create> UploadData(@Field("Supplier_Code") String Supplier_Code, @Field("Delivery_Date") String Delivery_Date,
                                  @Field("Shipping_Cost") String Shipping_Time, @Field("Purchase_Date") String Purchase_Date,
                                  @Field("Customer_Code") String Customer_Code, @Field("Aftersale_Invoice") String Bill_Number,
                                  @Field("Creation_Date") String Movement_Date, @Field("User_ID") String Employee_Name);


    @FormUrlEncoded
    @POST("aftersale/AfterSale_updateInvoice_Data.php")
    Observable<Create> UploadData(@Field("Supplier_Code") String Supplier_Code,
                                  @Field("Sending_Date") String Sending_Date);

    @FormUrlEncoded
    @POST("aftersale/InvoiceNumber.php")
    Observable<Create> ValidData(@Field("order_no") String order_no,
                                  @Field("customer_no") String customer_no);




}
