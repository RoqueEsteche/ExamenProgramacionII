package com.roque.examenprogramacionii.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.roque.examenprogramacionii.data.db.InscripcionConDetalles;
import com.roque.examenprogramacionii.databinding.ItemInscripcionBinding;

public class InscripcionAdapter extends ListAdapter<InscripcionConDetalles, InscripcionAdapter.InscripcionViewHolder> {

    private OnItemClickListener listener;
    private OnDeleteClickListener deleteListener;

    public InscripcionAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<InscripcionConDetalles> DIFF_CALLBACK = new DiffUtil.ItemCallback<InscripcionConDetalles>() {
        @Override
        public boolean areItemsTheSame(@NonNull InscripcionConDetalles oldItem, @NonNull InscripcionConDetalles newItem) {
            return oldItem.inscripcion.estudianteId == newItem.inscripcion.estudianteId &&
                   oldItem.inscripcion.materiaId == newItem.inscripcion.materiaId;
        }

        @Override
        public boolean areContentsTheSame(@NonNull InscripcionConDetalles oldItem, @NonNull InscripcionConDetalles newItem) {
            return oldItem.inscripcion.fechaInscripcion.equals(newItem.inscripcion.fechaInscripcion);
        }
    };

    @NonNull
    @Override
    public InscripcionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemInscripcionBinding binding = ItemInscripcionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new InscripcionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull InscripcionViewHolder holder, int position) {
        InscripcionConDetalles currentInscripcion = getItem(position);
        holder.bind(currentInscripcion);
    }

    public InscripcionConDetalles getInscripcionAt(int position) {
        return getItem(position);
    }

    class InscripcionViewHolder extends RecyclerView.ViewHolder {
        private final ItemInscripcionBinding binding;

        public InscripcionViewHolder(ItemInscripcionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.infoContainer.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });

            binding.buttonDelete.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (deleteListener != null && position != RecyclerView.NO_POSITION) {
                    deleteListener.onDeleteClick(getItem(position));
                }
            });
        }

        public void bind(InscripcionConDetalles inscripcion) {
            String titulo = inscripcion.estudianteNombre + " - " + inscripcion.materiaNombre;
            binding.textViewEstudianteMateria.setText(titulo);
            binding.textViewFechaInscripcion.setText("Inscrito el: " + inscripcion.inscripcion.fechaInscripcion);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(InscripcionConDetalles inscripcion);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(InscripcionConDetalles inscripcion);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteListener = listener;
    }
}
