package com.example.marce.luckypuzzle.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marce.luckypuzzle.di.app.LuckyGameComponent;
import com.example.marce.luckypuzzle.di.component.ActivityComponent;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by marce on 24/03/17.
 */

public abstract class LuckyFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(layout(),container,false);
        setUpComponent();
        ButterKnife.bind(this,v);
        init();
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


    protected abstract void init();



}
