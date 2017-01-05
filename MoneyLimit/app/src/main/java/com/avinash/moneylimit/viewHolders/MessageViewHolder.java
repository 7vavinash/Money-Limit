package com.avinash.moneylimit.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.avinash.moneylimit.Models.Message;
import com.avinash.moneylimit.R;

/**
 * Created by avinash on 1/2/2017.
 */

public class MessageViewHolder extends RecyclerView.ViewHolder {

    TextView tv_sender, tv_message, tv_timestamp;
    public MessageViewHolder(View itemView) {
        super(itemView);

        tv_sender = (TextView) itemView.findViewById(R.id.tv_sender);
        tv_message = (TextView) itemView.findViewById(R.id.tv_message);
        tv_timestamp = (TextView) itemView.findViewById(R.id.tv_timestamp);
    }

    public void bindToMessage(Message message) {
        tv_sender.setText(message.getSender());
        tv_message.setText(message.getMessage());
        tv_timestamp.setText(message.getTimestamp());

    }
}
