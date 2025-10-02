package com.roque.examenprogramacionii.ui;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.roque.examenprogramacionii.data.db.CalificacionConDetalles;
import com.roque.examenprogramacionii.data.db.Estudiante;
import com.roque.examenprogramacionii.data.db.InscripcionConDetalles;
import com.roque.examenprogramacionii.data.db.Materia;
import com.roque.examenprogramacionii.data.repository.CalificacionRepository;
import com.roque.examenprogramacionii.data.repository.EstudianteRepository;
import com.roque.examenprogramacionii.data.repository.InscripcionRepository;
import com.roque.examenprogramacionii.data.repository.MateriaRepository;

import java.util.List;

public class PortalViewModel extends AndroidViewModel {

    private final LiveData<List<Estudiante>> allEstudiantes;
    private final LiveData<List<Materia>> allMaterias;
    private final LiveData<List<InscripcionConDetalles>> allInscripciones;
    private final LiveData<List<CalificacionConDetalles>> allCalificaciones;

    public PortalViewModel(Application application) {
        super(application);
        allEstudiantes = new EstudianteRepository(application).getAllEstudiantes();
        allMaterias = new MateriaRepository(application).getAllMaterias();
        allInscripciones = new InscripcionRepository(application).getAllInscripciones();
        allCalificaciones = new CalificacionRepository(application).getAllCalificaciones();
    }

    public LiveData<List<Estudiante>> getAllEstudiantes() {
        return allEstudiantes;
    }

    public LiveData<List<Materia>> getAllMaterias() {
        return allMaterias;
    }

    public LiveData<List<InscripcionConDetalles>> getAllInscripciones() {
        return allInscripciones;
    }

    public LiveData<List<CalificacionConDetalles>> getAllCalificaciones() {
        return allCalificaciones;
    }
}
