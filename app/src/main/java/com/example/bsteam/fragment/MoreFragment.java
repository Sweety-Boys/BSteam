package com.example.bsteam.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bsteam.R;
import com.example.bsteam.activity.EditPassWord;
import com.example.bsteam.activity.EditTaskActivity;
import com.example.bsteam.util.ExitApplication;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class MoreFragment extends Fragment {
    private PieChart mHistorychart;

    public static final String USER_ID = "user_id";
    private String mUserId;

    public static MoreFragment newInstance(String userId) {
        Bundle args = new Bundle();
        args.putString(USER_ID, userId);
        MoreFragment fragment = new MoreFragment();
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
        return inflater.inflate(R.layout.more_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView titleView = view.findViewById(R.id.more_user_id);
        titleView.setText(mUserId);
        mHistorychart = (PieChart) view.findViewById(R.id.history_chart);
        ArrayList<Entry> Time = new ArrayList<Entry>();

        Time.add(new Entry(8, 0));
        Time.add(new Entry(1, 1));
        Time.add(new Entry(1, 2));
        Time.add(new Entry(5, 3));
        Time.add(new Entry(6, 4));
        Time.add(new Entry(2, 5));
        Time.add(new Entry(1, 6));

        PieDataSet dataSet = new PieDataSet(Time, "Task");

        ArrayList<String> Task = new ArrayList<>();

        Task.add("学习");
        Task.add("吃饭");
        Task.add("卫生");
        Task.add("娱乐");
        Task.add("运动");
        Task.add("睡觉");
        Task.add("活动");
        PieData data = new PieData(Task, dataSet);
        mHistorychart.setData(data);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        data.setValueTextSize(20f);
        mHistorychart.animateY(500, Easing.EasingOption.EaseInOutQuad);
        mHistorychart.animateXY(5000, 5000);

        Button exitButton = view.findViewById(R.id.btn_exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getActivity().finish();
//                System.exit(0);
                ExitApplication.getInstance().exit(getActivity());
            }
        });
        Button editButton = view.findViewById(R.id.btn_edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), EditPassWord.class);
                intent.putExtra("user_id",mUserId);
                startActivity(intent);
            }
        });
    }
}
