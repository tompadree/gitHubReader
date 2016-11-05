package com.example.tom.githubreader.presentation.di.modules;

import com.example.domain.executor.PostExecutionThread;
import com.example.domain.executor.ThreadExecutor;
import com.example.domain.interactor.GetResultDetails;
import com.example.domain.interactor.GetResultList;
import com.example.domain.interactor.UseCase;
import com.example.domain.repository.ResultRepository;
import com.example.tom.githubreader.presentation.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Tom on 31.10.2016..
 */

@Module
public class ResultModule {

    private String repoName = "",page="1";

    public ResultModule() {}

    public ResultModule(String repoName, String page) {
        this.repoName = repoName;
        this.page=page;
    }

    @Provides @PerActivity @Named("resultList") UseCase provideGetResultListUseCase(
            ResultRepository resultRepository, ThreadExecutor threadExecutor,PostExecutionThread postExecutionThread) {
        return new GetResultList(repoName, page, resultRepository, threadExecutor, postExecutionThread);
        //return new GetResultDetails(repoId, resultRepository, threadExecutor, postExecutionThread);
    }

    @Provides @PerActivity @Named("repoDetails") UseCase provideGetResultDetailsUseCase(
            ResultRepository resultRepository, ThreadExecutor threadExecutor,PostExecutionThread postExecutionThread) {
        return new GetResultDetails(repoName, resultRepository, threadExecutor, postExecutionThread);
        //return new GetResultDetails(repoId, resultRepository, threadExecutor, postExecutionThread);
    }
}
