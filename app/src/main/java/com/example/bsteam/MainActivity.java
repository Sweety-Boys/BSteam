package com.example.bsteam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.example.bsteam.activity.FirstActivity;
import com.example.bsteam.util.ExitApplication;

import java.util.prefs.PreferenceChangeEvent;

public class MainActivity extends AppCompatActivity {
    private EditText et_id,et_pwd;
    private CheckBox rememberPass;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExitApplication.getInstance().addActivity(this);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        databaseHelper = new DatabaseHelper(this);
        Button btn_signin = (Button) findViewById(R.id.btn_signin);
        Button btn_reg = (Button) findViewById(R.id.btn_reg);
        et_id=(EditText) findViewById(R.id.text_userid);
        et_pwd=(EditText) findViewById(R.id.text_userpwd);
        rememberPass = (CheckBox)findViewById(R.id.remember_pass);
        boolean isRemember = pref.getBoolean("remember_password",false);
        if(isRemember){
            //如果复选框为选中，则读取Pre中的账号和密码
            String accountId = pref.getString("accountId","");
            String passWord = pref.getString("passWord","");
            et_id.setText(accountId);
            et_pwd.setText(passWord);
            rememberPass.setChecked(true);
        }
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_id.getText().toString().equals("") || et_pwd.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"账号和密码均不能为空", Toast.LENGTH_SHORT).show();
                }else{
                    String id = et_id.getText().toString();
                    String pwd = et_pwd.getText().toString();
                    editor = pref.edit();
                    if(rememberPass.isChecked()){ //检查复选框是否被选中
                        editor.putBoolean("remember_password",true);
                        editor.putString("accountId",id);
                        editor.putString("passWord",pwd);
                    }else {
                        editor.clear();
                        ;                    }
                    editor.apply();
                    SQLiteDatabase db = databaseHelper.getReadableDatabase();
                    try{
                        String sql = "select * from user where id =? and pwd=?";
                        Cursor cursor = db.rawQuery(sql,new String[]{id,pwd});
                        if(cursor.getCount()>0){
                            Intent intent = new Intent(MainActivity.this, FirstActivity.class);
                            intent.putExtra("user_id",id);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(), "该账号未注册，请重新输入！",Toast.LENGTH_SHORT).show();
                        }
                    }catch (SQLiteException e){
                        Toast.makeText(getApplicationContext(), "亲，请注册！",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}