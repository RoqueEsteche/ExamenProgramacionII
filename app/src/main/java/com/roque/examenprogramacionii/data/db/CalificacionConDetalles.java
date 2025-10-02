package com.roque.examenprogramacionii.data.db;

import androidx.room.Embedded;

// POJO para combinar los datos de una Calificación con los nombres del Estudiante y la Materia
public class CalificacionConDetalles {

    @Embedded
    public Calificacion calificacion;

    public String estudianteNombre;
    public String materiaNombre;
}
