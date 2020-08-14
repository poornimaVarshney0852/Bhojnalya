package com.example.bhojnalya.ui.Sort;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bhojnalya.R;
import com.example.bhojnalya.ui.home.FeedAdapter;
import com.example.bhojnalya.ui.Sort.TransportModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class TransportFeed extends Fragment {

    private RecyclerView recyclerView;
    private TransportFeedAdapter feedAdapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.transport_feed_recycler, container, false);
        //To print latest data first from firebase
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        linearLayoutManager.setReverseLayout(false);

        //end here to print latest data first from firebase

        recyclerView.setLayoutManager(linearLayoutManager);
//        FirebaseDatabase.getInstance().getReference().child("Transport_Feed").removeValue();
//     Log.d("","hello FROM TRANSPORT FEED11111111111");
        FirebaseRecyclerOptions<TransportModel> options =
                new FirebaseRecyclerOptions.Builder<TransportModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Transport_Feed").orderByChild("accepted_by").equalTo("no"), TransportModel.class)
                        .build();


        feedAdapter = new TransportFeedAdapter(options);
        recyclerView.setAdapter(feedAdapter);


        return  root;
}
    @Override
    public void onStart() {

        super.onStart();
        feedAdapter.startListening();
    }

}
