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
import com.roque.examenprogramacionii.data.db.Calificacion;
import com.roque.examenprogramacionii.data.db.CalificacionConDetalles;
import com.roque.examenprogramacionii.data.db.InscripcionConDetalles;
import com.roque.examenprogramacionii.databinding.FragmentCalificacionesBinding;

import java.util.ArrayList;
import java.util.List;

public class CalificacionesFragment extends Fragment {

    private FragmentCalificacionesBinding binding;
    private CalificacionViewModel calificacionViewModel;
    private List<InscripcionConDetalles> listaInscripciones = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCalificacionesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final CalificacionAdapter adapter = new CalificacionAdapter();
        binding.recyclerViewCalificaciones.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewCalificaciones.setAdapter(adapter);

        calificacionViewModel = new ViewModelProvider(this).get(CalificacionViewModel.class);

        calificacionViewModel.getAllCalificaciones().observe(getViewLifecycleOwner(), adapter::submitList);
        calificacionViewModel.getAllInscripciones().observe(getViewLifecycleOwner(), inscripciones -> this.listaInscripciones = inscripciones);

        binding.fabAddCalificacion.setOnClickListener(v -> showCalificacionDialog(null));

        // Listener para editar (clic en la info de la calificación)
        adapter.setOnItemClickListener(this::showCalificacionDialog);

        // Listener para eliminar (clic en el botón de la papelera)
        adapter.setOnDeleteClickListener(calificacionDetalles -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Confirmar Eliminación")
                    .setMessage("¿Estás seguro de que quieres eliminar esta calificación? Esta acción no se puede deshacer.")
                    .setPositiveButton("Eliminar", (dialog, which) -> {
                        calificacionViewModel.delete(calificacionDetalles.calificacion);
                        Toast.makeText(getContext(), "Calificación eliminada", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });
    }

    private void showCalificacionDialog(@Nullable CalificacionConDetalles calificacionConDetalles) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(calificacionConDetalles == null ? "Nueva Calificación" : "Editar Calificación");

        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_edit_calificacion, null);
        builder.setView(dialogView);

        Spinner spinnerInscripciones = dialogView.findViewById(R.id.spinner_inscripciones);
        TextInputEditText editTextTipoPrueba = dialogView.findViewById(R.id.edit_text_tipo_prueba);
        TextInputEditText editTextNota = dialogView.findViewById(R.id.edit_text_nota);

        ArrayAdapter<String> inscripcionesAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, getInscripcionNombres());
        inscripcionesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerInscripciones.setAdapter(inscripcionesAdapter);

        if (calificacionConDetalles != null) {
            editTextTipoPrueba.setText(calificacionConDetalles.calificacion.tipoPrueba);
            editTextNota.setText(String.valueOf(calificacionConDetalles.calificacion.nota));
            spinnerInscripciones.setSelection(getInscripcionPosition(calificacionConDetalles.calificacion.estudianteId, calificacionConDetalles.calificacion.materiaId));
            spinnerInscripciones.setEnabled(false);
        }

        builder.setPositiveButton("Guardar", null);
        builder.setNegativeButton("Cancelar", null);
        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> 
            handleSave(dialog, calificacionConDetalles, spinnerInscripciones, editTextTipoPrueba, editTextNota)
        );
    }
    
    private void handleSave(DialogInterface mainDialog, @Nullable CalificacionConDetalles calificacionConDetalles, Spinner spinnerInscripciones, TextInputEditText editTextTipoPrueba, TextInputEditText editTextNota) {
        int inscripcionPos = spinnerInscripciones.getSelectedItemPosition();
        String tipoPrueba = editTextTipoPrueba.getText().toString().trim();
        String notaStr = editTextNota.getText().toString().trim();

        if (tipoPrueba.isEmpty() || notaStr.isEmpty() || inscripcionPos < 0) {
            Toast.makeText(getContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        double nota;
        try {
            nota = Double.parseDouble(notaStr);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "La nota debe ser un número válido", Toast.LENGTH_SHORT).show();
            return;
        }

        String confirmMessage = calificacionConDetalles == null 
            ? "¿Deseas guardar esta nueva calificación?"
            : "¿Deseas guardar los cambios en esta calificación?";

        new AlertDialog.Builder(requireContext())
                .setTitle("Confirmar Guardado")
                .setMessage(confirmMessage)
                .setPositiveButton("Confirmar", (confirmDialog, whichButton) -> {
                    InscripcionConDetalles selectedInscripcion = listaInscripciones.get(inscripcionPos);

                    if (calificacionConDetalles == null) { // Creación
                        Calificacion nuevaCalificacion = new Calificacion();
                        nuevaCalificacion.estudianteId = selectedInscripcion.inscripcion.estudianteId;
                        nuevaCalificacion.materiaId = selectedInscripcion.inscripcion.materiaId;
                        nuevaCalificacion.tipoPrueba = tipoPrueba;
                        nuevaCalificacion.nota = nota;
                        calificacionViewModel.insert(nuevaCalificacion);
                        Toast.makeText(getContext(), "Calificación guardada", Toast.LENGTH_SHORT).show();
                    } else { // Edición
                        Calificacion calificacionAActualizar = calificacionConDetalles.calificacion;
                        calificacionAActualizar.tipoPrueba = tipoPrueba;
                        calificacionAActualizar.nota = nota;
                        calificacionViewModel.update(calificacionAActualizar);
                        Toast.makeText(getContext(), "Calificación actualizada", Toast.LENGTH_SHORT).show();
                    }
                    mainDialog.dismiss();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    // Métodos de ayuda para el spinner
    private List<String> getInscripcionNombres() {
        List<String> nombres = new ArrayList<>();
        for (InscripcionConDetalles i : listaInscripciones) {
            nombres.add(i.estudianteNombre + " - " + i.materiaNombre);
        }
        return nombres;
    }

    private int getInscripcionPosition(int estudianteId, int materiaId) {
        for (int i = 0; i < listaInscripciones.size(); i++) {
            if (listaInscripciones.get(i).inscripcion.estudianteId == estudianteId && 
                listaInscripciones.get(i).inscripcion.materiaId == materiaId) {
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
