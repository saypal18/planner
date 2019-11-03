package com.spal.todolist;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyHolder extends RecyclerView.ViewHolder {

    TextView mtitle;
    TextView mdescr;
    TextView mdate;
    //TextView mchildren;
    View hidden;

    public MyHolder(@NonNull View itemView) {
        super(itemView);
        this.mtitle = itemView.findViewById(R.id.task_title);
        this.mdescr = itemView.findViewById(R.id.task_descr);
        this.mdate = itemView.findViewById(R.id.task_date);
        //this.mchildren = itemView.findViewById(R.id.task_children);
        this.hidden = itemView.findViewById(R.id.llExpandArea);
    }
}
