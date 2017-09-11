package com.github.syafiqq.reactivetest.controller.page.operators;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import com.github.syafiqq.reactivetest.R;
import timber.log.Timber;

public class OperatorsDashboard extends DrawerOperatorsActivity implements NavigationView.OnNavigationItemSelectedListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Timber.d("onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.operators_activity_operators_dasboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, super.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        super.drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override protected void onDestroy()
    {
        super.onDestroy();
    }

    @Override protected void onStart()
    {
        super.onStart();
    }

    @Override protected void onStop()
    {
        super.onStop();
    }

}
