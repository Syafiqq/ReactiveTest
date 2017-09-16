package com.github.syafiqq.reactivetest.controller.page.operators.create;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import com.github.syafiqq.reactivetest.R;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.LinkedList;
import java.util.List;
import java.util.Observer;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import timber.log.Timber;


/**
 * @author syafiq
 * @link http://reactivex.io/documentation/operators/defer.html
 * @link http://blog.danlew.net/2014/10/08/grokking-rxjava-part-4/#oldslowcode
 * @link http://blog.danlew.net/2015/07/23/deferring-observable-code-until-subscription-in-rxjava/
 */
class SomeType extends java.util.Observable
{
    private Long value;

    public SomeType()
    {
        this(0L);
    }

    public SomeType(Long value)
    {
        this.setValue(value);
    }

    public void setValue(Long value)
    {
        this.value = value;
        super.setChanged();
        super.notifyObservers(this.value);
    }

    public Observable<Long> valueObservableJust()
    {
        return Observable.just(value);
    }

    public Observable<Long> valueObservableCreate()
    {
        return Observable.create(new ObservableOnSubscribe<Long>()
        {
            @Override public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<Long> subscriber) throws Exception
            {
                subscriber.onNext(value);
                subscriber.onComplete();
            }
        });
    }

    public Observable<Long> valueObservableDefer()
    {
        return Observable.defer(new Callable<ObservableSource<? extends Long>>()
        {
            @Override public ObservableSource<? extends Long> call() throws Exception
            {
                return Observable.just(value);
            }
        });
    }
}

public class OpCreateDefer extends DrawerOpCreateActivity implements NavigationView.OnNavigationItemSelectedListener
{
    @BindView(R.id.text_view_value_display) public TextView tvValueDisplay;
    @BindView(R.id.switch_transfer) public Switch sTransfer;
    @BindView(R.id.list_view_just) public ListView lvJust;
    @BindView(R.id.list_view_create) public ListView lvCreate;
    @BindView(R.id.list_view_defer) public ListView lvDefer;

    private SomeType type;
    private AtomicBoolean guard;

    private Observable<Long> obsJust;
    private Observable<Long> obsCreate;
    private Observable<Long> obsDefer;
    private List<Long> justContainer;
    private List<Long> createContainer;
    private List<Long> deferContainer;
    private ArrayAdapter<Long> justAdapter;
    private ArrayAdapter<Long> createAdapter;
    private ArrayAdapter<Long> deferAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Timber.d("onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op_create_defer);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, super.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        super.drawer.addDrawerListener(toggle);
        toggle.syncState();

        super.navigation.setNavigationItemSelectedListener(this);

        //Initialize SomeType
        this.type = new SomeType();

        //Initialize Type Observer
        this.type.addObserver(new Observer()
        {
            @Override public void update(java.util.Observable observable, Object o)
            {
                Long value = (Long) o;
                tvValueDisplay.setText(String.valueOf(value));
            }
        });

        //Initialize Guard
        this.guard = new AtomicBoolean(sTransfer.isChecked());

        //Initialize Counter Observable
        Observable<Long> counter = Observable.interval(1, TimeUnit.SECONDS).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        counter.subscribe(new Consumer<Long>()
        {
            @Override public void accept(Long aLong) throws Exception
            {
                Timber.d(String.valueOf(aLong));
                if(guard.get())
                {
                    type.setValue(aLong);
                }
            }
        });

        this.justContainer = new LinkedList<>();
        this.createContainer = new LinkedList<>();
        this.deferContainer = new LinkedList<>();

        this.justAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.justContainer);
        this.createAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.createContainer);
        this.deferAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.deferContainer);

        this.lvJust.setAdapter(this.justAdapter);
        this.lvCreate.setAdapter(this.createAdapter);
        this.lvDefer.setAdapter(this.deferAdapter);

        this.obsJust = type.valueObservableJust().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        this.obsCreate = type.valueObservableCreate().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        this.obsDefer = type.valueObservableDefer().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    @OnCheckedChanged(R.id.switch_transfer) public void onTransferSwitched(boolean b)
    {
        guard.set(b);
    }

    @OnClick(R.id.button_shift) public void shiftClicked()
    {
        obsJust.subscribe(new Consumer<Long>()
        {
            @Override public void accept(Long aLong) throws Exception
            {
                justContainer.add(aLong);
                justAdapter.notifyDataSetChanged();
            }
        });
        obsCreate.subscribe(new Consumer<Long>()
        {
            @Override public void accept(Long aLong) throws Exception
            {
                createContainer.add(aLong);
                createAdapter.notifyDataSetChanged();
            }
        });
        obsDefer.subscribe(new Consumer<Long>()
        {
            @Override public void accept(Long aLong) throws Exception
            {
                deferContainer.add(aLong);
                deferAdapter.notifyDataSetChanged();
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
            case R.id.menu_item_defer:
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
