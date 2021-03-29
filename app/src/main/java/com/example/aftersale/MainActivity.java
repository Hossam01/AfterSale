package com.example.aftersale;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.content.Intent;

import android.os.Build;
import android.os.Bundle;

import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aftersale.Model.Data;
import com.example.aftersale.Model.Prefrancemanger;
import com.example.aftersale.Model.Suppliers;
import com.example.aftersale.ViewModel.MainViewModel;
import com.example.aftersale.databinding.ActivityMainBinding;
import android.net.Uri;

import org.apache.http.params.HttpParams;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

import static android.provider.CalendarContract.CalendarCache.URI;

@AndroidEntryPoint

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    MainViewModel viewModel;
    ArrayList<Suppliers> responseDriver;
    Calendar myCalendar;
    Calendar calendar;
    int code=0;
    String Email="";
    private static final int PICK_FROM_GALLERY = 101;
    Uri URI;
    ArrayList<String> uris = new ArrayList<String>();
    Date c = Calendar.getInstance().getTime();
    String myFormat = "MM-dd-yyyy hh:mm:ss"; //In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    String formattedDate = sdf.format(c);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.fetchSupplierData();


        if (checkPermissionForReadExtertalStorage()==false)
        {
            try {
                requestPermissionForReadExtertalStorage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        List<String> driver = new ArrayList<String>();

        String UserID = Prefrancemanger.getString("UserID");

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


        myCalendar = Calendar.getInstance();
        calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        binding.NameArabic.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog=   new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                dialog.show();
            }
        });

        String myFormat1 = "MM-dd-yyyy"; //In which you need put here
        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
        String formattedDate1 = sdf1.format(c);
        binding.Status.setText(formattedDate1);

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
       binding.shot.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              // openFolder();
               Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.intsig.camscanner");
               if (launchIntent != null) {
                   startActivity(launchIntent);//null pointer check in case package name was not found
               }
           }
       });
       binding.pick.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               openFolder();
           }
       });

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.NameArabic.length()==0)
                    binding.NameArabic.setError("add delivery date");
                else if(binding.NameEnglish.length()==0)
                    binding.NameEnglish.setError("add shipping cost");
                else if (binding.Company.length()==0)
                    binding.Company.setError("add customer code");
                else if (binding.Phone.length()==0)
                    binding.Phone.setError("add invoice number");
                else {
                    viewModel.validData(binding.Phone.getText().toString(),binding.Company.getText().toString());
                }
            }
        });

        viewModel.valid.observe(MainActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("Found"))
                {
                    viewModel.uploadData(String.valueOf(code), binding.NameArabic.getText().toString(), binding.NameEnglish.getText().toString(), binding.Status.getText().toString(), binding.Company.getText().toString(), binding.Phone.getText().toString(), formattedDate, UserID);
                    for (int i = 0; i < uris.size(); i++) {
                        viewModel.addData(new Data(Email, uris.get(i)));
                    }
                    clear();

                }
                else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Not Found")
                            .setMessage("this customer and this Invoice not Found")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    clear();

                }
            }
        });


        viewModel.createDone.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }
        });


    }
    public void makeToast() {
        Toast toast = new Toast(this);
        View customToastView = getLayoutInflater().inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.customToastContainer));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(customToastView);
        toast.show();
    }






    private void updateLabel() {
        String myFormat = "MM-dd-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        binding.NameArabic.setText(sdf.format(myCalendar.getTime()));
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {


            if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {
//            if (data != null) {
//                URI = data.getData();
//            }

                // compare the resultCode with the
                // SELECT_PICTURE constant
                if (requestCode == 101) {
                    // Get the url of the image from data
                    uris.clear();
                    Uri selectedImageUri = data.getData();
                    if (null != selectedImageUri) {
                        uris.add(getFileName(selectedImageUri));
                    }
                }
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                    for (int i = 0; i < count; i++) {
                        URI = data.getClipData().getItemAt(i).getUri();
                        Log.e("PATH", URI.toString());
                        uris.add(getFileName(URI));
                    }
                    //do something with the image (save it to some directory or whatever you need to do with it here)
                }
            } else if (data.getData() != null) {
                String imagePath = data.getData().getPath();
                //do something with the image (save it to some directory or whatever you need to do with it here)
            }
        }catch (NullPointerException ex){Log.e("Error",ex.getMessage());}

    }
    public void openFolder() {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.putExtra("return-data", true);
        startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), PICK_FROM_GALLERY);
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public boolean checkPermissionForReadExtertalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    public void requestPermissionForReadExtertalStorage() throws Exception {
        try {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    101);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    public void clear()
    {
        binding.NameArabic.setText("");
        binding.NameEnglish.setText("");
        binding.Company.setText("");
        binding.Phone.setText("");
    }
}