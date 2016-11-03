package com.example.domain.interactor;

import com.example.domain.Result;
import com.example.domain.executor.PostExecutionThread;
import com.example.domain.executor.ThreadExecutor;
import com.example.domain.repository.ResultRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Tom on 31.10.2016..
 */

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving data related to an specific {@link Result}.
 */
public class GetResultDetails extends UseCase {

    private final String repoName;
    private final ResultRepository resultRepository;

    @Inject
    public GetResultDetails(String repoName, ResultRepository resultRepository,ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repoName = repoName;
        this.resultRepository = resultRepository;
    }

    @Override protected Observable buildUseCaseObservable() {
        return this.resultRepository.result(repoName);
    }
}
