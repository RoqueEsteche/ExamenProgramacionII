package com.roque.examenprogramacionii.ui;

import android.content.DialogInterface;
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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.textfield.TextInputEditText;
import com.roque.examenprogramacionii.R;
import com.roque.examenprogramacionii.data.db.Materia;
import com.roque.examenprogramacionii.databinding.FragmentMateriasBinding;

public class MateriasFragment extends Fragment {

    private FragmentMateriasBinding binding;
    private MateriaViewModel materiaViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMateriasBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final MateriaAdapter adapter = new MateriaAdapter();
        binding.recyclerViewMaterias.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewMaterias.setAdapter(adapter);

        materiaViewModel = new ViewModelProvider(this).get(MateriaViewModel.class);
        materiaViewModel.getAllMaterias().observe(getViewLifecycleOwner(), adapter::submitList);

        binding.fabAddMateria.setOnClickListener(v -> showMateriaDialog(null));

        // Listener para editar (clic en la info de la materia)
        adapter.setOnItemClickListener(this::showMateriaDialog);

        // Listener para eliminar (clic en el botón de la papelera)
        adapter.setOnDeleteClickListener(materia -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Confirmar Eliminación")
                    .setMessage("¿Estás seguro de que quieres eliminar la materia '" + materia.nombre + "'? Esta acción no se puede deshacer.")
                    .setPositiveButton("Eliminar", (dialog, which) -> {
                        materiaViewModel.delete(materia);
                        Toast.makeText(getContext(), "Materia eliminada", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });
    }

    private void showMateriaDialog(@Nullable Materia materia) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(materia == null ? "Añadir Nueva Materia" : "Editar Materia");

        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_materia, null);
        builder.setView(dialogView);

        final TextInputEditText editTextNombre = dialogView.findViewById(R.id.edit_text_dialog_nombre_materia);
        final TextInputEditText editTextCurso = dialogView.findViewById(R.id.edit_text_dialog_curso_materia);

        if (materia != null) {
            editTextNombre.setText(materia.nombre);
            editTextCurso.setText(materia.curso);
        }

        builder.setPositiveButton("Guardar", null);
        builder.setNegativeButton("Cancelar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
        
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> 
            handleSave(dialog, materia, editTextNombre, editTextCurso)
        );
    }

    private void handleSave(DialogInterface mainDialog, @Nullable Materia materia, TextInputEditText editTextNombre, TextInputEditText editTextCurso) {
        String nombre = editTextNombre.getText().toString().trim();
        String curso = editTextCurso.getText().toString().trim();

        if (nombre.isEmpty() || curso.isEmpty()) {
            Toast.makeText(getContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        String confirmMessage = materia == null
            ? "¿Deseas guardar esta nueva materia?"
            : "¿Deseas guardar los cambios en esta materia?";

        new AlertDialog.Builder(requireContext())
                .setTitle("Confirmar Guardado")
                .setMessage(confirmMessage)
                .setPositiveButton("Confirmar", (confirmDialog, whichButton) -> {
                    if (materia == null) { // Añadir nueva materia
                        Materia nuevaMateria = new Materia();
                        nuevaMateria.nombre = nombre;
                        nuevaMateria.curso = curso;
                        materiaViewModel.insert(nuevaMateria);
                        Toast.makeText(getContext(), "Materia guardada", Toast.LENGTH_SHORT).show();
                    } else { // Actualizar materia existente
                        materia.nombre = nombre;
                        materia.curso = curso;
                        materiaViewModel.update(materia);
                        Toast.makeText(getContext(), "Materia actualizada", Toast.LENGTH_SHORT).show();
                    }
                    mainDialog.dismiss();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
