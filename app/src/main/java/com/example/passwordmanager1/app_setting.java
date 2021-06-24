package com.example.passwordmanager1;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class app_setting extends Fragment {
    SharedPreferences sharedPreferences;
    String SHARED_NAME = "com.example.passwordmanager1";
    String USER_NAME = "username";
    String USER_PASSWORD = "user_password";
    String USER_OPTION = "user_option";
    String USER_RECOVERY = "user_recover";

    Context context;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View xv = inflater.inflate(R.layout.app_setting,null);
        context = container.getContext();
        TextView textView_name = (TextView)xv.findViewById(R.id.text_view_setting_user);
        textView_name.setText("Hello "+userinfo.getUsername());
        Button btn_change_pass = (Button)xv.findViewById(R.id.btn_setting_change_pass);

        View view = inflater.inflate(R.layout.alert_change_password,null);
        AlertDialog change_pass_alert = new AlertDialog.Builder(context).create();
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)view.findViewById(R.id.spinner_alert_change_password);
        ArrayList<String> recover = new ArrayList<>();
        recover.add("What is your mother's maiden name?");
        recover.add("What is the name of your first pet?");
        recover.add("What was your first car?");
        recover.add("What elementary school did you attend?");
        recover.add("What is the name of the town where you were born?");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item,recover);
        autoCompleteTextView.setKeyListener(null);
        autoCompleteTextView.setAdapter(arrayAdapter);

        EditText new_pass = (EditText)view.findViewById(R.id.Edit_text_alert_change_password);
        EditText new_recovery = (EditText)view.findViewById(R.id.Edit_text_alert_change_recoveryoption);

        Button btn_alert_changePass = (Button)view.findViewById(R.id.btn_alertchange_change_pass);
        Button btn_alert_cancel = (Button)view.findViewById(R.id.btn_alertchange_cancel);
        autoCompleteTextView.setText(autoCompleteTextView.getAdapter().getItem(0).toString(),false);
        btn_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_pass_alert.setView(view);
                change_pass_alert.show();
            }
        });

        btn_alert_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_pass_alert.cancel();
            }
        });
        btn_alert_changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = new_pass.getText().toString();
                String new_recover = new_recovery.getText().toString();
                String new_option = "";
                if(password.length()!=0&&new_recover.length()!=0)
                {
                    userinfo.setPassword(password);

                    new_option = autoCompleteTextView.getText().toString();
                    userinfo.setRecovery_option(new_option);
                    userinfo.setRecovery_ans(new_recover);
                }
                else if(new_recover.length()==0)
                {
                    userinfo.setPassword(password);
                }
                else if(password.length()==0){

                    new_option = autoCompleteTextView.getText().toString();
                    userinfo.setRecovery_option(new_option);
                    userinfo.setRecovery_ans(new_recover);
                }
                System.out.println(userinfo.getRecovery_option()+"\n"+userinfo.getRecovery_ans());
                sharedPreferences = context.getSharedPreferences(SHARED_NAME,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(USER_PASSWORD,userinfo.getPassword());
                editor.putString(USER_OPTION,userinfo.getRecovery_option());
                editor.putString(USER_RECOVERY,userinfo.getRecovery_ans());
                editor.commit();
                Snackbar.make(xv,"Changed",Snackbar.LENGTH_SHORT).show();
                change_pass_alert.cancel();

            }
        });

        return xv;
    }
}
