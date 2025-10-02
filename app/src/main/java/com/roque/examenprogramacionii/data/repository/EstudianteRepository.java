package com.roque.examenprogramacionii.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.roque.examenprogramacionii.data.db.AppDatabase;
import com.roque.examenprogramacionii.data.db.Estudiante;
import com.roque.examenprogramacionii.data.db.EstudianteDao;

import java.util.List;

public class EstudianteRepository {

    private EstudianteDao estudianteDao;
    private LiveData<List<Estudiante>> allEstudiantes;

    public EstudianteRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        estudianteDao = db.estudianteDao();
        allEstudiantes = estudianteDao.getAllEstudiantes();
    }

    public LiveData<List<Estudiante>> getAllEstudiantes() {
        return allEstudiantes;
    }

    public LiveData<Estudiante> getEstudianteById(int id) {
        return estudianteDao.getEstudianteById(id);
    }

    public void insert(Estudiante estudiante) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            estudianteDao.insert(estudiante);
        });
    }

    public void update(Estudiante estudiante) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            estudianteDao.update(estudiante);
        });
    }

    public void delete(Estudiante estudiante) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            estudianteDao.delete(estudiante);
        });
    }
}
