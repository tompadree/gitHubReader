package com.example.data.cache.serializer;

/**
 * Created by Tom on 28.10.2016..
 */

import com.example.data.entity.ResultEntity;
import com.google.gson.Gson;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class JsonSerializer {

    private final Gson gson = new Gson();

    @Inject
    public JsonSerializer() {}

    /**
     * Serialize an object to Json.
     *
     * @param resultEntity {@link ResultEntity} to serialize.
     */
    public String serialize(ResultEntity resultEntity) {
        String jsonString = gson.toJson(resultEntity, ResultEntity.class);
        return jsonString;
    }

    /**
     * Deserialize a json representation of an object.
     *
     * @param jsonString A json string to deserialize.
     * @return {@link ResultEntity}
     */
    public ResultEntity deserialize(String jsonString) {
        ResultEntity resultEntity = gson.fromJson(jsonString, ResultEntity.class);
        return resultEntity;
    }
}
