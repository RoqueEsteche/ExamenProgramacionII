package com.roque.examenprogramacionii.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface InscripcionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Inscripcion inscripcion);

    @Update
    void update(Inscripcion inscripcion);

    @Delete
    void delete(Inscripcion inscripcion);

    @Query("SELECT i.*, e.nombre as estudianteNombre, m.nombre as materiaNombre " +
           "FROM inscripciones i " +
           "JOIN estudiantes e ON i.estudianteId = e.id " +
           "JOIN materias m ON i.materiaId = m.id " +
           "ORDER BY i.fechaInscripcion DESC")
    LiveData<List<InscripcionConDetalles>> getAllInscripcionesConDetalles();
}
