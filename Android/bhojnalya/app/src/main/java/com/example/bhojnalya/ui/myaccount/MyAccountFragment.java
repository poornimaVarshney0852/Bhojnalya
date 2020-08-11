package com.example.bhojnalya.ui.myaccount;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.bhojnalya.R;
import com.example.bhojnalya.login;
import com.example.bhojnalya.ui.home.FormRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MyAccountFragment extends Fragment {

    private MyAccountViewModel notificationsViewModel;
    private DatabaseReference reff;
    FloatingActionButton logoutButton;
    TextView phonenumberTextView,emailTextView;
    TextView locationTextView;
    ImageView currentLocationImageView;
    EditText locationEditText;
    Button saveChanges;
    FusedLocationProviderClient fusedLocationProviderClient;
    String clocation ;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(MyAccountViewModel.class);
        View root = inflater.inflate(R.layout.fragment_myaccount, container, false);
        final TextView nameTextView = root.findViewById(R.id.username);
        phonenumberTextView = root.findViewById(R.id.phonenumber);
        locationTextView = root.findViewById(R.id.location);
        emailTextView = root.findViewById(R.id.email);
        currentLocationImageView = root.findViewById(R.id.imageView_location);
        saveChanges = root.findViewById(R.id.savechanges);
        locationEditText = root.findViewById(R.id.currentlocation);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        reff = FirebaseDatabase.getInstance().getReference().child("UserDetails").child(user.getUid());
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("Name").getValue().toString();
                String phoneNumber = dataSnapshot.child("PhoneNumber").getValue().toString();
                String location = dataSnapshot.child("Location").getValue().toString();
                String email = dataSnapshot.child("Email").getValue().toString();
                nameTextView.setText(name);
                phonenumberTextView.setText(phoneNumber);
                locationTextView.setText(location);
                emailTextView.setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        logoutButton = root.findViewById(R.id.floatingLogoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), login.class);
                startActivity(intent);
            }
        });


        currentLocationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationTextView.setVisibility(View.GONE);
                locationEditText.setVisibility(View.VISIBLE);
                saveChanges.setVisibility(View.VISIBLE);
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clocation = locationEditText.getText().toString();
                FirebaseDatabase.getInstance().getReference().child("UserDetails").child(user.getUid()).child("Location").setValue(clocation);
                Log.d("location","location added"+clocation);
            }
        });

        return root;
    }

    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if(location != null){

                    try {
                        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(),location.getLongitude(),1);
                        locationEditText.setText(addresses.get(0).getAddressLine(0));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
