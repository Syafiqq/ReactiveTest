package com.github.syafiqq.reactivetest.controller.page.operators.create;

import android.os.Bundle;
import android.os.SystemClock;
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
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import timber.log.Timber;

public class OpCreateCreate extends DrawerOpCreateActivity implements NavigationView.OnNavigationItemSelectedListener
{

    @BindView(R.id.list_view_container) public ListView displayData;
    private List<AtomicReference<String>> dataContainer;
    private ArrayAdapter<AtomicReference<String>> listAdapter;
    private Observable<String> observable;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Timber.d("onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op_create_create);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, super.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        super.drawer.addDrawerListener(toggle);
        toggle.syncState();

        super.navigation.setNavigationItemSelectedListener(this);

        this.dataContainer = new LinkedList<>();

        this.listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.dataContainer);
        this.displayData.setAdapter(this.listAdapter);

        this.observable = Observable.create(new ObservableOnSubscribe<String>()
        {
            @Override public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<String> observer) throws Exception
            {
                final Faker faker = new Faker();
                try
                {
                    for(int i = 1; i < 10; i++)
                    {
                        SystemClock.sleep(2000L);
                        observer.onNext(faker.name().fullName());
                    }
                    SystemClock.sleep(2000L);
                    observer.onComplete();
                }
                catch(Exception e)
                {
                    observer.onError(e);
                }
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
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

    public void subscribe(final AtomicReference<String> ref, final Observable<String> observable, final ArrayAdapter adapter, final List list)
    {
        Timber.d("subscribe");

        list.add(ref);
        adapter.notifyDataSetChanged();

        observable.subscribe(new Consumer<String>()
        {
            @Override public void accept(String name) throws Exception
            {
                String data = String.format(Locale.getDefault(), "Next : %s", name);
                Timber.d(data);

                ref.set(data);
                adapter.notifyDataSetChanged();
            }
        }, new Consumer<Throwable>()
        {
            @Override public void accept(Throwable throwable) throws Exception
            {
                Timber.e(throwable);

                ref.set(throwable.getMessage());
                adapter.notifyDataSetChanged();
            }
        }, new Action()
        {
            @Override public void run() throws Exception
            {
                String data = "Sequence complete.";
                Timber.d(data);

                ref.set(data);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick(R.id.button_generate)
    public void generate()
    {
        Timber.d("generate");

        Observable.intervalRange(1L, 5L, 1000L, 500L, TimeUnit.MILLISECONDS)
                  .subscribeOn(Schedulers.newThread())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Consumer<Long>()
                  {
                      @Override public void accept(Long aLong) throws Exception
                      {
                          OpCreateCreate.this.doSubscribe();
                      }
                  });
    }

    private void doSubscribe()
    {
        Timber.d("doSubscribe");

        this.subscribe(new AtomicReference<>("Initial"), this.observable, this.listAdapter, this.dataContainer);
    }

}
