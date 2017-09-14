package com.github.syafiqq.reactivetest.controller.custom.java.util;

/*
 * This <ReactiveTest> created by : 
 * Name         : syafiq
 * Date / Time  : 14 September 2017, 8:39 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import java.util.Observable;

public class IntegerObservable extends Observable
{
    private Integer value;

    public IntegerObservable(Integer value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return this.value;
    }

    public void setValue(Integer value)
    {
        this.value = value;

        super.setChanged();
        this.notifyObservers(this.value);
    }

    public void increment(Integer value)
    {
        this.setValue(this.getValue() + value);
    }

    public void decrement(Integer value)
    {
        this.setValue(this.getValue() - value);
    }

    @Override public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof IntegerObservable))
        {
            return false;
        }

        IntegerObservable that = (IntegerObservable) o;

        return getValue() == that.getValue();

    }

    @Override public int hashCode()
    {
        return getValue();
    }

    @Override public String toString()
    {
        @SuppressWarnings("StringBufferReplaceableByString") final StringBuilder sb = new StringBuilder("IntegerObservable{");
        sb.append("value=").append(value);
        sb.append('}');
        return sb.toString();
    }
}