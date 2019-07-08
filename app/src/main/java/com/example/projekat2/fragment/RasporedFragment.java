package com.example.projekat2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekat2.R;
import com.example.projekat2.activity.RasporedActivity;
import com.example.projekat2.adapter.RasporedAdapter;
import com.example.projekat2.model.Raspored;
import com.example.projekat2.repository.network.api.Resource;
import com.example.projekat2.repository.raspored_db.entity.RasporedEntity;
import com.example.projekat2.viewModel.RasporedViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class RasporedFragment extends Fragment {

    public static final String TAG= "RasporedFragment";
    private RasporedViewModel viewModel;
    private RasporedAdapter rasporedAdapter;
    private Spinner spinner1;
    private Spinner spinner2;
    private EditText filterEt;
    private Button traziBtn;
    public static RasporedFragment newInstance() {
        return new RasporedFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.raspored_fragment_layout, container, false);
        init(view);
        initViewModel();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void init(View view){
        spinner1 = view.findViewById(R.id.spinner1_raspored);
        spinner2 = view.findViewById(R.id.spinner2_raspored);
        spinner1.setPrompt("Dan");
        spinner2.setPrompt("Grupa");
        filterEt = view.findViewById(R.id.filter_raspored);
        traziBtn = view.findViewById(R.id.trazi_raspored);

        RecyclerView recyclerView = view.findViewById(R.id.raspored_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        rasporedAdapter = new RasporedAdapter();
        recyclerView.setAdapter(rasporedAdapter);

        Intent intent = new Intent(this.getActivity(), RasporedActivity.class);
        rasporedAdapter.setOnItemClickedListener(new RasporedAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(RasporedEntity rasporedEntity) {
                intent.putExtra(RasporedActivity.RASPORED_KEY,rasporedEntity.getPredmet());
                startActivityForResult(intent, 1);
            }
        });

        rasporedAdapter.setOnItemClickedListener1(new RasporedAdapter.OnItemClickedListener1() {
            @Override
            public void onItemClicked1(RasporedEntity rasporedEntity) {
                intent.putExtra(RasporedActivity.RASPORED_KEY,rasporedEntity.getNastavnik());
                startActivityForResult(intent, 1);
            }
        });

        rasporedAdapter.setOnItemClickedListener2(new RasporedAdapter.OnItemClickedListener2() {
            @Override
            public void onItemClicked2(RasporedEntity rasporedEntity) {
                intent.putExtra(RasporedActivity.RASPORED_KEY,rasporedEntity.getUcionica());
                startActivityForResult(intent, 1);
            }
        });
        traziBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dan = spinner1.getSelectedItem().toString();
                String grupa = spinner2.getSelectedItem().toString();
                String predemt = filterEt.getText().toString();
                String profesor = filterEt.getText().toString();
                if(dan.equals("Dan"))
                    dan = "";
                if(grupa.equals("Grupa"))
                    grupa = "";
                Raspored raspored = new Raspored(predemt, null, profesor, grupa, dan, null, null);
                viewModel.setFilter(raspored);
                filterEt.setText("");
            }
        });
    }

    public void initViewModel(){
        viewModel = ViewModelProviders.of(this).get(RasporedViewModel.class);
        viewModel.getRasporeds().observe(this, new Observer<Resource<Void>>() {
            @Override
            public void onChanged(Resource<Void> resource) {
                showToast(resource.isSuccessful());
            }
        });

        viewModel.getAllRasporeds().observe(this,
                new Observer<List<RasporedEntity>>() {
                    @Override
                    public void onChanged(List<RasporedEntity> rasporedEntities) {
                        rasporedAdapter.setData(rasporedEntities);
                        List<String> dani = new ArrayList<>();
                        dani.add("Dan");
                        for(RasporedEntity re:rasporedEntities){
                            if(!dani.contains(re.getDan().trim()))
                                dani.add(re.getDan().trim());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item, dani);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner1.setAdapter(adapter);

                        List<String> grupe = new ArrayList<>();
                        grupe.add("Grupa");
                        for(RasporedEntity re:rasporedEntities){
                            StringTokenizer st = new StringTokenizer(re.getGrupe(), ",");
                            while(st.hasMoreTokens()){
                                String grupa = st.nextToken().trim();
                                if(!grupe.contains(grupa))
                                    grupe.add(grupa);
                            }
                        }
                        adapter = new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item, grupe);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(adapter);
                    }
                });
        viewModel.getFilteredRasporeds().observe(this, new Observer<List<RasporedEntity>>() {
            @Override
            public void onChanged(List<RasporedEntity> rasporedEntities) {
                rasporedAdapter.setData(rasporedEntities);
            }
        });

        viewModel.getFilteredRasporeds1().observe(this, new Observer<List<RasporedEntity>>() {
            @Override
            public void onChanged(List<RasporedEntity> rasporedEntities) {
                rasporedAdapter.setData(rasporedEntities);
            }
        });

    }
    private void showToast(boolean isSuccess) {
        String message = isSuccess ? getString(R.string.fetch_success_message) : getString(R.string.fetch_fail_message);
        Toast.makeText(RasporedFragment.this.getContext() ,message, Toast.LENGTH_LONG).show();
    }

}
