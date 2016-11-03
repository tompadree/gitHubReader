package com.example.data.repository.datasource;

import com.example.data.cache.ResultCache;
import com.example.data.entity.ResultEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by Tom on 31.10.2016..
 */

public class DiskResultDataStore implements ResultDataStore {

        private final ResultCache resultCache;

        /**
         * Construct a {@link ResultDataStore} based file system data store.
         *
         * @param resultCache A {@link ResultCache} to cache data retrieved from the api.
         */
        DiskResultDataStore(ResultCache resultCache) {
            this.resultCache = resultCache;
        }

        @Override
        public Observable<List<ResultEntity>> resultEntityList(String repoSearch,String page) {
            throw new UnsupportedOperationException("Operation is not available!!!");
        }

    @Override
    public Observable<ResultEntity> resultEntityDetails(String repoName) {
        return this.resultCache.get(repoName);
    }
}
