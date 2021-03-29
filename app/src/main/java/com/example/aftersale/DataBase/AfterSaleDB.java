package com.example.aftersale.DataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.aftersale.Model.Data;


@Database(entities = {Data.class},version = 2,exportSchema = false)
public abstract class AfterSaleDB extends RoomDatabase {
    public abstract UserDao pokeDao();
}
