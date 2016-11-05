package com.example.data.entity.mapper;

import android.util.Log;

import com.example.data.entity.ResultEntity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Tom on 31.10.2016..
 */

public class ResultEntityJsonMapper {

    private final Gson gson;

    @Inject
    public ResultEntityJsonMapper() {
        this.gson = new Gson();
    }

    /**
     * Transform from valid json string to {@link ResultEntity}.
     *
     * @param resultJsonResponse A json representing a user profile.
     * @return {@link ResultEntity}.
     * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json structure.
     */
    public ResultEntity transformResultEntity(String resultJsonResponse) throws JsonSyntaxException {
        try {
            Type ResultEntityType = new TypeToken<ResultEntity>() {}.getType();
            ResultEntity ResultEntity = this.gson.fromJson(resultJsonResponse, ResultEntityType);

            return ResultEntity;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }

    /**
     * Transform from valid json string to List of {@link ResultEntity}.
     *
     * @param resultListJsonResponse A json representing a collection of results.
     * @return List of {@link ResultEntity}.
     * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json structure.
     */
    public List<ResultEntity> transformResultEntityCollection(String resultListJsonResponse) throws JsonSyntaxException, JSONException {

        List<ResultEntity> resultEntityCollection;// = new ArrayList<ResultEntity>();
       // List<ResultEntity> resultEntityCollectionTemp = null;
        try {

            Type listOfResultEntityType = new TypeToken<List<ResultEntity>>(){}.getType();

            JsonParser jsonParser = new JsonParser();
            JsonObject jo = (JsonObject)jsonParser.parse(resultListJsonResponse);
            JsonArray jsonArr = jo.getAsJsonArray("items");
           // int a = jsonArr.size();

            resultEntityCollection = this.gson.fromJson(jsonArr,listOfResultEntityType);

//            for(int i=0;i<5;i++){
//                resultEntityCollectionTemp = this.gson.fromJson(jsonArr,listOfResultEntityType);
//                //resultEntityCollection.addAll(resultEntityCollectionTemp);
//                for(int j=0;j<28;j++){
//                   // String g = resultEntityCollectionTemp.get(j).getLanguage();
//                    resultEntityCollection.add(resultEntityCollectionTemp.get(j));
//                    //jsonArr.size()
//                }
//            }

            if(resultEntityCollection==null)
                Log.e("resultEntityCollectio",String.valueOf(resultListJsonResponse));
            return resultEntityCollection;
        } catch (JsonSyntaxException jsonException){//| JSONException jsonException) {

            throw jsonException;
        }
    }
    
}
