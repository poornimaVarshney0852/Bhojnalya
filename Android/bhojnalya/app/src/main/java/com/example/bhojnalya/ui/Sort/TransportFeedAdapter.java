package com.example.bhojnalya.ui.Sort;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bhojnalya.R;
import com.example.bhojnalya.ui.home.FeedAdapter;
import com.example.bhojnalya.ui.home.HomeViewModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class TransportFeedAdapter extends FirebaseRecyclerAdapter<TransportModel, TransportFeedAdapter.FeedViewHolder> {

    public TransportFeedAdapter(@NonNull FirebaseRecyclerOptions<TransportModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull TransportFeedAdapter.FeedViewHolder holder, final int position, @NonNull final TransportModel model) {
        Log.d("","hello FROM TRANSPORT FEED");
            holder.description.setText(model.Description);
            holder.pickup_location.setText(model.Pick_up_location);
            holder.pickup_number.setText(model.Pick_up_Phone_number);
            holder.delivery_transport.setText(model.Delivery_location);
            holder.delivery_number.setText(model.Delivery_phone_Number);

            holder.Delivery_accept_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HashMap <String, Object> hm = new HashMap<>();
                    hm.put("accepted_by",FirebaseAuth.getInstance().getUid());
                    hm.put("to_delivery",model.getTo_deliver_Uid());
                    hm.put("from_pickup_id",model.getFrom_pickup_Uid());
                    hm.put("food_discription",model.Description);
                    FirebaseDatabase.getInstance().getReference().child("Transport_Feed").child(getRef(position).getKey()).child("accepted_by").setValue(FirebaseAuth.getInstance().getUid());
                    FirebaseDatabase.getInstance().getReference().child("Notification_Of_Feed").push().setValue(hm);
                }
            });

        }


    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transport_feed, parent, false);
        Log.d("","hello FROM TFEED VIEWHOLDER");
        FeedViewHolder feedViewHolder = new FeedViewHolder(view);
        return feedViewHolder;
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder {
        TextView description, pickup_location,pickup_number, delivery_transport,delivery_number;
        Button Delivery_accept_btn;

        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);

            description = itemView.findViewById(R.id.foodDescription);
            pickup_location = itemView.findViewById(R.id.pickup_location);
            pickup_number = itemView.findViewById(R.id.pickup_phonenumber);
            delivery_transport = itemView.findViewById(R.id.delivery_transport);
            delivery_number = itemView.findViewById(R.id.delivery_phonenumber);
            Delivery_accept_btn =  itemView.findViewById(R.id.delivery_accept_btn);

        }
    }

}
