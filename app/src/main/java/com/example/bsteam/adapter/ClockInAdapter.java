package com.example.bsteam.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bsteam.DatabaseOperation;
import com.example.bsteam.R;
import com.example.bsteam.activity.EditTaskActivity;
import com.example.bsteam.dialog.ClockInDialog;
import com.example.bsteam.entity.Task;
import com.example.bsteam.fragment.ClockInFragment;

import java.util.List;

public class ClockInAdapter extends RecyclerView.Adapter<ClockInAdapter.ViewHolder>{
    private Context mContext;
    private List<Task> mTaskList;
    private int mTaskView;
    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView taskImage;
        TextView taskName;
        TextView taskInfo;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            taskImage = (ImageView) view.findViewById(R.id.clockin_task_image);
            taskName = (TextView) view.findViewById(R.id.clockin_task_name);
            taskInfo = (TextView) view.findViewById(R.id.clockin_task_info);
        }
    }

    public ClockInAdapter(List<Task> taskList, int taskView) {
        mTaskList = taskList;
        mTaskView = taskView;
    }

    @Override
    public ClockInAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {  mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(mTaskView, parent, false);
        final ClockInAdapter.ViewHolder holder = new ClockInAdapter.ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = holder.getAdapterPosition();
                Task task = mTaskList.get(position);
                holder.taskImage.setImageResource(task.getTaskId());
                final ClockInDialog clockInDialog = new ClockInDialog(mContext, task, new ClockInDialog.clockDialogEventListener() {
                    @Override
                    public void clockDialogEvent(Task task, int state) {
                        clickUpdate(task,position,state);
                    }
                });
                clockInDialog.show();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ClockInAdapter.ViewHolder holder, int position) {
        Task task = mTaskList.get(position);
        holder.taskName.setText(task.getTaskName());
        holder.taskInfo.setText(task.getInfo());
        Glide.with(mContext).load(task.getTaskId()).into(holder.taskImage);
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }
    public void clickUpdate(Task task,int position,int state){
        DatabaseOperation dbo = new DatabaseOperation("task",mContext);
        switch (state){
            case 1:
                dbo.updateTask(task);//数据库中更新task
                mTaskList.set(position,task);
                break;
            case 2:
//                Toast.makeText(mContext,"1", Toast.LENGTH_SHORT).show();
//                dbo.updateTask(task);                              //更新数据库文件
//                mTaskList.set(position,task);
                Bundle bundle = new Bundle();
                bundle.putSerializable("task",task);
                Intent intent = new Intent(mContext, EditTaskActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                break;
//            case 2:
////                Toast.makeText(mContext,"2", Toast.LENGTH_SHORT).show();
//                dbo.updateTask(task);
//                mTaskList.set(position,task);
//                break;
            case 3:
//                Toast.makeText(mContext,"3", Toast.LENGTH_SHORT).show();
                dbo.deleteTask(task);
                mTaskList.remove(position);
                break;
            default:
//                Toast.makeText(mContext,"0", Toast.LENGTH_SHORT).show();
                break;
        }
        notifyDataSetChanged();    //刷新adapters
    }
    //刷新显示
    public void reflesh(List<Task> taskList){
        this.mTaskList.clear();
        this.mTaskList.addAll(taskList);
        //重点内容
        this.notifyDataSetChanged();
    }

}
