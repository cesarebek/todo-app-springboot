package com.cezarybek.todoApp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String description;

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
