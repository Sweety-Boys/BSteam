package com.example.bsteam;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bsteam.util.ExitApplication;

import java.security.DomainCombiner;

public class RegisterActivity extends AppCompatActivity {
    private EditText etid,etpwd1,etpwd2;
    private Button btn_confirm;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ExitApplication.getInstance().addActivity(this);
        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        etid = (EditText) findViewById(R.id.account);
        etpwd1 = findViewById(R.id.password_1);
        etpwd2 = (EditText) findViewById(R.id.password_2);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new confirmListener());
    }
    class confirmListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            try {
                if(etpwd1.getText().toString().equals(etpwd2.getText().toString())){
                    if(etid.getText().toString().equals("") || etpwd1.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"账号和密码均不能为空", Toast.LENGTH_SHORT).show();
                    }else{
                        if(checkId()){
                            Toast.makeText(getApplicationContext(),"已存在该账号", Toast.LENGTH_SHORT).show();
                        }else{
                            SQLiteDatabase sdb = databaseHelper.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put("id",etid.getText().toString());
                            values.put("pwd",etpwd1.getText().toString());
                            sdb.insert("user",null,values);
                            Toast.makeText(getApplicationContext(),"注册成功", Toast.LENGTH_SHORT).show();
                            //未绑定数据（传递数据到Mainactivity）
                            sdb.close();
                            Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"两次输入密码不一致", Toast.LENGTH_SHORT).show();
                }
            }catch (SQLException e){
                Toast.makeText(getApplicationContext(),"注册失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public boolean checkId(){
        SQLiteDatabase sdb = databaseHelper.getReadableDatabase();
        String sql = "select * from user where id = ?";
        String id = etid.getText().toString();
        Cursor cursor = sdb.rawQuery(sql,new String[]{id});
        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

}