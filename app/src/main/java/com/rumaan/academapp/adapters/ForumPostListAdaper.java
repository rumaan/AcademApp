package com.rumaan.academapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rumaan.academapp.R;
import com.rumaan.academapp.model.ForumPost;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForumPostListAdaper extends RecyclerView.Adapter<ForumPostListAdaper.ViewHolder> {
    private List<ForumPost> forumPostList;


    public ForumPostListAdaper(Context context, List<ForumPost> forumPosts) {
        this.forumPostList = new ArrayList<>(forumPosts);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forum_post_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(forumPostList.get(position).getPost_title());
        holder.desc.setText(forumPostList.get(position).getPost_desc());

    }

    @Override
    public int getItemCount() {
        return forumPostList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.forum_item_title)
        TextView title;
        @BindView(R.id.forum_item_desc)
        TextView desc;
        @BindView(R.id.root_item_view)
        LinearLayout rootItemView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
