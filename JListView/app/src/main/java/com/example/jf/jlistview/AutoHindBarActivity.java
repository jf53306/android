package com.example.jf.jlistview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jf.jlistview.jnestrefreshview.impl.RefreshingView;
import com.example.jf.jlistview.presenter.RefreshListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jf on 2017/8/14.
 */

public class AutoHindBarActivity extends AppCompatActivity{
    private List<String> datas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_hind_bar);

        RefreshingView refreshingView= (RefreshingView) this.findViewById(R.id.view_list);


        refreshingView.getListView().setAdapter(new RefreshListAdapter(this,datas=new ArrayList<String>()));

        for (int i=0;i<7;i++){
            datas.add("jj");
        }
        refreshingView.getListView().getAdapter().notifyDataSetChanged();
    }
}
