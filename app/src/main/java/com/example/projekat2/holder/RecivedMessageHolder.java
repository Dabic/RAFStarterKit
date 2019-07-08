package com.example.projekat2.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.projekat2.R;
import com.example.projekat2.model.Message;

public class RecivedMessageHolder extends AbstractMessageHolder {
    private TextView text;
    private TextView date;

    public RecivedMessageHolder(@NonNull View itemView) {
        super(itemView);
        text = itemView.findViewById(R.id.left_tv);
        date = itemView.findViewById(R.id.left_date);

    }

    @Override
    public void bind(Message message) {

        text.setText(message.getText());
        date.setText(message.getTime());
    }
}
