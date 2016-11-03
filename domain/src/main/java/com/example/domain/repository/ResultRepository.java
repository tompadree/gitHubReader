package com.example.domain.repository;

import com.example.domain.Result;

import java.util.List;

import rx.Observable;

/**
 * Created by Tom on 31.10.2016..
 */

public interface ResultRepository {

    /**
     * Get an {@link rx.Observable} which will emit a List of {@link Result}.
     */
    Observable<List<Result>> results(final String searchRepo, String page);

    /**
     * Get an {@link rx.Observable} which will emit a {@link Result}.
     *
     * @param repoName The user id used to retrieve user data.
     */
    Observable<Result> result(final String repoName);
}
