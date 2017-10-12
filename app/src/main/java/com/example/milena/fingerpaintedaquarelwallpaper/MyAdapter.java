package com.example.milena.fingerpaintedaquarelwallpaper;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

/**
 * Created by Milena on 23/11/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private View.OnClickListener mCtx;
    private int[] mDataset;
    private AppCompatActivity app;


    public MyAdapter(View.OnClickListener ctx, int[] data) {
        mCtx=ctx;
        mDataset=data;
       app=(AppCompatActivity)mCtx;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;

        public ViewHolder (View v){
            super (v);
            mImageView=(ImageView) v;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_image_view, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mImageView.setImageResource(mDataset[position]);
        holder.mImageView.setTag("slika"+(position+1));
        holder.mImageView.setOnClickListener(mCtx);
        app.registerForContextMenu(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

}




