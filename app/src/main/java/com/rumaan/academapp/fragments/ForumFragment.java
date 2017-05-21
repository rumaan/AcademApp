package com.rumaan.academapp.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rumaan.academapp.R;
import com.rumaan.academapp.adapters.ForumPostListAdaper;
import com.rumaan.academapp.model.ForumPost;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForumFragment extends Fragment {

    @BindView(R.id.forum_recycler)
    RecyclerView forumRecycler;
    @BindString(R.string.lorem_ipsum)
    String loremIpsum;

    public ForumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // hook up recycler view
        List<ForumPost> list = new ArrayList<>();
        list.add(new ForumPost("Discussion Title here", loremIpsum));
        list.add(new ForumPost("Discussion Title here", loremIpsum));
        ForumPostListAdaper forumPostListAdaper = new ForumPostListAdaper(view.getContext(), list);
        forumRecycler.setAdapter(forumPostListAdaper);
        forumRecycler.setHasFixedSize(true);
        forumRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        forumRecycler.setItemAnimator(new DefaultItemAnimator());
    }

}
