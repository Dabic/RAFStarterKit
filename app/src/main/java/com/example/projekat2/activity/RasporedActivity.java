package com.example.projekat2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekat2.R;
import com.example.projekat2.adapter.RasporedAdapter;
import com.example.projekat2.fragment.RasporedFragment;
import com.example.projekat2.repository.raspored_db.entity.RasporedEntity;
import com.example.projekat2.viewModel.RasporedViewModel;

import java.util.List;

public class RasporedActivity extends AppCompatActivity {
    public static final String RASPORED_KEY = "RasporedActivityKey";
    private RasporedViewModel rasporedViewModel;
    private RasporedAdapter rasporedAdapter;
    private String mRasporedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.raspored_activity_layout);
        init();
    }

    private void init(){
        Intent intent = getIntent();
        if (intent != null) {
            mRasporedId = intent.getStringExtra(RASPORED_KEY);
            Toast.makeText(this, mRasporedId, Toast.LENGTH_LONG).show();
        }
        rasporedAdapter = new RasporedAdapter();
        rasporedViewModel = ViewModelProviders.of(this).get(RasporedViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.raspored_activity_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(rasporedAdapter);

        rasporedViewModel.getFilteredRasporeds1().observe(this, new Observer<List<RasporedEntity>>() {
            @Override
            public void onChanged(List<RasporedEntity> rasporedEntities) {
                rasporedAdapter.setData(rasporedEntities);
            }
        });
        rasporedViewModel.setFilter1(mRasporedId);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu_raspored, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.finish_raspored:
                Intent intent = new Intent(RasporedActivity.this, RasporedFragment.class);
                setResult(RESULT_OK, intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
