package com.example.data.repository.datasource;

import com.example.data.entity.ResultEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by Tom on 31.10.2016..
 */

public interface ResultDataStore {

    /**
     * Get an {@link rx.Observable} which will emit a List of {@link ResultEntity}.
     */
    Observable<List<ResultEntity>> resultEntityList(final String repoSearch, String page);

    /**
     * Get an {@link rx.Observable} which will emit a {@link ResultEntity} by its id.
     *
     * @param repoName The id to retrieve user data.
     */
    Observable<ResultEntity> resultEntityDetails(final String repoName);
}
