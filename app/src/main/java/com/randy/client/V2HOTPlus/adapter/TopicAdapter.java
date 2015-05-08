package com.randy.client.V2HOTPlus.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.randy.client.V2HOTPlus.R;
import com.randy.client.V2HOTPlus.model.Topic;
import com.randy.client.V2HOTPlus.ui.ContentActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by randy on 15/5/7.
 */
public class TopicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Topic> topics;
    private Context context;

    public class TopicViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView content;
        RoundedImageView avatar;
        public TopicViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title);
            content = (TextView)itemView.findViewById(R.id.content);
            avatar = (RoundedImageView)itemView.findViewById(R.id.avatar);
        }
    }

    public class SloganViewHolder extends RecyclerView.ViewHolder{
        public SloganViewHolder(View itemView) {
            super(itemView);
        }
    }

    public TopicAdapter(Context context, List<Topic> topics) {
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
        return topics.size() + 1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TopicViewHolder){
            ((TopicViewHolder)holder).title.setText(topics.get(position).getTitle());
            ((TopicViewHolder)holder).content.setText(topics.get(position).getContent());
            Picasso.with(context).load(topics.get(position).getMember().getAvatar_normal().charAt(0) == '/' ? "https:" + topics.get(position).getMember().getAvatar_normal() : topics.get(position).getMember().getAvatar_normal())
                    .placeholder(R.drawable.avatar)
                    .into(((TopicViewHolder) holder).avatar);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View topic_view = LayoutInflater.from(context).inflate(R.layout.item_topic,parent,false);
        View slogan_view = LayoutInflater.from(context).inflate(R.layout.item_slogan,parent,false);

        if(viewType == topics.size()){
            return new SloganViewHolder(slogan_view);
        } else {
            topic_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ContentActivity.class);
                    intent.putExtra("url",topics.get(viewType).getUrl());
                    intent.putExtra("topic_id", String.valueOf(topics.get(viewType).getId()));
                    intent.putExtra("username",topics.get(viewType).getMember().getUsername());
                    intent.putExtra("title",topics.get(viewType).getTitle());
                    intent.putExtra("content",topics.get(viewType).getContent());
                    intent.putExtra("avatar",topics.get(viewType).getMember().getAvatar_large());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
            return new TopicViewHolder(topic_view);
        }
    }
}
