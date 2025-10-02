package com.roque.examenprogramacionii.data.db;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "calificaciones",
        foreignKeys = @ForeignKey(entity = Inscripcion.class,
                                   parentColumns = {"estudianteId", "materiaId"},
                                   childColumns = {"estudianteId", "materiaId"},
                                   onDelete = ForeignKey.CASCADE))
public class Calificacion {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int estudianteId;
    public int materiaId;

    public double nota;
    public String tipoPrueba; // "Parcial 1", "Final", etc.
}
