package com.cezarybek.todoApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    private String title;

    private String description;

    @Column(name = "is_completed")
    private Boolean isCompleted;

    public Todo(Integer userId, String title, String description, Boolean isCompleted) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
    }
}
