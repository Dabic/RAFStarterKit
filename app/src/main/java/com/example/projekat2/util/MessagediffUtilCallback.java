package com.example.projekat2.util;

import androidx.recyclerview.widget.DiffUtil;
import com.example.projekat2.model.Message;

import java.util.List;

public class MessagediffUtilCallback extends DiffUtil.Callback {

    private List<Message> mOldList;
    private List<Message> mNewList;

    public MessagediffUtilCallback(List<Message> oldList, List<Message> newList){
        mOldList = oldList;
        mNewList = newList;
    }

    @Override
    public int getOldListSize() {
        return mOldList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        //Raspored oldRaspored = mOldList.get(oldItemPosition);
        //Raspored newRaspored = mNewList.get(newItemPosition);
        //return oldRaspored.getId() == newRaspored.getId();
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        //Raspored oldRaspored = mOldList.get(oldItemPosition);
        //Raspored newRaspored = mNewList.get(newItemPosition);
        return false;
    }


}
