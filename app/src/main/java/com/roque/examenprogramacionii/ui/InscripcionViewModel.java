package com.roque.examenprogramacionii.ui;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.roque.examenprogramacionii.data.db.Estudiante;
import com.roque.examenprogramacionii.data.db.Inscripcion;
import com.roque.examenprogramacionii.data.db.InscripcionConDetalles;
import com.roque.examenprogramacionii.data.db.Materia;
import com.roque.examenprogramacionii.data.repository.EstudianteRepository;
import com.roque.examenprogramacionii.data.repository.InscripcionRepository;
import com.roque.examenprogramacionii.data.repository.MateriaRepository;

import java.util.List;

public class InscripcionViewModel extends AndroidViewModel {

    private final InscripcionRepository inscripcionRepository;
    private final EstudianteRepository estudianteRepository;
    private final MateriaRepository materiaRepository;

    private final LiveData<List<InscripcionConDetalles>> allInscripciones;
    private final LiveData<List<Estudiante>> allEstudiantes;
    private final LiveData<List<Materia>> allMaterias;

    public InscripcionViewModel(Application application) {
        super(application);
        inscripcionRepository = new InscripcionRepository(application);
        estudianteRepository = new EstudianteRepository(application);
        materiaRepository = new MateriaRepository(application);

        allInscripciones = inscripcionRepository.getAllInscripciones();
        allEstudiantes = estudianteRepository.getAllEstudiantes();
        allMaterias = materiaRepository.getAllMaterias();
    }

    public LiveData<List<InscripcionConDetalles>> getAllInscripciones() {
        return allInscripciones;
    }

    public LiveData<List<Estudiante>> getAllEstudiantes() {
        return allEstudiantes;
    }

    public LiveData<List<Materia>> getAllMaterias() {
        return allMaterias;
    }

    public void insert(Inscripcion inscripcion) {
        inscripcionRepository.insert(inscripcion);
    }

    public void update(Inscripcion inscripcion) {
        inscripcionRepository.update(inscripcion);
    }

    public void delete(Inscripcion inscripcion) {
        inscripcionRepository.delete(inscripcion);
    }
}
