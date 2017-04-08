package com.example.marce.luckypuzzle.di.component;

import com.example.marce.luckypuzzle.di.module.SignInModule;
import com.example.marce.luckypuzzle.di.scopes.FragmentScope;
import com.example.marce.luckypuzzle.ui.fragments.SignUpOptionsFragment;

import dagger.Component;

/**
 * Created by marce on 01/04/17.
 */
@FragmentScope
@Component(dependencies = SignUpActivityComponent.class,
        modules = SignInModule.class)
public interface SignUpOptionsComponent {
    void inject(SignUpOptionsFragment signUpOptionsFragment);
}
