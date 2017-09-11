package com.github.syafiqq.reactivetest.controller.page.operators;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import com.github.syafiqq.reactivetest.R;
import com.github.syafiqq.reactivetest.controller.page.MainDashboard;
import timber.log.Timber;

public class DrawerOperatorsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    protected DrawerLayout drawer;
    protected NavigationView navigation;

    @Override public void setContentView(@LayoutRes int layoutResID)
    {
        Timber.d("setContentView");

        this.drawer = (DrawerLayout) super.getLayoutInflater().inflate(R.layout.drawer_operators, null);
        this.navigation = drawer.findViewById(R.id.navigation_view);
        final FrameLayout container = drawer.findViewById(R.id.frame_layout);

        this.navigation.setNavigationItemSelectedListener(this);

        super.getLayoutInflater().inflate(layoutResID, container, true);
        super.setContentView(drawer);
    }


    @Override
    public void onBackPressed()
    {
        if(this.drawer.isDrawerOpen(GravityCompat.START))
        {
            this.drawer.closeDrawer(GravityCompat.START);
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
            case R.id.menu_drawer_operators_item_back:
            {
                final Intent intent = new Intent(this, MainDashboard.class);
                super.startActivity(intent);
                super.finish();
            }
            break;
            case R.id.menu_drawer_operators_item_create:
            {
                Timber.d("Operators Create");
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
