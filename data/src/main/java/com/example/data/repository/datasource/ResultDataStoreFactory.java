package com.example.data.repository.datasource;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.data.cache.ResultCache;
import com.example.data.entity.mapper.ResultEntityJsonMapper;
import com.example.data.net.RestApi;
import com.example.data.net.RestApiImpl;

import javax.inject.Inject;

/**
 * Created by Tom on 31.10.2016..
 */

public class ResultDataStoreFactory {
    private final Context context;
    private final ResultCache resultCache;

    @Inject
    public ResultDataStoreFactory(@NonNull Context context, @NonNull ResultCache resultCache) {
        this.context = context.getApplicationContext();
        this.resultCache = resultCache;
    }

    /**
     * Create {@link ResultDataStore} from a user id.
     */
    public ResultDataStore create(String repoName) {
        ResultDataStore resultDataStore;

        if (!this.resultCache.isExpired() && this.resultCache.isCached(repoName)) {
            resultDataStore = new DiskResultDataStore(this.resultCache);
        } else {
            resultDataStore = createCloudDataStore();
        }

        return resultDataStore;
    }

    /**
     * Create {@link ResultDataStore} to retrieve data from the Cloud.
     */
    public ResultDataStore createCloudDataStore() {
        ResultEntityJsonMapper resultEntityJsonMapper = new ResultEntityJsonMapper();
        RestApi restApi = new RestApiImpl(this.context, resultEntityJsonMapper);

        return new CloudResultDataStore(restApi, this.resultCache);
    }
}


