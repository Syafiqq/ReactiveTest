package com.github.syafiqq.reactivetest.controller.page;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.javafaker.Faker;
import com.github.syafiqq.reactivetest.R;
import com.github.syafiqq.reactivetest.controller.DrawerActivity;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import java.util.LinkedList;
import java.util.List;

public class Page0001 extends DrawerActivity implements NavigationView.OnNavigationItemSelectedListener
{
    @BindView(R.id.page0001_list_view_data_container) public ListView displayData;
    private List<String> dataContainer;
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_0001);
        ButterKnife.bind(this, super.drawer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.page_0001_toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, super.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        super.drawer.addDrawerListener(toggle);
        toggle.syncState();

        super.navigation.setNavigationItemSelectedListener(this);

        this.dataContainer = new LinkedList<>();

        this.listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.dataContainer);
        this.displayData.setAdapter(this.listAdapter);
    }

    @Override protected void onStart()
    {
        super.onStart();
    }

    @Override protected void onStop()
    {
        super.onStop();
    }

    @Override protected void onDestroy()
    {
        super.onDestroy();
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

    @OnClick(R.id.page0001_button_generate_data)
    public void generate()
    {
        Faker faker = new Faker();
        List<String> _tempData = new LinkedList<>();
        for(int i = -1, is = 50; ++i < is; )
        {
            _tempData.add(faker.name().fullName());
        }
        this.dataContainer.clear();
        this.listAdapter.notifyDataSetChanged();


        Observable.fromArray(_tempData.toArray(new String[_tempData.size()])).subscribe(new Consumer<String>()
        {
            @Override public void accept(String s) throws Exception
            {
                Page0001.this.dataContainer.add(s);
                Page0001.this.listAdapter.notifyDataSetChanged();
            }
        });
    }
}
