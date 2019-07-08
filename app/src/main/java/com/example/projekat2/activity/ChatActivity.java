package com.example.projekat2.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekat2.R;
import com.example.projekat2.adapter.MessageAdapter;
import com.example.projekat2.fragment.ChatFragment;
import com.example.projekat2.model.Message;
import com.example.projekat2.model.User;
import com.example.projekat2.viewModel.ChatViewModel;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChatViewModel viewModel;
    private MessageAdapter messageAdapter;
    private ImageButton send;
    private TextView name;
    private CircleImageView img;
    private EditText text;
    private Message message1;
    private Message message2;

    private String username1;
    private String id1;
    private String username2;
    private String id2;
    public static final String USER_NAME = "userName";
    public static final String USER_ID = "userId";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity_layout);
        init();
        initViewModel();
    }

    public void init(){
        recyclerView = findViewById(R.id.message_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        messageAdapter = new MessageAdapter();
        recyclerView.setAdapter(messageAdapter);

        send = findViewById(R.id.send_btn);
        name = findViewById(R.id.contact_username);
        img = findViewById(R.id.contact_pic);
        if(img != null)
            Picasso.get().load("https://picsum.photos/300/300/?random").into(img);
        text = findViewById(R.id.message_et);

        Intent intent = getIntent();
        if(intent != null) {
            username1 = intent.getStringExtra(USER_NAME);
            id1 = intent.getStringExtra(USER_ID);
            name.setText(username1);
        }
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy");
                DateFormat df2 = new SimpleDateFormat("h:mm a");
                String date = df.format(Calendar.getInstance().getTime());
                String time = df2.format(Calendar.getInstance().getTime());
                String poruka = text.getText().toString();

                User sender = viewModel.getLoggedInUser();
                User reciver = new User(id1, username1);

                if(!poruka.equals("")) {
                    message1 = new Message(sender, reciver, poruka, null, time + " " + date, 0);
                    viewModel.addMessage(message1);
                    text.setText("");
                }
            }
        });

    }
    public void initViewModel(){
        viewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        viewModel.getMessageLiveData().observe(ChatActivity.this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                ArrayList<Message> newMessagesList = new ArrayList<>();
                Intent intent = getIntent();
                if(intent != null) {
                    username2 = intent.getStringExtra(USER_NAME);
                    id2 = intent.getStringExtra(USER_ID);
                }
                User sender = viewModel.getLoggedInUser();
                User reciver = new User(id2, username2);
                message2 = new Message(sender, reciver);

                for(Message msg:messages){
                    if(msg.getSender().getIndexId().equals(message2.getSender().getIndexId()) && msg.getReciver().getIndexId()
                            .equals(message2.getReciver().getIndexId())){
                        msg.setType(0);
                        newMessagesList.add(msg);
                    }else if(msg.getSender().getIndexId().equals(id2) && msg.getReciver().getIndexId()
                            .equals(sender.getIndexId())){
                        msg.setType(1);
                        newMessagesList.add(msg);
                    }
                }
                messageAdapter.setData(newMessagesList);
                newMessagesList.clear();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.finish_chat:
                Intent intent = new Intent(ChatActivity.this, ChatFragment.class);
                setResult(RESULT_OK, intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
