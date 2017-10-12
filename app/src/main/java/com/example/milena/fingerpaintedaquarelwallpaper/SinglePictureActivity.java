package com.example.milena.fingerpaintedaquarelwallpaper;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Milena on 24/11/2016.
 */
public class SinglePictureActivity extends AppCompatActivity {



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlepicture);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        Intent receivePictureSrc=getIntent();
        String pictureSrc=receivePictureSrc.getStringExtra(MainActivity.EXTRA_PICTUREMESSAGE);
        ImageView v=(ImageView)findViewById(R.id.singleimage);
        registerForContextMenu(v);

        Log.d("m",pictureSrc);
        int pId=getResources().getIdentifier(pictureSrc,"drawable",getPackageName());

        try{
            assert v != null;
            v.setImageResource(pId);}
        catch(NullPointerException ex){
            ex.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {

            case R.id.action_settings:
                Toast.makeText(this, "settings selected", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_favorite:
                Toast.makeText(this, "favourite selected", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.context_menu,menu);
        menu.setHeaderTitle(R.string.context_title);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case (R.id.context_setAs):
                Toast.makeText(getApplicationContext(), "set as chosen", Toast.LENGTH_LONG).show();
                return true;
            case (R.id.context_shareVia):
                Toast.makeText(getApplicationContext(), "share via is chosen", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
