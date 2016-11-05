package com.example.tom.githubreader.presentation.di.components;

import android.support.v7.widget.RecyclerView;

import com.example.tom.githubreader.presentation.di.PerActivity;
import com.example.tom.githubreader.presentation.di.modules.ActivityModule;
import com.example.tom.githubreader.presentation.di.modules.ResultModule;
import com.example.tom.githubreader.presentation.view.fragment.ResultDetailsFragment;
import com.example.tom.githubreader.presentation.view.fragment.ResultListFragment;

import dagger.Component;

/**
 * Created by Tom on 31.10.2016..
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, ResultModule.class})
public interface ResultComponent extends ActivityComponent {

    void inject(ResultListFragment resultListFragment);

    void inject(ResultDetailsFragment resultDetailsFragment);
}

