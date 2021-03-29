package com.example.aftersale.ViewModel;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aftersale.Model.User;
import com.example.aftersale.repository.Repository;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class ViewModelLogin extends ViewModel {
    private Repository repository;
    public MutableLiveData<User> pokemonList = new MutableLiveData<User>();
    public MutableLiveData<String> pokemonListError = new MutableLiveData<>();

    @ViewModelInject
    public ViewModelLogin(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<User> getUser() {
        return pokemonList;
    }

    public MutableLiveData<String> getPokemonListError() {
        return pokemonListError;
    }

    public void fetchUserData(String UserName,String Password){
        repository.getPokemons(UserName,Password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((User user)-> {
                    pokemonList.setValue(user);
                        },throwable -> {
                    Log.d("Error",throwable.getMessage());
                    pokemonListError.setValue(throwable.getMessage());
                        }
                );
    }




}
