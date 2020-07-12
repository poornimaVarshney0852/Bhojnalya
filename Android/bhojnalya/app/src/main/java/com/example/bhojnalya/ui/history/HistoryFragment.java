package com.example.bhojnalya.ui.history;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bhojnalya.R;
import com.example.bhojnalya.ui.home.FeedAdapter;
import com.example.bhojnalya.ui.home.HomeViewModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HistoryFragment extends Fragment {

    private DatabaseReference ref;
    private FirebaseRecyclerOptions<HomeViewModel> options;
    private FirebaseRecyclerAdapter<HomeViewModel, FeedAdapter.FeedViewHolder> adapter;
    private RecyclerView recyclerView;
    private HistoryFeedAdapter feedAdapter;
    private int position=-1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = root.findViewById(R.id.recycler);

        //To print latest data first from firebase
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        linearLayoutManager.setReverseLayout(false);

        //end here to print latest data first from firebase

        String id = FirebaseAuth.getInstance().getUid();
        recyclerView.setLayoutManager(linearLayoutManager);
        FirebaseRecyclerOptions<HomeViewModel> options =
                new FirebaseRecyclerOptions.Builder<HomeViewModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Feed").orderByChild("UserId").equalTo(id), HomeViewModel.class)
                        .build();

        Log.d("data","data = "+options);
        feedAdapter = new HistoryFeedAdapter(options);
        recyclerView.setAdapter(feedAdapter);



        return root;
    }
    @Override
    public void onStart() {

        super.onStart();
        feedAdapter.startListening();
    }


}
