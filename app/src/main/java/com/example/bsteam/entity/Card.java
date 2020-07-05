package com.example.bsteam.entity;

public class Card {
    private int cardId;
    private String cardName;
    private int TaskId;//任务id
    private String userId;//用户id

    public Card(String cardName,int cardId) {
        this.cardId = cardId;
        this.cardName = cardName;
    }

    public Card(int cardId, String cardName, int taskId, String userId) {
        this.cardId = cardId;
        this.cardName = cardName;
        TaskId = taskId;
        this.userId = userId;
    }

    public int getCardId() {
        return cardId;
    }

    public String getCardName() {
        return cardName;
    }

    public int getTaskId() {
        return TaskId;
    }

    public String getUserId() {
        return userId;
    }
}
