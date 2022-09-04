package com.example.cookfood.ui.caipu;

import android.media.Image;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CaiPuClass {
    public String title;//标题
    public String time;//时间
    public String userId;//发布者id
    public int id;//菜谱id
    public String name;//用户昵称
    public String titleimg;//封面
    public String titlecon;//菜谱概要描述
    public List<NeedFood> food = new ArrayList<NeedFood>();//食材列表
    public List<MethodClass> imglist = new ArrayList<MethodClass>();//步骤列表
    public String likes;//点赞数
    public String collect;//收藏数

    public CaiPuClass(String userId){
        this.userId = userId;
    }
    //提取食材数据
    public void getFoodList(String foodStr){
       String[] food;
       if(foodStr!=null) {
           food = foodStr.split(";");
           for (int i = 0; i < food.length; i++) {
               String[] list = food[i].split(":");
               NeedFood needFood = new NeedFood();
               needFood.food = list[0];
               needFood.num = list[1];

               this.food.add(needFood);
           }
       }
    }
    //提取步骤数据
    public void getMethodList(String img,String content){
        String[] meImg = img.split(";");
        String[] meContent;
        this.titleimg = meImg[0];
        if(content!=null){
            meContent = content.split(";");
            this.titlecon = meContent[0];
            if(meImg.length>1){
                    for (int i = 1; i < meImg.length; i++) {
                        MethodClass methodClass = new MethodClass();
                        methodClass.img = meImg[i];
                        methodClass.content = meContent[i];
                        this.imglist.add(methodClass);
                    }
            }
        }
        else {
            for (int i = 1; i < meImg.length; i++) {
                MethodClass methodClass = new MethodClass();
                methodClass.img = meImg[i];
                this.imglist.add(methodClass);
            }
        }


    }
}
