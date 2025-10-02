package com.roque.examenprogramacionii.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.roque.examenprogramacionii.data.db.CalificacionConDetalles;
import com.roque.examenprogramacionii.databinding.ItemCalificacionBinding;

public class CalificacionAdapter extends ListAdapter<CalificacionConDetalles, CalificacionAdapter.CalificacionViewHolder> {

    private OnItemClickListener listener;
    private OnDeleteClickListener deleteListener;

    public CalificacionAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<CalificacionConDetalles> DIFF_CALLBACK = new DiffUtil.ItemCallback<CalificacionConDetalles>() {
        @Override
        public boolean areItemsTheSame(@NonNull CalificacionConDetalles oldItem, @NonNull CalificacionConDetalles newItem) {
            return oldItem.calificacion.id == newItem.calificacion.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull CalificacionConDetalles oldItem, @NonNull CalificacionConDetalles newItem) {
            return oldItem.calificacion.nota == newItem.calificacion.nota &&
                   oldItem.calificacion.tipoPrueba.equals(newItem.calificacion.tipoPrueba);
        }
    };

    @NonNull
    @Override
    public CalificacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCalificacionBinding binding = ItemCalificacionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CalificacionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CalificacionViewHolder holder, int position) {
        CalificacionConDetalles currentCalificacion = getItem(position);
        holder.bind(currentCalificacion);
    }

    public CalificacionConDetalles getCalificacionAt(int position) {
        return getItem(position);
    }

    class CalificacionViewHolder extends RecyclerView.ViewHolder {
        private final ItemCalificacionBinding binding;

        public CalificacionViewHolder(ItemCalificacionBinding binding) {
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

        public void bind(CalificacionConDetalles calificacion) {
            String titulo = calificacion.estudianteNombre + " - " + calificacion.materiaNombre + " (" + calificacion.calificacion.tipoPrueba + ")";
            binding.textViewCalificacionTitulo.setText(titulo);
            binding.textViewCalificacionNota.setText("Nota: " + calificacion.calificacion.nota);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(CalificacionConDetalles calificacion);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(CalificacionConDetalles calificacion);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteListener = listener;
    }
}
