package com.marineinsight.android;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by User on 23-08-2016.
 */
public class PostAdapterReport extends RecyclerView.Adapter<PostAdapterReport.ViewHolder> {



    private List<PostValue> postList;
    private LayoutInflater inflater;
    private Context context;

    public PostAdapterReport(Context context, List<PostValue> postList) {
        this.context = context;
        this.postList = postList;
        this.inflater = LayoutInflater.from(context);
    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (position ==0) {
//            return ITEM_TYPE_HEADER;
//        } else {
//            return ITEM_TYPE_NORMAL;
//        }
//    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

           View headerView = inflater.inflate(R.layout.list_item_post, parent, false);
           return new ViewHolder(headerView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Spanned deTitle;
        String postTitle;
        PostValue currentPost = postList.get(position);
        postTitle = currentPost.getTitle();
        deTitle = Html.fromHtml(Html.fromHtml((String) postTitle).toString());
        holder.tvTitle.setText(String.valueOf(deTitle));
        holder.tvPublishDate.setText(currentPost.getContent());
        Picasso.with(context).load(currentPost.getThumbnailUrl()).resize(80,80).into(holder.img_android);
    }

    @Override
    public int getItemCount() {
        return postList == null ? 0 : postList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvPublishDate;
        ImageView img_android;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvPublishDate = (TextView) itemView.findViewById(R.id.tvPublishDate);
            img_android = (ImageView)itemView.findViewById(R.id.img_android);
        }
    }
}


