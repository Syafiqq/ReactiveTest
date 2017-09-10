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

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_camera)
        {
            // Handle the camera action
        }
        else if(id == R.id.nav_gallery)
        {

        }
        else if(id == R.id.nav_slideshow)
        {

        }
        else if(id == R.id.nav_manage)
        {

        }
        else if(id == R.id.nav_share)
        {

        }
        else if(id == R.id.nav_send)
        {

        }

        super.drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
