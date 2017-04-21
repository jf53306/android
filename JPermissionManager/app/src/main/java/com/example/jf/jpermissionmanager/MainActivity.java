package com.example.jf.jpermissionmanager;

import android.Manifest;
import android.app.DownloadManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.jf.jpermissionmanager.jpermissionmanager.ICallBackPsRequest;
import com.example.jf.jpermissionmanager.jpermissionmanager.JPermissionManager;

public class MainActivity extends AppCompatActivity implements ICallBackPsRequest {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        JPermissionManager.getInstance().requestPs(this,this,new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE});
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        JPermissionManager.getInstance().onRequestPermissionsResult(this,requestCode,permissions,grantResults);
    }

    @Override
    public void onPsRequestSuc() {
        Toast.makeText(this,"request suc",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPsRequestFai(String permission,int faiCode) {

        switch (faiCode){
            case JPermissionManager.FAI_REFUSE_ALLTIME_POSSIBLE:
                Toast.makeText(this,"ps("+permission+") request fai,set by yourself",Toast.LENGTH_SHORT).show();
                break;
            case JPermissionManager.FAI_REFUSE_ONETIME_POSSIBLE:
                Toast.makeText(this,"ps("+permission+") request fai",Toast.LENGTH_SHORT).show();
                break;
        }


    }
}
