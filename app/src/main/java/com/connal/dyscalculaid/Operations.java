package com.connal.dyscalculaid;

import android.app.Activity;
import android.view.View;

import com.connal.dyscalculaid.R;

import java.util.ArrayList;

/**
 * Created by Connal on 2015-03-15.
 */
class Operations {

    public static int getPic(String tag){

        int resId = R.mipmap.n;

        if(tag.equals("n"))
            resId = R.mipmap.n;
        else if(tag.equals("d"))
            resId = R.mipmap.d;
        else if(tag.equals("q"))
            resId = R.mipmap.q;
        else if(tag.equals("loonie"))
            resId = R.mipmap.loonie;
        else if(tag.equals("toonie"))
            resId = R.mipmap.toonie;
        else if(tag.equals("five"))
            resId = R.mipmap.five;
        else if(tag.equals("ten"))
            resId = R.mipmap.ten;
        else if(tag.equals("twenty"))
            resId = R.mipmap.twenty;
        else if(tag.equals("fifty"))
            resId = R.mipmap.fifty;
        else if(tag.equals("hun"))
            resId = R.mipmap.hun;

        return resId;
    }

    public static int getWallet(int count){

        if(count < 320)
            return R.id.wallet1;

        else if (count < 740)
            return R.id.wallet2;

        else if (count < 1060)
            return R.id.wallet3;

        else if (count < 1380)
            return R.id.wallet4;

        return R.id.wallet5;

    }

    public static int getPay(int count){

        if(count < 320)
            return R.id.change1;

        else if (count < 740)
            return R.id.change2;

        else if (count < 1060)
            return R.id.change3;

        else if (count < 1380)
            return R.id.change4;

        return R.id.change5;

    }

    public static int getPixels(String tag){

        if(tag.equals("n"))
            return 20;
        else if (tag.equals("d"))
            return 12;
        else if (tag.equals("q"))
            return 23;
        else if (tag.equals("loonie") || tag.equals("toonie"))
            return 25;

        return 60;
    }

    public static double getValue(String tag){

        double value = 0;

        if(tag.equals("n"))
            value = 0.05;
        else if(tag.equals("d"))
            value= 0.1;
        else if(tag.equals("q"))
            value = 0.25;
        else if(tag.equals("loonie"))
            value = 1;
        else if(tag.equals("toonie"))
            value = 2;
        else if(tag.equals("five"))
            value = 5;
        else if(tag.equals("ten"))
            value = 10;
        else if(tag.equals("twenty"))
            value = 20;
        else if(tag.equals("fifty"))
            value= 50;
        else if(tag.equals("hun"))
            value = 100;

        return value;
    }

    public static ArrayList convertToMoney(String prev, Activity active){

        prev = prev.substring(1, prev.length() - 1);
        String[] next =  prev.split(", ");
        ArrayList newMoney = new ArrayList();
        Object tag;

        for(int i = 0; i < next.length; i++){

            tag = (Object) next[i];
            newMoney.add(tag);

        }

        return newMoney;
    }
}
