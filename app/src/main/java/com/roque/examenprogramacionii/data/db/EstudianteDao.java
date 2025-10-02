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
public interface EstudianteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Estudiante estudiante);

    @Update
    void update(Estudiante estudiante);

    @Delete
    void delete(Estudiante estudiante);

    @Query("SELECT * FROM estudiantes ORDER BY apellido, nombre ASC")
    LiveData<List<Estudiante>> getAllEstudiantes();

    @Query("SELECT * FROM estudiantes WHERE id = :estudianteId")
    LiveData<Estudiante> getEstudianteById(int estudianteId);
}
