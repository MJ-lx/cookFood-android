package com.example.cookfood.ui.putout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.cookfood.ui.caipu.PutoutCaipu;
import com.example.cookfood.R;

public class PutoutFragment extends Fragment {
    private PutoutViewModel notificationsViewModel;
    private ImageButton putoutBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(PutoutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_putout, container, false);
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        putoutBtn = root.findViewById(R.id.imageButton2);
        putoutBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PutoutCaipu.class));
            }
        });
        return root;
    }
}
