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
public interface CalificacionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Calificacion calificacion);

    @Update
    void update(Calificacion calificacion);

    @Delete
    void delete(Calificacion calificacion);

    @Query("SELECT c.*, e.nombre as estudianteNombre, m.nombre as materiaNombre " +
           "FROM calificaciones c " +
           "JOIN estudiantes e ON c.estudianteId = e.id " +
           "JOIN materias m ON c.materiaId = m.id " +
           "ORDER BY c.id DESC")
    LiveData<List<CalificacionConDetalles>> getAllCalificacionesConDetalles();
}
