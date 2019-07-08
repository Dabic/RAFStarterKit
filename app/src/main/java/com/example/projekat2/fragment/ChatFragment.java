package com.example.projekat2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekat2.R;
import com.example.projekat2.activity.ChatActivity;
import com.example.projekat2.adapter.ChatAdapter;
import com.example.projekat2.model.User;
import com.example.projekat2.util.Utils;
import com.example.projekat2.viewModel.ChatViewModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }
    private ChatAdapter chatAdapter;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private ChatViewModel viewModel;
    private ValueEventListener valueEventListener;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_fragment_layout, container, false);
        init(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void init(View view){
        chatAdapter = new ChatAdapter();
        recyclerView = view.findViewById(R.id.chat_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(chatAdapter);
        initViewModel();
        Intent intent = new Intent(this.getActivity(), ChatActivity.class);
        chatAdapter.setOnItemClickedListener(new ChatAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(User user) {
                intent.putExtra(ChatActivity.USER_NAME, user.getName());
                intent.putExtra(ChatActivity.USER_ID, user.getIndexId());
                startActivityForResult(intent, 1);
            }
        });
        getDataFromFirebase();
    }
    public void getDataFromFirebase(){
        databaseReference = Utils.getDatabase().getReference().child("users");
        addListener();
    }
    public void addListener() {
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<User> contacts = new ArrayList<>();
                if (dataSnapshot.getValue() == null) {
                    return;
                } else {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        User contact = ds.getValue(User.class);
                        String index = ds.getKey();
                        String name = ds.child("name").getValue(String.class);
                        contact.setName(name);
                        contact.setIndexId(index);
                        contacts.add(contact);
                    }
                }
                User loggedIn = viewModel.getLoggedInUser();

                List<User> adapterList = new ArrayList<>();
                for (User contact : contacts) {
                    if (!(contact.getIndexId().equals(loggedIn.getIndexId())))
                        adapterList.add(contact);

                }
                chatAdapter.setData(adapterList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databaseReference.addValueEventListener(valueEventListener);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        databaseReference.removeEventListener(valueEventListener);
    }
    public void initViewModel(){
        viewModel = ViewModelProviders.of(this).get(ChatViewModel.class);

    }
}
