package org.example.task3.models;


import org.example.task3.Column;
import org.example.task3.Table;

import java.util.UUID;

@Table(name = "users")
public class Entity {

    @Column(name = "id", primaryKey = true)
    private UUID id;

}
