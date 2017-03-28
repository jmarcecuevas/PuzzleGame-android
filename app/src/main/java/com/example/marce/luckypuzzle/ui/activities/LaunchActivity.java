package com.example.marce.luckypuzzle.ui.activities;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.marce.luckypuzzle.R;
import com.example.marce.luckypuzzle.common.LuckyActivity;
import com.example.marce.luckypuzzle.di.app.LuckyGameComponent;
import com.example.marce.luckypuzzle.ui.fragments.SignUpOptionsFragment;

public class LaunchActivity extends LuckyActivity {
    private FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentContainer= (FrameLayout) findViewById(R.id.launch_fragment_container);
        addFragmentWithBackStack(SignUpOptionsFragment.class,false);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void setUpComponent(LuckyGameComponent appComponent) {
        appComponent.plus(new LogRegModule(this)).inject(this);
    }

    @Override
    public int getFragmentLayout() {
        return fragmentContainer.getId();
    }
}
