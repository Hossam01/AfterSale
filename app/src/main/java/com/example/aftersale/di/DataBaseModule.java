package com.example.aftersale.di;

import android.app.Application;

import androidx.room.Room;

import com.example.aftersale.DataBase.AfterSaleDB;
import com.example.aftersale.DataBase.UserDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class DataBaseModule {

    @Provides
    @Singleton
    public static AfterSaleDB providePokemonDB(Application application){
        return Room.databaseBuilder(application,AfterSaleDB.class,"AfterSale Database")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    public static UserDao providePokeDao(AfterSaleDB pokemonDB){
        return pokemonDB.pokeDao();
    }
}
