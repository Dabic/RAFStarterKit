package com.example.projekat2.holder;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.projekat2.R;
import com.example.projekat2.model.Message;
import com.squareup.picasso.Picasso;

public class PhotoMessageHolder extends AbstractMessageHolder{
    private ImageView imageView;
    private TextView dateTime;
    private TextView name;

    public PhotoMessageHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.wall_li_pic);
        name = itemView.findViewById(R.id.wall_li_name);
        dateTime = itemView.findViewById(R.id.wall_li_date);


    }

    @Override
    public void bind(Message message) {

        name.setText(message.getSender().getName());
        dateTime.setText(message.getTime());
        Uri imgUri;
        imgUri = Uri.parse(message.getImage());
        imageView.getLayoutParams().height = 500;
        Picasso.get().load(imgUri).fit().centerCrop().into(imageView);


    }
}
