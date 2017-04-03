package com.example.marce.luckypuzzle.di.component;

import com.example.marce.luckypuzzle.di.module.SignInModule;
import com.example.marce.luckypuzzle.di.module.SignUpModule;
import com.example.marce.luckypuzzle.di.scopes.FragmentScope;
import com.example.marce.luckypuzzle.ui.fragments.SignUpFragment;

import dagger.Component;

/**
 * Created by marce on 02/04/17.
 */

@FragmentScope
@Component(dependencies = LaunchComponent.class,
        modules = SignUpModule.class)
public interface SignUpComponent {
    void inject(SignUpFragment signUpFragment);
}
