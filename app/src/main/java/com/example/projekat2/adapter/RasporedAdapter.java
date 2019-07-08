package com.example.projekat2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekat2.R;
import com.example.projekat2.repository.raspored_db.entity.RasporedEntity;
import com.example.projekat2.util.RasporedDiffUtilCallback;

import java.util.ArrayList;
import java.util.List;

public class RasporedAdapter extends RecyclerView.Adapter<RasporedAdapter.RasporedHolder>{

    private List<RasporedEntity> termData;
    private OnItemClickedListener onItemClickedListener;
    private OnItemClickedListener1 onItemClickedListener1;
    private OnItemClickedListener2 onItemClickedListener2;
    public RasporedAdapter() {
        termData = new ArrayList<>();
    }

    @NonNull
    @Override
    public RasporedAdapter.RasporedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.raspored_list_item, parent, false);
        return new RasporedHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RasporedAdapter.RasporedHolder holder, int position) {
        RasporedEntity termin = termData.get(position);
        holder.profesorTv.setText(termin.getNastavnik());
        holder.predmetTv.setText(termin.getPredmet()+"-" + termin.getTip());
        holder.ucionicaTv.setText(termin.getUcionica());
        holder.vremeTv.setText(termin.getTermin());
        holder.grupeTv.setText(termin.getGrupe());
        holder.danTv.setText(termin.getDan());

    }

    @Override
    public int getItemCount() {
        return termData.size();
    }

    public void setData(List<RasporedEntity> rasporeds){
        RasporedDiffUtilCallback callback = new RasporedDiffUtilCallback(termData, rasporeds);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        termData.clear();
        termData.addAll(rasporeds);
        result.dispatchUpdatesTo(this);
    }
    public class RasporedHolder extends RecyclerView.ViewHolder {

        TextView profesorTv;
        TextView predmetTv;
        TextView ucionicaTv;
        TextView grupeTv;
        TextView vremeTv;
        TextView danTv;
        public RasporedHolder(@NonNull View itemView) {
            super(itemView);
            profesorTv = itemView.findViewById(R.id.raspored_li_profesor);
            predmetTv = itemView.findViewById(R.id.raspored_li_predmet);
            ucionicaTv = itemView.findViewById(R.id.raspored_li_ucionica);
            grupeTv = itemView.findViewById(R.id.raspored_li_grupe);
            vremeTv = itemView.findViewById(R.id.raspored_li_vreme);
            danTv = itemView.findViewById(R.id.raspored_li_dan);

            predmetTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onItemClickedListener != null && position != RecyclerView.NO_POSITION) {
                        RasporedEntity rasporedEntity = termData.get(position);
                        onItemClickedListener.onItemClicked(rasporedEntity);
                    }
                }
            });

            profesorTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onItemClickedListener1 != null && position != RecyclerView.NO_POSITION) {
                        RasporedEntity rasporedEntity = termData.get(position);
                        onItemClickedListener1.onItemClicked1(rasporedEntity);
                    }
                }
            });

            ucionicaTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onItemClickedListener2 != null && position != RecyclerView.NO_POSITION) {
                        RasporedEntity rasporedEntity = termData.get(position);
                        onItemClickedListener2.onItemClicked2(rasporedEntity);
                    }
                }
            });
        }
    }

    public interface OnItemClickedListener {
        void onItemClicked(RasporedEntity rasporedEntity);
    }
    public interface OnItemClickedListener1 {
        void onItemClicked1(RasporedEntity rasporedEntity);
    }
    public interface OnItemClickedListener2 {
        void onItemClicked2(RasporedEntity rasporedEntity);
    }
    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
    public void setOnItemClickedListener1(OnItemClickedListener1 onItemClickedListener1) {
        this.onItemClickedListener1 = onItemClickedListener1;
    }
    public void setOnItemClickedListener2(OnItemClickedListener2 onItemClickedListener2) {
        this.onItemClickedListener2 = onItemClickedListener2;
    }
}
