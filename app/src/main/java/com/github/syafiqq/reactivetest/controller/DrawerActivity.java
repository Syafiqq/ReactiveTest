package com.github.syafiqq.reactivetest.controller;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import com.github.syafiqq.reactivetest.R;
import timber.log.Timber;

public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    protected DrawerLayout drawer;
    protected NavigationView navigation;

    @Override public void setContentView(@LayoutRes int layoutResID)
    {
        Timber.d("setContentView");

        this.drawer = (DrawerLayout) super.getLayoutInflater().inflate(R.layout.drawer_container, null);
        this.navigation = drawer.findViewById(R.id.navigation_view);
        final FrameLayout container = drawer.findViewById(R.id.drawer_container);

        this.navigation.setNavigationItemSelectedListener(this);

        super.getLayoutInflater().inflate(layoutResID, container, true);
        super.setContentView(drawer);
    }

    @Override public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        // Handle navigation view item clicks here.
        final int id = item.getItemId();

        switch(id)
        {
            case R.id.drawer_menu_page_0001:
            {
                Timber.d("Page 0001");
            }
            break;
            default:
            {
                Timber.d("Can't Understand");
            }
        }

        this.drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
