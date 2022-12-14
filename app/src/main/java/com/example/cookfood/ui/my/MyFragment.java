package com.example.cookfood.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.cookfood.Login;
import com.example.cookfood.MainActivity;
import com.example.cookfood.MyCailanzi;
import com.example.cookfood.MySetting;
import com.example.cookfood.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MyFragment extends Fragment {

    private MyViewModel notificationsViewModel;
    private ImageView myBtn;
    private TableLayout my_collect;
    private TextView id_txt;
    private BottomNavigationView Navview;
    private TableLayout lanzi;
    private ImageButton setting;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(MyViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my, container, false);
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        myBtn = root.findViewById(R.id.my);
        myBtn.setEnabled(true);
        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Login.class));
            }
        });

        my_collect = root.findViewById(R.id.my_collect);
        my_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), CollectFragment.class));
                MainActivity activity = (MainActivity)getActivity();
                Navview = activity.findViewById(R.id.nav_view);
                Navview.setSelectedItemId(R.id.navigation_collect);
            }
        });

        id_txt = root.findViewById(R.id.my_id);
        // ??????????????????
        Intent intent = getActivity().getIntent();
        //??????????????????
        String str = intent.getStringExtra("user_id");
//        Log.d("user_id",str);
        if(str!=null){
            //?????????
            id_txt.setText(str);
            ImageButton my_btn = root.findViewById(R.id.my);
            my_btn.setEnabled(false);
        }

        lanzi = root.findViewById(R.id.lanzi);
        lanzi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //??????????????????
                Intent intent = new Intent(getContext(), MyCailanzi.class);
                //?????????????????????
                intent.putExtra("user_id",str);
                //????????????
                startActivity(intent);
            }
        });

        setting = root.findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //??????????????????
                Intent intent = new Intent(getContext(), MySetting.class);
                startActivity(intent);
            }
        });
        return root;
    }
}