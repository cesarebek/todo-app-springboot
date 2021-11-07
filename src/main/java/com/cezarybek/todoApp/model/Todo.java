package com.cezarybek.todoApp.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
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

}
