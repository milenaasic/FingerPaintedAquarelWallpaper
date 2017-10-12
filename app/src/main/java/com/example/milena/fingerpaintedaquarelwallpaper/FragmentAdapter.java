package com.example.milena.fingerpaintedaquarelwallpaper;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Milena on 02/12/2016.
 */
public class FragmentAdapter extends RecyclerView.Adapter<FragmentAdapter.ViewHolder> {

        private View.OnClickListener mFrag;
        private List<ResolveInfo> mDataset;
        private PackageManager pm;



    public FragmentAdapter(View.OnClickListener frag, List<ResolveInfo> data, PackageManager pm) {
        mFrag=frag;
        mDataset=data;
       this.pm=pm;
    }


        public static class ViewHolder extends RecyclerView.ViewHolder {

            private ImageView mImageView;
             private TextView mTitle;

             public ViewHolder (View v){
                 super (v);
                 LinearLayout linearLayout=(LinearLayout)v;
                 mImageView=(ImageView)linearLayout.getChildAt(0);
                 mTitle=(TextView)linearLayout.getChildAt(1);

            }
        }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_item_fragment, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        Log.d("mojlog","frag adapter on create View Holder");
        return vh;
    }

    @Override
    public void onBindViewHolder(FragmentAdapter.ViewHolder holder, int position) {
        ResolveInfo ri=mDataset.get(position);

        Drawable icon=ri.loadIcon(pm);
        holder.mImageView.setImageDrawable(icon);

        holder.mTitle.setText(ri.loadLabel(pm));


        holder.mImageView.setTag(ri);
        //holder.mImageView.setTag(s2);
        holder.mImageView.setOnClickListener(mFrag);
        Log.d("mojlog","frag adapter on Bind View Holder"+position);
    }


    @Override
    public int getItemCount() {
        Log.d("mojlog","size "+mDataset.size());
        return mDataset.size();
    }



}
