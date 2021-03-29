package com.example.aftersale;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.example.aftersale.Model.Prefrancemanger;
import com.example.aftersale.databinding.ActivityDashbordBinding;
import com.example.aftersale.databinding.ActivityMainBinding;

import java.util.Locale;

public class DashbordActivity extends AppCompatActivity {
    ActivityDashbordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashbordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.rellayTimeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent refresh = new Intent(DashbordActivity.this, EmailActivity.class);
                startActivity(refresh);
            }
        });

        binding.rellayFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DashbordActivity.this);
                builder.setTitle(R.string.Language);
                // Add the buttons
                builder.setPositiveButton(R.string.english, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String languageToLoad = "en"; // your language
                        Locale locale = new Locale(languageToLoad);
                        Locale.setDefault(locale);
                        Configuration config = new Configuration();
                        config.locale = locale;
                        getBaseContext().getResources().updateConfiguration(config,
                                getBaseContext().getResources().getDisplayMetrics());
                        dialog.dismiss();
                        Prefrancemanger.put("language", languageToLoad);



                        Intent refresh = new Intent(DashbordActivity.this, DashbordActivity.class);
                        startActivity(refresh);

                    }
                });
                builder.setNegativeButton(R.string.arabic, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog

                        String languageToLoad = "ar"; // your language
                        Locale locale = new Locale(languageToLoad);
                        Locale.setDefault(locale);
                        Configuration config = new Configuration();
                        config.locale = locale;
                        getBaseContext().getResources().updateConfiguration(config,
                                getBaseContext().getResources().getDisplayMetrics());
                        dialog.dismiss();
                        Prefrancemanger.put("language", languageToLoad);


                        Intent refresh = new Intent(DashbordActivity.this, DashbordActivity.class);
                        startActivity(refresh);

                    }
                });

                builder.create().show();
            }
        });


        binding.rellayChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }
}