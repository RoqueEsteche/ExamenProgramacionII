package com.roque.examenprogramacionii.data.db;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "inscripciones",
        primaryKeys = {"estudianteId", "materiaId"},
        foreignKeys = {
                @ForeignKey(entity = Estudiante.class,
                        parentColumns = "id",
                        childColumns = "estudianteId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Materia.class,
                        parentColumns = "id",
                        childColumns = "materiaId",
                        onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index("estudianteId"), @Index("materiaId")})
public class Inscripcion {
    public int estudianteId;
    public int materiaId;
    public String fechaInscripcion; // Ejemplo: "2024-05-21"
}
