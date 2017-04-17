package com.example.marce.luckypuzzle.ui.activities;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;


import com.example.marce.luckypuzzle.R;
import com.example.marce.luckypuzzle.common.LuckyActivity;
import com.example.marce.luckypuzzle.common.LuckyFragment;
import com.example.marce.luckypuzzle.di.app.LuckyGameComponent;
import com.example.marce.luckypuzzle.di.component.ActivityComponent;
import com.example.marce.luckypuzzle.ui.recyclerViews.adapters.SettingsAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by marce on 17/04/17.
 */
public class SettingsActivity extends LuckyActivity implements SettingsAdapter.SettingsCallback{
    @BindView(R.id.appbar)Toolbar toolbar;
    @BindView(R.id.recyclerSettings)RecyclerView mRecycler;
    @BindView(R.id.ctlLayout)CollapsingToolbarLayout ctlLayout;

    @Override
    protected int getLayout() {
        return R.layout.fragment_settings;
    }

    @Override
    protected void setupActivityComponent(LuckyGameComponent appComponent) {

    }

    @Override
    protected ActivityComponent getComponent() {
        return null;
    }

    @Override
    protected void init() {
        //App bar
        setSupportActionBar(toolbar);
        ctlLayout.setTitle("Marce Cuevas");

        ArrayList<String> mData= new ArrayList<>();
        mData.add("Music");
        mData.add("Sound effects");
        mData.add("Vibration");

        SettingsAdapter adapter= new SettingsAdapter(mData,this);
        mRecycler.setAdapter(adapter);
        mRecycler.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecycler.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecycler.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onMusicSwitcherChanged(boolean isChecked) {

    }

    @Override
    public void onVibrationSwitcherChanged(boolean isChecked) {

    }

    @Override
    public void onSoundEffectsSwitcherChanged(boolean isChecked) {

    }

    @Override
    public void onLogoutPressed() {

    }
}

