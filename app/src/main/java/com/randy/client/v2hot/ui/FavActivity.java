package com.randy.client.v2hot.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.randy.client.v2hot.R;
import com.randy.client.v2hot.adapter.FavAdapter;
import com.randy.client.v2hot.data.Topic;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class FavActivity extends ActionBarActivity {


    private RecyclerView recyclerView;
    private Realm realm;
    private RealmResults<Topic> topics;

    private FavAdapter favAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        Realm.deleteRealmFile(this);
        realm = Realm.getInstance(this);


        RealmQuery<Topic> query = realm.where(Topic.class);

        topics = query.findAll();
        topics.sort("created_at",RealmResults.SORT_ORDER_DESCENDING);

        favAdapter = new FavAdapter(this,topics);
        recyclerView.setAdapter(favAdapter);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        } else if(item.getItemId() == R.id.clear){
            realm.beginTransaction();
            RealmQuery<Topic> query = realm.where(Topic.class);
            query.findAll().clear();
            topics = query.findAll();
            favAdapter.notifyDataSetChanged();
            realm.commitTransaction();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
