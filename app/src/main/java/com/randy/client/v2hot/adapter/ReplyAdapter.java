package com.randy.client.v2hot.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.randy.client.v2hot.R;
import com.randy.client.v2hot.model.Reply;
import com.randy.client.v2hot.model.Topic;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit.http.HEAD;

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
        ImageView avatar;
        public CardViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title);
            username = (TextView)itemView.findViewById(R.id.username);
            content = (TextView)itemView.findViewById(R.id.content);
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof CardViewHolder){
            ((CardViewHolder) holder).title.setText(topic.getTitle());
            ((CardViewHolder) holder).username.setText(topic.getMember().getUsername());
            ((CardViewHolder) holder).content.setText(topic.getContent());
            Picasso.with(context).load(topic.getMember().getAvatar_large()).into(((CardViewHolder) holder).avatar);
        } else if(holder instanceof HeaderViewHolder){
            ((HeaderViewHolder) holder).title.setText("评论");
        } else if (holder instanceof ReplyViewHolder){
            Log.e("reply","true");
            ((ReplyViewHolder) holder).username.setText(replies.get(position - 2).getMember().getUsername());
            ((ReplyViewHolder) holder).content.setText(replies.get(position - 2).getContent());
            Picasso.with(context).load(replies.get(position - 2).getMember().getAvatar_normal()).into(((ReplyViewHolder) holder).avatar);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View card_view = LayoutInflater.from(context).inflate(R.layout.header_card,parent,false);
        View header_view = LayoutInflater.from(context).inflate(R.layout.header,parent,false);
        View reply_view = LayoutInflater.from(context).inflate(R.layout.item_reply,parent,false);

        Log.e("size",String.valueOf(replies.size()));

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
