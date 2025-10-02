package com.roque.examenprogramacionii.ui;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.roque.examenprogramacionii.data.db.Materia;
import com.roque.examenprogramacionii.data.repository.MateriaRepository;

import java.util.List;

public class MateriaViewModel extends AndroidViewModel {

    private MateriaRepository repository;
    private final LiveData<List<Materia>> allMaterias;

    public MateriaViewModel(Application application) {
        super(application);
        repository = new MateriaRepository(application);
        allMaterias = repository.getAllMaterias();
    }

    public LiveData<List<Materia>> getAllMaterias() {
        return allMaterias;
    }

    public void insert(Materia materia) {
        repository.insert(materia);
    }

    public void update(Materia materia) {
        repository.update(materia);
    }

    public void delete(Materia materia) {
        repository.delete(materia);
    }
}
