package com.example.projekat2.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.projekat2.R;
import com.example.projekat2.adapter.PagerAdapter;
import com.example.projekat2.fragment.RasporedFragment;
import com.example.projekat2.repository.network.api.Resource;
import com.example.projekat2.repository.raspored_db.entity.RasporedEntity;
import com.example.projekat2.viewModel.MainViewModel;
import com.example.projekat2.viewModel.RasporedViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RasporedViewModel viewModel;
    private MainViewModel mainViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initViewModel();
    }
    private void init(){
        ViewPager viewPager = findViewById(R.id.vp_main);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initViewModel(){
        viewModel = ViewModelProviders.of(this).get(RasporedViewModel.class);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

    }

    private void showToast(boolean isSuccess) {
        String message = isSuccess ? getString(R.string.fetch_success_message) : getString(R.string.fetch_fail_message);
        Toast.makeText(MainActivity.this ,message, Toast.LENGTH_LONG).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.logout:
                mainViewModel.logOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
