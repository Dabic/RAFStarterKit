package com.example.projekat2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekat2.R;
import com.example.projekat2.model.User;
import com.example.projekat2.util.ContactDiffUtilCallback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder>{
    private List<User> kontakti;
    private OnItemClickedListener mOnItemClickedListener;
    public ChatAdapter() {
        kontakti = new ArrayList<>();
    }

    @NonNull
    @Override
    public ChatAdapter.ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.chat_contact_list_item, parent, false);
        return new ChatAdapter.ChatHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int position) {
        holder.name.setText(kontakti.get(position).getName());
        Picasso.get().load("https://picsum.photos/300/300/?random").into(holder.img);
    }

    public void setData(List<User> kontakts){
        ContactDiffUtilCallback callback = new ContactDiffUtilCallback(kontakti, kontakts);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        kontakti.clear();
        kontakti.addAll(kontakts);
        result.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemCount() {
        return kontakti.size();
    }

    public class ChatHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView name;
        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.contact_img);
            name = itemView.findViewById(R.id.contact_name);
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (mOnItemClickedListener != null && position != RecyclerView.NO_POSITION) {
                        User user = kontakti.get(position);
                        mOnItemClickedListener.onItemClicked(user);
                    }
                }
            });
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (mOnItemClickedListener != null && position != RecyclerView.NO_POSITION) {
                        User user = kontakti.get(position);
                        mOnItemClickedListener.onItemClicked(user);
                    }
                }
            });
        }
    }
    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        mOnItemClickedListener = onItemClickedListener;
    }

    public interface OnItemClickedListener {
        void onItemClicked(User user);
    }
}
