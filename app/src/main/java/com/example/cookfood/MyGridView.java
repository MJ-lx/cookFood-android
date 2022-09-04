package com.example.cookfood;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

public class MyGridView extends GridView {
    public MyGridView(Context context) {
        super(context);
    }
    //重新测量高度
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //重新设置
        //MeasureSpec 两个参数  1.size 2.mode
        int heightMeasure = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasure);
    }
}
