package com.example.cookfood.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cookfood.ListItemAdapter;
import com.example.cookfood.MyListView;
import com.example.cookfood.R;

import java.util.ArrayList;
import java.util.List;

public class HomeAttention extends Fragment {
    private View root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(root==null){
            root = inflater.inflate(R.layout.home_attention,container,false);
        }
        LinearLayout linear = root.findViewById(R.id.attention_add);

        //自定义ListView，消除与ScrollView的冲突
        MyListView listView = new MyListView(root.getContext());
        //消除item自带的分割线
        listView.setDividerHeight(0);
        //传输数据
        List<String> data = new ArrayList<String>();
        for(int i=0;i<9;i++){
            data.add(String.valueOf(i));
        }

        listView.setAdapter(new ListItemAdapter(data,root.getContext()));
//      listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                startActivity(new Intent(getActivity(),CaipuDetail.class));
//            }
//        });
        linear.addView(listView);
        return root;
    }
}
