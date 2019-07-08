package com.example.projekat2.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekat2.R;
import com.example.projekat2.holder.AbstractMessageHolder;
import com.example.projekat2.holder.PhotoMessageHolder;
import com.example.projekat2.holder.RecivedMessageHolder;
import com.example.projekat2.holder.SentMessageHolder;
import com.example.projekat2.holder.TextMessageHolder;
import com.example.projekat2.model.Message;
import com.example.projekat2.util.MessagediffUtilCallback;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<AbstractMessageHolder> {
    private List<Message> poruke;

    public MessageAdapter() {
        poruke = new ArrayList<>();
    }

    @NonNull
    @Override
    public AbstractMessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case Message.RECEIVED: {
                View view = inflater.inflate(R.layout.chat_activity_list_item_left, parent, false);
                return new RecivedMessageHolder(view);
            }
            case Message.SENT: {
                View view = inflater.inflate(R.layout.chat_activity_list_item_right, parent, false);
                return new SentMessageHolder(view);
            }
            case Message.PHOTO:{
                View view = inflater.inflate(R.layout.wall_list_item, parent, false);
                return new PhotoMessageHolder(view);

            }
            case Message.TEXT:{
                View view = inflater.inflate(R.layout.wall_list_item, parent, false);
                return new TextMessageHolder(view);

            }

        }
        return null;
    }
    @Override
    public void onBindViewHolder(@NonNull AbstractMessageHolder holder, int position) {
        Message message = poruke.get(position);
        holder.bind(message);
    }

    @Override
    public int getItemCount() {
        return poruke.size();
    }

    public void setData(List<Message> messageList){
        MessagediffUtilCallback callback = new MessagediffUtilCallback(poruke, messageList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        poruke.clear();
        poruke.addAll(messageList);
        result.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemViewType(int position) {
        Message message = poruke.get(position);
        return message.getType();
    }
}
