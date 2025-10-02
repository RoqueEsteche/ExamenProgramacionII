package com.roque.examenprogramacionii.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.roque.examenprogramacionii.data.db.Estudiante;
import com.roque.examenprogramacionii.databinding.FragmentAddEditEstudianteBinding;

public class AddEditEstudianteFragment extends Fragment {

    private FragmentAddEditEstudianteBinding binding;
    private EstudianteViewModel estudianteViewModel;
    private Estudiante currentEstudiante;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddEditEstudianteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        estudianteViewModel = new ViewModelProvider(this).get(EstudianteViewModel.class);

        if (getArguments() != null && getArguments().getInt("estudiante_id", -1) != -1) {
            int estudianteId = getArguments().getInt("estudiante_id");
            
            estudianteViewModel.getEstudianteById(estudianteId).observe(getViewLifecycleOwner(), estudiante -> {
                if (estudiante != null) {
                    currentEstudiante = estudiante;
                    binding.editTextNombre.setText(estudiante.nombre);
                    binding.editTextApellido.setText(estudiante.apellido);
                    binding.editTextCedula.setText(estudiante.cedula);
                    // Cambiar título del botón si es una edición
                    binding.buttonSave.setText("Actualizar");
                }
            });
        }

        binding.buttonSave.setOnClickListener(v -> confirmAndSaveEstudiante());
    }

    private void confirmAndSaveEstudiante() {
        String nombre = binding.editTextNombre.getText().toString().trim();
        String apellido = binding.editTextApellido.getText().toString().trim();
        String cedula = binding.editTextCedula.getText().toString().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || cedula.isEmpty()) {
            Toast.makeText(getContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        String confirmMessage = currentEstudiante != null
            ? "¿Deseas guardar los cambios en este estudiante?"
            : "¿Deseas guardar este nuevo estudiante?";

        new AlertDialog.Builder(requireContext())
                .setTitle("Confirmar Guardado")
                .setMessage(confirmMessage)
                .setPositiveButton("Confirmar", (dialog, which) -> {
                    saveEstudiante(nombre, apellido, cedula);
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void saveEstudiante(String nombre, String apellido, String cedula) {
        if (currentEstudiante != null) { // Edición
            currentEstudiante.nombre = nombre;
            currentEstudiante.apellido = apellido;
            currentEstudiante.cedula = cedula;
            estudianteViewModel.update(currentEstudiante);
            Toast.makeText(getContext(), "Estudiante actualizado", Toast.LENGTH_SHORT).show();
        } else { // Creación
            Estudiante nuevoEstudiante = new Estudiante();
            nuevoEstudiante.nombre = nombre;
            nuevoEstudiante.apellido = apellido;
            nuevoEstudiante.cedula = cedula;
            estudianteViewModel.insert(nuevoEstudiante);
            Toast.makeText(getContext(), "Estudiante guardado", Toast.LENGTH_SHORT).show();
        }

        NavHostFragment.findNavController(this).popBackStack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
