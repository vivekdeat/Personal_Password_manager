package com.example.passwordmanager1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.viewholder> {

    ArrayList<userNamePass> arrayList;
    private clicklisterner clicklisterner;


    public RecyclerAdapter(ArrayList<userNamePass> arrayList,clicklisterner clicklisterner) {
        this.arrayList = arrayList;
        this.clicklisterner=clicklisterner;
    }

    @NonNull
    @NotNull
    @Override
    public viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.showlayout,parent,false);
        return new viewholder(view,clicklisterner);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerAdapter.viewholder holder, int position) {
        userNamePass userNamePass = arrayList.get(position);
        holder.textusername.setText(userNamePass.getSitename());
        holder.textpass.setText(userNamePass.getPassword());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        TextView textusername,textpass;
        clicklisterner clicklisterner;
        public viewholder(@NonNull @NotNull View itemView,clicklisterner clicklisterner) {
            super(itemView);
            textusername= (TextView)itemView.findViewById(R.id.userweb);
            textpass = (TextView)itemView.findViewById(R.id.userpass);
            this.clicklisterner = clicklisterner;
            itemView.setOnClickListener(this::onClick);
            itemView.setOnLongClickListener(this::onLongClick);

        }


        @Override
        public void onClick(View v) {
            clicklisterner.onclick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            clicklisterner.onlongclick(getAdapterPosition());
            return false;
        }
    }
    public interface clicklisterner{
        public void onclick(int position);
        public void onlongclick(int position);

    }
}

