package com.example.firebase_crud_operation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ThoughtAdapter extends RecyclerView.Adapter<ThoughtViewHolder>{
    private List<Thought> thoughtList;
    public ThoughtAdapter(List<Thought> thoughtList) {
        this.thoughtList = thoughtList;
    }


    @Override
    public ThoughtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.thought_item, parent, false);
        return new ThoughtViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThoughtViewHolder holder, int position) {
        Thought thought = thoughtList.get(position);
        holder.ThoughtTV.setText(thought.getThought());
        holder.AdminTV.setText(thought.getAdmin());
    }

    @Override
    public int getItemCount() {
        return thoughtList.size();
    }
}
