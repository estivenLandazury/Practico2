package com.example.firebase;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterTemplate extends RecyclerView.Adapter<AdapterTemplate.CustomViewHolder> {

    ArrayList<Materia> data;

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;

        public CustomViewHolder(LinearLayout v) {
            super(v);
            root = v;
        }
    }

    public AdapterTemplate() {
        data = new ArrayList<>();
    }


    /***cear renglón*/
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.renglon_materia, parent, false);
        CustomViewHolder vh = new CustomViewHolder(v);

        return vh;
    }

    /***llenar renglón*/
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        ((TextView) holder.root.findViewById(R.id.txv_nombre_mat)).setText(data.get(position).getNombre_materia());
        ((TextView) holder.root.findViewById(R.id.txv_nota1)).setText(data.get(position).getNota1());
        ((TextView) holder.root.findViewById(R.id.txv_nota2)).setText(data.get(position).getNota2());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void agregarMateria(Materia mat){

        data.add(mat);
        notifyDataSetChanged();
    }


}

