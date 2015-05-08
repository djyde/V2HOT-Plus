package com.randy.client.v2hot.ui;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.randy.client.v2hot.R;
import com.randy.client.v2hot.adapter.TopicAdapter;
import com.randy.client.v2hot.api.V2EXService;
import com.randy.client.v2hot.model.Topic;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;


    private V2EXService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://www.v2ex.com/api")
                .build();

        service = restAdapter.create(V2EXService.class);

        // http://stackoverflow.com/a/26910973/903502
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        requestTopic();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestTopic();
            }
        });

    }

    private void requestTopic() {
        service.listTopics(new Callback<List<Topic>>() {
            @Override
            public void success(List<Topic> topics, Response response) {
                recyclerView.setAdapter(new TopicAdapter(getApplicationContext(),topics));
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "网络出现问题", Toast.LENGTH_SHORT).show();
                Log.e("v2hot_topic_request", error.getMessage());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.action_fav:
                startActivity(new Intent(this,FavActivity.class));
                return true;
            case R.id.action_about:
                startActivity(new Intent(this,AboutActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
