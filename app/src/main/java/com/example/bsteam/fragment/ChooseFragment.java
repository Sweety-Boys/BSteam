package com.example.bsteam.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bsteam.R;
import com.example.bsteam.adapter.ChooseAdapter;
import com.example.bsteam.entity.Task;
import com.example.bsteam.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChooseFragment extends Fragment {
    public static final String USER_ID = "user_id";
    private String mUserId;
    private String startDate = DateUtil.getCurDate();
    private String endDate = DateUtil.addDateMinut(startDate,2);

    //0代表未打卡,此处用静态
    private Task[] tasks = {new Task(R.drawable.study_finish,"学习", startDate,endDate,mUserId,"",0),//我爱学习
            new Task(R.drawable.eat_finish,"吃饭", startDate,endDate,mUserId,"",0),//我爱吃米饭
            new Task(R.drawable.clean_finish,"卫生", startDate,endDate,mUserId,"",0),//我爱打扫卫生
            new Task(R.drawable.funny_finish,"娱乐", startDate,endDate,mUserId,"",0),//我喜欢玩和平精英
            new Task(R.drawable.sleep_finish,"睡觉", startDate,endDate,mUserId,"",0),//我是小懒虫
            new Task(R.drawable.running_finish,"跑步", startDate,endDate,mUserId,"",0),//我是长跑冠军
            new Task(R.drawable.sports_finish,"运动", startDate,endDate,mUserId,"",0)};//我是游泳健将
    private List<Task> taskList = new ArrayList<>();
    private ChooseAdapter adapter;

    public static ChooseFragment newInstance(String userId) {
        Bundle args = new Bundle();
        args.putString(USER_ID, userId);
        ChooseFragment fragment = new ChooseFragment();
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.choose_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTasks();
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.choose_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(),1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ChooseAdapter(taskList,R.layout.choose_item);
        recyclerView.setAdapter(adapter);
    }

    private void initTasks() {
        taskList.clear();
        for(int i = 0;i < tasks.length;i ++){//开始userId未赋值，这里赋值
            tasks[i].setUserId(mUserId);
        }
        taskList.addAll(Arrays.asList(tasks));
    }
}
