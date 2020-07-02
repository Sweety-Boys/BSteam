package com.example.bsteam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bsteam.DatabaseOperation;
import com.example.bsteam.R;
import com.example.bsteam.dialog.ChooseDateDialog;
import com.example.bsteam.dialog.ChooseTimeDialog;
import com.example.bsteam.entity.Task;
import com.example.bsteam.util.DateUtil;
import com.example.bsteam.util.ExitApplication;

public class EditTaskActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        ExitApplication.getInstance().addActivity(this);

        final Task task = (Task)getIntent().getSerializableExtra("task");
//        Toast.makeText(EditTaskActivity.this,task.getTaskName(),Toast.LENGTH_LONG).show();
        TextView textView_title = findViewById(R.id.edit_task_title);
        ImageView imageView_task = findViewById(R.id.edit_task_image);

//        Button btStartTime = findViewById(R.id.edit_task_start_time);
//        Button btEndTime = findViewById(R.id.edit_task_end_time);
        final TextView textView_startTime_date = findViewById(R.id.edit_task_start_time_date);
        final TextView textView_startTime_time = findViewById(R.id.edit_task_start_time_time);
        final TextView textView_EndTime_date = findViewById(R.id.edit_task_end_time_date);
        final TextView textView_EndTime_time = findViewById(R.id.edit_task_end_time_time);

        final EditText editText_info = findViewById(R.id.edit_task_info);
        Button btEditConfirm = findViewById(R.id.edit_task_confirm);
        CheckBox cb_study = findViewById(R.id.edit_task_cb_study);
        CheckBox cb_eat = findViewById(R.id.edit_task_cb_eat);
        CheckBox cb_clean = findViewById(R.id.edit_task_cb_clean);
        CheckBox cb_funny = findViewById(R.id.edit_task_cb_funny);
        CheckBox cb_sleep = findViewById(R.id.edit_task_cb_sleep);
        CheckBox cb_running = findViewById(R.id.edit_task_cb_running);
        CheckBox cb_sports = findViewById(R.id.edit_task_cb_sports);
        textView_title.setText(task.getTaskName());
        imageView_task.setImageResource(task.getTaskId());
//        Toast.makeText(EditTaskActivity.this,task.getStartDate()+","+task.getEndDate(),Toast.LENGTH_LONG).show();

        textView_startTime_date.setText(DateUtil.getStrDateFromStr(task.getStartDate()));
        textView_startTime_time.setText(DateUtil.getStrTimeFromStr(task.getStartDate()));
        textView_EndTime_date.setText(DateUtil.getStrDateFromStr(task.getEndDate()));
        textView_EndTime_time.setText(DateUtil.getStrTimeFromStr(task.getEndDate()));
        editText_info.setText(task.getInfo());
        switch (task.getTaskName()){
            case "学习":
                cb_study.setChecked(true);
                task.setTaskId(R.drawable.study_normal);
                break;
            case "吃饭":
                cb_eat.setChecked(true);
                task.setTaskId(R.drawable.eat_normal);
                break;
            case "卫生":
                cb_clean.setChecked(true);
                task.setTaskId(R.drawable.clean_normal);
                break;
            case "娱乐":
                cb_funny.setChecked(true);
                task.setTaskId(R.drawable.funny_normal);
                break;
            case "睡觉":
                cb_sleep.setChecked(true);
                task.setTaskId(R.drawable.sleep_normal);
                break;
            case "跑步":
                cb_running.setChecked(true);
                task.setTaskId(R.drawable.running_noraml);
                break;
            case "运动":
                cb_sports.setChecked(true);
                task.setTaskId(R.drawable.sports_normal);
                break;
        }

        textView_startTime_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ChooseDateDialog chooseDateDialog = new ChooseDateDialog(EditTaskActivity.this,new ChooseDateDialog.chooseDateDialogEventListener(){
                    @Override
                    public void chooseDateDialogEvent(String strDate) {
                        textView_startTime_date.setText(strDate);
                    }
                });
                chooseDateDialog.show();
            }
        });
        textView_EndTime_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ChooseDateDialog chooseDateDialog = new ChooseDateDialog(EditTaskActivity.this,new ChooseDateDialog.chooseDateDialogEventListener(){
                    @Override
                    public void chooseDateDialogEvent(String strDate) {
                        textView_EndTime_date.setText(strDate);
                    }
                });
                chooseDateDialog.show();
            }
        });
        textView_startTime_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ChooseTimeDialog chooseTimeDialog = new ChooseTimeDialog(EditTaskActivity.this,new ChooseTimeDialog.chooseTimeDialogEventListener(){
                    @Override
                    public void chooseTimeDialogEvent(String strDate) {
                        textView_startTime_time.setText(strDate);
                        Toast.makeText(EditTaskActivity.this,strDate,Toast.LENGTH_LONG).show();
                    }
                });
                chooseTimeDialog.show();
            }
        });
        textView_EndTime_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ChooseTimeDialog chooseTimeDialog = new ChooseTimeDialog(EditTaskActivity.this,new ChooseTimeDialog.chooseTimeDialogEventListener(){
                    @Override
                    public void chooseTimeDialogEvent(String strDate) {
                        textView_EndTime_time.setText(strDate);
                    }
                });
                chooseTimeDialog.show();
            }
        });
        btEditConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textView_startTime_date.getText().toString().equals("开始日期")||
                        textView_startTime_time.getText().toString().equals("时间")||
                        textView_EndTime_date.getText().toString().equals("结束日期")||
                        textView_EndTime_time.getText().toString().equals("时间")){//如果有时间或日期选择器未选择，则提醒
                    Toast.makeText(EditTaskActivity.this,"时间未选中！",Toast.LENGTH_LONG).show();
                }else{
                    task.setEndDate(textView_EndTime_date.getText()+" "+textView_EndTime_time.getText());
                    task.setStartDate(textView_startTime_date.getText()+" "+textView_startTime_time.getText());
                    task.setEndDate(textView_EndTime_date.getText()+" "+textView_EndTime_time.getText());
                    task.setInfo(editText_info.getText().toString());
                    String startDate = task.getStartDate();
                    String endDate = task.getEndDate();
                    if(DateUtil.comepareStr(startDate,endDate)){
                        //更新数据库
                        DatabaseOperation dbo = new DatabaseOperation("task",EditTaskActivity.this);
                        task.setTaskState(0);
                        dbo.updateTask(task);
//                        //销毁上一个活动
//                        if(FirstActivity.instance!=null){
//                            FirstActivity.instance.finish();
//                        }
                        //跳转回FirstActivity
                        Intent intent = new Intent(EditTaskActivity.this,FirstActivity.class);
                        intent.putExtra("user_id",task.getUserId());
                        startActivity(intent);
//                        finish();//销毁当前活动
                    }else{
                        Toast.makeText(EditTaskActivity.this,"任务开始时间晚于结束时间！",Toast.LENGTH_LONG).show();
                        task.setEndDate("");//结束时间置空
                        textView_EndTime_date.setText(DateUtil.getStrDateFromStr(DateUtil.getCurDate()));
                        textView_EndTime_time.setText(DateUtil.getStrTimeFromStr(DateUtil.getCurDate()));
                    }
                }
            }
        });
    }
}