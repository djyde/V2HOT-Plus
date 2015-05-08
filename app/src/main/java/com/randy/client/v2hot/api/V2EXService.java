package com.randy.client.v2hot.api;

import com.randy.client.v2hot.model.Reply;
import com.randy.client.v2hot.model.Topic;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by randy on 15/5/7.
 */
public interface V2EXService {

    @GET("/topics/hot.json")
    void listTopics(Callback<List<Topic>> callback);

    @GET("/replies/show.json")
    void listReplies(@Query("topic_id") String topic_id, Callback<List<Reply>> callback);
}
