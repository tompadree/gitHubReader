package com.example.data.entity.mapper;

import android.util.Log;

import com.example.data.entity.ResultEntity;
import com.example.domain.Result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Tom on 31.10.2016..
 */

public class ResultEntityDataMapper {


    @Inject
    public ResultEntityDataMapper() {}

    /**
     * Transform a {@link ResultEntity} into an {@link Result}.
     *
     * @param resultEntity Object to be transformed.
     * @return {@link Result} if valid {@link ResultEntity} otherwise null.
     */
    public Result transform(ResultEntity resultEntity) {
        Result result = null;
        if (resultEntity != null) {
            result = new Result(resultEntity.getRepoId());
            result.setAvatarUrl(resultEntity.getOwner().getAvatarUrl());
            result.setUserName(resultEntity.getOwner().getUserName());
            result.setRepoName(resultEntity.getRepoName());
            result.setIssues(resultEntity.getIssues());
            result.setWatchers(resultEntity.getWatchers());
            result.setForks(resultEntity.getForks());
            result.setSubscribers(resultEntity.getSubscribers());
            result.setCreatedAt(resultEntity.getCreatedAt());
            result.setModified(resultEntity.getModified());
            result.setLanguage(resultEntity.getLanguage());
            result.setUserType(resultEntity.getOwner().getUserType());
            result.setSiteAdmin(resultEntity.getOwner().getSiteAdmin());
        }

        return result;
    }

    /**
     * Transform a List of {@link ResultEntity} into a Collection of {@link Result}.
     *
     * @param resultEntityCollection Object Collection to be transformed.
     * @return {@link Result} if valid {@link ResultEntity} otherwise null.
     */
    public List<Result> transform(Collection<ResultEntity> resultEntityCollection) {

        List<Result> resultList;
//        if(resultEntityCollection==null || resultEntityCollection.isEmpty())
//            return null;
       // try {

           resultList = new ArrayList<>(50);
           Result result;
           for (ResultEntity resultEntity : resultEntityCollection) {
               result = transform(resultEntity);
               if (result != null) {
                   resultList.add(result);
               }
           }

           return resultList;
//       }catch (Exception e){
//            e.printStackTrace();
//        }
//        return resultList;
    }
}
