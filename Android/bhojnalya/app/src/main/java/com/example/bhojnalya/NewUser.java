package com.example.bhojnalya;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.bhojnalya.ui.home.FormRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class NewUser extends AppCompatActivity {

    EditText nameEditText,phoneNumberEditText,emailEditText,passwordEditText,locationEditText;
    String name,phoneNumber,email,password,location,currentlocation;
    ImageView imageLocation;
    Button createNewUserButton, loginButton;
    FirebaseAuth firebaseAuth;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user);

        locationEditText = findViewById(R.id.textView_sign_location);
        imageLocation = findViewById(R.id.imageView_location);
        nameEditText = findViewById(R.id.username);
        phoneNumberEditText = findViewById(R.id.phonenumber);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        createNewUserButton = findViewById(R.id.createNewUser);
        loginButton = findViewById(R.id.login);

        firebaseAuth = FirebaseAuth.getInstance();

        // Get Current Location
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(NewUser.this);
        imageLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(NewUser.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(NewUser.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewUser.this,login.class);
                startActivity(intent);
            }
        });

        createNewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = nameEditText.getText().toString().trim();
                password = passwordEditText.getText().toString().trim();
                phoneNumber = phoneNumberEditText.getText().toString().trim();
                email = emailEditText.getText().toString().trim();
                location = locationEditText.getText().toString();

                if(TextUtils.isEmpty(name)){
                    nameEditText.setError("Enter Name");
                    nameEditText.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    emailEditText.setError("Enter Email");
                    emailEditText.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(phoneNumber)){
                    phoneNumberEditText.setError("Enter Phone Number");
                    phoneNumberEditText.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    passwordEditText.setError("Enter Password");
                    passwordEditText.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(location)){
                    locationEditText.setError("Enter Location");
                    locationEditText.requestFocus();
                    return;
                }


                getAuthentication();


            }
        });


    }

    private void getAuthentication() {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            HashMap<String, Object> hm = new HashMap<>();
                            hm.put("Name",name );
                            hm.put("Email",email);
                            hm.put("PhoneNumber",phoneNumber);
                            hm.put("Password",password);
                            hm.put("UserId",FirebaseAuth.getInstance().getUid());

                            FirebaseDatabase.getInstance().getReference().child("Poornima").child("UserDetails").push().setValue(hm).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(NewUser.this, "Successfully added", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(NewUser.this, "On failure" + e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });


                            Intent intent = new Intent(NewUser.this,MainActivity.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(NewUser.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
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
                        Geocoder geocoder = new Geocoder(NewUser.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(),location.getLongitude(),1);
                        currentlocation = addresses.get(0).getAddressLine(0);
                        locationEditText.setText(currentlocation);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
