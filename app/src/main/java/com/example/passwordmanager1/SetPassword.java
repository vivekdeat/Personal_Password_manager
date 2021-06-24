package com.example.passwordmanager1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class SetPassword extends AppCompatActivity {
    TextInputLayout textInputLayout;
    AutoCompleteTextView autoCompleteTextView;
    EditText user_name,user_pass,user_recovery;
    Button btn_setPass,btn_setcancel;

    SharedPreferences sharedPreferences;
    String SHARED_NAME = "com.example.passwordmanager1";
    String USER_NAME = "username";
    String USER_PASSWORD = "user_password";
    String USER_OPTION = "user_option";
    String USER_RECOVERY = "user_recover";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        ArrayList<String> recover = new ArrayList<>();
        recover.add("What is your mother's maiden name?");
        recover.add("What is the name of your first pet?");
        recover.add("What was your first car?");
        recover.add("What elementary school did you attend?");
        recover.add("What is the name of the town where you were born?");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,recover);
        autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.spinner_recovery);
        autoCompleteTextView.setAdapter(arrayAdapter);
        user_name=(EditText)findViewById(R.id.Edittext_Username);
        user_pass=(EditText)findViewById(R.id.Edittext_Userpassword);
        user_recovery=(EditText)findViewById(R.id.Edittext_Userrecovery);
        textInputLayout = (TextInputLayout)findViewById(R.id.text_spinner_name);
        autoCompleteTextView.setKeyListener(null);
        autoCompleteTextView.setText(autoCompleteTextView.getAdapter().getItem(0).toString(),false);

        btn_setPass = (Button)findViewById(R.id.btn_setPassword);
        btn_setcancel = (Button)findViewById(R.id.btn_setcancel);
        btn_setPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name =user_name.getText().toString();
                String password =user_pass.getText().toString();
                String recoveryans = user_recovery.getText().toString();
                if(name.length()==0||password.length()==0||recoveryans.length()==0)
                {
                    Snackbar.make(findViewById(android.R.id.content),"Please fill all the details",Snackbar.LENGTH_LONG).show();
                }
                else{
                    userinfo.setUsername(user_name.getText().toString());
                    userinfo.setPassword(user_pass.getText().toString());
                    userinfo.setRecovery_ans(user_recovery.getText().toString());
                    userinfo.setRecovery_option(autoCompleteTextView.getText().toString());
                    updateShared();
                    Intent intent =  new Intent(SetPassword.this,main_layout.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        btn_setcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(SetPassword.this)
                        .setMessage("Do you really want to exit")
                        .setTitle("Alert")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).create();
                alertDialog.show();
            }
        });




    }

    private void updateShared() {
        sharedPreferences = getSharedPreferences(SHARED_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_NAME,userinfo.getUsername());
        editor.putString(USER_PASSWORD,userinfo.getPassword());
        editor.putString(USER_OPTION,userinfo.getRecovery_option());
        editor.putString(USER_RECOVERY,userinfo.getRecovery_ans());
        editor.commit();
    }
}