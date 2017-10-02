package com.github.syafiqq.reactivetest.controller.page.operators.create;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.syafiqq.reactivetest.R;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;
import timber.log.Timber;

class Util
{
    static Consumer<? super Long> newCounterSubscriber(final Context context, final View imageEmpty)
    {
        return new Consumer<Long>()
        {
            @Override public void accept(Long value) throws Exception
            {
                Timber.d(String.valueOf(value));
                if(value % 2 == 0)
                {
                    imageEmpty.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent));
                }
                else
                {
                    imageEmpty.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow_a200));
                }
            }
        };
    }

    public static Disposable subscribeEvent(final Observable<Long> values, final DisContainer container, final Observable<Long> counter, final Context context)
    {
        return values.subscribe(new Consumer<Long>()
        {
            @Override public void accept(Long s) throws Exception
            {

            }
        }, new Consumer<Throwable>()
        {
            @Override public void accept(Throwable throwable) throws Exception
            {
                container.getViewContainer().setBackgroundColor(ContextCompat.getColor(context, R.color.red_500));
                container.dispose();
                Util.revive(container, counter, context);
            }
        }, new Action()
        {
            @Override public void run() throws Exception
            {
                container.getViewContainer().setBackgroundColor(ContextCompat.getColor(context, R.color.green_500));
                container.dispose();
                Util.revive(container, counter, context);
            }
        });
    }

    private static void revive(final DisContainer container, final Observable<Long> counter, final Context context)
    {
        new AsyncTask<Void, Void, Void>()
        {
            @Override protected Void doInBackground(Void... voids)
            {
                SystemClock.sleep(5000);
                return null;
            }

            @Override protected void onPostExecute(Void aVoid)
            {
                super.onPostExecute(aVoid);
                container.generate(counter, context);
            }
        }.execute();
    }
}

class DisContainer
{
    private Disposable counter;
    private View viewContainer;

    public DisContainer(View viewContainer)
    {
        this.viewContainer = viewContainer;
    }

    public View getViewContainer()
    {
        return this.viewContainer;
    }

    public void generate(Observable<Long> counter, Context context)
    {
        this.counter = counter.subscribe(Util.newCounterSubscriber(context, this.viewContainer));
    }

    public void dispose()
    {
        this.counter.dispose();
    }
}

/**
 * @author syafiq
 * @link http://reactivex.io/documentation/operators/empty-never-throw.html
 * @link https://github.com/Froussios/Intro-To-RxJava/blob/master/Part%202%20-%20Sequence%20Basics/1.%20Creating%20a%20sequence.md
 * @link https://github.com/Froussios/Intro-To-RxJava/blob/master/tests/java/itrx/chapter2/creating/ObservableFactoriesExample.java
 */

public class OpCreateEmptyNeverError extends DrawerOpCreateActivity implements NavigationView.OnNavigationItemSelectedListener
{
    @BindView(R.id.view_empty) public View imageEmpty;
    @BindView(R.id.view_never) public View imageNever;
    @BindView(R.id.view_error) public View imageError;

    private DisContainer containerEmpty;
    private DisContainer containerNever;
    private DisContainer containerError;
    private Observable<Long> counter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op_create_empty_never_error);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, super.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        super.drawer.addDrawerListener(toggle);
        toggle.syncState();

        super.navigation.setNavigationItemSelectedListener(this);

        //Initialize Counter Observable
        this.counter = Observable.interval(1, TimeUnit.SECONDS).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).publish();
        ((ConnectableObservable) counter).connect();

        this.containerEmpty = new DisContainer(this.imageEmpty);
        this.containerNever = new DisContainer(this.imageNever);
        this.containerError = new DisContainer(this.imageError);

        this.containerEmpty.generate(this.counter, this);
        this.containerNever.generate(this.counter, this);
        this.containerError.generate(this.counter, this);
    }

    @OnClick(R.id.button_empty)
    public void onEmptyButtonClicked()
    {
        Observable<Long> values = Observable.empty();
        Disposable subscription = Util.subscribeEvent(values, this.containerEmpty, this.counter, this);
    }

    @OnClick(R.id.button_never)
    public void onNeverButtonClicked()
    {
        Observable<Long> values = Observable.never();
        Disposable subscription = Util.subscribeEvent(values, this.containerNever, this.counter, this);
    }

    @OnClick(R.id.button_error)
    public void onErrorButtonClicked()
    {
        Observable<Long> values = Observable.error(new Throwable("Testing"));
        Disposable subscription = Util.subscribeEvent(values, this.containerError, this.counter, this);
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
            case R.id.menu_item_empty_never_error:
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
