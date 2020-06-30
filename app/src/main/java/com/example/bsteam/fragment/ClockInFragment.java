package com.example.bsteam.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bsteam.DatabaseHelper;
import com.example.bsteam.DatabaseOperation;
import com.example.bsteam.R;
import com.example.bsteam.adapter.ClockInAdapter;
import com.example.bsteam.entity.Task;
import com.example.bsteam.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ClockInFragment extends Fragment {

    public static final String USER_ID = "user_id";
    private String mUserId;
    DatabaseHelper databaseHelper;
    private String startDate = DateUtil.getCurDate();
    private String endDate = DateUtil.addDateMinut(startDate,2);
    TextView tv;
    //0代表未打卡
    private Task[] tasks = {new Task(R.drawable.study_normal,"学习", startDate,endDate,mUserId,"我爱学习",0),
            new Task(R.drawable.eat_normal,"吃饭", startDate,endDate,mUserId,"我爱吃米饭",0),
            new Task(R.drawable.clean_normal,"卫生", startDate,endDate,mUserId,"我爱打扫卫生",0),
            new Task(R.drawable.funny_normal,"娱乐", startDate,endDate,mUserId,"我喜欢玩和平精英",0),
            new Task(R.drawable.sleep_normal,"睡觉", startDate,endDate,mUserId,"我是小懒虫",0),
            new Task(R.drawable.running_noraml,"跑步", startDate,endDate,mUserId,"我是长跑冠军",0),
            new Task(R.drawable.sport_normal,"运动", startDate,endDate,mUserId,"我是游泳健将",0)};
    private List<Task> taskList = new ArrayList<>();
    private ClockInAdapter adapter;

    public static ClockInFragment newInstance(String userId) {
        Bundle args = new Bundle();
        args.putString(USER_ID, userId);
        ClockInFragment fragment = new ClockInFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mUserId = arguments.getString(USER_ID, "");
        }
    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.clockin_fragment_layout, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTasks();
        tv = view.findViewById(R.id.clockin_fragment_tv);
        if(taskList.size()==0){
            tv.setText("你还没有任务,快去添加一个吧~");
        }else{
            tv.setText("");
        }
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.clockin_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(),1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ClockInAdapter(taskList,R.layout.clockin_item);
        recyclerView.setAdapter(adapter);
    }

    private void initTasks() {
        taskList.clear();
//        for(int i = 0;i < tasks.length;i ++){//开始userId未赋值，这里赋值
//            tasks[i].setUserId(mUserId);
//        }
//        taskList.addAll(Arrays.asList(tasks));
        DatabaseOperation dbo = new DatabaseOperation("task",getActivity());//调取数据库操作界面
        taskList=dbo.initList(mUserId);                                                //获取数据库中指定userid的所有task
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        initTasks();
        adapter.reflesh(taskList);
    }

}
