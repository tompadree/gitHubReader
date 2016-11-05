package com.example.data.net;

/**
 * Created by Tom on 28.10.2016..
 */


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.data.entity.ResultEntity;
import com.example.data.entity.mapper.ResultEntityJsonMapper;
import com.example.data.exception.ApiLimitException;
import com.example.data.exception.NetworkConnectionException;
import com.example.data.exception.StringNotFoundException;
import com.fernandocejas.frodo.annotation.RxLogObservable;
import java.net.MalformedURLException;
import java.util.List;

import rx.Observable;

/**
 * {@link RestApi} implementation for retrieving data from the network.
 */
public class RestApiImpl implements RestApi {


//    @Inject
//    public RestApiImpl(Page page)
//    {
//        System.out.println("value of fooString is " + fooString);
//    }
    private final Context context;
    private final ResultEntityJsonMapper resultEntityJsonMapper;
    /**
     * Constructor of the class
     *
     * @param context {@link android.content.Context}.
     * @param resultEntityJsonMapper {@link ResultEntityJsonMapper}.
     */
    public RestApiImpl(Context context, ResultEntityJsonMapper resultEntityJsonMapper) {
        if (context == null || resultEntityJsonMapper == null) {
            throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
        this.resultEntityJsonMapper = resultEntityJsonMapper;
    }


    @RxLogObservable
    @Override public Observable<List<ResultEntity>> resultEntityList(String repoSearch, String page) {
        return Observable.create(subscriber -> {
            if (isThereInternetConnection() || repoSearch.isEmpty() || repoSearch.equals("")) {
                try {

                    String responseResultEntities = getResultEntitiesFromApi(repoSearch,page);

                    // https://developer.github.com/v3/#rate-limiting
                        if((responseResultEntities.split("limit exceeded")[0]).equals("{\"message\":\"API rate ")){
                            Log.e("LIMIT",String.valueOf(responseResultEntities));
                            subscriber.onError(new ApiLimitException());
                            Thread.sleep(5000);
                            responseResultEntities = getResultEntitiesFromApi(repoSearch,page);}

                    if (responseResultEntities != null) {

                        subscriber.onNext(resultEntityJsonMapper.transformResultEntityCollection(responseResultEntities));
                        subscriber.onCompleted();
                    } else {
                        if(repoSearch.isEmpty() || repoSearch.equals(""))
                            subscriber.onError(new StringNotFoundException());
                        else
                            subscriber.onError(new NetworkConnectionException());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if(repoSearch.isEmpty() || repoSearch.equals(""))
                        subscriber.onError(new StringNotFoundException());
                    else
                        subscriber.onError(new NetworkConnectionException(e.getCause()));
                }
            } else {
                if(repoSearch.isEmpty() || repoSearch.equals(""))
                    subscriber.onError(new Exception());
                else
                    subscriber.onError(new NetworkConnectionException());
            }
        });
    }

    @RxLogObservable
    @Override public Observable<ResultEntity> resultEntityById(final String repoName) {
        return Observable.create(subscriber -> {
            if (isThereInternetConnection()) {
                try {
                    String responseRepoDetails = getRepoDetailsFromApi(repoName);
                    if (responseRepoDetails != null) {
                        subscriber.onNext(resultEntityJsonMapper.transformResultEntity(responseRepoDetails));
                        subscriber.onCompleted();
                    } else {
                        if(repoName.isEmpty() || repoName.equals(""))
                            subscriber.onError(new StringNotFoundException());
                        else
                            subscriber.onError(new NetworkConnectionException());
                    }
                } catch (Exception e) {
                    subscriber.onError(new NetworkConnectionException(e.getCause()));
                }
            } else {
                subscriber.onError(new NetworkConnectionException());
            }
        });
    }


    private String getResultEntitiesFromApi(String repoSearch, String page) throws MalformedURLException {
        String call = API_URL_GET_SEARCH + repoSearch + "&page=" + page + "&per_page=100";
        return ApiConnection.createGET(call).requestSyncCall();
    }

    private String getRepoDetailsFromApi(String repoName) throws MalformedURLException {
        return ApiConnection.createGET(API_URL_GET_REPO + repoName).requestSyncCall();
    }

    /**
     * Checks if the device has any active internet connection.
     *
     * @return true device with internet connection, otherwise false.
     */
    private boolean isThereInternetConnection() {
        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }
}

