package com.example.domain.interactor;

import com.example.domain.executor.PostExecutionThread;
import com.example.domain.executor.ThreadExecutor;
import com.example.domain.repository.ResultRepository;
import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;

/**
 * Created by Tom on 1.11.2016..
 */

public class GetResultList extends UseCase {

    private final String searchRepo;
    private final ResultRepository resultRepository;

    @Inject
    public GetResultList(String searchRepo, String page, ResultRepository resultRepository, ThreadExecutor threadExecutor,PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.searchRepo = searchRepo;
        this.resultRepository = resultRepository;
    }


    @Override public Observable buildUseCaseObservable(String page) {
        return this.resultRepository.results(searchRepo, page);
    }
}
