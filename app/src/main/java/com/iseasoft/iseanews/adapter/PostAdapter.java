package com.iseasoft.iseanews.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iseasoft.iseanews.entity.Post;
import com.iseasoft.iseanews.R;
//import com.squareup.picasso.Picasso;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter{

    static final int TYPE_ITEM_NORMAL = 0;
    static final int TYPE_ITEM_LOAD_MORE = 1;

    private List<Post> mItems;
    private Context mContext;
    private AdapterListener mItemListener;
    public PostAdapter(List<Post> items, AdapterListener postItemListener){
        //this.mContext = context;
        this.mItems = items;
        this.mItemListener = postItemListener;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        mContext = parent.getContext();
        if(viewType == TYPE_ITEM_NORMAL){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, null);

            PostViewHolder postViewHolder = new PostViewHolder(v);
            return postViewHolder;
        }

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_more, null);

        LoadMoreViewHolder loadMoreViewHolder = new LoadMoreViewHolder(v);

        return loadMoreViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof PostViewHolder){
            final PostViewHolder postViewHolder = (PostViewHolder) holder;
            final Post post = mItems.get(position);
            postViewHolder.tvTitle.setText(post.getPostTitle());
            postViewHolder.tvDesc.setText(post.getPostDesc());
            //Picasso.get().load(post.getPostThumb()).into(postViewHolder.imgThumb);
            Glide.with(mContext)
                    .load(post.getPostThumb())
                    .into(postViewHolder.imgThumb);

            postViewHolder.rlPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mItemListener != null){
                        mItemListener.onItemClickListener(post, position, postViewHolder);
                    }
                }
            });
        } else {
            final LoadMoreViewHolder viewHolder = (LoadMoreViewHolder)holder;
            viewHolder.btnLoadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mItemListener != null){
                        mItemListener.onItemClickListener(null, position, viewHolder);
                    }
                }
            });
        }


    }

    public class PostViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout rlPost;
        ImageView imgThumb;
        TextView tvTitle;
        TextView tvDesc;


        public PostViewHolder(View itemView) {
            super(itemView);

            rlPost = itemView.findViewById(R.id.rl_post);
            imgThumb = (ImageView) itemView.findViewById(R.id.img_thumb);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvDesc = (TextView) itemView.findViewById(R.id.tv_desc);

        }
    }

    private class LoadMoreViewHolder extends RecyclerView.ViewHolder{
        Button btnLoadMore;
        public LoadMoreViewHolder(View itemView) {
            super(itemView);
            btnLoadMore = itemView.findViewById(R.id.btn_load_more);
        }
    }

    @Override
    public int getItemViewType(int position){
        if(position < mItems.size()){
            return TYPE_ITEM_NORMAL;
        }
        return TYPE_ITEM_LOAD_MORE;
    }
    @Override
    public int getItemCount() {
        return mItems.size() + 1;
    }

    public void updatePosts(List<Post> items) {
        mItems = items;
        notifyDataSetChanged();
    }

}
