package com.roque.examenprogramacionii.data.db;

import androidx.room.Embedded;

// POJO para combinar los datos de una Inscripción con los nombres del Estudiante y la Materia
public class InscripcionConDetalles {

    @Embedded
    public Inscripcion inscripcion;

    public String estudianteNombre;
    public String materiaNombre;
}
