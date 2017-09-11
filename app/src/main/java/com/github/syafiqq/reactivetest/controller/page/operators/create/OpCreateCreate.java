package com.github.syafiqq.reactivetest.controller.page.operators.create;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.github.syafiqq.reactivetest.R;
import timber.log.Timber;

public class OpCreateCreate extends DrawerOpCreateActivity implements NavigationView.OnNavigationItemSelectedListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op_create_create);
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

    @Override public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        // Handle navigation view item clicks here.
        final int id = item.getItemId();

        switch(id)
        {
            case R.id.menu_item_create:
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
