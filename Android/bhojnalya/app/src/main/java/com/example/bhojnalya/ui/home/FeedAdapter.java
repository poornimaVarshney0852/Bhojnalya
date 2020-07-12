package com.example.bhojnalya.ui.home;

import android.app.Dialog;
import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.system.Os;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bhojnalya.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class FeedAdapter extends FirebaseRecyclerAdapter<HomeViewModel, FeedAdapter.FeedViewHolder> {

  //  ArrayList<HomeViewModel> mModel;

    public FeedAdapter(@NonNull FirebaseRecyclerOptions<HomeViewModel> options) {
        super(options);

    }



    @Override
    protected void onBindViewHolder(@NonNull FeedViewHolder holder, final int position, @NonNull HomeViewModel model) {

                    if(!FirebaseAuth.getInstance().getUid().equals(model.getUserId())) {
                        holder.description.setText(model.getFoodDiscription());
                        holder.location.setText(model.getLocation());
                        holder.transport.setText(model.getTransport());
                        holder.userType.setText(model.getUserType());
                        Log.d("user id", " user id of feeds" + model.getUserId());
                        Log.d("user id", " user id of self = " + model.getSelf_d_p());

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
                    else
                    {
                        holder.itemView.setVisibility(View.GONE);
                        holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                    }


        }




    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed, parent, false);

           FeedViewHolder feedViewHolder = new FeedViewHolder(view);
        return feedViewHolder;


    }


    public class FeedViewHolder extends RecyclerView.ViewHolder {
        TextView description, location, transport,userType;

        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);

            description = itemView.findViewById(R.id.foodDescription);
            location = itemView.findViewById(R.id.location);
            transport = itemView.findViewById(R.id.transport);
            userType = itemView.findViewById(R.id.type);

            }
            }

        }





