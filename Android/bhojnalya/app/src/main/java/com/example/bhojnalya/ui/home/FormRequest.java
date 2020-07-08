package com.example.bhojnalya.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.bhojnalya.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import java.util.HashMap;

public class FormRequest extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
//    ImageButton imageLocation;
//    ImageButton imageLocation;
    private EditText foodDiscription,quantity;
    private CheckBox pickup ;
    private TextView location;
    private String Transport="";
    private Button submit;
    ImageView imageLocation;
    TextView textViewLocation;
    Spinner quantityMeasureSpninner, donationSpinner, vegSpinner, cookedSpinner;

    FusedLocationProviderClient  fusedLocationProviderClient;
    ArrayAdapter<CharSequence> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donate_form);
        foodDiscription = findViewById(R.id.editText_donation_food);
        quantity = findViewById(R.id.editText_donation_amount);
        pickup = findViewById(R.id.checkBox_donation_pick);
        location = findViewById(R.id.textView_sign_location);
        submit = findViewById(R.id.button_donation_submit);
        donationSpinner = findViewById(R.id.spinner_donation_donation);
        quantityMeasureSpninner = findViewById(R.id.spinner_donation_amount);
        vegSpinner = findViewById(R.id.spinner_veg_non_veg);
        cookedSpinner = findViewById(R.id.spinner_donation_cooked);

    // Retrieving Spinner data
        adapter = ArrayAdapter.createFromResource(this, R.array.donation, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        donationSpinner.setAdapter(adapter);
        donationSpinner.setOnItemSelectedListener(this);

        adapter = ArrayAdapter.createFromResource(this, R.array.measurements, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quantityMeasureSpninner.setAdapter(adapter);
        quantityMeasureSpninner.setOnItemSelectedListener(this);

        adapter = ArrayAdapter.createFromResource(this, R.array.cooked, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cookedSpinner.setAdapter(adapter);
        cookedSpinner.setOnItemSelectedListener(this);

        adapter = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vegSpinner.setAdapter(adapter);
        vegSpinner.setOnItemSelectedListener(this);


        imageLocation = findViewById(R.id.imageView_location);
        textViewLocation = findViewById(R.id.textView_sign_location);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(FormRequest.this);

        imageLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(FormRequest.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(FormRequest.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });

        pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = (((CheckBox) v).isChecked());
                if (checked) {
                    Transport = "yes";
                } else {
                    Transport = "no";
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> hm = new HashMap<>();
                hm.put("FoodDiscription", foodDiscription.getText().toString());
                hm.put("location", location.getText().toString());
                hm.put("UserType",donationSpinner.getSelectedItem().toString());
                hm.put("Cooked_UnCooked",cookedSpinner.getSelectedItem().toString());
                hm.put("Veg_NonVeg",vegSpinner.getSelectedItem().toString());
                hm.put("QuantityMeasurement",quantity.getText().toString()+" "+quantityMeasureSpninner.getSelectedItem().toString());
                if (Transport.equals("yes")) {
                    hm.put("transport", "yes");
                } else {
                    hm.put("transport", "no");
                }

                FirebaseDatabase.getInstance().getReference().child("Feed").push().setValue(hm).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(FormRequest.this, "Successfully added", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FormRequest.this, "On failure" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String str = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Intent intent = new Intent(this.getBaseContext(), FormRequest.class);
        startActivity(intent);
    }
}
