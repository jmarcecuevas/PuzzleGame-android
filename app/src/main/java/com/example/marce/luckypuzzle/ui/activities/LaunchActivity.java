package com.example.marce.luckypuzzle.ui.activities;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.marce.luckypuzzle.R;
import com.example.marce.luckypuzzle.common.LuckyActivity;
import com.example.marce.luckypuzzle.di.app.LuckyGameComponent;
//import com.example.marce.luckypuzzle.di.module.LaunchModule;
import com.example.marce.luckypuzzle.di.component.ActivityComponent;
import com.example.marce.luckypuzzle.di.component.DaggerLaunchComponent;
import com.example.marce.luckypuzzle.di.component.LaunchComponent;
import com.example.marce.luckypuzzle.di.module.LaunchModule;
import com.example.marce.luckypuzzle.ui.fragments.SignUpOptionsFragment;
import com.example.marce.luckypuzzle.utils.SessionManager;

import javax.inject.Inject;

public class LaunchActivity extends LuckyActivity {
    private FrameLayout fragmentContainer;
    @Inject
    SessionManager sessionManager;
    private LaunchComponent launchComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentContainer= (FrameLayout) findViewById(R.id.launch_fragment_container);
        addFragmentWithBackStack(SignUpOptionsFragment.class,false);
        sessionManager.isLoggedIn();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void setupActivityComponent(LuckyGameComponent appComponent) {
        launchComponent = DaggerLaunchComponent.builder()
                .luckyGameComponent(appComponent)
                .launchModule(new LaunchModule()).build();
    }

    @Override
    public LaunchComponent getComponent() {
        return launchComponent;
    }


    @Override
    public int getFragmentLayout() {
        return fragmentContainer.getId();
    }
}
