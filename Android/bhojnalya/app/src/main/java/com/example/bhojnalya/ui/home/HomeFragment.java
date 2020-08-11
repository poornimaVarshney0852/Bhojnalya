package com.example.bhojnalya.ui.home;

import android.app.Dialog;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bhojnalya.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;


public class HomeFragment extends Fragment  {
    private DatabaseReference ref;
    private FirebaseRecyclerOptions<HomeViewModel> options;
    private FirebaseRecyclerAdapter<HomeViewModel, FeedAdapter.FeedViewHolder> adapter;
    private FloatingActionButton addRequestbutton;
    private RecyclerView recyclerView;
    private FeedAdapter feedAdapter;
    List<HomeViewModel> hvm = new ArrayList<>();
    private int position=-1;



    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        addRequestbutton = root.findViewById(R.id.add_request_button);
        recyclerView = root.findViewById(R.id.recycler);

//        //To print latest data first from firebase
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        linearLayoutManager.setReverseLayout(false);

      //end here to print latest data first from firebase

         recyclerView.setLayoutManager(linearLayoutManager);
//        FirebaseDatabase.getInstance().getReference().child("Transport_Feed").removeValue();

        FirebaseRecyclerOptions<HomeViewModel> options =
                new FirebaseRecyclerOptions.Builder<HomeViewModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Feed").orderByChild("feedAccepted").equalTo("no"), HomeViewModel.class)
                        .build();


        feedAdapter = new FeedAdapter(options);
        recyclerView.setAdapter(feedAdapter);





//.child().child("Feed").orderByChild("feedAccepted").equalTo("no")



        addRequestbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), FormRequest.class));
            }
        });

        return root;
    }

    @Override
    public void onStart() {

        super.onStart();
        feedAdapter.startListening();
    }


}
