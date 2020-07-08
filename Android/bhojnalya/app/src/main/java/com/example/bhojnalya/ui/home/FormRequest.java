package com.example.bhojnalya.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bhojnalya.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class FormRequest extends AppCompatActivity {
//    ImageButton imageLocation;
    private EditText foodDiscription,quantity;
    private CheckBox pickup ;
    private TextView location;
    private String Transport="";
    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donate_form);
        foodDiscription =findViewById(R.id.editText_donation_food);
        pickup=findViewById(R.id.checkBox_donation_pick);
       location = findViewById(R.id.textView_sign_location);
        submit = findViewById(R.id.button_donation_submit);
        pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = (((CheckBox)v).isChecked());
                if(checked)
                {
                    Transport="yes";
                }
                else
                {
                    Transport = "no";
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,Object> hm = new HashMap<>();
                hm.put("FoodDiscription", foodDiscription.getText().toString());
                hm.put("location", location.getText().toString());
                if(Transport.equals("yes"))
                {
                    hm.put("transport", "yes");
                }
                else
                {
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
                        Toast.makeText(FormRequest.this, "On failure"+e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });










    }


}
