package com.roque.examenprogramacionii.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.textfield.TextInputEditText;
import com.roque.examenprogramacionii.R;
import com.roque.examenprogramacionii.data.db.Estudiante;
import com.roque.examenprogramacionii.data.db.Inscripcion;
import com.roque.examenprogramacionii.data.db.InscripcionConDetalles;
import com.roque.examenprogramacionii.data.db.Materia;
import com.roque.examenprogramacionii.databinding.FragmentInscripcionesBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class InscripcionesFragment extends Fragment {

    private FragmentInscripcionesBinding binding;
    private InscripcionViewModel inscripcionViewModel;
    private List<Estudiante> listaEstudiantes = new ArrayList<>();
    private List<Materia> listaMaterias = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentInscripcionesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final InscripcionAdapter adapter = new InscripcionAdapter();
        binding.recyclerViewInscripciones.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewInscripciones.setAdapter(adapter);

        inscripcionViewModel = new ViewModelProvider(this).get(InscripcionViewModel.class);

        inscripcionViewModel.getAllInscripciones().observe(getViewLifecycleOwner(), adapter::submitList);
        inscripcionViewModel.getAllEstudiantes().observe(getViewLifecycleOwner(), estudiantes -> this.listaEstudiantes = estudiantes);
        inscripcionViewModel.getAllMaterias().observe(getViewLifecycleOwner(), materias -> this.listaMaterias = materias);

        binding.fabAddInscripcion.setOnClickListener(v -> showInscripcionDialog(null));

        adapter.setOnItemClickListener(this::showInscripcionDialog);

        adapter.setOnDeleteClickListener(inscripcionDetalles -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Confirmar Eliminación")
                    .setMessage("¿Estás seguro de que quieres eliminar esta inscripción? Esta acción no se puede deshacer.")
                    .setPositiveButton("Eliminar", (dialog, which) -> {
                        inscripcionViewModel.delete(inscripcionDetalles.inscripcion);
                        Toast.makeText(getContext(), "Inscripción eliminada", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });
    }

    private void showInscripcionDialog(@Nullable InscripcionConDetalles inscripcionConDetalles) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(inscripcionConDetalles == null ? "Nueva Inscripción" : "Editar Inscripción");

        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_edit_inscripcion, null);
        builder.setView(dialogView);

        Spinner spinnerEstudiantes = dialogView.findViewById(R.id.spinner_estudiantes);
        Spinner spinnerMaterias = dialogView.findViewById(R.id.spinner_materias);
        TextInputEditText editTextFecha = dialogView.findViewById(R.id.edit_text_fecha_inscripcion);

        ArrayAdapter<String> estudiantesAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, getEstudianteNombres());
        estudiantesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstudiantes.setAdapter(estudiantesAdapter);

        ArrayAdapter<String> materiasAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, getMateriaNombres());
        materiasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMaterias.setAdapter(materiasAdapter);

        if (inscripcionConDetalles != null) {
            editTextFecha.setText(inscripcionConDetalles.inscripcion.fechaInscripcion);
            spinnerEstudiantes.setSelection(getEstudiantePosition(inscripcionConDetalles.inscripcion.estudianteId));
            spinnerMaterias.setSelection(getMateriaPosition(inscripcionConDetalles.inscripcion.materiaId));
            spinnerEstudiantes.setEnabled(false);
            spinnerMaterias.setEnabled(false);
        } else {
            editTextFecha.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
        }

        builder.setPositiveButton("Guardar", null);
        builder.setNegativeButton("Cancelar", null);
        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> 
            handleSave(dialog, inscripcionConDetalles, spinnerEstudiantes, spinnerMaterias, editTextFecha)
        );
    }

    private void handleSave(DialogInterface mainDialog, @Nullable InscripcionConDetalles inscripcionConDetalles, Spinner spinnerEstudiantes, Spinner spinnerMaterias, TextInputEditText editTextFecha) {
        int estudiantePos = spinnerEstudiantes.getSelectedItemPosition();
        int materiaPos = spinnerMaterias.getSelectedItemPosition();
        String fecha = editTextFecha.getText().toString().trim();

        if (fecha.isEmpty() || estudiantePos < 0 || materiaPos < 0) {
            Toast.makeText(getContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        String confirmMessage = inscripcionConDetalles == null
            ? "¿Deseas registrar esta nueva inscripción?"
            : "¿Deseas guardar los cambios en esta inscripción?";

        new AlertDialog.Builder(requireContext())
                .setTitle("Confirmar Guardado")
                .setMessage(confirmMessage)
                .setPositiveButton("Confirmar", (confirmDialog, whichButton) -> {
                    int estudianteId = listaEstudiantes.get(estudiantePos).id;
                    int materiaId = listaMaterias.get(materiaPos).id;

                    if (inscripcionConDetalles == null) { // Creación
                        Inscripcion nuevaInscripcion = new Inscripcion();
                        nuevaInscripcion.estudianteId = estudianteId;
                        nuevaInscripcion.materiaId = materiaId;
                        nuevaInscripcion.fechaInscripcion = fecha;
                        inscripcionViewModel.insert(nuevaInscripcion);
                        Toast.makeText(getContext(), "Inscripción guardada", Toast.LENGTH_SHORT).show();
                    } else { // Edición
                        Inscripcion inscripcionAActualizar = inscripcionConDetalles.inscripcion;
                        inscripcionAActualizar.fechaInscripcion = fecha;
                        inscripcionViewModel.update(inscripcionAActualizar);
                        Toast.makeText(getContext(), "Inscripción actualizada", Toast.LENGTH_SHORT).show();
                    }
                    mainDialog.dismiss();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    // Métodos de ayuda para los spinners
    private List<String> getEstudianteNombres() {
        List<String> nombres = new ArrayList<>();
        for (Estudiante e : listaEstudiantes) {
            nombres.add(e.nombre + " " + e.apellido);
        }
        return nombres;
    }

    private List<String> getMateriaNombres() {
        List<String> nombres = new ArrayList<>();
        for (Materia m : listaMaterias) {
            nombres.add(m.nombre);
        }
        return nombres;
    }

    private int getEstudiantePosition(int estudianteId) {
        for (int i = 0; i < listaEstudiantes.size(); i++) {
            if (listaEstudiantes.get(i).id == estudianteId) {
                return i;
            }
        }
        return 0;
    }

    private int getMateriaPosition(int materiaId) {
        for (int i = 0; i < listaMaterias.size(); i++) {
            if (listaMaterias.get(i).id == materiaId) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
