package com.example.milena.fingerpaintedaquarelwallpaper;

import android.media.MediaScannerConnection;
import android.net.Uri;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;

/**
 * Created by Milena on 18/01/2017.
 */

class MediaScannerListener implements MediaScannerConnection.MediaScannerConnectionClient {

    private MyDialogFragment fragment;

    MediaScannerListener(MyDialogFragment fragment) {

        this.fragment = fragment;
        Log.d("mfragscannerobject","scanner created");
    }

    @Override
    public void onMediaScannerConnected() {


    }

    @Override
    public void onScanCompleted(String path, Uri uri) {

        fragment.setPicturePathAndUri(path, uri);
        Log.d("mfragonScanCall","onScanCallBack created");
    }
}
