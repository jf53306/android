package com.example.jf.jtoolbarhelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.example.jf.jtoolbarhelper.toolbarhelper.IToolbarHelper;
import com.example.jf.jtoolbarhelper.toolbarhelper.impl.JToolBarHelper;

public class MainActivity extends AppCompatActivity {
    IToolbarHelper toolbarHelper=new JToolBarHelper();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(toolbarHelper.buildToolBar(this,R.layout.activity_main));

        toolbarHelper.setTitle("wtf");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
