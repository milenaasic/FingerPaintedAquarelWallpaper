package com.example.milena.fingerpaintedaquarelwallpaper;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.net.nsd.NsdManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener{

    private static final String ARG_PICTURE = "argPicture";
    private static final String ARG_ACTION = "argAction";

    // TODO: Rename and change types of parameters
    private String mParamPicture;
    private String mParamAction;

    private OnFragmentInteractionListener mListener;

    private  Intent myIntent;

    private List<ResolveInfo> mResolveInfoList;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private String picturePathIntent;
    private Uri pictureUriIntent;



    public MyDialogFragment() {
        // Required empty public constructor
    }

    public static MyDialogFragment newInstance(String paramPicture, String paramAction) {
        MyDialogFragment fragment = new MyDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PICTURE, paramPicture);
        args.putString(ARG_ACTION, paramAction);
        fragment.setArguments(args);
        Log.d("milena","napravljen fragment");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParamPicture = getArguments().getString(ARG_PICTURE);
            mParamAction = getArguments().getString(ARG_ACTION);
            Log.d("milena",mParamAction);
            Intent intent=new Intent();
            if (mParamAction.equals("ACTION_SEND")){
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("*/*");
            }else if (mParamAction.equals("ACTION_ATTACH_DATA")){

                intent.setAction(Intent.ACTION_ATTACH_DATA);
                intent.setType("*/*");
            };


            PackageManager pm=getContext().getPackageManager();
            mResolveInfoList=pm.queryIntentActivities(intent, 0);
            Log.d("mResolveInfoList size",""+mResolveInfoList.size());

        }
        Log.d("milena","napravljen fragment on create");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_my_dialog, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(),3);
        mRecyclerView.setLayoutManager(mLayoutManager);

        PackageManager pm=getContext().getPackageManager();
        FragmentAdapter fragmentAdapter=new FragmentAdapter(this,mResolveInfoList,pm);
        mRecyclerView.setAdapter(fragmentAdapter);

        Log.d("milena","napravljen fragment onCreateView");
        return v ;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        ResolveInfo ri=(ResolveInfo) v.getTag();

        Log.d("mfrag",mParamPicture);
        Log.d("mfrag",mParamAction);

        String fullIdString=mParamPicture+"_full";// npr slika1_full
        Log.d("mfrag fullIdString",fullIdString);

        String className=ri.activityInfo.name;
        String packageName=ri.activityInfo.packageName;

       ComponentName componentName=new ComponentName(packageName,className);

        Log.d("mfrag2",className);
        Log.d("mfrag3",packageName);

        final Resources resources =getResources();

        int pictureToSendId=resources.getIdentifier(fullIdString,"drawable","com.example.milena.fingerpaintedaquarelwallpaper");
        // pictureToSendId je id slike u mom resurs folderu, napravljen na osnovu string imena npr.slika1_full
        Log.d("frag resId", ((Integer)pictureToSendId).toString());
        // napravim Uri slike na osnovu resource id , pictureToSendId
        Uri pictureUri = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(resources.getResourcePackageName(pictureToSendId))
                .appendPath(resources.getResourceTypeName(pictureToSendId))
                .appendPath(resources.getResourceEntryName(pictureToSendId))
                .build();

        // String pictureUriToString, Uri moje resurs lokacije prebacen u String
        String pictureUriToString=pictureUri.toString();


        Log.d("mfraguriToString",pictureUriToString);

        //provera da li je u MediaStoru slika


        //skeniranje slike u MediaStore treba ovako. Scanfile je samo za fajlove,ne za image
        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/PNG");
        values.put(MediaStore.MediaColumns.DATA, pictureUriToString);

        Uri shareUri=null;
        try{
            shareUri=getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }catch (NullPointerException ne){
            Log.d("shareUri je null", "null");
        }

        Log.d("shareUri",shareUri.toString());

        Intent doThisWithPicture = new Intent();

        String action="Intent."+mParamAction;
        Log.d("action",action);

        doThisWithPicture.setAction(action);
        doThisWithPicture.setComponent(componentName);
        if(pictureUriIntent!=null){
        doThisWithPicture.setData(shareUri);}

        else{
            Toast.makeText(getActivity(),"nema urija za sliku",Toast.LENGTH_SHORT);
        }

        startActivity(doThisWithPicture);


    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }

    protected void setPicturePathAndUri(String path, Uri uri){
        picturePathIntent=path;
        pictureUriIntent=uri;
    }
}
