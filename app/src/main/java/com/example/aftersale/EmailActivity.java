package com.example.aftersale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.aftersale.Model.Data;
import com.example.aftersale.Model.Suppliers;
import com.example.aftersale.ViewModel.MainViewModel;
import com.example.aftersale.databinding.ActivityEmailBinding;
import com.example.aftersale.databinding.ActivityMainBinding;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint

public class EmailActivity extends AppCompatActivity {
    ActivityEmailBinding binding;
    MainViewModel viewModel;
    ArrayList<Suppliers> responseDriver;
    Date c = Calendar.getInstance().getTime();
    String myFormat = "MM-dd-yyyy hh:mm:ss"; //In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    String formattedDate = sdf.format(c);

    int code=0;
    String Email="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.fetchSupplierData();
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        List<String> driver = new ArrayList<String>();
        ArrayAdapter spinnerAdapterDriver = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, driver);
        spinnerAdapterDriver.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.DriverID.setAdapter(spinnerAdapterDriver);

        viewModel.getSupplierList().observe(this, (ArrayList<Suppliers> responseDriver) -> {
            if (driver.size() == 0) {
                for (int i = 0; i < responseDriver.size(); i++) {
                    driver.add(responseDriver.get(i).getSupplier_Name());
                }
            }
            this.responseDriver = responseDriver;
            spinnerAdapterDriver.notifyDataSetChanged();

        });
        binding.DriverID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                code=responseDriver.get(arg2).getSupplier_Code();
                Email=responseDriver.get(arg2).getEmail();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();

            }
        });
    }


    public void sendEmail()
    {
        viewModel.getFavoritePokemonList().observe(this, new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> data) {
                if (data.size() == 0) {
                    new AlertDialog.Builder(EmailActivity.this)
                            .setTitle("Empty Data")
                            .setMessage("the Database is empty no data to send")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } else {
                    ArrayList<String> names = new ArrayList<>();
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getMail().equals(Email)) {
                            names.add(data.get(i).getImageName());
                        }
                    }
                    Log.i("SendMailActivity", "Send Button Clicked.");
                    String fromEmail = "nf.zayed@hyperone.com.eg";
                    String fromPassword = "NF4888ZZtt";
                    String toEmails = Email;
                    List<String> toEmailList = Arrays.asList(toEmails
                            .split("\\s*,\\s*"));
                    Log.i("SendMailActivity", "To List: " + toEmailList);
                    String emailSubject = "Invoices";
                    String emailBody = "Gentlemen, partners of success, if there is any comment, please inform us within 24 hours";
                    new SendMailTask(EmailActivity.this).execute(fromEmail,
                            fromPassword, toEmailList, emailSubject, emailBody, names, getApplicationContext());
                    viewModel.delete(Email);
                    viewModel.getFavoritePokemonList().removeObserver(this);
                    viewModel.updateData(String.valueOf(code),formattedDate);

                }
            }
        });
    }
}