package com.example.jf.jtoolbarhelper.toolbarhelper.impl;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.jf.jtoolbarhelper.R;
import com.example.jf.jtoolbarhelper.toolbarhelper.IToolbarHelper;

/**
 * Created by jf on 17-4-20.
 */

public class JToolBarHelper implements IToolbarHelper{
    Toolbar toolbar;
    AppCompatActivity activity;
    @Override
    public View buildToolBar(AppCompatActivity activity,int layoutId) {
        this.activity=activity;
        LinearLayout root=new LinearLayout(activity);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setLayoutParams(new FrameLayout.LayoutParams(-1,-1));
        LayoutInflater inf = LayoutInflater.from(activity);
        root.addView(toolbar= (Toolbar) inf.inflate(R.layout.item_toolbar,root,false));
        root.addView(inf.inflate(layoutId,root,false));

        build();
        return root;
    }

    @Override
    public void backEnable(boolean backEnable) {
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(backEnable);
        if (backEnable){
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onBackPressed();
                }
            });
        }
    }

    @Override
    public void setTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public void release() {
        toolbar=null;
        activity=null;
    }

    @Override
    public Toolbar getToolBar() {
        return toolbar;
    }

    private void build(){
        toolbar.setTitle("");  //resolve toolbar settitle unuse
        activity.setSupportActionBar(toolbar);
        backEnable(true);
    }
}
