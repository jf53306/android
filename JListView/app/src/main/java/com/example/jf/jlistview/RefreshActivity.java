package com.example.jf.jlistview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.jf.jlistview.jnestrefreshview.impl.JNestRefreshView;
import com.example.jf.jlistview.jnestrefreshview.impl.RefreshingView;
import com.example.jf.jlistview.presenter.RefreshListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jf on 17-4-19.
 */

public class RefreshActivity extends AppCompatActivity{
    RefreshingView viewList;
    RefreshListAdapter adapterList;
    List<String> dataList=new ArrayList<>();
    Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);

        viewList= (RefreshingView) findViewById(R.id.view_list);



        viewList.getListView().setAdapter(adapterList=new RefreshListAdapter(this,dataList));

        viewList.setOnRefreshListener(new JNestRefreshView.OnRefreshListener() {
            @Override
            public void onTopRefresh(final JNestRefreshView view) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dataList.clear();
                        loadDatas();
                        adapterList.notifyDataSetChanged();
                      view.finishRefresh();
                    }
                },1000);
            }

            @Override
            public void onBotRefresh(final JNestRefreshView view) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadDatas();
                        adapterList.notifyDataSetChanged();
                        view.finishRefresh();
                    }
                },1000);
            }
        });


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewList.autoTopRefresh();
            }
        },300);

    }


    private void loadDatas(){
        for (int i=0;i<7;i++){
            dataList.add("jj");
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }
}
