package com.example.data.repository.datasource;

import com.example.data.cache.ResultCache;
import com.example.data.entity.ResultEntity;
import com.example.data.net.RestApi;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Tom on 31.10.2016..
 */

public class CloudResultDataStore implements ResultDataStore {

    private final RestApi restApi;
    private final ResultCache resultCache;

    private final Action1<ResultEntity> saveToCacheAction = resultEntity -> {
        if (resultEntity != null) {
            CloudResultDataStore.this.resultCache.put(resultEntity);
        }
    };

    /**
     * Construct a {@link ResultDataStore} based on connections to the api (Cloud).
     *
     * @param restApi The {@link RestApi} implementation to use.
     * @param resultCache A {@link ResultCache} to cache data retrieved from the api.
     */
    CloudResultDataStore(RestApi restApi, ResultCache resultCache) {
        this.restApi = restApi;
        this.resultCache = resultCache;
    }


    @Override public Observable<List<ResultEntity>> resultEntityList(final String repoSearch, String page) {
        return this.restApi.resultEntityList(repoSearch,page);
    }

    @Override public Observable<ResultEntity> resultEntityDetails(final String repoName) {
        return this.restApi.resultEntityById(repoName).doOnNext(saveToCacheAction);
    }
}