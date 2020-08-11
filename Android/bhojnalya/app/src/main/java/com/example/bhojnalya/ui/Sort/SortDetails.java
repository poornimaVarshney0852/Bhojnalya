package com.example.bhojnalya.ui.Sort;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bhojnalya.R;
import com.example.bhojnalya.ui.home.FeedAdapter;
import com.example.bhojnalya.ui.home.HomeViewModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SortDetails extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FeedAdapter feedAdapter;
    String sort_type = null;
    public static String city = null;
    private DatabaseReference reff;
    TextView textView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_history);

        recyclerView = findViewById(R.id.recycler);
        textView = findViewById(R.id.textView4);
        //To print latest data first from firebase
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(false);


        recyclerView.setLayoutManager(linearLayoutManager);
        FirebaseRecyclerOptions<HomeViewModel> options = null;


        Bundle bundle = getIntent().getExtras();
        sort_type = bundle.getString("type");
        city = bundle.getString("city");
        Log.d("type",sort_type);

        if(sort_type.equals("Location")) {

            options =
                    new FirebaseRecyclerOptions.Builder<HomeViewModel>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("Feed").orderByChild("City").equalTo(city), HomeViewModel.class)
                            .build();
        }
        else {
            options =
                    new FirebaseRecyclerOptions.Builder<HomeViewModel>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("Feed").orderByChild("UserType").equalTo(sort_type), HomeViewModel.class)
                            .build();
        }

        feedAdapter = new FeedAdapter(options);
        recyclerView.setAdapter(feedAdapter);


    }
    @Override
    public void onStart() {

        super.onStart();
        feedAdapter.startListening();
    }
}
