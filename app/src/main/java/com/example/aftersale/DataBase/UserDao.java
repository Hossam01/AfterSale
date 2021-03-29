package com.example.aftersale.DataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.aftersale.Model.Data;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {
   @Query("SELECT * FROM user")
   LiveData<List<Data>> getUserData();

    @Insert(onConflict = REPLACE)
    void insertUser(Data mUser);

    @Query("DELETE FROM user WHERE mail = :pokemonName")
    void deletePokemon(String pokemonName);


}
