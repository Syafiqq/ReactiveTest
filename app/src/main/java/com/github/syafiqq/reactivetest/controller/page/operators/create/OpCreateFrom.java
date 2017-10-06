package com.github.syafiqq.reactivetest.controller.page.operators.create;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import butterknife.ButterKnife;
import com.github.syafiqq.reactivetest.R;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import timber.log.Timber;

public class OpCreateFrom extends DrawerOpCreateActivity implements NavigationView.OnNavigationItemSelectedListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op_create_from);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, super.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        super.drawer.addDrawerListener(toggle);
        toggle.syncState();

        super.navigation.setNavigationItemSelectedListener(this);
        FutureTask<Long> task = new FutureTask<>(new Callable<Long>()
        {
            @Override public Long call() throws Exception
            {
                SystemClock.sleep(2000);
                return Math.round(Math.random() * Byte.MAX_VALUE);
            }
        });

        Observable<Long> observable = Observable.fromFuture(task, Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());

        task.run();

        observable.subscribe(new Consumer<Long>()
        {
            @Override public void accept(Long aLong) throws Exception
            {
                Timber.d("onNext" + aLong);
            }
        }, new Consumer<Throwable>()
        {
            @Override public void accept(Throwable throwable) throws Exception
            {
                Timber.e(throwable);
            }
        }, new Action()
        {
            @Override public void run() throws Exception
            {
                Timber.d("onComplete");
            }
        });
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
            case R.id.menu_item_from:
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
