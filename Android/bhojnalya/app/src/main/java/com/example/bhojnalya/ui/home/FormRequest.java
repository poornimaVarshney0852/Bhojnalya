package com.example.bhojnalya.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.bhojnalya.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class FormRequest extends AppCompatActivity {
    ImageView imageLocation;
    EditText textViewLocation;

    FusedLocationProviderClient  fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donate_form);

        imageLocation = findViewById(R.id.imageView_location);
        textViewLocation = findViewById(R.id.textView_sign_location);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(FormRequest.this);

        imageLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(ActivityCompat.checkSelfPermission(FormRequest.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                   getLocation();
               }
               else{
                   ActivityCompat.requestPermissions(FormRequest.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
               }
            }

            private void getLocation() {
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if(location != null){

                            try {
                                Geocoder geocoder = new Geocoder(FormRequest.this, Locale.getDefault());
                                List<Address> addresses = geocoder.getFromLocation(
                                        location.getLatitude(),location.getLongitude(),1);
                                textViewLocation.setText(addresses.get(0).getAddressLine(0));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });


    }



}
