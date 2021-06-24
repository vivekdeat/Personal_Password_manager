package com.example.passwordmanager1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class login extends AppCompatActivity {

    TextView textView_username,textView_forget;
    EditText pass;
    Button btn_login,btn_cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        textView_username=(TextView)findViewById(R.id.text_view_name);
        textView_forget=(TextView)findViewById(R.id.text_view_forget);
        pass=(EditText)findViewById(R.id.login_pass);
        btn_login=(Button)findViewById(R.id.btn_login);
        btn_cancel=(Button)findViewById(R.id.btn_login_cancel);
        textView_username.setText("Hii, "+userinfo.getUsername());



        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String x = pass.getText().toString();
                if(x.equals(userinfo.getPassword()))
                {

                    Intent intent = new Intent(login.this,main_layout.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(login.this,"Wrong Password",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textView_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = LayoutInflater.from(login.this);
                View view = inflater.inflate(R.layout.recovery,null);
                AlertDialog dialog = new AlertDialog.Builder(login.this).create();
                Button recovery_pass = (Button)view.findViewById(R.id.btn_show_pass);
                Button recover_cancel = (Button)view.findViewById(R.id.btn_show_cancel);
                EditText recover_ans = (EditText)view.findViewById(R.id.Edittext_recovery);
                TextView showpass = (TextView) view.findViewById(R.id.showpass);
                AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)view.findViewById(R.id.recovery_dropdown);
                ArrayList<String> recover = new ArrayList<>();
                recover.add("What is your mother's maiden name?");
                recover.add("What is the name of your first pet?");
                recover.add("What was your first car?");
                recover.add("What elementary school did you attend?");
                recover.add("What is the name of the town where you were born?");
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(login.this,R.layout.support_simple_spinner_dropdown_item,recover);
                autoCompleteTextView.setAdapter(arrayAdapter);
                autoCompleteTextView.setText(autoCompleteTextView.getAdapter().getItem(0).toString(),false);
                autoCompleteTextView.setKeyListener(null);
                recover_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                recovery_pass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String option = autoCompleteTextView.getText().toString();
                        String ans = recover_ans.getText().toString();
                        System.out.println(userinfo.getRecovery_option()+"/n"+userinfo.getRecovery_ans());
                        if(option.equals(userinfo.getRecovery_option())&&ans.equals(userinfo.getRecovery_ans())){
                            showpass.setText("Your password is "+userinfo.getPassword());
                        }
                        else{
                            showpass.setText("Wrong recovery option or answer entered");
                        }
                    }
                });
                dialog.setView(view);
                dialog.show();

            }
        });

    }
}