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
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.roque.examenprogramacionii.R;
import com.roque.examenprogramacionii.data.db.Estudiante;
import com.roque.examenprogramacionii.databinding.FragmentEstudiantesBinding;

public class EstudiantesFragment extends Fragment {

    private FragmentEstudiantesBinding binding;
    private EstudianteViewModel estudianteViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEstudiantesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EstudianteAdapter adapter = new EstudianteAdapter();
        binding.recyclerViewEstudiantes.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewEstudiantes.setAdapter(adapter);

        estudianteViewModel = new ViewModelProvider(this).get(EstudianteViewModel.class);
        estudianteViewModel.getAllEstudiantes().observe(getViewLifecycleOwner(), adapter::submitList);

        final NavController navController = NavHostFragment.findNavController(this);

        binding.fabAddEstudiante.setOnClickListener(v -> {
            if (navController.getCurrentDestination() != null && navController.getCurrentDestination().getId() == R.id.estudiantesFragment) {
                navController.navigate(R.id.action_estudiantesFragment_to_addEditEstudianteFragment);
            }
        });

        // Listener para editar (al hacer clic en la info del estudiante)
        adapter.setOnItemClickListener(estudiante -> {
            Bundle args = new Bundle();
            args.putInt("estudiante_id", estudiante.id);
            if (navController.getCurrentDestination() != null && navController.getCurrentDestination().getId() == R.id.estudiantesFragment) {
                navController.navigate(R.id.action_estudiantesFragment_to_addEditEstudianteFragment, args);
            }
        });

        // Listener para eliminar (al hacer clic en el botón de la papelera)
        adapter.setOnDeleteClickListener(estudiante -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Confirmar Eliminación")
                    .setMessage("¿Estás seguro de que quieres eliminar a " + estudiante.nombre + " " + estudiante.apellido + "? Esta acción no se puede deshacer.")
                    .setPositiveButton("Eliminar", (dialog, which) -> {
                        estudianteViewModel.delete(estudiante);
                        Toast.makeText(getContext(), "Estudiante eliminado", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
