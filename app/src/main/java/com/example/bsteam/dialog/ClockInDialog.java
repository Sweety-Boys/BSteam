package com.example.bsteam.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bsteam.R;
import com.example.bsteam.entity.Task;
import com.example.bsteam.view.TabView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClockInDialog extends Dialog implements View.OnClickListener{
    @BindView(R.id.dialog_btn_edit_clock_in)
    Button btnEditClockIn;
    @BindView(R.id.dialog_btn_edit_edit)
    Button btnEditEdit;
    @BindView(R.id.dialog_btn_edit_del)
    Button btnEditDel;
    private Task mtask;
    private Button dialog_btn_edit_clock_in;
    private Button dialog_btn_edit_edit;
    private Button dialog_btn_edit_del;

    public interface clockDialogEventListener{
        public void clockDialogEvent(Task task,int state);
    }
    private clockDialogEventListener onClockDialogEventListener;
    public ClockInDialog(@NonNull  Context context,Task task,clockDialogEventListener listener) {
        super(context);
        mtask=task;
        this.onClockDialogEventListener=listener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clock_in_dialog);
        //使用注解监听
        dialog_btn_edit_clock_in = (Button)findViewById(R.id.dialog_btn_edit_clock_in);
        dialog_btn_edit_edit =  (Button)findViewById(R.id.dialog_btn_edit_edit);
        dialog_btn_edit_del =  (Button)findViewById(R.id.dialog_btn_edit_del);
        dialog_btn_edit_clock_in.setOnClickListener(this);
        dialog_btn_edit_edit.setOnClickListener(this);
        dialog_btn_edit_del.setOnClickListener(this);
//        dialog_btn_edit_clock_in.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                chooseState=1;
//                mtask.updateTaskState(1,searchName(mtask.getTaskName()));
//                dismiss();
//            }
//        });
//        dialog_btn_edit_edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                chooseState=2;
//                dismiss();
//
//            }
//        });
//        dialog_btn_edit_del.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                chooseState=3;
//                dismiss();
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        int state =0;
        switch (id){
            case R.id.dialog_btn_edit_clock_in:
                state=1;
                mtask.updateTaskState(1,searchName(mtask.getTaskName()));
                break;
            case R.id.dialog_btn_edit_edit:
                state=2;
                break;
            case R.id.dialog_btn_edit_del:
                state=3;
                break;
        }
        if(state!=0){
            onClockDialogEventListener.clockDialogEvent(mtask,state);
        }
        dismiss();
    }
//通过人物名，修改打卡图
    public int searchName(String name){
        int taskId=0;
        switch (name){
            case "学习":
                taskId= R.drawable.study_finish;
                break;
            case "吃饭":
                taskId= R.drawable.eat_finish;
                break;
            case "卫生":
                taskId=R.drawable.clean_finish;
                break;
            case "娱乐":
                taskId= R.drawable.funny_finish;
                break;
            case "睡觉":
                taskId= R.drawable.sleep_finish;
                break;
            case "跑步":
                taskId= R.drawable.running_finish;
                break;
            case "运动":
                taskId= R.drawable.sports_finish;
                break;
        }
        return taskId;
    }
    public Task getMtask(){
        return mtask;
    }

}
