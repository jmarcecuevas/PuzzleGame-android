package com.example.marce.luckypuzzle.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marce.luckypuzzle.di.app.LuckyGameComponent;
import com.example.marce.luckypuzzle.di.component.ActivityComponent;


/**
 * Created by marce on 24/03/17.
 */

public abstract class LuckyFragment<T extends LuckyPresenter> extends Fragment {

    protected T mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(layout(),container,false);
        setUpComponent();
        mPresenter=createPresenter();
        setUi(v);
        init();
        populate();
        setListeners();
        return v;
    }
    /**
     * Setup the object graph and inject the dependencies needed on this activity.
     */
    private void injectDependencies() {
        //setUpComponent(LuckyGameApp.getApp(getActivity()).getComponent());
    }

    public abstract void setUpComponent();

    /**
     * Returns the layout id for the inflater so the view can be populated
     */
    protected abstract int layout();

    /**
     * Loads the view elements for the fragment
     */
    protected abstract void setUi(View v);

    /**
     * Initializes any variables that the fragment needs
     */
    protected abstract void init();

    /**
     * Populates the view elements of the fragment
     */
    protected abstract void populate();

    /**
     * Sets the listeners for the views of the fragment
     */
    protected abstract void setListeners();

    /**
     * Create the presenter for this fragment
     */

    protected abstract T createPresenter();
}
