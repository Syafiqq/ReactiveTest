package com.github.syafiqq.reactivetest.controller.page.operators.create;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import butterknife.ButterKnife;
import com.github.syafiqq.reactivetest.R;
import timber.log.Timber;

public class OpCreateDefer extends DrawerOpCreateActivity implements NavigationView.OnNavigationItemSelectedListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Timber.d("onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op_create_defer);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, super.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        super.drawer.addDrawerListener(toggle);
        toggle.syncState();

        super.navigation.setNavigationItemSelectedListener(this);
    }

    @Override protected void onDestroy()
    {
        Timber.d("onDestroy");

        super.onDestroy();
    }

    @Override protected void onStart()
    {
        Timber.d("onStart");

        super.onStart();
    }

    @Override protected void onStop()
    {
        Timber.d("onStop");

        super.onStop();
    }

    @Override public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        Timber.d("onNavigationItemSelected");

        // Handle navigation view item clicks here.
        final int id = item.getItemId();

        switch(id)
        {
            case R.id.menu_item_defer:
            {
                Timber.e("Not Implemented");
            }
            break;
            default:
            {
                return super.onNavigationItemSelected(item);
            }
        }

        this.drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
