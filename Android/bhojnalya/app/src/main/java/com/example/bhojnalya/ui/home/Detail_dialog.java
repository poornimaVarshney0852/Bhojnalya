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
        import com.example.bhojnalya.ui.Sort.TransportModel;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.HashMap;

public class Detail_dialog extends AppCompatActivity {
    private TextView description,type,veg_non_veg,quantity,cook,location,transport, self;
    private Button  acceptButton;
    private RadioButton radioButton;
    private RadioGroup radioGroup;
    private String feedId;
    private DatabaseReference reff;
    private String radioButtonText;
    String Pick_up_location,Pick_up_Phone_number,Pick_up_name,Delivery_location,Deliver_name,Delivery_phone_Number,to_Delivery_Uid,Pickup_Uid;
    TransportModel hm = new TransportModel();


//  String Pick_up_location,Pick_up_Phone_number,Pick_up_name,Delivery_location,Deliver_name,Delivery_phone_Number;

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

                    if(radio.equals("Yes")) {
                        //  RecyclerView recyclerView =
                        radioButtonText = "yes";
                        FirebaseDatabase.getInstance().getReference().child("Feed").child(feedId).child("self_d_p").setValue(radioButtonText);

                    }

                    else {


                        radioButtonText = "no";
                        FirebaseDatabase.getInstance().getReference().child("Feed").child(feedId).child("self_d_p").setValue(radioButtonText) ;
//                        String Pick_up_location,Pick_up_Phone_number,Pick_up_name,Delivery_location,Deliver_name,Delivery_phone_Number;
//                        TransportModel hm = new TransportModel();
                       FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                //Entering the post information into Transport branch in database when feed type is "DONATION"

                                if (dataSnapshot.child("Feed").child(feedId).child("UserType").getValue().toString().equals("Donation")) {

                                   // HashMap<String,Object> hm = new HashMap<>();
                                     Pick_up_location = dataSnapshot.child("Feed").child(feedId).child("location").getValue().toString();
                                   //  hm.setPick_up_location(Pick_up_location);
                                    Pickup_Uid = dataSnapshot.child("Feed").child(feedId).child("UserId").getValue().toString();
                                             Pick_up_Phone_number = dataSnapshot.child("UserDetails").child(Pickup_Uid).child("PhoneNumber").getValue().toString();
                                             Pick_up_name = dataSnapshot.child("UserDetails").child(Pickup_Uid).child("Name").getValue().toString();



                                    //  FirebaseDatabase.getInstance().getReference().child("Transport_Feed").push().child(FirebaseAuth.getInstance().getUid()).child("Pick_up_location").setValue(Pick_up_location);
                                             Delivery_location = dataSnapshot.child("UserDetails").child(FirebaseAuth.getInstance().getUid()).child("Location").getValue().toString();
                                             Deliver_name = dataSnapshot.child("UserDetails").child(FirebaseAuth.getInstance().getUid()).child("Name").getValue().toString();
                                             Delivery_phone_Number = dataSnapshot.child("UserDetails").child(FirebaseAuth.getInstance().getUid()).child("PhoneNumber").getValue().toString();
                                                to_Delivery_Uid = FirebaseAuth.getInstance().getUid();
//                                    hm.put("Pick_up_name",Pick_up_name);
//                                    hm.put("Pick_up_location",Pick_up_location);
//                                    hm.put("Pick_up_Phone_number",Pick_up_Phone_number);
//                                    hm.put("Delivery_location",Delivery_location);
//                                    hm.put("Delivery_name",Deliver_name);
//                                    hm.put("Delivery_phone_Number",Delivery_phone_Number);
//                                    hm.setFrom_pickup_Uid(Pick_phone_Uid);
//                                    hm.setTo_deliver_Uid(FirebaseAuth.getInstance().getUid());
//                                    hm.setPick_up_name(Pick_up_name);
//                                    hm.setPick_up_Phone_number(Pick_up_Phone_number);
//                                    hm.setDelivery_name(Deliver_name);
//                                    hm.setDelivery_location(Delivery_location);
//                                    hm.setDelivery_phone_Number(Delivery_phone_Number);
//                                    FirebaseDatabase.getInstance().getReference().child("Transport_Feed").setValue(hm);

                                }
                                //Entering the post information into Transport branch in database when feed type is "Request"

                                else {
                                   // HashMap<String,Object> hm = new HashMap<>();

                                            Pick_up_location = dataSnapshot.child("UserDetails").child(FirebaseAuth.getInstance().getUid()).child("Location").getValue().toString();
                                            Pick_up_name = dataSnapshot.child("UserDetails").child(FirebaseAuth.getInstance().getUid()).child("Name").getValue().toString();
                                            Pick_up_Phone_number = dataSnapshot.child("UserDetails").child(FirebaseAuth.getInstance().getUid()).child("PhoneNumber").getValue().toString();
                                            Pickup_Uid = FirebaseAuth.getInstance().getUid();
                                            Delivery_location = dataSnapshot.child("Feed").child(feedId).child("location").getValue().toString();
                                            hm.setDelivery_location(Delivery_location);
                                            to_Delivery_Uid = dataSnapshot.child("Feed").child(feedId).child("UserId").getValue().toString();


                                            Delivery_phone_Number = dataSnapshot.child("UserDetails").child(to_Delivery_Uid).child("PhoneNumber").getValue().toString();
                                            Deliver_name = dataSnapshot.child("UserDetails").child(to_Delivery_Uid).child("Name").getValue().toString();

//                                    hm.put("Pick_up_location",Pick_up_location);
//                                    hm.put("Pick_up_name",Pick_up_name);
//                                    hm.put("Pick_up_Phone_Number",Pick_up_Phone_number);
//                                    hm.put("Delivery_location",Delivery_location);
//                                    hm.put("Delivery_name",Deliver_name);
//                                    hm.put("Delivery_phone_Number",Delivery_phone_Number);
//                                    hm.setFrom_pickup_Uid(FirebaseAuth.getInstance().getUid());
//                                    hm.setTo_deliver_Uid(Delivery_phone_Uid);
//                                    hm.setPick_up_name(Pick_up_name);
//                                    hm.setPick_up_location(Pick_up_location);
//                                    hm.setPick_up_Phone_number(Pick_up_Phone_number);
//                                    hm.setDelivery_name(Deliver_name);
//                                    hm.setDelivery_phone_Number(Delivery_phone_Number);
//                                    FirebaseDatabase.getInstance().getReference().child("Transport_Feed").setValue(hm);
                                }

                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }


                        });

                }

                    //                   HomeViewModel hvm = new HomeViewModel();
//                   hvm.setSelf_p_d(radioButtonText);
                    hm.setFrom_pickup_Uid(Pickup_Uid);
                    hm.setTo_deliver_Uid(to_Delivery_Uid);
                    hm.setPick_up_name(Pick_up_name);
                    hm.setPick_up_location(Pick_up_location);
                    hm.setPick_up_Phone_number(Pick_up_Phone_number);
                    hm.setDelivery_name(Deliver_name);
                    hm.setDelivery_phone_Number(Delivery_phone_Number);
                    FirebaseDatabase.getInstance().getReference().child("Transport_Feed").push().setValue(hm);
                    FirebaseDatabase.getInstance().getReference().child("Feed").child(feedId).child("feedAccepted").setValue(FirebaseAuth.getInstance().getUid());
                    Toast.makeText(Detail_dialog.this, "Accepted and Tranport feed Generated", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getBaseContext(), HomeFragment.class);
//                    startActivity(intent);
            }
    });

}}}