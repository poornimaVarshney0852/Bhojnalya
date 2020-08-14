package com.example.bhojnalya.ui.Notification;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bhojnalya.R;
import com.example.bhojnalya.ui.history.HistoryFeedAdapter;
import com.example.bhojnalya.ui.home.HomeViewModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Notification extends Fragment {

    private RecyclerView recyclerView;
    private NotifictaionAdapter feedAdapter;


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


        FirebaseRecyclerOptions<NotificationViewModel> options =
                new FirebaseRecyclerOptions.Builder<NotificationViewModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Notification_Of_Feed"), NotificationViewModel.class)
                        .build();

        Log.d("data","data = "+options);
        feedAdapter = new NotifictaionAdapter(options);
        recyclerView.setAdapter(feedAdapter);

        return  root;
    }
    @Override
    public void onStart() {

        super.onStart();
        feedAdapter.startListening();
    }

}
