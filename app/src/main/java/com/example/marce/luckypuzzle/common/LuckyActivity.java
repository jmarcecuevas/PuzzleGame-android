package com.example.marce.luckypuzzle.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.marce.luckypuzzle.R;
import com.example.marce.luckypuzzle.di.app.LuckyGameApp;
import com.example.marce.luckypuzzle.di.app.LuckyGameComponent;
import com.example.marce.luckypuzzle.ui.UiManager;

/**
 * Created by marce on 24/03/17.
 */

public abstract class LuckyActivity extends AppCompatActivity implements UiManager.ChangeFragmentListener {

    private UiManager uiManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        injectDependencies();
        uiManager = new UiManager(getSupportFragmentManager());
        uiManager.setChangeFragmentListener(this);
    }

    /**
     * @return The layout that's gonna be the activity view.
     */
    protected abstract int getLayout();
    /**
     * Setup the object graph and inject the dependencies needed on this activity.
     */
    private void injectDependencies() {
        setUpComponent(LuckyGameApp.getApp(this).getComponent());
    }

    public abstract void setUpComponent(LuckyGameComponent appComponent);

    @Override
    public void startActivity(Intent intent){
        super.startActivity(intent);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    @Override
    public void onShowFragment(FragmentTransaction ft, Fragment fragmentToShow) {
        ft.show(fragmentToShow);
    }

    @Override
    public void onHideFragment(FragmentTransaction ft, Fragment fragmentToHide) {
        ft.hide(fragmentToHide);
    }

    @Override
    public void onAddFragment(FragmentTransaction ft, Fragment fragmentToAdd, String tag) {
        ft.add(getFragmentLayout(), fragmentToAdd, tag);
    }

    @Override
    public void onReplaceFragment(FragmentTransaction ft, Fragment fragmentToShow, Integer container, String tag) {
        ft.replace(container, fragmentToShow, tag);
    }

    public Fragment addFragmentWithBackStack(Class mNewFragmentClass, Boolean withBackstack) {
        Fragment myNewFragment = Fragment.instantiate(getApplicationContext(), mNewFragmentClass.getName());
        String newFragment = myNewFragment.getClass().getName();
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        if (withBackstack){
            t.setCustomAnimations(R.anim.pull_in_right,R.anim.push_out_left,R.anim.pull_in_left, R.anim.push_out_right);
            t.addToBackStack(newFragment);
        }
        t.add(getFragmentLayout(), myNewFragment, newFragment);
        t.commit();
        return myNewFragment;
    }

    public Fragment replaceFragmentWithBackStack(Class mNewFragmentClass, Boolean withBackstack) {
        Fragment myNewFragment = Fragment.instantiate(getApplicationContext(), mNewFragmentClass.getName());
        String newFragment = myNewFragment.getClass().getName();
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        if (withBackstack){
            t.setCustomAnimations(R.anim.pull_in_right,R.anim.push_out_left,R.anim.pull_in_left, R.anim.push_out_right);
            t.addToBackStack(newFragment);
        }
        t.replace(getFragmentLayout(), myNewFragment, newFragment);
        t.commit();
        return myNewFragment;
    }

    public Fragment replaceFragmentWithBackStackAnimation(Class myNewFragmentClass, Boolean withBackstack) {
        Fragment myNewFragment = Fragment.instantiate(getApplicationContext(), myNewFragmentClass.getName());
        String newFragment = myNewFragment.getClass().getName();
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.setCustomAnimations(R.anim.pull_in_right,R.anim.push_out_left,R.anim.pull_in_left, R.anim.push_out_right);
        if (withBackstack){
            t.addToBackStack(newFragment);
        }
        t.replace(getFragmentLayout(), myNewFragment, newFragment);
        t.commit();
        return myNewFragment;
    }

    public int getFragmentLayout() {
        return 0;
    }
}