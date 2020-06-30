package com.example.bsteam.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bsteam.R;
import com.example.bsteam.activity.EditTaskActivity;
import com.example.bsteam.entity.Task;

import java.util.List;

public class ChooseAdapter extends RecyclerView.Adapter<ChooseAdapter.ViewHolder>{
    private Context mContext;

    private List<Task> mTaskList;
    private int mTaskView;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView taskImage;
        TextView taskName;
        ImageView taskAddImage;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            taskImage = (ImageView) view.findViewById(R.id.choose_item_image);
            taskName = (TextView) view.findViewById(R.id.choose_item_name);
            taskAddImage = (ImageView) view.findViewById(R.id.choose_add_task);
        }
    }

    public ChooseAdapter(List<Task> taskList, int taskView) {
        mTaskList = taskList;
        mTaskView = taskView;
    }

    @Override
    public ChooseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(mTaskView, parent, false);
        final ChooseAdapter.ViewHolder holder = new ChooseAdapter.ViewHolder(view);
        holder.taskAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Task task = mTaskList.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("task",task);
                Intent intent = new Intent(mContext, EditTaskActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ChooseAdapter.ViewHolder holder, int position) {
        Task task = mTaskList.get(position);
        holder.taskName.setText(task.getTaskName());
        Glide.with(mContext).load(task.getTaskId()).into(holder.taskImage);
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }
}
