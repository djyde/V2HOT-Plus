package com.randy.client.v2hot.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.randy.client.v2hot.ui.ContentActivity;
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
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(topics.get(position).getTitle());
        holder.content.setText(topics.get(position).getContent());
        Picasso.with(context).load(topics.get(position).getMember().getAvatar_normal().charAt(0) == '/' ? "https:" + topics.get(position).getMember().getAvatar_normal() : topics.get(position).getMember().getAvatar_normal())
                .placeholder(R.drawable.avatar)
                .into(holder.avatar);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_topic,parent,false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("viewtype", String.valueOf(viewType));
                Intent intent = new Intent(context, ContentActivity.class);
                intent.putExtra("topic_id", String.valueOf(topics.get(viewType).getId()));
                intent.putExtra("username",topics.get(viewType).getMember().getUsername());
                intent.putExtra("title",topics.get(viewType).getTitle());
                intent.putExtra("content",topics.get(viewType).getContent());
                intent.putExtra("avatar",topics.get(viewType).getMember().getAvatar_large());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return new ViewHolder(v);
    }
}
