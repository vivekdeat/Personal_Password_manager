package com.example.passwordmanager1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

public class main_layout extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    String USERNAME_PASSWORD = "username_recover";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);

        getFragmentManager().beginTransaction().replace(R.id.frame_layout,new homeFrag()).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottom_nav);
    }




    private BottomNavigationView.OnNavigationItemSelectedListener bottom_nav = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
            Fragment selectfrag = new Fragment();
            switch (item.getItemId()){
                case R.id.home_app:
                    selectfrag=new homeFrag();
                    break;
                case R.id.pass_gener:
                    selectfrag=new pass_generator();
                    break;
                case R.id.app_setting:
                    selectfrag=new app_setting();
                    break;
            }
            getFragmentManager().beginTransaction().replace(R.id.frame_layout,selectfrag).commit();
            return true;
        }
    };



}