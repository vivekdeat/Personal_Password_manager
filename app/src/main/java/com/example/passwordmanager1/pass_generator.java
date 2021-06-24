package com.example.passwordmanager1;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class pass_generator extends Fragment {

    Context context;
    SharedPreferences sharedPreferences;
    String USERNAME_PASSWORD = "username_password";
    String SHARED_NAME = "com.example.passwordmanager1";



    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.app_pass_gener,container,false);
        context = container.getContext();
        ArrayList<Integer> sizes = new ArrayList<>();
        for(int i=1;i<257;i++)
        {
            sizes.add(i);
        }
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)v.findViewById(R.id.wordlength);
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(context,R.layout.support_simple_spinner_dropdown_item,sizes);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setKeyListener(null);
        autoCompleteTextView.setText(autoCompleteTextView.getAdapter().getItem(0).toString(),false);
        Button generbtn = (Button)v.findViewById(R.id.btn_gener);
        Button copybtn = (Button)v.findViewById(R.id.btn_copy);
        Button sharebtn = (Button)v.findViewById(R.id.btn_share);
        Button savepassbtn = (Button)v.findViewById(R.id.btn_save);
        TextView showpass = (TextView)v.findViewById(R.id.password_gener);
        generbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int leng = Integer.parseInt(autoCompleteTextView.getText().toString());
                String[] setsLetter = {"0123456789","ABCDEFGHIJKLMNOPQRSTUVWXZ","abcdefghijklmnopqrstuvwxyz","£$&[]@#^-_!?"};
                int categ = 4;
                String password = "";
                for(int i=0;i<leng;i++)
                {
                    int z = (int)(Math.random()*100)%4;
                    switch (z)
                    {
                        case 0:
                            z = (int) (Math.random()*100)%(setsLetter[0].length());
                            password+=(setsLetter[0].charAt(z));
                            break;
                        case 1:
                            z = (int) (Math.random()*100)%(setsLetter[1].length());
                            password+=(setsLetter[1].charAt(z));
                            break;
                        case 2:
                            z = (int) (Math.random()*100)%(setsLetter[2].length());
                            password+=(setsLetter[2].charAt(z));
                            break;
                        case 3:
                            z = ((int) (Math.random()*100))%(setsLetter[3].length());
                            password+=(setsLetter[3].charAt(z));
                            break;
                    }
                }
                showpass.setText(password);
            }
        });
        copybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData data = ClipData.newPlainText("Password",showpass.getText().toString());
                clipboardManager.setPrimaryClip(data);

            }
        });
        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,showpass.getText());
                intent.setType("text/plain");
                startActivity(intent);

            }
        });

//

        View alertview = inflater.inflate(R.layout.alertsave,null);
        AlertDialog savedialog = new AlertDialog.Builder(context).create();
        Button alertsavebtn = (Button)alertview.findViewById(R.id.btnalertsavepass);
        Button alertcancelbtn = (Button)alertview.findViewById(R.id.btnalertcancel);
        EditText alersaveweb = (EditText)alertview.findViewById(R.id.alert_Edittext_save_pass);
        TextView textView_alertpass = (TextView)alertview.findViewById(R.id.textView_savepass);

        alertsavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String webname = alersaveweb.getText().toString();

                if(webname.length()==0)
                    Toast.makeText(context,"Please enter website name",Toast.LENGTH_SHORT).show();
                else{
                    sharedPreferences = context.getSharedPreferences(SHARED_NAME,Context.MODE_PRIVATE);
                    String str = sharedPreferences.getString(USERNAME_PASSWORD,"");
//                    System.out.println("Passgenr"+str.length());
                    if(str==null||str.length()==0)
                        str=webname+","+showpass.getText();
                    else str =str+","+webname+","+showpass.getText();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(USERNAME_PASSWORD,str);
                    editor.commit();
                    Toast.makeText(context,"Saved",Toast.LENGTH_SHORT).show();
                    savedialog.cancel();
                }
            }
        });

        alertcancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                savedialog.cancel();
            }
        });


//
        savepassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_alertpass.setText(showpass.getText());
                savedialog.setView(alertview);
                savedialog.show();
            }
        });
        return v;
    }

}
