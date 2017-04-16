package com.example.marce.luckypuzzle.di.component;

import com.example.marce.luckypuzzle.di.module.GameModule;
import com.example.marce.luckypuzzle.di.module.SignUpModule;
import com.example.marce.luckypuzzle.di.scopes.FragmentScope;
import com.example.marce.luckypuzzle.ui.activities.HomeActivity;
import com.example.marce.luckypuzzle.ui.fragments.GameFragment;
import com.example.marce.luckypuzzle.ui.fragments.SignUpFragment;

import dagger.Component;

/**
 * Created by marce on 14/04/17.
 */

@FragmentScope
@Component(dependencies = HomeComponent.class,
        modules = GameModule.class)
public interface GameComponent{
    void inject(GameFragment gameFragment);
}