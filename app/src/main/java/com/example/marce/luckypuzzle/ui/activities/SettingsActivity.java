package com.example.marce.luckypuzzle.ui.activities;

import android.app.Activity;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;


import com.example.marce.luckypuzzle.R;
import com.example.marce.luckypuzzle.common.LuckyActivity;
import com.example.marce.luckypuzzle.common.LuckyFragment;
import com.example.marce.luckypuzzle.di.app.LuckyGameComponent;
import com.example.marce.luckypuzzle.di.component.ActivityComponent;
import com.example.marce.luckypuzzle.ui.recyclerViews.adapters.SettingsAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by marce on 17/04/17.
 */
public class SettingsActivity extends LuckyActivity implements SettingsAdapter.SettingsCallback{
    @BindView(R.id.appbar)Toolbar toolbar;
    @BindView(R.id.recyclerSettings)RecyclerView mRecycler;
    @BindView(R.id.ctlLayout)CollapsingToolbarLayout ctlLayout;
    @BindView(R.id.circle)CircleImageView circle;

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
        if(getIntent().getStringExtra("userName")!=null && getIntent().getStringExtra("uri")!=null){
            ctlLayout.setTitle(getIntent().getStringExtra("userName"));
            Picasso.with(this).load(getIntent().getStringExtra("uri")).into(circle);
        }else{
            ctlLayout.setTitle("SlideME");
        }


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
        Toast.makeText(this,R.string.featureNotImplemented,Toast.LENGTH_SHORT);
    }

    @Override
    public void onVibrationSwitcherChanged(boolean isChecked) {
        Toast.makeText(this,R.string.featureNotImplemented,Toast.LENGTH_SHORT);
    }

    @Override
    public void onSoundEffectsSwitcherChanged(boolean isChecked) {
        Toast.makeText(this,R.string.featureNotImplemented,Toast.LENGTH_SHORT);
    }

    @Override
    public void onLogoutPressed() {
        Toast.makeText(this,R.string.featureNotImplemented,Toast.LENGTH_SHORT);
    }
}

