package com.roque.examenprogramacionii.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.roque.examenprogramacionii.data.db.Estudiante;
import com.roque.examenprogramacionii.databinding.ItemEstudianteBinding;

public class EstudianteAdapter extends ListAdapter<Estudiante, EstudianteAdapter.EstudianteViewHolder> {

    private OnItemClickListener listener;
    private OnDeleteClickListener deleteListener;

    public EstudianteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Estudiante> DIFF_CALLBACK = new DiffUtil.ItemCallback<Estudiante>() {
        @Override
        public boolean areItemsTheSame(@NonNull Estudiante oldItem, @NonNull Estudiante newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Estudiante oldItem, @NonNull Estudiante newItem) {
            return oldItem.nombre.equals(newItem.nombre) &&
                    oldItem.apellido.equals(newItem.apellido) &&
                    oldItem.cedula.equals(newItem.cedula);
        }
    };

    @NonNull
    @Override
    public EstudianteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemEstudianteBinding binding = ItemEstudianteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new EstudianteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EstudianteViewHolder holder, int position) {
        Estudiante currentEstudiante = getItem(position);
        holder.bind(currentEstudiante);
    }

    public Estudiante getEstudianteAt(int position) {
        return getItem(position);
    }

    class EstudianteViewHolder extends RecyclerView.ViewHolder {
        private final ItemEstudianteBinding binding;

        public EstudianteViewHolder(ItemEstudianteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            // Click listener for editing
            binding.infoContainer.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });

            // Click listener for deleting
            binding.buttonDelete.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (deleteListener != null && position != RecyclerView.NO_POSITION) {
                    deleteListener.onDeleteClick(getItem(position));
                }
            });
        }

        public void bind(Estudiante estudiante) {
            binding.textViewNombreCompleto.setText(estudiante.nombre + " " + estudiante.apellido);
            binding.textViewCedula.setText("Cédula: " + estudiante.cedula);
        }
    }

    // Interfaces for click listeners
    public interface OnItemClickListener {
        void onItemClick(Estudiante estudiante);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(Estudiante estudiante);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteListener = listener;
    }
}
