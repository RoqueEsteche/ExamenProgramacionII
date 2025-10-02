package com.roque.examenprogramacionii.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.roque.examenprogramacionii.data.db.AppDatabase;
import com.roque.examenprogramacionii.data.db.Calificacion;
import com.roque.examenprogramacionii.data.db.CalificacionConDetalles;
import com.roque.examenprogramacionii.data.db.CalificacionDao;

import java.util.List;

public class CalificacionRepository {

    private CalificacionDao calificacionDao;
    private LiveData<List<CalificacionConDetalles>> allCalificaciones;

    public CalificacionRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        calificacionDao = db.calificacionDao();
        allCalificaciones = calificacionDao.getAllCalificacionesConDetalles();
    }

    public LiveData<List<CalificacionConDetalles>> getAllCalificaciones() {
        return allCalificaciones;
    }

    public void insert(Calificacion calificacion) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            calificacionDao.insert(calificacion);
        });
    }

    public void update(Calificacion calificacion) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            calificacionDao.update(calificacion);
        });
    }

    public void delete(Calificacion calificacion) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            calificacionDao.delete(calificacion);
        });
    }
}
