package com.roque.examenprogramacionii.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.roque.examenprogramacionii.data.db.AppDatabase;
import com.roque.examenprogramacionii.data.db.Materia;
import com.roque.examenprogramacionii.data.db.MateriaDao;

import java.util.List;

public class MateriaRepository {

    private MateriaDao materiaDao;
    private LiveData<List<Materia>> allMaterias;

    public MateriaRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        materiaDao = db.materiaDao();
        allMaterias = materiaDao.getAllMaterias();
    }

    public LiveData<List<Materia>> getAllMaterias() {
        return allMaterias;
    }

    public void insert(Materia materia) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            materiaDao.insert(materia);
        });
    }

    public void update(Materia materia) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            materiaDao.update(materia);
        });
    }

    public void delete(Materia materia) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            materiaDao.delete(materia);
        });
    }
}
