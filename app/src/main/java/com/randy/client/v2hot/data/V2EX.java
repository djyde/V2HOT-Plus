package com.randy.client.v2hot.data;

import android.content.Context;
import android.widget.Toast;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmQuery;

/**
 * Created by randy on 15/5/8.
 */
public class V2EX {

    public interface AddToTopicToFavListener{
        void onCommit();
    }

    public interface RemoveTopicFromFavListener{
        void onCommit();
    }

    public static void addTopicToFav(Context context, com.randy.client.v2hot.model.Topic topic, AddToTopicToFavListener listener){
        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();
        com.randy.client.v2hot.data.Topic data_topic = realm.createObject(com.randy.client.v2hot.data.Topic.class);
        data_topic.setId("/t/" + String.valueOf(topic.getId()));
        data_topic.setTitle(topic.getTitle());
        data_topic.setCreated_at(new Date());
        listener.onCommit();
        realm.commitTransaction();
    }

    public static void removeTopicFromFav(Context context, com.randy.client.v2hot.model.Topic topic, RemoveTopicFromFavListener listener){
        Realm realm = Realm.getInstance(context);
        RealmQuery<Topic> query = realm.where(Topic.class);

        query.equalTo("id",String.valueOf(topic.getId()));
        realm.beginTransaction();
        query.findAll().clear();
        listener.onCommit();
        realm.commitTransaction();
    }

    public static boolean isExistFav(Context context, com.randy.client.v2hot.model.Topic topic){
        Realm realm = Realm.getInstance(context);
        RealmQuery<Topic> query = realm.where(Topic.class);

        query.equalTo("id",String.valueOf(topic.getId()));
        return query.findAll().size() == 1;
    }
}
