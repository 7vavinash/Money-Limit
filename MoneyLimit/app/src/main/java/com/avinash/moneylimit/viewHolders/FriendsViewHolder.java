package com.avinash.moneylimit.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.avinash.moneylimit.Models.Friend;
import com.avinash.moneylimit.R;

/**
 * Created by avinash on 1/5/2017.
 */

public class FriendsViewHolder extends RecyclerView.ViewHolder {

    TextView tv_friend;
    public FriendsViewHolder(View itemView) {
        super(itemView);
        tv_friend = (TextView) itemView.findViewById(R.id.tv_friend);
    }

    public void bindToViewHolder(Friend friends) {
        tv_friend.setText(friends.getName());
    }
}
