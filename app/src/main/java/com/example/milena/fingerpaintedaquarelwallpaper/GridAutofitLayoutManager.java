package com.example.milena.fingerpaintedaquarelwallpaper;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;

/**
 * Created by Milena on 10/01/2017.
 */
public class GridAutofitLayoutManager extends GridLayoutManager {

    private int mColumnWidth;
    private boolean mColumnWidthChanged=true;

    public GridAutofitLayoutManager(Context context, int columhWidth) {
        //initally set to 1
        super(context, 1);
        setColumnWidth(checkedColumnWidth(context, columhWidth));

    }

    private int checkedColumnWidth(Context context,int columnWidth){
        if (columnWidth>=0){
            columnWidth=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,48,context.getResources().getDisplayMetrics());
        }
        return columnWidth;
    }

    public void setColumnWidth(int newColumnWidth){
        if (newColumnWidth>0 && newColumnWidth !=mColumnWidth){
            mColumnWidth=newColumnWidth;
            mColumnWidthChanged=true;
        }
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler,RecyclerView.State state){
        int width=getWidth();
        int height=getHeight();

        if( mColumnWidthChanged && mColumnWidth> 0 && width>0 && height>0){
            int totalSpace;

            if(getOrientation()== VERTICAL){
                totalSpace=width-getPaddingRight()-getPaddingLeft();
            }else{
                totalSpace=height-getPaddingTop()-getPaddingBottom();
            }

            int spanCount=Math.max(1,totalSpace/mColumnWidth);
            setSpanCount(spanCount);
            mColumnWidthChanged=false;
        }
        super.onLayoutChildren(recycler, state);
    }

}
