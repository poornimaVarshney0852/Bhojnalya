package com.example.bhojnalya.ui.history;

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
import com.example.bhojnalya.ui.home.Detail_dialog;
import com.example.bhojnalya.ui.home.HomeViewModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class HistoryFeedAdapter extends FirebaseRecyclerAdapter<HomeViewModel, HistoryFeedAdapter.FeedViewHolder> {

  //  ArrayList<HomeViewModel> mModel;
  private DatabaseReference reff;
    public HistoryFeedAdapter(@NonNull FirebaseRecyclerOptions<HomeViewModel> options) {
        super(options);

    }



    @Override
    protected void onBindViewHolder(@NonNull final FeedViewHolder holder, final int position, @NonNull HomeViewModel model) {

                    holder.description.setText(model.getFoodDiscription());
                    holder.userType.setText(model.getUserType());
                    if(model.getFeedAccepted().equals("no")) {
                        holder.status.setText("Not Accepted");
                        holder.status.setTextColor(-16776961);
                    }
                    else {
                        holder.status.setText("Accepted");
                        holder.status.setTextColor(-16776961 );
                        String id = model.getFeedAccepted();
                        Log.d("id","id = "+id);
                        reff = FirebaseDatabase.getInstance().getReference().child("UserDetails").child(id);
                        reff.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String type_1 = dataSnapshot.child("Name").getValue().toString();
                                String description_1 = dataSnapshot.child("Location").getValue().toString();
                                holder.name.setText(type_1);
                                holder.location.setText(description_1);

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        holder.acceptedform.setVisibility(View.VISIBLE);
                    }

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String id = getRef(position).getKey();
                            //   int positionOfFeed = position;

                            Intent intent = new Intent(v.getContext(), Detail_dialog.class);
                            intent.putExtra("id", "" + id);
                            //intent.putExtra("feedPos", positionOfFeed);
                            v.getContext().startActivity(intent);
                        }
                    });


        }




    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.historyfeed, parent, false);

           FeedViewHolder feedViewHolder = new FeedViewHolder(view);
        return feedViewHolder;


    }


    public class FeedViewHolder extends RecyclerView.ViewHolder {
        TextView description, location, name,userType,status;
        LinearLayout acceptedform;

        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);

            description = itemView.findViewById(R.id.foodDescription);
            location = itemView.findViewById(R.id.location);
            name = itemView.findViewById(R.id.name);
            userType = itemView.findViewById(R.id.type);
            status = itemView.findViewById(R.id.status);
            acceptedform = itemView.findViewById(R.id.acceptedlayout);

            }
            }

        }





