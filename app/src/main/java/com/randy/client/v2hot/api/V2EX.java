package com.randy.client.v2hot.api;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.randy.client.v2hot.model.Topic;

import java.util.List;

/**
 * Created by randy on 15/5/7.
 */
public class V2EX {
    private Context context;

    private final String ENDPOINT = "https://www.v2ex.com/api";
    private final String TOPIC = "/topics/hot.json";

    public interface Listener{
        void onResponse(String jsonString);
        void onErrorResponse(VolleyError error);
    }

    public V2EX(Context context){
        this.context = context;
    }
    public void getHotTopics(final Listener listener){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(ENDPOINT + TOPIC, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onResponse(JSON.toJSONString(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onErrorResponse(error);
            }
        });
        queue.add(request);
    }
}
