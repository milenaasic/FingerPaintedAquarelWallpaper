package com.example.milena.fingerpaintedaquarelwallpaper;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,MyDialogFragment.OnFragmentInteractionListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public final static String EXTRA_PICTUREMESSAGE="com.example.milena.fingerpaintedaqarelwallpaper.PICTUREMESSAGE";


    private String pictureToFragment;
    private static final int MY_PERMISSIONS_REQUEST_READ_WRITE=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Integer screenWidth =metrics.widthPixels;
        Float scale=metrics.density;
        float spanCount=screenWidth/(96*scale);
        int spanCountFinal=(int)spanCount;

        mLayoutManager = new GridLayoutManager(this,spanCountFinal);
        mRecyclerView.setLayoutManager(mLayoutManager);

        int[] nizSlika = {R.drawable.slika1_thumb, R.drawable.slika2_thumb, R.drawable.slika3_thumb, R.drawable.slika4_thumb, R.drawable.slika5_thumb, R.drawable.slika6_thumb};
        mRecyclerView.setAdapter(new MyAdapter(this,nizSlika));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings:
                Toast.makeText(this, "settings selected", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_favorite:
                Toast.makeText(this, "favourite seleced", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        //ask for permission to read and write MediaStore
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d("kontex","version M");
            if((ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) ||
                    (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED)){
              askForPermission();
                return;
            }
        }
        // if there is a permission continue
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.context_menu,menu);
        menu.setHeaderTitle(R.string.context_title);
        pictureToFragment=(String)v.getTag();
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    private void askForPermission() {

        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)|| ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE )){

            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   Log.d("alertovani","on klik listener");
                }
            });
            AlertDialog dialog=builder.create();
            dialog.show();

        }else {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_WRITE);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_READ_WRITE:{
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                   Toast.makeText(this,"permission granted",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this,"permission not granted",Toast.LENGTH_SHORT).show();

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {case (R.id.context_setAs):
                Toast.makeText(getApplicationContext(), "set as chosen", Toast.LENGTH_SHORT).show();
                callFragment("ACTION_ATTACH_DATA");
                return true;

            case (R.id.context_shareVia):
                Toast.makeText(getApplicationContext(), "share via is chosen", Toast.LENGTH_SHORT).show();
                callFragment("ACTION_SEND");
                return true;

            default:
                return super.onContextItemSelected(item);
        }


    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }

    @Override
    public void onStop() {
        super.onStop();


    }

    @Override
    public void onClick(View v) {
        String whichPicture= (String) v.getTag();
        String singlePictRes=whichPicture+"_full";

        Toast.makeText(getApplicationContext(), singlePictRes, Toast.LENGTH_SHORT).show();
        Intent openSinglePicture=new Intent(this,SinglePictureActivity.class);
        openSinglePicture.putExtra(EXTRA_PICTUREMESSAGE,singlePictRes);
        startActivity(openSinglePicture);

    }


    void callFragment(String action){
        Toast.makeText(getApplicationContext(), "call Fragment first line", Toast.LENGTH_SHORT).show();

        MyDialogFragment bottomSheetDialogFragment=MyDialogFragment.newInstance(pictureToFragment,action);

        bottomSheetDialogFragment.show(getSupportFragmentManager(),"bottom sheet");


    }


    @Override
    public void onFragmentInteraction() {
        Toast.makeText(this,"fragment interaction listener",Toast.LENGTH_SHORT).show();
    }
}

