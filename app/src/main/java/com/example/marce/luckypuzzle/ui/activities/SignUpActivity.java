package com.example.marce.luckypuzzle.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.marce.luckypuzzle.R;
import com.example.marce.luckypuzzle.common.LuckyActivity;
import com.example.marce.luckypuzzle.di.app.LuckyGameComponent;
import com.example.marce.luckypuzzle.di.component.DaggerSignUpActivityComponent;
import com.example.marce.luckypuzzle.di.component.SignUpActivityComponent;
import com.example.marce.luckypuzzle.di.module.SignUpActivityModule;
import com.example.marce.luckypuzzle.ui.fragments.SignUpOptionsFragment;
import com.example.marce.luckypuzzle.utils.SessionManager;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class SignUpActivity extends LuckyActivity {
    private SignUpActivityComponent signUpActivityComponent;
    private FrameLayout fragmentContainer;
    @Inject SessionManager sessionManager;
    @Inject Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
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
        signUpActivityComponent = DaggerSignUpActivityComponent.builder()
                .luckyGameComponent(appComponent)
                .signUpActivityModule(new SignUpActivityModule()).build();
        signUpActivityComponent.inject(this);
    }

    @Override
    public SignUpActivityComponent getComponent() {
        return signUpActivityComponent;
    }

    @Override
    protected void init() {

    }

    @Override
    public int getFragmentLayout() {
        return fragmentContainer.getId();
    }
}
