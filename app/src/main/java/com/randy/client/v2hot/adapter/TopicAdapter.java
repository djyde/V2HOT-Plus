package com.randy.client.v2hot.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.randy.client.v2hot.R;
import com.randy.client.v2hot.model.Topic;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by randy on 15/5/7.
 */
public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder> {

    private List<Topic> topics;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView content;
        RoundedImageView avatar;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title);
            content = (TextView)itemView.findViewById(R.id.content);
            avatar = (RoundedImageView)itemView.findViewById(R.id.avatar);
        }
    }

    public TopicAdapter(Context context, List<Topic> topics) {
        super();
        this.context = context;
        this.topics = topics;
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(topics.get(position).getTitle());
        holder.content.setText(topics.get(position).getContent());
        Picasso.with(context).load(topics.get(position).getMember().getAvatar_normal().charAt(0) == '/' ? "https:" + topics.get(position).getMember().getAvatar_normal() : topics.get(position).getMember().getAvatar_normal()).into(holder.avatar);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_topic,parent,false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"test",Toast.LENGTH_SHORT).show();
            }
        });
        return new ViewHolder(v);
    }
}
