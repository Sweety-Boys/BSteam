package com.example.bsteam.entity;

import java.io.Serializable;

public class Task implements Serializable {
    private int taskId;
    private String taskName;
    private String startDate;
    private String endDate;
    private String userId;
    private String info;
    private int taskState;
    public Task(int taskId, String taskName, String startDate, String endDate, String userId,String info,int taskState) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
        this.info = info;
        this.taskState=taskState;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getUserId() {
        return userId;
    }

    public String getInfo(){return info;}

    public int getTaskState(){return taskState;}

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setTaskState(int taskState) {
        this.taskState = taskState;
    }
    public void updateTaskState(int taskState,int taskId){
        this.taskState=taskState;
        this.taskId=taskId;
    }
}
