package com.example.domain.interactor;

import com.example.domain.executor.PostExecutionThread;
import com.example.domain.executor.ThreadExecutor;
import com.example.domain.repository.ResultRepository;
import javax.inject.Inject;
import rx.Observable;

/**
 * Created by Tom on 1.11.2016..
 */

public class GetResultList extends UseCase {

    //private final UserRepository userRepository;
    private final String searchRepo,page;
    private final ResultRepository resultRepository;

    @Inject
    public GetResultList(String searchRepo, String page, ResultRepository resultRepository, ThreadExecutor threadExecutor,PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.searchRepo = searchRepo;
        this.page = page;
        this.resultRepository = resultRepository;
    }

    @Override public Observable buildUseCaseObservable() {
        return this.resultRepository.results(searchRepo, page);

        //return this.userRepository.users();
    }
}
