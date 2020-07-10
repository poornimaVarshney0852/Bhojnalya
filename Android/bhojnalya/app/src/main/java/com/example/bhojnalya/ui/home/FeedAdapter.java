package com.example.bhojnalya.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bhojnalya.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class FeedAdapter extends FirebaseRecyclerAdapter<HomeViewModel, FeedAdapter.FeedViewHolder> {

    public FeedAdapter(@NonNull FirebaseRecyclerOptions<HomeViewModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FeedViewHolder holder, int position, @NonNull HomeViewModel model) {
        holder.description.setText(model.getFoodDiscription());
        holder.location.setText(model.getLocation());
        holder.transport.setText(model.getTransport());
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed, parent, false);
        return new FeedViewHolder(view);
    }

    class FeedViewHolder extends RecyclerView.ViewHolder{
        TextView description, location, transport;
        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.foodDescription);
            location = itemView.findViewById(R.id.location);
            transport = itemView.findViewById(R.id.transport);
        }
    }
}
