package com.github.syafiqq.reactivetest.controller.page;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.github.syafiqq.reactivetest.R;
import com.github.syafiqq.reactivetest.controller.DrawerActivity;

public class Page0001 extends DrawerActivity implements NavigationView.OnNavigationItemSelectedListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_0001);
        Toolbar toolbar = (Toolbar) findViewById(R.id.page_0001_toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, super.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        super.drawer.addDrawerListener(toggle);
        toggle.syncState();

        super.navigation.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed()
    {
        if(super.drawer.isDrawerOpen(GravityCompat.START))
        {
            super.drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        // Handle navigation view item clicks here.
        final int id = item.getItemId();

        switch(id)
        {
            case R.id.drawer_menu_page_0001:
            {
            }
            break;
            default:
            {
                super.onNavigationItemSelected(item);
            }
        }

        this.drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
