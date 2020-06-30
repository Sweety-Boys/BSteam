package com.example.bsteam;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String DBNAME="Bsteam.db";
    private static final String TABLENAME_USER="user";
    private static final String TABLENAME_TASK="task";
    private static final String TABLENAME_CARD="card";
    private static final int TESTVERSION=1;
    private Context mContext;
    public DatabaseHelper(@Nullable Context context) {
        super(context, DBNAME, null, TESTVERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table"+" "+TABLENAME_USER+"(id varchar,pwd varchar)";
        db.execSQL(sql);
        String sql1 = "create table"+" "+TABLENAME_TASK+"(task_id integer,task_name text,start_date text,end_date text,user_id text,info text,task_state integer)";
        Toast.makeText(mContext,"sql1:"+sql1,Toast.LENGTH_LONG).show();
        db.execSQL(sql1);
        String sql2 = "create table"+" "+TABLENAME_CARD+"(card_id integer,card_name varchar,task_id integer,user_id text)";
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion>oldVersion)
        {
            String sql1="drop table if exists "+TABLENAME_USER;
            db.execSQL(sql1);
            String sql2="drop table if exists "+TABLENAME_TASK;
            db.execSQL(sql2);
            String sql3="drop table if exists "+TABLENAME_CARD;
            db.execSQL(sql3);
            this.onCreate(db);
        }
    }

}