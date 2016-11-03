package com.example.data.repository;

import com.example.data.entity.mapper.ResultEntityDataMapper;
import com.example.data.repository.datasource.ResultDataStore;
import com.example.data.repository.datasource.ResultDataStoreFactory;
import com.example.domain.Result;
import com.example.domain.repository.ResultRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Tom on 31.10.2016..
 */

public class ResultDataRepository implements ResultRepository{
    private final ResultDataStoreFactory resultDataStoreFactory;
    private final ResultEntityDataMapper resultEntityDataMapper;

    /**
     * Constructs a {@link ResultRepository}.
     *
     * @param dataStoreFactory A factory to construct different data source implementations.
     * @param resultEntityDataMapper {@link ResultEntityDataMapper}.
     */
    @Inject
    public ResultDataRepository(ResultDataStoreFactory dataStoreFactory,
                              ResultEntityDataMapper resultEntityDataMapper) {
        this.resultDataStoreFactory = dataStoreFactory;
        this.resultEntityDataMapper = resultEntityDataMapper;
    }

    @Override public Observable<List<Result>> results(String searchRepo, String page) {
        //we always get all repos from the cloud
        final ResultDataStore resultDataStore = this.resultDataStoreFactory.createCloudDataStore();
        return resultDataStore.resultEntityList(searchRepo, page).map(this.resultEntityDataMapper::transform);
    }

    @Override public Observable<Result> result(String repoName) {
        //we always get all details from the cloud
        final ResultDataStore resultDataStore = this.resultDataStoreFactory.createCloudDataStore();
        return resultDataStore.resultEntityDetails(repoName).map(this.resultEntityDataMapper::transform);
    }
}
