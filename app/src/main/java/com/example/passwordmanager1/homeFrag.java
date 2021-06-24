package com.example.passwordmanager1;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.icu.text.SymbolTable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class homeFrag extends Fragment implements RecyclerAdapter.clicklisterner {
    SharedPreferences sharedPreferences;
    String USERNAME_PASSWORD = "username_password";
    String SHARED_NAME = "com.example.passwordmanager1";
    View xv;
    Context context;
    String returned_string;
    ArrayList<userNamePass> dataUser;
    RecyclerAdapter recyclerAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        xv = inflater.inflate(R.layout.fragment_home,container,false);
        context = container.getContext();

        loadDataonView();
        RecyclerView recyclerView = xv.findViewById(R.id.recyclerView);
        recyclerAdapter = new RecyclerAdapter(dataUser,homeFrag.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(recyclerAdapter);


        FloatingActionButton ftb = xv.findViewById(R.id.floating_btn);
        ftb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                View view = inflater.inflate(R.layout.adduserpass,null);
                Button savebtn = (Button)view.findViewById(R.id.savepassword);
                Button cancelbtn = (Button)view.findViewById(R.id.cancelpass);
                EditText addweb = (EditText)view.findViewById(R.id.web_name);
                EditText addwebpass = (EditText)view.findViewById(R.id.web_pass);
                savebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String webs = addweb.getText().toString();
                        String webp = addwebpass.getText().toString();
                        if(webs.length()==0||webp.length()==0)
                            Snackbar.make(xv,"Please fill All details",Snackbar.LENGTH_SHORT).show();
                        else {
                            updatestorage(webs, webp,0);
                            recyclerAdapter.notifyDataSetChanged();
                            alertDialog.cancel();
                            Snackbar.make(xv,"Added",Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
                cancelbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });
                alertDialog.setView(view);
                alertDialog.show();
            }
        });

        return xv;
    }

    private void loadDataonView() {

        sharedPreferences = context.getSharedPreferences(SHARED_NAME,MODE_PRIVATE);
        returned_string = sharedPreferences.getString(USERNAME_PASSWORD,null);
        dataUser = new ArrayList<userNamePass>();
//        System.out.println("Homefrag"+returned_string.length());
        if(returned_string!=null&&returned_string.length()!=0)
        {
            System.out.println("X+"+returned_string.length());
            String[] data = returned_string.split(",");
            for(int i=0;i<data.length;i+=2)
            {
                dataUser.add(new userNamePass(data[i],data[i+1]));
            }
        }

    }
    private void updatestorage(String username,String Pass,int i)
    {
        sharedPreferences = context.getSharedPreferences(SHARED_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(i==0)
        {
            dataUser.add(new userNamePass(username,Pass));
            if(returned_string==null)
            returned_string = username+","+Pass;
            else returned_string = returned_string+","+username+","+Pass;
        }
        else{
            returned_string = null;
            for(int k=0;k<dataUser.size();k++)
            {
                returned_string= returned_string+dataUser.get(k).getSitename()+","+dataUser.get(k).getPassword();
                if(k!=dataUser.size()-1)
                    returned_string+=",";
            }
        }
        editor.putString(USERNAME_PASSWORD,returned_string);
        editor.commit();
    }

    @Override
    public void onclick(int position) {
        Snackbar.make(xv,"Copied",Snackbar.LENGTH_LONG).show();
        ClipboardManager clipboardManager = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText(dataUser.get(position).getSitename(),dataUser.get(position).getPassword());
        clipboardManager.setPrimaryClip(data);
    }

    @Override
    public void onlongclick(int position) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Delete")
                .setMessage("Do you really want to delete")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataUser.remove(position);
                        recyclerAdapter.notifyDataSetChanged();
                        updatestorage("","",1);
                        Snackbar.make(xv,"Deleted",Snackbar.LENGTH_LONG).show();
                        dialog.cancel();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }
}
