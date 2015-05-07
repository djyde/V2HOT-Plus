package com.randy.client.v2hot.api;

import com.randy.client.v2hot.model.Topic;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by randy on 15/5/7.
 */
public interface V2EXService {

    @GET("/topics/hot.json")
    void listTopics(Callback<List<Topic>> callback);

}
