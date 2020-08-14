package com.example.bhojnalya.ui.Notification;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bhojnalya.R;
import com.example.bhojnalya.ui.history.HistoryFeedAdapter;
import com.example.bhojnalya.ui.home.Detail_dialog;
import com.example.bhojnalya.ui.home.HomeViewModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotifictaionAdapter extends FirebaseRecyclerAdapter<NotificationViewModel, NotifictaionAdapter.FeedViewHolder>

    {

        //  ArrayList<HomeViewModel> mModel;
        private DatabaseReference reff;
    public NotifictaionAdapter(@NonNull FirebaseRecyclerOptions<NotificationViewModel> options) {
        super(options);

    }




        @Override
        protected void onBindViewHolder(@NonNull final NotifictaionAdapter.FeedViewHolder holder, final int position, @NonNull final NotificationViewModel model) {


        if(FirebaseAuth.getInstance().getUid().equals(model.getFrom_pickup_id()) || FirebaseAuth.getInstance().getUid().equals(model.getTo_delivery()) ){
            String id = model.getAccepted_by();
            reff = FirebaseDatabase.getInstance().getReference().child("UserDetails").child(id);
            reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String type_1 = dataSnapshot.child("Name").getValue().toString();
                    String number = dataSnapshot.child("PhoneNumber").getValue().toString();
                    holder.name.setText(type_1);
                    holder.phonenumber.setText(number);
                    holder.description.setText(model.getDescription());

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            holder.itemView.setVisibility(View.VISIBLE);
        }
        else {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }




        @NonNull
        @Override
        public NotifictaionAdapter.FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_notification, parent, false);

        NotifictaionAdapter.FeedViewHolder feedViewHolder = new NotifictaionAdapter.FeedViewHolder(view);
        return feedViewHolder;


    }



        public class FeedViewHolder extends RecyclerView.ViewHolder {
            TextView description, name, phonenumber;
            LinearLayout acceptedform;

            public FeedViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.transporter_name);
                phonenumber = itemView.findViewById(R.id.transporter_phonenumber);
                description = itemView.findViewById(R.id.foodDescription);

            }
        }

    }


