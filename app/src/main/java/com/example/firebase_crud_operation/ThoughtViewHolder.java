package com.example.firebase_crud_operation;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ThoughtViewHolder extends RecyclerView.ViewHolder {

    public TextView ThoughtTV , AdminTV;
    public ThoughtViewHolder(@NonNull View itemView) {
        super(itemView);

        ThoughtTV=itemView.findViewById(R.id.thoughtTV);
        AdminTV=itemView.findViewById(R.id.AdminTV);
    }
}
