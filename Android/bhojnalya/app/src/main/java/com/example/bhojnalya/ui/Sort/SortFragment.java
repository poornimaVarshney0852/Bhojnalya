package com.example.bhojnalya.ui.Sort;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.bhojnalya.R;

public class SortFragment extends Fragment {

    private SortViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(SortViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sort, container, false);
        final Button TransportFeed = root.findViewById(R.id.transportDetailButton);
        TransportFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TransportFeed.class);
                startActivity(intent);
            }
        });

        return root;
    }
}
