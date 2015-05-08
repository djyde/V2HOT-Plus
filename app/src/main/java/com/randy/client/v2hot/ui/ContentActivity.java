package com.randy.client.v2hot.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.randy.client.v2hot.R;
import com.randy.client.v2hot.adapter.ReplyAdapter;
import com.randy.client.v2hot.api.V2EXService;
import com.randy.client.v2hot.data.V2EX;
import com.randy.client.v2hot.model.Member;
import com.randy.client.v2hot.model.Reply;
import com.randy.client.v2hot.model.Topic;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ContentActivity extends ActionBarActivity {

    private RecyclerView recyclerView;

    private String topic_id;

    private V2EXService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        topic_id = getIntent().getStringExtra("topic_id");

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://www.v2ex.com/api")
                .build();
        service = restAdapter.create(V2EXService.class);

        service.getTopicInfo(topic_id, new Callback<List<Topic>>() {
            @Override
            public void success(final List<Topic> topics, Response response) {
                getSupportActionBar().setTitle(topics.get(0).getTitle());
                service.listReplies(topic_id, new Callback<List<Reply>>() {
                    @Override
                    public void success(List<Reply> replies, Response response) {
                        recyclerView.setAdapter(new ReplyAdapter(getApplicationContext(), replies, topics.get(0)));
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                // todo 错误处理
            }
        });












    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if(id == R.id.action_fav){

            service.getTopicInfo(topic_id, new Callback<List<Topic>>() {
                @Override
                public void success(List<Topic> topics, Response response) {
                    V2EX.addTopicToFav(getApplicationContext(), topics.get(0), new V2EX.AddToTopicToFavListener() {
                        @Override
                        public void onCommit() {
                            Toast.makeText(getApplicationContext(),"收藏成功",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void failure(RetrofitError error) {
                    //TODO 错误处理
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
