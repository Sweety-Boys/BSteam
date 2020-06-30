package com.example.bsteam.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bsteam.DatabaseHelper;
import com.example.bsteam.R;

public class EditPassWord extends Activity {

    private EditText oldPassword,etnewpass1,etnewpass2;
    private Button btn_sure;

    SQLiteOpenHelper helper;
    private String userId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);
        helper=new DatabaseHelper(this);
        Intent intent = getIntent();
        userId = intent.getStringExtra("user_id");

        oldPassword=(EditText)findViewById(R.id.edit_old_password);
        etnewpass1=(EditText)findViewById(R.id.edit_new_password_1);
        etnewpass2=(EditText)findViewById(R.id.edit_new_password_2);
        btn_sure=(Button)findViewById(R.id.edit_confirm);
        btn_sure.setOnClickListener(new sureListener());

    }

    class sureListener implements OnClickListener{

        @Override
        public void onClick(View v) {
//            // TODO Auto-generated method stub
            if (checkListener() == 1){
                    if (!etnewpass1.getText().toString().equals(etnewpass2.getText().toString()))
                    {
                        Toast.makeText(getApplicationContext(), "两次输入的新密码不相同！", Toast.LENGTH_SHORT).show();
                    }
                    else
                        {
                            SQLiteDatabase sdb = helper.getReadableDatabase();
                            String id = userId;
                            String sql = "update user set pwd ="+etnewpass1.getText().toString()+" where id ="+ id;
                            sdb.execSQL(sql);
                            Intent intent = new Intent(EditPassWord.this,FirstActivity.class);
                            intent.putExtra("user_id",userId);
                            startActivity(intent);
                        }
            }else{
                {Toast.makeText(getApplicationContext(), "输入的用户密码不正确！", Toast.LENGTH_SHORT).show();}
            }
        }
        public int checkListener(){
            SQLiteDatabase sdb = helper.getReadableDatabase();
            String id = userId;
            String password = oldPassword.getText().toString();
            String sql = "select * from user where id = ? and pwd = ?";
            Cursor cursor = sdb.rawQuery(sql,new String[]{id,password});
            if (cursor.getCount() > 0){
                return 1;
            }else{
                return 0;
            }
        }
    }
}