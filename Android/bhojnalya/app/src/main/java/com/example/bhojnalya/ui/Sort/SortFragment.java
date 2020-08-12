package com.example.bhojnalya.ui.Sort;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.bhojnalya.MainActivity;
import com.example.bhojnalya.R;
import com.example.bhojnalya.ui.home.FormRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SortFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    Spinner sortspinner;
    ArrayAdapter<CharSequence> adapter;
    static String sort_type ;
    private DatabaseReference reff;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_sort, container, false);

        sortspinner = root.findViewById(R.id.sort_spinner);

        adapter = ArrayAdapter.createFromResource(getContext(), R.array.sort, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortspinner.setAdapter(adapter);
        sortspinner.setOnItemSelectedListener(this);


        return root;
    }


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String str = parent.getItemAtPosition(position).toString();
        if(str.equals("Select")){

        }
        else if (str.equals("Location")) {
            sort_type = "Location";
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            reff = FirebaseDatabase.getInstance().getReference().child("UserDetails").child(user.getUid());
            reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String city = dataSnapshot.child("City").getValue().toString();
                    Intent intent = new Intent(getActivity(), SortDetails.class);
                    intent.putExtra("type", sort_type);
                    intent.putExtra("city",city);
                    startActivity(intent);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if (str.equals("Transport Feed")) {

            Log.d("","hello FROM SORT FRAGMENT");
            Intent intent = new Intent(getActivity(), TransportFeed.class);
            startActivity(intent);

        } else {
            if (str.equals("Donation")) {
                sort_type = "Donation";
            } else if (str.equals("Request")) {
                sort_type = "Request";
            }
            Intent intent = new Intent(getActivity(), SortDetails.class);
            intent.putExtra("type", sort_type);
            startActivity(intent);

        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        Intent intent = new Intent(this.getContext(), SortFragment.class);
        startActivity(intent);
    }
}
