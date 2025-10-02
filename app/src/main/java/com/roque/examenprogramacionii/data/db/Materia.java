package com.roque.examenprogramacionii.data.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "materias")
public class Materia {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String nombre;
    public String curso;
}
