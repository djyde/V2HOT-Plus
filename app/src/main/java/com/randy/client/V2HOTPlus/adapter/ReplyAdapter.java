package com.randy.client.V2HOTPlus.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.randy.client.V2HOTPlus.R;
import com.randy.client.V2HOTPlus.data.V2EX;
import com.randy.client.V2HOTPlus.model.Reply;
import com.randy.client.V2HOTPlus.model.Topic;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by randy on 15/5/7.
 */
public class ReplyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int CARD = 0;
    private final int HEADER = 1;
    private final int REPLY = 2;

    private List<Reply> replies;
    private Topic topic;
    private Context context;

    public ReplyAdapter(Context context, List<Reply> replies, Topic topic) {
        super();
        this.context = context;
        this.replies = replies;
        this.topic = topic;
    }

    public class CardViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView content;
        TextView username;
        TextView open;
        TextView fav;
        ImageView avatar;
        public CardViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title);
            username = (TextView)itemView.findViewById(R.id.username);
            content = (TextView)itemView.findViewById(R.id.content);
            open = (TextView)itemView.findViewById(R.id.open);
            fav = (TextView)itemView.findViewById(R.id.fav);
            avatar = (ImageView)itemView.findViewById(R.id.avatar);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title);
        }
    }

    public class ReplyViewHolder extends RecyclerView.ViewHolder{
        TextView username;
        TextView content;
        RoundedImageView avatar;
        public ReplyViewHolder(View itemView) {
            super(itemView);
            username = (TextView)itemView.findViewById(R.id.username);
            content = (TextView)itemView.findViewById(R.id.content);
            avatar = (RoundedImageView)itemView.findViewById(R.id.avatar);
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (position){
            case 0:
                return CARD;
            case 1:
                return HEADER;
            default:
                return REPLY;
        }
    }

    @Override
    public int getItemCount() {
        return replies.size() + 2;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof CardViewHolder){
            ((CardViewHolder) holder).title.setText(topic.getTitle());
            ((CardViewHolder) holder).username.setText(topic.getMember().getUsername());
            ((CardViewHolder) holder).content.setText(topic.getContent());

            ((CardViewHolder) holder).fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (V2EX.isExistFav(context, topic)) {
                        V2EX.removeTopicFromFav(context, topic, new V2EX.RemoveTopicFromFavListener() {
                            @Override
                            public void onCommit() {
                                Toast.makeText(context,"取消收藏成功",Toast.LENGTH_SHORT).show();
                                ((CardViewHolder) holder).fav.setText("加入收藏");
                            }
                        });
                    } else {
                        V2EX.addTopicToFav(context, topic, new V2EX.AddToTopicToFavListener() {
                            @Override
                            public void onCommit() {
                                Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT).show();
                                ((CardViewHolder) holder).fav.setText("取消收藏");
                            }
                        });
                    }
                }
            });

            if(V2EX.isExistFav(context,topic)){
                ((CardViewHolder) holder).fav.setText("取消收藏");
            }

            ((CardViewHolder) holder).open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(topic.getUrl())).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            });
            Picasso.with(context).load(topic.getMember().getAvatar_large()).into(((CardViewHolder) holder).avatar);
        } else if(holder instanceof HeaderViewHolder){
            ((HeaderViewHolder) holder).title.setText("评论");
        } else if (holder instanceof ReplyViewHolder){
            if (topic.getMember().getUsername().equals(replies.get(position - 2).getMember().getUsername())) {
                ((ReplyViewHolder) holder).username.setText(replies.get(position - 2).getMember().getUsername() + "（楼主）");
            } else {
                ((ReplyViewHolder) holder).username.setText(replies.get(position - 2).getMember().getUsername());
            }
            ((ReplyViewHolder) holder).content.setText(replies.get(position - 2).getContent());
            Picasso.with(context).load(replies.get(position - 2).getMember().getAvatar_normal())
                    .placeholder(R.drawable.avatar)
                    .into(((ReplyViewHolder) holder).avatar);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View card_view = LayoutInflater.from(context).inflate(R.layout.header_card, parent, false);
        View header_view = LayoutInflater.from(context).inflate(R.layout.header,parent,false);
        View reply_view = LayoutInflater.from(context).inflate(R.layout.item_reply,parent,false);

        switch (viewType){
            case CARD:
                return new CardViewHolder(card_view);
            case HEADER:
                return new HeaderViewHolder(header_view);
            case REPLY:
                return new ReplyViewHolder(reply_view);
            default:
                return new CardViewHolder(card_view);
        }
    }
}
