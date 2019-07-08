package com.example.projekat2.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.projekat2.R;
import com.example.projekat2.model.Message;

public class TextMessageHolder extends AbstractMessageHolder{
    private TextView name;
    private TextView dateTime;
    private TextView content;

    public TextMessageHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.wall_li_name);
        dateTime = itemView.findViewById(R.id.wall_li_date);
        content = itemView.findViewById(R.id.wall_li_text);
    }

    @Override
    public void bind(Message message) {
        name.setText(message.getSender().getName());
        dateTime.setText(message.getTime());
        content.setText(message.getText());


    }
}
