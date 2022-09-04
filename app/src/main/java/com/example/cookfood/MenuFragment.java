package com.example.cookfood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment{

    private View rootview;
    private int index;
    private ArrayList<ArrayList<String>> caipu = new ArrayList<ArrayList<String>>();
    private ArrayList<TableLayout> tables =new ArrayList<TableLayout>();
    private ArrayList<Button> buttons = new ArrayList<Button>();
    private TableRow tableRow;

    private String[] menu1 = {"家常菜","下饭菜","快手菜","减肥食谱"};
    private String[] menu1_1 = {"红烧肉","可乐鸡翅","糖醋排骨","鱼香肉丝","红烧排骨","宫保鸡丁","酸菜鱼","清蒸鲈鱼","西红柿炒鸡蛋"};
    private String[] menu1_2 = {"麻婆豆腐","菠萝咕噜肉","油焖大虾","水煮肉片","豆腐","回锅肉","糖醋里脊","黄焖鸡"};
    private String[] menu1_3 = {"麻辣香锅","红烧茄子","酸辣土豆丝","京酱肉丝","地三鲜","烤鸡翅","土豆泥","红烧鱼"};
    private String[] menu1_4 = {"沙拉","薏米","红豆","芹菜","冬瓜","黄瓜","番茄","生菜","西兰花","卷心菜","苦瓜"};

    private String[] menu2 = {"绿叶蔬菜","时令蔬菜","瓜类","菌类","豆科","根茎蔬菜"};
    private String[] menu2_1 = {"莴笋","芦笋","西兰花","菠菜","芹菜","娃娃菜","生菜","菜花","小白菜","油麦菜","卷心菜","茼蒿","蒜苗","空心菜","大白菜","苦菊"};
    private String[] menu2_2 = {"茄子","土豆","菠菜","韭菜","西红柿","油菜","春笋","紫菜"};
    private String[] menu2_3 = {"黄瓜","南瓜","西葫芦","冬瓜","丝瓜","苦瓜","佛手瓜"};
    private String[] menu2_4 = {"金针菇","杏鲍菇","香菇","木耳","平菇","白玉菇","银耳","松茸","虫草花","茶树菇","猴头菇","白口蘑"};
    private String[] menu2_5 = {"豆角","豌豆","毛豆","蚕豆","豆芽","四季豆","豇豆","芸豆","黄豆芽","扁豆"};
    private String[] menu2_6 = {"山药","胡萝卜","白萝卜","芋头","红薯","茭白","莲藕","甜菜根","芥菜头"};
    public void setStr(int group){
        switch (group){
            case 0:
                caipu.add(addString(menu1));
                caipu.add(addString(menu1_1));
                caipu.add(addString(menu1_2));
                caipu.add(addString(menu1_3));
                caipu.add(addString(menu1_4));
                break;
            case 1:
                caipu.add(addString(menu2));
                caipu.add(addString(menu2_1));
                caipu.add(addString(menu2_2));
                caipu.add(addString(menu2_3));
                caipu.add(addString(menu2_4));
                caipu.add(addString(menu2_5));
                caipu.add(addString(menu2_6));
                break;
            case 2:
                caipu.add(addString(menu1));
                caipu.add(addString(menu1_1));
                caipu.add(addString(menu1_2));
                caipu.add(addString(menu1_3));
                caipu.add(addString(menu1_4));
                break;
            case 3:
                caipu.add(addString(menu1));
                caipu.add(addString(menu1_1));
                caipu.add(addString(menu1_2));
                caipu.add(addString(menu1_3));
                caipu.add(addString(menu1_4));
                break;
            case 4:
                caipu.add(addString(menu1));
                caipu.add(addString(menu1_1));
                caipu.add(addString(menu1_2));
                caipu.add(addString(menu1_3));
                caipu.add(addString(menu1_4));
                break;
            case 5:
                caipu.add(addString(menu1));
                caipu.add(addString(menu1_1));
                caipu.add(addString(menu1_2));
                caipu.add(addString(menu1_3));
                caipu.add(addString(menu1_4));
                break;
            case 6:
                caipu.add(addString(menu1));
                caipu.add(addString(menu1_1));
                caipu.add(addString(menu1_2));
                caipu.add(addString(menu1_3));
                caipu.add(addString(menu1_4));
                break;
        }
    }

    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(int index) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putInt("key",index);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            index = getArguments().getInt("key");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(rootview == null){
            rootview = inflater.inflate(R.layout.fragment_menu, container, false);
            setStr(index);
            initView(caipu);
        }
        return rootview;
    }
    private void initView(ArrayList<ArrayList<String>> stringList){
        tables.add(rootview.findViewById(R.id.table1));
        tables.add(rootview.findViewById(R.id.table2));
        tables.add(rootview.findViewById(R.id.table3));
        tables.add(rootview.findViewById(R.id.table4));
        tables.add(rootview.findViewById(R.id.table5));
        tables.add(rootview.findViewById(R.id.table6));
        tables.add(rootview.findViewById(R.id.table7));

        buttons.add(rootview.findViewById(R.id.menu_btn1));
        buttons.add(rootview.findViewById(R.id.menu_btn2));
        buttons.add(rootview.findViewById(R.id.menu_btn3));
        buttons.add(rootview.findViewById(R.id.menu_btn4));
        buttons.add(rootview.findViewById(R.id.menu_btn5));
        buttons.add(rootview.findViewById(R.id.menu_btn6));
        buttons.add(rootview.findViewById(R.id.menu_btn7));
//显示table布局
        for(int k = 0;k<stringList.get(0).size();k++){
            tables.get(k).setVisibility(View.VISIBLE);
            buttons.get(k).setText(stringList.get(0).get(k));
        }
        //添加按钮
        for(int i = 1;i<stringList.size();i++){
            for(int j = 0;j<stringList.get(i).size();j++){
                if(j==0){
                    tableRow = new TableRow(tables.get(i-1).getContext());
                }
                else if(j%3 == 0){
                    tables.get(i-1).addView(tableRow);
//                    Log.e("table",String.valueOf(tables.get(i).getChildCount()));
                    tableRow = new TableRow(tables.get(i-1).getContext());
                }
                Button btn = new Button(tables.get(i-1).getContext());
//               配置属性
                btn.setText(stringList.get(i).get(j));
                btn.setTextSize(14);
                btn.setWidth(80);
                btn.setHeight(40);
                btn.setBackground(getResources().getDrawable(R.drawable.roundboder_gray));
                btn.setTextColor(getResources().getColor(R.color.black));
//               设置margin
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT);
                lp.setMargins(5,6,5,6);
                btn.setLayoutParams(lp);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(),MenuSearch.class);
                        intent.putExtra("key",btn.getText());
                        startActivity(intent);
                    }
                });
                tableRow.addView(btn);
//                将尾部不满三个的按钮添加上
                if(j%3!=0 && j+1 == stringList.get(i).size()){
                    tables.get(i-1).addView(tableRow);
                }
            }
        }

    }
    private ArrayList<String> addString(String[] addstr){
        ArrayList<String> str= new ArrayList<String>();
        str.clear();
        for(int i=0;i<addstr.length;i++){
            str.add(addstr[i]);
        }
        return str;
    }

}