package com.randy.client.v2hot.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.randy.client.v2hot.R;
import com.randy.client.v2hot.adapter.ReplyAdapter;
import com.randy.client.v2hot.api.V2EXService;
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
    private String title;
    private String content;
    private String avatar;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        topic_id = getIntent().getStringExtra("topic_id");
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        username = getIntent().getStringExtra("username");
        avatar = getIntent().getStringExtra("avatar");


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://www.v2ex.com/api")
                .build();

        V2EXService service = restAdapter.create(V2EXService.class);

        service.listReplies(topic_id, new Callback<List<Reply>>() {
            @Override
            public void success(List<Reply> replies, Response response) {
                Log.e("replies",replies.toString());
                Log.e("response",response.getUrl());
                Member member = new Member();
                member.setAvatar_large(avatar);
                member.setUsername(username);
                Topic topic = new Topic();
                topic.setTitle(title);
                topic.setContent(content);
                topic.setMember(member);
                recyclerView.setAdapter(new ReplyAdapter(getApplicationContext(), replies, topic));
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
