package com.github.syafiqq.reactivetest.controller;

import android.support.annotation.LayoutRes;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import com.github.syafiqq.reactivetest.R;
import timber.log.Timber;

public class DrawerActivity extends AppCompatActivity
{
    protected DrawerLayout drawer;
    protected NavigationView navigation;

    @Override public void setContentView(@LayoutRes int layoutResID)
    {
        Timber.d("setContentView");

        this.drawer = (DrawerLayout) super.getLayoutInflater().inflate(R.layout.drawer_container, null);
        this.navigation = drawer.findViewById(R.id.navigation_view);
        final FrameLayout container = drawer.findViewById(R.id.drawer_container);

        super.getLayoutInflater().inflate(layoutResID, container, true);
        super.setContentView(drawer);
    }
}
