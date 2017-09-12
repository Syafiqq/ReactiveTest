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
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import timber.log.Timber;

/**
 * @author syafiq
 * @link https://blog.thoughtram.io/angular/2016/06/16/cold-vs-hot-observables.html
 * @link https://medium.com/@p.tournaris/rxjava-one-observable-multiple-subscribers-7bf497646675
 */
public class OpCreateCreate extends DrawerOpCreateActivity implements NavigationView.OnNavigationItemSelectedListener
{
    final @NonNull DateTimeFormatter format = DateTimeFormat.forPattern("HH:mm:ss.SSS");

    @BindView(R.id.list_view_container) public ListView displayData;
    private List<AtomicReference<String>> dataContainer;
    private ArrayAdapter<AtomicReference<String>> listAdapter;
    private Observable<LocalTime> observable;

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

        this.observable = Observable.create(new ObservableOnSubscribe<LocalTime>()
        {
            @Override public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<LocalTime> observer) throws Exception
            {
                Random random = new Random();
                try
                {
                    for(int i = 1; i < 5; i++)
                    {
                        SystemClock.sleep(1500L + (random.nextInt(1000)));
                        observer.onNext(LocalTime.now());
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

    public void subscribe(final AtomicReference<String> ref, final Observable<LocalTime> observable, final ArrayAdapter<AtomicReference<String>> adapter, final List<AtomicReference<String>> list)
    {
        Timber.d("subscribe");

        list.add(ref);
        adapter.notifyDataSetChanged();

        observable.subscribe(new Consumer<LocalTime>()
        {
            @Override public void accept(LocalTime number) throws Exception
            {
                Timber.d(String.format(Locale.getDefault(), "Next : %s", format.print(number)));

                ref.set(format.print(number));
                adapter.notifyDataSetChanged();
            }
        }, new Consumer<Throwable>()
        {
            @Override public void accept(Throwable throwable) throws Exception
            {
                Timber.e(throwable);

                ref.set(throwable.toString());
                adapter.notifyDataSetChanged();
            }
        }, new Action()
        {
            @Override public void run() throws Exception
            {
                String data = "Sequence complete.";
                Timber.d(data);

                ref.set(ref.get() + " - Finish ");
                adapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick(R.id.button_generate)
    public void generate()
    {
        Timber.d("generate");

        Observable.intervalRange(1L, 5L, 4000L, 4000L, TimeUnit.MILLISECONDS)
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
