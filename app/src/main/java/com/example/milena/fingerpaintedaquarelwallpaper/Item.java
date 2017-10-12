package com.example.milena.fingerpaintedaquarelwallpaper;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Milena on 02/12/2016.
 */
public class Item {
   public Drawable icon;
    public String iconTitle;

    public Item(Drawable icon, String iconTitle){
        this.icon=icon;
        this.iconTitle=iconTitle;
    }

}