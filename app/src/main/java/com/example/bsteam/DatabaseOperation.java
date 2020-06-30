package com.example.bsteam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.method.MultiTapKeyListener;
import android.view.View;
import android.widget.Toast;

import com.example.bsteam.DatabaseHelper;
import com.example.bsteam.entity.Card;
import com.example.bsteam.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class DatabaseOperation {
    DatabaseHelper databaseHelper;
    private String table_name;
    private Context mContext;
    public DatabaseOperation(String tablename, Context context){
        this.table_name=tablename;
        mContext = context;
        databaseHelper = new DatabaseHelper(mContext);
    }
    public void addCard(Card card){
        if(checkId(card.getUserId(),"card_name",card.getCardId())){
            Toast.makeText(mContext,"该用户已存在该卡片", Toast.LENGTH_SHORT).show();
        }else{
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("card_id",card.getCardId());
            values.put("card_name",card.getCardName());
            values.put("task_id",card.getTaskId());
            values.put("user_id",card.getUserId());
            db.insert(table_name,null,values);
            db.close();
        }

    }
    public void addTask(Task task){
        if(checkId(task.getUserId(),"task_name",task.getTaskId())){
            Toast.makeText(mContext,"该用户已存在该任务", Toast.LENGTH_SHORT).show();
        }else{
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("task_id",task.getTaskId());
            values.put("task_name",task.getTaskName());
            values.put("start_date",String.valueOf(task.getStartDate()));
            values.put("end_date",String.valueOf(task.getEndDate()));
            values.put("user_id",task.getUserId());
            values.put("info",task.getInfo());
            values.put("task_state",task.getTaskState());
            db.insert(table_name,null,values);
            db.close();
        }
    }
    public void deleteCard(Card card){
        if(!checkId(card.getUserId(),"card_name",card.getCardId())){
            Toast.makeText(mContext,"该用户没有该卡片", Toast.LENGTH_SHORT).show();
        }else{
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            db.delete(table_name,"user_id = ? and card_id = ?",new String[]{card.getUserId(), String.valueOf(card.getCardId())});
            db.close();
        }

    }
    public void deleteTask(Task task){
//        if(!checkId(task.getUserId(),"task_name",task.getTaskId())){
//            Toast.makeText(mContext,"该用户没有该任务", Toast.LENGTH_SHORT).show();
//        }else{
            SQLiteDatabase db = databaseHelper.getWritableDatabase();//根据开始时间和结束时间来删除
            db.delete(table_name,"user_id = ? and task_name = ? and start_date = ? and end_date = ?",new String[]{task.getUserId(),task.getTaskName(), task.getStartDate(),task.getEndDate()});
            db.close();
//        }
    }
    public void updateCard(Card card){
        if(!checkId(card.getUserId(),"card_name",card.getCardId())){
            Toast.makeText(mContext,"该用户没有该卡片", Toast.LENGTH_SHORT).show();
        }else{
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("card_id",card.getCardId());
            values.put("card_name",card.getCardName());
            values.put("task_id",card.getTaskId());
            values.put("user_id",card.getUserId());
            db.update(table_name,values,"user_id = ? and card_id = ?",new String[]{card.getUserId(), String.valueOf(card.getCardId())});
            db.close();
        }

    }
    public void updateTask(Task task){
        if(!isHaveTask(task)){
//            Toast.makeText(mContext,"添加", Toast.LENGTH_SHORT).show();
            addTask(task);
        }else{
//            Toast.makeText(mContext,"修改", Toast.LENGTH_SHORT).show();
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("task_id",task.getTaskId());
            values.put("task_name",task.getTaskName());
            values.put("start_date",String.valueOf(task.getStartDate()));
            values.put("end_date",String.valueOf(task.getEndDate()));
            values.put("user_id",task.getUserId());
            values.put("info",task.getInfo());
            values.put("task_state",task.getTaskState());
            db.update(table_name,values,"user_id = ? and task_name = ?",new String[]{task.getUserId(), task.getTaskName(),});
            db.close();
        }
    }
    public Boolean isHaveTask(Task task){
        SQLiteDatabase sdb = databaseHelper.getReadableDatabase();
        String sql = "select * from "+table_name+" where user_id = ? and task_name = ?";
        Cursor cursor = sdb.rawQuery(sql,new String[]{task.getUserId(), task.getTaskName()});
        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

    public ArrayList<Task> initList(String userid){
        SQLiteDatabase sdb = databaseHelper.getReadableDatabase();
        String sql = "select * from "+table_name+" where user_id = ?";
        Cursor cursor = sdb.rawQuery(sql,new String[]{userid});
        ArrayList<Task> res = new ArrayList<>();
        int taskId;
        String taskName;
        String startDate;
        String endDate;
        String userId;
        String info;
        int taskState;
        Task task;
        if(cursor.moveToFirst()){
            do{
                 taskId = cursor.getInt(cursor.getColumnIndex("task_id"));
                 taskName = cursor.getString(cursor.getColumnIndex("task_name"));
                 startDate = cursor.getString(cursor.getColumnIndex("start_date"));
                 endDate = cursor.getString(cursor.getColumnIndex("end_date"));
                 userId = cursor.getString(cursor.getColumnIndex("user_id"));
                 info = cursor.getString(cursor.getColumnIndex("info"));
                 taskState = cursor.getInt(cursor.getColumnIndex("task_state"));
                 task = new Task(taskId,taskName,startDate,endDate,userId,info,taskState);
                res.add(task);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return res;
    }
    public boolean checkId(String userid,String itemIdName,int id){
        SQLiteDatabase sdb = databaseHelper.getReadableDatabase();
        String sql = "select * from "+table_name+" where user_id = ? and "+itemIdName+" = ?";
        Cursor cursor = sdb.rawQuery(sql,new String[]{userid, String.valueOf(id)});
        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }
}
