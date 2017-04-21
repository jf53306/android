package com.example.jf.jpermissionmanager.jpermissionmanager;

/**
 * Created by jf on 17-4-21.
 */

public interface ICallBackPsRequest {

    //when all permission allow ,
    void onPsRequestSuc();

    void onPsRequestFai(String permission,int faiCode);
}
