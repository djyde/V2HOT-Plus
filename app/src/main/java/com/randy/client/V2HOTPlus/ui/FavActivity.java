package com.randy.client.V2HOTPlus.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.randy.client.V2HOTPlus.R;
import com.randy.client.V2HOTPlus.adapter.FavAdapter;
import com.randy.client.V2HOTPlus.data.Topic;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class FavActivity extends ActionBarActivity {


    private RecyclerView recyclerView;
    private TextView message;

    private Realm realm;
    private RealmResults<Topic> topics;

    private FavAdapter favAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        message = (TextView)findViewById(R.id.message);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        Realm.deleteRealmFile(this);
        realm = Realm.getInstance(this);


        RealmQuery<Topic> query = realm.where(Topic.class);

        topics = query.findAll();
        topics.sort("created_at",RealmResults.SORT_ORDER_DESCENDING);

        if(topics.size() == 0) message.setVisibility(View.VISIBLE);

        favAdapter = new FavAdapter(this,topics);
        recyclerView.setAdapter(favAdapter);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
