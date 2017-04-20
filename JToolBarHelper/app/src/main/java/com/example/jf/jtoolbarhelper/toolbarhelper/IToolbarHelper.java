package com.example.jf.jtoolbarhelper.toolbarhelper;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by jf on 17-4-20.
 */

public interface IToolbarHelper {

    View buildToolBar(AppCompatActivity activity,int layoutId);

    void backEnable(boolean backEnable);

    void setTitle(String title);

    void release();

    Toolbar getToolBar();
}
