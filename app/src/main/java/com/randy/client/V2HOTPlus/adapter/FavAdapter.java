package com.randy.client.V2HOTPlus.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.randy.client.V2HOTPlus.R;
import com.randy.client.V2HOTPlus.data.Topic;
import com.randy.client.V2HOTPlus.ui.ContentActivity;

import io.realm.RealmResults;

/**
 * Created by randy on 15/5/8.
 */
public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {

    private RealmResults<Topic> topics;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView id;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title);
            id = (TextView)itemView.findViewById(R.id.id);
        }
    }

    public FavAdapter(Context context, RealmResults<Topic> topics) {
        super();
        this.context = context;
        this.topics = topics;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_fav,parent,false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContentActivity.class);
                intent.putExtra("topic_id",topics.get(viewType).getId());
                context.startActivity(intent);
            }
        });
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(topics.get(position).getTitle());
        holder.id.setText(topics.get(position).getId());
    }
}
