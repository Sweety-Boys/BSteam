package com.example.bsteam.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.bsteam.R;

public class ChooseDateDialog extends Dialog implements View.OnClickListener{

    public interface chooseDateDialogEventListener{
        public void chooseDateDialogEvent(String strDate);
    }
    private chooseDateDialogEventListener chooseDateDialogEventListener;
    DatePicker datePicker;

    public ChooseDateDialog(@NonNull  Context context) {
        super(context);
    }
    public ChooseDateDialog(@NonNull  Context context, int themeId) {
        super(context,themeId);
    }
    public ChooseDateDialog(@NonNull  Context context,chooseDateDialogEventListener listener) {
        super(context);
        this.chooseDateDialogEventListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_choose_dialog);
        Button bt_ok = findViewById(R.id.btn_date_dialog_cancel);
        Button bt_cancel = findViewById(R.id.btn_date_dialog_ok);
        datePicker = findViewById(R.id.date_choose_dialog_date_picker);
        bt_ok.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btn_date_dialog_ok){
            int year = datePicker.getYear();
            int month = datePicker.getMonth();
            int day = datePicker.getDayOfMonth();
//            Date date = DateUtil.ymdToDate(year, month, day);//转换为date格式
            String strDate = year+"-"+month+"-"+day;
            chooseDateDialogEventListener.chooseDateDialogEvent(strDate);
        }
        dismiss();
    }

    public interface IOnNewTaskListener{
    }
}
