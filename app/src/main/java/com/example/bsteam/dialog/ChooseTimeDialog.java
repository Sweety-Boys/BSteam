package com.example.bsteam.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.bsteam.R;

public class ChooseTimeDialog extends Dialog implements View.OnClickListener{

    public interface chooseTimeDialogEventListener {
        public void chooseTimeDialogEvent(String strDate);
    }
    private chooseTimeDialogEventListener chooseTimeDialogEventListener;
    TimePicker timePicker;

    public ChooseTimeDialog(@NonNull  Context context) {
        super(context);
    }
    public ChooseTimeDialog(@NonNull  Context context, int themeId) {
        super(context,themeId);
    }
    public ChooseTimeDialog(@NonNull  Context context, chooseTimeDialogEventListener listener) {
        super(context);
        this.chooseTimeDialogEventListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_choose_dialog);
        Button bt_ok = findViewById(R.id.btn_time_dialog_cancel);
        Button bt_cancel = findViewById(R.id.btn_time_dialog_ok);
        timePicker = findViewById(R.id.time_choose_dialog_time_picker);
        bt_ok.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
//        int hour,minute;
        if(id == R.id.btn_time_dialog_ok){
            timePicker.setIs24HourView(true);//设置为24小时制
            int hour = timePicker.getCurrentHour();
            int minute = timePicker.getCurrentMinute();
            String strDate = hour+":"+minute+":00";
            chooseTimeDialogEventListener.chooseTimeDialogEvent(strDate);
        }
        dismiss();
    }
}
