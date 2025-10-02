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
public interface MateriaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Materia materia);

    @Update
    void update(Materia materia);

    @Delete
    void delete(Materia materia);

    @Query("SELECT * FROM materias ORDER BY curso, nombre ASC")
    LiveData<List<Materia>> getAllMaterias();
}
