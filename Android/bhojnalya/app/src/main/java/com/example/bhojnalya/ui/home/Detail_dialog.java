package com.example.bhojnalya.ui.home;

        import android.app.FragmentTransaction;
        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.RadioButton;
        import android.widget.RadioGroup;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.fragment.app.DialogFragment;

        import android.app.Fragment;

        import androidx.fragment.app.FragmentManager;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.bhojnalya.R;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

public class Detail_dialog extends AppCompatActivity {
    private TextView description,type,veg_non_veg,quantity,cook,location,transport, self;
    private Button transportButton, acceptButton;
    private RadioButton radioButton;
    private RadioGroup radioGroup;
    private String feedId;
    private DatabaseReference reff;
    private String radioButtonText;
    private int feedPos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_dialogue);
        description = findViewById(R.id.description);
        type = findViewById(R.id.userType);
        veg_non_veg = findViewById(R.id.veg_non_veg);
        quantity = findViewById(R.id.quantity);
        cook = findViewById(R.id.cooked);
        location = findViewById(R.id.location);
        transport = findViewById(R.id.transport);
        self = findViewById(R.id.selfTransport);
        self.setVisibility(View.GONE);

        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setVisibility(View.GONE);
        feedId = getIntent().getExtras().get("id").toString();
//        feedPos = (int)getIntent().getExtras().get("feedPos");

        reff = FirebaseDatabase.getInstance().getReference().child("Feed").child(feedId);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String type_1 = dataSnapshot.child("UserType").getValue().toString();
                String description_1 = dataSnapshot.child("FoodDiscription").getValue().toString();
                String quantity_1 = dataSnapshot.child("QuantityMeasurement").getValue().toString();
                String cooked_1 = dataSnapshot.child("Cooked_UnCooked").getValue().toString();
                String veg_1 = dataSnapshot.child("Veg_NonVeg").getValue().toString();
                String location_1 = dataSnapshot.child("location").getValue().toString();
                String transport_1 = dataSnapshot.child("transport").getValue().toString();
                //tranportRequired = dataSnapshot.child("transport").getValue().toString();
                description.setText(description_1);
                type.setText(type_1);
                quantity.setText(quantity_1);
                cook.setText(cooked_1);
                veg_non_veg.setText(veg_1);
                location.setText(location_1);
                transport.setText(transport_1);

                if(transport_1.equals("no"))
                {
                    self.setVisibility(View.VISIBLE);
                    radioGroup.setVisibility(View.VISIBLE);
                }
                else{
                    acceptButton.setEnabled(true);
                    acceptButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseDatabase.getInstance().getReference().child("Feed").child(feedId).child("feedAccepted").setValue(FirebaseAuth.getInstance().getUid());

                            Log.d("feedAccepted","changed = "+FirebaseAuth.getInstance().getUid());

                        }
                    });
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //    transportButton = findViewById(R.id.transportButton);
        acceptButton = findViewById(R.id.accept);
        acceptButton.setEnabled(false);



    }
    public void checkButton(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);

        if(radioButton.isChecked()) {
            acceptButton.setEnabled(true);
            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String radio = radioButton.getText().toString();

                    if(radio.equals("Yes"))
                    {
                        //  RecyclerView recyclerView =
                        radioButtonText = "yes";
                    }

                    else
                    {
                        radioButtonText = "no";
                    }
//                   HomeViewModel hvm = new HomeViewModel();
//                   hvm.setSelf_p_d(radioButtonText);
                    FirebaseDatabase.getInstance().getReference().child("Feed").child(feedId).child("self_d_p").setValue(radioButtonText);
                    FirebaseDatabase.getInstance().getReference().child("Feed").child(feedId).child("feedAccepted").setValue(FirebaseAuth.getInstance().getUid());
                    Toast.makeText(Detail_dialog.this, "Accepted and Tranport feed Generated", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getBaseContext(), HomeFragment.class);
//                    startActivity(intent);
                }
            });
        }

    }

}