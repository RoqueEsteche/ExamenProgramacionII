package com.roque.examenprogramacionii.ui;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.roque.examenprogramacionii.data.db.Calificacion;
import com.roque.examenprogramacionii.data.db.CalificacionConDetalles;
import com.roque.examenprogramacionii.data.db.InscripcionConDetalles;
import com.roque.examenprogramacionii.data.repository.CalificacionRepository;
import com.roque.examenprogramacionii.data.repository.InscripcionRepository;

import java.util.List;

public class CalificacionViewModel extends AndroidViewModel {

    private final CalificacionRepository calificacionRepository;
    private final InscripcionRepository inscripcionRepository;

    private final LiveData<List<CalificacionConDetalles>> allCalificaciones;
    private final LiveData<List<InscripcionConDetalles>> allInscripciones;

    public CalificacionViewModel(Application application) {
        super(application);
        calificacionRepository = new CalificacionRepository(application);
        inscripcionRepository = new InscripcionRepository(application);

        allCalificaciones = calificacionRepository.getAllCalificaciones();
        allInscripciones = inscripcionRepository.getAllInscripciones();
    }

    public LiveData<List<CalificacionConDetalles>> getAllCalificaciones() {
        return allCalificaciones;
    }

    public LiveData<List<InscripcionConDetalles>> getAllInscripciones() {
        return allInscripciones;
    }

    public void insert(Calificacion calificacion) {
        calificacionRepository.insert(calificacion);
    }

    public void update(Calificacion calificacion) {
        calificacionRepository.update(calificacion);
    }

    public void delete(Calificacion calificacion) {
        calificacionRepository.delete(calificacion);
    }
}
