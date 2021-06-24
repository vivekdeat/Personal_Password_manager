package com.example.passwordmanager1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    String SHARED_NAME = "com.example.passwordmanager1";
    String USER_NAME = "username";
    String USER_PASSWORD = "user_password";
    String USER_OPTION = "user_option";
    String USER_RECOVERY = "user_recover";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView)findViewById(R.id.image_lock);
        TextView textView_name = (TextView)findViewById(R.id.start_text);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
        Animation fromBottom = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.from_down);
        Animation fillin = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fillin);
        imageView.startAnimation(animation);
        textView_name.startAnimation(fromBottom);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if(checkFirstTime())
                {
                    intent=new Intent(MainActivity.this,SetPassword.class);
                    startActivity(intent);
                }
                else {
                    intent = new Intent(MainActivity.this,login.class);
                    startActivity(intent);
                }
                finish();

            }
        },2000);
    }
    public boolean checkFirstTime()
    {
        sharedPreferences = getSharedPreferences(SHARED_NAME,MODE_PRIVATE);
        String password = sharedPreferences.getString(USER_PASSWORD,null);
        if(password!=null)
        {
            String name  = sharedPreferences.getString(USER_NAME,null);
            String recoveryOption  = sharedPreferences.getString(USER_OPTION,null);
            String recovery_ans = sharedPreferences.getString(USER_RECOVERY,null);
            userinfo.setPassword(password);
            userinfo.setRecovery_ans(recovery_ans);
            userinfo.setRecovery_option(recoveryOption);
            userinfo.setUsername(name);
            return false;
        }
        return true;
    }
}