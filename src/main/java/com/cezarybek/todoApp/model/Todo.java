package com.cezarybek.todoApp.model;

import javax.persistence.*;

@Entity
@Table(name="todos")
public class Todo {
    @Id
    @GeneratedValue
    @Column(name="id")
    private Integer id;

    @Column(name="userId")
    private Integer userId;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="description")
    private String description;

    @Column(name="isCompleted")
    private Boolean isCompleted;

    public Todo(Integer id, Integer userId, String title, String description, Boolean isCompleted) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
    }

    public Todo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}
