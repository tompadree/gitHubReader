package com.example.tom.githubreader.presentation.mapper;

import com.example.domain.Result;
import com.example.tom.githubreader.presentation.di.PerActivity;
import com.example.tom.githubreader.presentation.model.ResultModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Created by Tom on 1.11.2016..
 */
@PerActivity
public class ResultModelDataMapper {


    @Inject
    public ResultModelDataMapper() {}

    /**
     * Transform a {@link Result} into an {@link ResultModel}.
     *
     * @param result Object to be transformed.
     * @return {@link ResultModel}.
     */
    public ResultModel transform(Result result) {
        if (result == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        ResultModel resultModel = new ResultModel(result.getRepoId());
        resultModel.setUserName(result.getUserName());
        resultModel.setRepoName(result.getRepoName());
        resultModel.setAvatarUrl(result.getAvatarUrl());
        resultModel.setWatchers(result.getWatchers());
        resultModel.setIssues(result.getIssues());
        resultModel.setForks(result.getForks());
        resultModel.setSubscribers(result.getSubscribers());
        resultModel.setCreatedAt(result.getCreatedAt());
        resultModel.setModified(result.getModified());
        resultModel.setLanguage(result.getLanguage());
        resultModel.setUserType(result.getUserType());
        resultModel.setSiteAdmin(result.getSiteAdmin());


        return resultModel;
    }

    /**
     * Transform a Collection of {@link Result} into a Collection of {@link ResultModel}.
     *
     * @param resultCollection Objects to be transformed.
     * @return List of {@link ResultModel}.
     */
    public Collection<ResultModel> transform(Collection<Result> resultCollection) {
        Collection<ResultModel> resultModelsCollection;

        if (resultCollection != null && !resultCollection.isEmpty()) {
            resultModelsCollection = new ArrayList<>();
            for (Result result : resultCollection) {
                resultModelsCollection.add(transform(result));
            }
        } else {
            resultModelsCollection = Collections.emptyList();
        }

        return resultModelsCollection;
    }
}
