package com.spal.todolist;

import android.content.Context;
import android.content.Intent;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    Context c;
    ArrayList<Model> models;
    private int expandedPosition=-1;
    private int previousExpandedPosition = -1;

    public MyAdapter(Context c, ArrayList<Model> models) {
        this.c = c;
        this.models = models;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {

        holder.mtitle.setText(models.get(position).getTitle());
        holder.mdescr.setText(models.get(position).getDescr());
        holder.mdate.setText(models.get(position).getDate());


        final boolean isExpanded = position==expandedPosition;
        holder.hidden.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.itemView.setActivated(isExpanded);

        if (isExpanded)
            previousExpandedPosition = position;


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandedPosition = isExpanded ? -1:position;
                notifyItemChanged(previousExpandedPosition);
                notifyItemChanged(position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                View parent = (View) v.getParent();
                TextView taskTextView = (TextView) v.findViewById(R.id.task_title);
                String task = String.valueOf(taskTextView.getText());

                Intent myIntent = new Intent(c, TaskDetail.class);
                myIntent.putExtra("parentname", task); //Optional parameters
                c.startActivity(myIntent);
                return true; }});

    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
