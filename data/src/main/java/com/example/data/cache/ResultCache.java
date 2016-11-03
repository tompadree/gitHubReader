package com.example.data.cache;

/**
 * Created by Tom on 31.10.2016..
 */

import com.example.data.entity.ResultEntity;

import rx.Observable;

/**
 * An interface representing a user Cache.
 */
public interface ResultCache {
    /**
     * Gets an {@link rx.Observable} which will emit a {@link ResultEntity}.
     *
     * @param repoName The user id to retrieve data.
     */
    Observable<ResultEntity> get(final String repoName);

    /**
     * Puts and element into the cache.
     *
     * @param ResultEntity Element to insert in the cache.
     */
    void put(ResultEntity ResultEntity);

    /**
     * Checks if an element (Result) exists in the cache.
     *
     * @param repoName The id used to look for inside the cache.
     * @return true if the element is cached, otherwise false.
     */
    boolean isCached(final String repoName);

    /**
     * Checks if the cache is expired.
     *
     * @return true, the cache is expired, otherwise false.
     */
    boolean isExpired();

    /**
     * Evict all elements of the cache.
     */
    void evictAll();
}