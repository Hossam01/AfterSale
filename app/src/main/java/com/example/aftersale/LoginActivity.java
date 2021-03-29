package com.example.aftersale;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.aftersale.Model.Prefrancemanger;
import com.example.aftersale.Model.User;
import com.example.aftersale.ViewModel.ViewModelLogin;
import com.example.aftersale.databinding.ActivityLoginBinding;

import org.w3c.dom.Text;

import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    ViewModelLogin viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ViewModelLogin.class);

        binding.login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View view) {
                if (binding.username.getText().toString().isEmpty()==true)
                    binding.username.setError("Please Enter UserName");
                else if (binding.password.getText().toString().isEmpty()==true)
                    binding.password.setError("Please Enter Password");
                viewModel.fetchUserData(binding.username.getText().toString(),binding.password.getText().toString());
            }
        });
        viewModel.pokemonList.observe(this, new Observer<User>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onChanged(User user) {
                if (user.getMessage().equals("Not Authenticate"))
                {
                    Toast.makeText(getApplicationContext()," incorrect username or password",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), DashbordActivity.class);
                    Prefrancemanger.put("UserID",binding.username.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }
        });
        viewModel.pokemonListError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }
        });

    }
}