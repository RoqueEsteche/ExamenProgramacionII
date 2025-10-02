package com.roque.examenprogramacionii.ui;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.roque.examenprogramacionii.data.db.Estudiante;
import com.roque.examenprogramacionii.data.repository.EstudianteRepository;

import java.util.List;

public class EstudianteViewModel extends AndroidViewModel {

    private EstudianteRepository repository;
    private final LiveData<List<Estudiante>> allEstudiantes;

    public EstudianteViewModel(Application application) {
        super(application);
        repository = new EstudianteRepository(application);
        allEstudiantes = repository.getAllEstudiantes();
    }

    public LiveData<List<Estudiante>> getAllEstudiantes() {
        return allEstudiantes;
    }

    public LiveData<Estudiante> getEstudianteById(int id) {
        return repository.getEstudianteById(id);
    }

    public void insert(Estudiante estudiante) {
        repository.insert(estudiante);
    }

    public void update(Estudiante estudiante) {
        repository.update(estudiante);
    }

    public void delete(Estudiante estudiante) {
        repository.delete(estudiante);
    }
}
