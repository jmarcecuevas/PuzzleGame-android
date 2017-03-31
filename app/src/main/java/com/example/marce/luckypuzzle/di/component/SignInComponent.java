package com.example.marce.luckypuzzle.di.component;

import com.example.marce.luckypuzzle.di.module.SignInModule;
import com.example.marce.luckypuzzle.di.scopes.FragmentScope;
import com.example.marce.luckypuzzle.ui.fragments.SignInFragment;

import dagger.Component;

/**
 * Created by tictapps on 31/03/17.
 */

@FragmentScope
@Component(dependencies = LaunchComponent.class,
        modules = SignInModule.class)

public interface SignInComponent {
    void inject(SignInFragment signInFragment);

}
