package com.example.jf.jpermissionmanager.jpermissionmanager;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jf on 17-4-21.
 */

public class JPermissionManager {
    public static final int FAI_REFUSE_ONETIME_POSSIBLE=1;
    public static final int FAI_REFUSE_ALLTIME_POSSIBLE=2;


    public static JPermissionManager instance;
    public static final int rCode=110;
    private List<String> shouldRequestPs;
    private List<String> alreadyRequestPs;
    private ICallBackPsRequest mCallBack;

    public static JPermissionManager getInstance(){
        if (instance==null){
            instance=new JPermissionManager();
        }
        return instance;
    }

    private JPermissionManager(){
        shouldRequestPs=new ArrayList<>();
        alreadyRequestPs=new ArrayList<>();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private   boolean shouldRequest(AppCompatActivity activity, String ps){
        return activity.checkSelfPermission(ps)== PackageManager.PERMISSION_DENIED;
    }

    public  void requestPs(AppCompatActivity activity, ICallBackPsRequest callBack, String[] pss){
        mCallBack=callBack;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            shouldRequestPs.clear();
            alreadyRequestPs.clear();
            for (int i=0;i<pss.length;i++){
                if (shouldRequest(activity,pss[i])){
                    Log.e("should_req","oo");
                    shouldRequestPs.add(pss[i]);
                }else{
                    alreadyRequestPs.add(pss[i]);
                }
            }

            if (shouldRequestPs.size()==0){
                callBack.onPsRequestSuc();
            }else {
                String[] result=new String[shouldRequestPs.size()];
                getShouldRequestPs(result);
                activity.requestPermissions(result,rCode);
            }
        }else {
            callBack.onPsRequestSuc();
        }
    }

    private void getShouldRequestPs(String[] pss){
        if (shouldRequestPs!=null&&shouldRequestPs.size()==pss.length){
            for (int i=0;i<shouldRequestPs.size();i++){
                pss[i]=shouldRequestPs.get(i);
            }
        }
    }

    public void onRequestPermissionsResult(AppCompatActivity activity,int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (requestCode==rCode){
                for (int i=0;i<grantResults.length;i++){
                    if (grantResults[i]== PackageManager.PERMISSION_GRANTED){
                        //success
                        shouldRequestPs.remove(permissions[i]);
                    }else {
                        final boolean refuseOnce=activity.shouldShowRequestPermissionRationale(permissions[i]);
                        int faiCode=refuseOnce?FAI_REFUSE_ONETIME_POSSIBLE:FAI_REFUSE_ALLTIME_POSSIBLE;
                        if (mCallBack!=null){
                            mCallBack.onPsRequestFai(permissions[i],faiCode);
                        }
                        break;
                    }
                }
                if (shouldRequestPs.size()==0&&mCallBack!=null){
                    mCallBack.onPsRequestSuc();
                }
            }
        }else {
            //
        }
    }

}
