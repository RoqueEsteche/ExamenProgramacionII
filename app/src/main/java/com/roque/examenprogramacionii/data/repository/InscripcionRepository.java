package com.roque.examenprogramacionii.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.roque.examenprogramacionii.data.db.AppDatabase;
import com.roque.examenprogramacionii.data.db.Inscripcion;
import com.roque.examenprogramacionii.data.db.InscripcionConDetalles;
import com.roque.examenprogramacionii.data.db.InscripcionDao;

import java.util.List;

public class InscripcionRepository {

    private InscripcionDao inscripcionDao;
    private LiveData<List<InscripcionConDetalles>> allInscripciones;

    public InscripcionRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        inscripcionDao = db.inscripcionDao();
        allInscripciones = inscripcionDao.getAllInscripcionesConDetalles();
    }

    public LiveData<List<InscripcionConDetalles>> getAllInscripciones() {
        return allInscripciones;
    }

    public void insert(Inscripcion inscripcion) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            inscripcionDao.insert(inscripcion);
        });
    }

    public void update(Inscripcion inscripcion) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            inscripcionDao.update(inscripcion);
        });
    }

    public void delete(Inscripcion inscripcion) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            inscripcionDao.delete(inscripcion);
        });
    }
}
