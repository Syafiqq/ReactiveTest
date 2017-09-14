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
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.syafiqq.reactivetest.R;
import com.github.syafiqq.reactivetest.controller.custom.java.util.IntegerObservable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
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
    @BindView(R.id.switch_observable) public Switch switchObservable;

    private List<AtomicReference<String>> dataContainer;
    private ArrayAdapter<AtomicReference<String>> listAdapter;
    private List<Disposable> disposableContainer;
    private Observable<LocalTime> observable;
    private AtomicBoolean subscriberGuard;
    private IntegerObservable _switchObservable;

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

        this.subscriberGuard = new AtomicBoolean(true);
        this.dataContainer = new LinkedList<>();
        this._switchObservable = new IntegerObservable(0);
        this.disposableContainer = new LinkedList<>();

        this._switchObservable.addObserver(new Observer()
        {
            @Override public void update(java.util.Observable observable, Object o)
            {
                int value = (Integer) o;
                Timber.d("Shift : %d", value);
                OpCreateCreate.this.switchObservable.setEnabled(!(value > 0));
            }
        });
        this.switchObservable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                Timber.d(String.valueOf(b));

                if(b)
                {
                    OpCreateCreate.this.subscriberGuard.set(true);
                    OpCreateCreate.this.observable = OpCreateCreate.this.generateObservable();
                    OpCreateCreate.this.observable = OpCreateCreate.this.observable.publish();
                    ((ConnectableObservable) observable).connect();
                }
                else
                {
                    OpCreateCreate.this.subscriberGuard.set(true);
                    OpCreateCreate.this.observable = OpCreateCreate.this.generateObservable();
                }
            }
        });

        this.listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.dataContainer);
        this.displayData.setAdapter(this.listAdapter);
        this.observable = this.generateObservable();
    }

    public Observable<LocalTime> generateObservable()
    {
        return Observable.create(new ObservableOnSubscribe<LocalTime>()
        {
            @Override public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<LocalTime> observer) throws Exception
            {
                Random random = new Random();
                try
                {
                    while(subscriberGuard.get())
                    {
                        SystemClock.sleep(1000L + (random.nextInt(500)));
                        observer.onNext(LocalTime.now());
                    }
                    SystemClock.sleep(1000L);
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

        disposableContainer.add(observable.subscribe(new Consumer<LocalTime>()
        {
            @Override public void accept(LocalTime number) throws Exception
            {
                Timber.d(String.format(Locale.getDefault(), "Next [%s] : [%s] %s", System.identityHashCode(ref), System.identityHashCode(number), format.print(number)));

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
                OpCreateCreate.this._switchObservable.decrement(1);
                adapter.notifyDataSetChanged();
            }
        }));
    }

    @OnClick(R.id.button_generate)
    public void generate()
    {
        Timber.d("generate");

        this.subscriberGuard.set(true);
        this._switchObservable.increment(1);
        this.doSubscribe();
    }

    @OnClick(R.id.button_terminate)
    public void terminate()
    {
        Timber.d("terminate");

        this.subscriberGuard.set(false);
    }

    @OnClick(R.id.button_clear)
    public void clear()
    {
        Timber.d("clear");

        this.dataContainer.clear();
        for(Disposable dispose : this.disposableContainer)
        {
            if(!dispose.isDisposed())
            {
                dispose.dispose();
            }
        }
        this.disposableContainer.clear();
        this._switchObservable.setValue(0);
        this.listAdapter.notifyDataSetChanged();
    }

    private void doSubscribe()
    {
        Timber.d("doSubscribe");

        this.subscribe(new AtomicReference<>("Initial"), this.observable, this.listAdapter, this.dataContainer);
    }


    @Deprecated
    private void _testRange()
    {
        Timber.d("_testRange");

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
}
