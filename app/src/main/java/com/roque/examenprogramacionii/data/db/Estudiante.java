package com.roque.examenprogramacionii.data.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "estudiantes")
public class Estudiante {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String nombre;
    public String apellido;
    public String cedula;
}
