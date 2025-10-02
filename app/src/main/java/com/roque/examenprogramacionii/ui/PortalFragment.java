package com.roque.examenprogramacionii.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.roque.examenprogramacionii.R;
import com.roque.examenprogramacionii.databinding.FragmentPortalBinding;

public class PortalFragment extends Fragment {

    private FragmentPortalBinding binding;
    private PortalViewModel portalViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPortalBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        portalViewModel = new ViewModelProvider(this).get(PortalViewModel.class);

        // Observar los datos y actualizar los contadores
        portalViewModel.getAllEstudiantes().observe(getViewLifecycleOwner(), estudiantes -> {
            binding.textViewEstudiantesCount.setText("Total: " + (estudiantes != null ? estudiantes.size() : 0));
        });

        portalViewModel.getAllMaterias().observe(getViewLifecycleOwner(), materias -> {
            binding.textViewMateriasCount.setText("Total: " + (materias != null ? materias.size() : 0));
        });

        portalViewModel.getAllInscripciones().observe(getViewLifecycleOwner(), inscripciones -> {
            binding.textViewInscripcionesCount.setText("Total: " + (inscripciones != null ? inscripciones.size() : 0));
        });

        portalViewModel.getAllCalificaciones().observe(getViewLifecycleOwner(), calificaciones -> {
            binding.textViewCalificacionesCount.setText("Total: " + (calificaciones != null ? calificaciones.size() : 0));
        });

        // --- SOLUCIÓN: Navegar simulando un clic en la BottomNavigationView ---
        BottomNavigationView bottomNav = requireActivity().findViewById(R.id.bottom_navigation);
        if (bottomNav != null) {
            binding.cardEstudiantes.setOnClickListener(v -> bottomNav.setSelectedItemId(R.id.estudiantesFragment));
            binding.cardMaterias.setOnClickListener(v -> bottomNav.setSelectedItemId(R.id.materiasFragment));
            binding.cardInscripciones.setOnClickListener(v -> bottomNav.setSelectedItemId(R.id.inscripcionesFragment));
            binding.cardCalificaciones.setOnClickListener(v -> bottomNav.setSelectedItemId(R.id.calificacionesFragment));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
