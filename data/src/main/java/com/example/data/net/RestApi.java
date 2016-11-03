package com.example.data.net;

/**
 * Created by Tom on 28.10.2016..
 */


import com.example.data.entity.ResultEntity;

import java.util.List;
import rx.Observable;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApi {
    //String API_BASE_URL = "https://api.github.com/";
    String API_BASE_URL = "https://api.github.com/";

    /** Api url for getting all users */
    String API_URL_GET_SEARCH = API_BASE_URL + "search/repositories?q=";//drek";

    /** Api url for getting repo */
    String API_URL_GET_REPO = API_BASE_URL + "repos/"; //"hagane/drek";

    /**
     * Retrieves an {@link rx.Observable} which will emit a {@link ResultEntity}.
     *
     * @param repoName The user id used to get user data.
     */
    Observable<ResultEntity> resultEntityById(final String repoName);

    /**
     * Retrieves an {@link rx.Observable} which will emit a {@link ResultEntity}.
     */
    Observable<List<ResultEntity>> resultEntityList(final String repoSearch, String page);
}
